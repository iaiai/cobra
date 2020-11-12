package com.iaiai.cobra.admin.web.controller;

import com.iaiai.cobra.admin.core.Attribute;
import com.iaiai.cobra.admin.core.BaseController;
import com.iaiai.cobra.admin.core.util.IpUtil;
import com.iaiai.cobra.admin.redis.RedisUserCache;
import com.iaiai.cobra.admin.core.util.captcha.GifCaptcha;
import com.iaiai.cobra.admin.system.controller.vo.OnlineVo;
import com.iaiai.cobra.admin.system.service.ConfigService;
import com.iaiai.cobra.admin.system.service.MenuService;
import com.iaiai.cobra.admin.system.service.RoleService;
import com.iaiai.cobra.admin.system.service.UserService;
import com.iaiai.cobra.admin.web.controller.params.LoginParams;
import com.iaiai.cobra.common.util.CheckPassword;
import com.iaiai.cobra.common.util.ClientUtil;
import com.iaiai.cobra.common.util.JsonUtil;
import com.iaiai.cobra.common.util.StringUtil;
import com.iaiai.cobra.common.vo.ResultVo;
import com.iaiai.cobra.common.vo.constant.YesOrNo;
import com.iaiai.cobra.repository.beans.Config;
import com.iaiai.cobra.repository.beans.Menu;
import com.iaiai.cobra.repository.beans.Role;
import com.iaiai.cobra.repository.beans.User;
import com.iaiai.cobra.repository.constants.ConfigKey;
import com.iaiai.cobra.repository.constants.MenuShow;
import com.iaiai.cobra.repository.constants.MenuType;
import com.iaiai.cobra.repository.constants.UserStatus;
import com.iaiai.cobra.repository.pojo.MenuPojo;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * Package: com.iaiai.cobra.admin.web.controller
 * Author: iaiai
 * Create Time: 2019/12/3 5:22 下午
 * QQ: 176291935
 * Url: http://iaiai.iteye.com
 * Email: 176291935@qq.com
 * Description:
 */
@RestController
@RequestMapping
public class LoginController extends BaseController {

    public static final long timeout = 1 * 60 * 60 * 24 * 30;  //秒，过时1月

    @Autowired
    protected UserService userService;

    @Autowired
    protected RoleService roleService;

    @Autowired
    protected MenuService menuService;

    @Autowired
    private RedisUserCache redisUserCache;

    @Autowired
    private IpUtil ipUtil;

    @Autowired
    private ConfigService configService;

    @PostMapping(value = "/login/accessCode.do")
    public ResultVo accessCode() throws IOException {
        //生成一个请求码，后面接口需要拿着请求码请求登录相关的所有接口,10分钟内有效
        String accessCode = StringUtil.getUUID();

        //存储到redis，验证的时候只要判断是否有值即可
        redisUserCache.setLoginAccessCode(accessCode);

        return success(accessCode);
    }

    @PostMapping(value = "/login.do")
    public ResultVo login(HttpServletRequest request, @RequestBody LoginParams params) throws Exception {
        if (params == null
                || StringUtils.isEmpty(params.getUsername())
                || StringUtils.isEmpty(params.getPassword())
                || StringUtils.isEmpty(params.getAccessCode())) {
            return fail("参数异常");
        }

        String value = redisUserCache.getLoginAccessCode(params.getAccessCode());
        if(StringUtils.isEmpty(value)){
            return fail("请刷新页面重新登录");
        }

        //判断redis中存储验证码是否正确
        String _code = redisUserCache.getLoginImgCode(params.getAccessCode());
        if(StringUtils.isEmpty(_code)){
            return fail("验证码超时，请点击刷新重新输入");
        }
        if(!_code.toLowerCase().equals(params.getCode().toLowerCase())){
            return fail("验证码输入错误");
        }

        User user = userService.queryByUserAndPassword(params.getUsername());
        if (user == null) {
            return fail("无此用户");
        }

        if(!CheckPassword.checkPasswordRule(params.getPassword())){
            return fail("密码格式错误");
        }

        //判断密码
        if (!user.getPassword().equals(DigestUtils.md5Hex(DigestUtils.md5Hex(params.getPassword())))) {
            return fail("密码错误");
        }

        if(user.getStatus().intValue()== UserStatus.disable.getKey()){
            return fail("账号禁用，请联系管理员");
        }
        user.setPassword(null);

        //把之前登录的用户T下线
        Config config = configService.queryByKey(ConfigKey.systemLoginSingle.getKey());
        if(config!=null){
            if(config.getValue().equals(String.valueOf(YesOrNo.YES.getValue()))){
                Set<String> userKeys = redisUserCache.getUserKey(user.getId());
                if(userKeys!=null && userKeys.size()>0){
                    //把这些用户全部T掉
                    Iterator<String> iterator = userKeys.iterator();
                    while (iterator.hasNext()){
                        String key = iterator.next();
                        String token = redisUserCache.getKey(key);
                        redisUserCache.delLogin(token,user.getId());
                    }
                }
            }
        }

        //生成token
        String token = StringUtil.get32UUID();

        String ip = ClientUtil.getIpAddress(request);
        String ipAddress = ipUtil.getIpAddress(ip);

        OnlineVo onlineSession = new OnlineVo();
        onlineSession.setUser(user);
        onlineSession.setIp(ip);
        onlineSession.setIpAddress(ipAddress);
        onlineSession.setUserAgent(request.getHeader("user-agent"));
        onlineSession.setUrl(request.getRequestURL().toString());
        onlineSession.setUsername(user.getUsername());
        onlineSession.setNickname(user.getNickname());
        onlineSession.setLoginTime(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss.SSS"));
        onlineSession.setLastTime(onlineSession.getLoginTime());

        //查询权限
        Set<String> permissions = new HashSet<>(); //存储所有的code
        List<Role> roles = roleService.queryByUser(user.getId());
        List<Menu> ms = new ArrayList<>();
        if(roles!=null&&roles.size()>0){
            roles.forEach(role -> {
                List<Menu> menus = menuService.queryByRole(role.getId());
                if(menus!=null&&menus.size()>0){
                    menus.forEach(menu -> {
                        permissions.add(menu.getCode());
                        if(menu.getType()==MenuType.directory.getKey()
                                ||menu.getType()==MenuType.menu.getKey()
                                ||menu.getType()==MenuType.childPage.getKey()){
                            ms.add(menu);
                        }
                    });
                }
            });

            //存储权限
            getSession().setAttribute(Attribute.Session.permissions,permissions);
        }

        getSession().setAttribute(Attribute.Session.loginTime, DateFormatUtils.format(new Date(),"yyyy-MM-dd HH:mm:ss"));

        List<MenuPojo> treeMenu = new ArrayList<>();    //隐藏显示的不给
        List<String> noCacheMenu = new ArrayList<>();    //存储不缓存的名子
        for (int i = 0; i < ms.size(); i++) {
            if(ms.get(i).getCache()==0){
                noCacheMenu.add(ms.get(i).getCode());
            }

            if(ms.get(i).getShow().intValue()== MenuShow.hide.getKey() || ms.get(i).getType().intValue()==MenuType.childPage.getKey()){
                continue;
            }

            if(ms.get(i).getParentId()==null || ms.get(i).getParentId().length()<=0) {
                MenuPojo pojo = new MenuPojo();
                BeanUtils.copyProperties(ms.get(i),pojo);
                treeMenu.add(pojo);

                addTreeMenu(pojo,ms,ms.get(i));
            }
        }

        //返回值
        Map<String,Object> map = new HashMap<>();
        map.put("permissions",permissions);
        map.put("menus",ms);    //这个存储所有的路由
        map.put("treeMenus",treeMenu);    //这个是树形列表
        map.put("noCacheMenu",noCacheMenu);    //不缓存的菜单code列表
        map.put("token", token);    //token
        map.put("user",user);

        onlineSession.setPermissions(permissions);
        onlineSession.setMenu(ms);
        onlineSession.setTreeMenu(treeMenu);
        onlineSession.setSessionId(request.getSession().getId());

        //存储缓存
        redisUserCache.setLogin(token,onlineSession);

        return success(map);
    }

    //添加到树型列表里
    private void addTreeMenu(MenuPojo pojo, List<Menu> menus,Menu currentMenu){
        List<MenuPojo> list = new ArrayList<>();
        for (int i = 0; i < menus.size(); i++) {
            if(menus.get(i).getShow().intValue()== MenuShow.hide.getKey() || menus.get(i).getType().intValue()== MenuType.childPage.getKey()){
                continue;
            }
            if(menus.get(i).getParentId()!=null && menus.get(i).getParentId().length()>0 && menus.get(i).getParentId().equals(currentMenu.getId())){
                MenuPojo po = new MenuPojo();
                BeanUtils.copyProperties(menus.get(i),po);
                list.add(po);

                addTreeMenu(po,menus,menus.get(i));
            }
        }
        pojo.setChildren(list);
    }

    @PostMapping(value = "/logout.do")
    public ResultVo logout() throws IOException {
        String json = redisUserCache.getLoginByToken(getToken());
        OnlineVo onlineVo = JsonUtil.getInstance().deserialize(json, OnlineVo.class);
        redisUserCache.delLogin(getToken(),onlineVo.getUser().getId());
        getSession().invalidate();

        return success();
    }

    @PostMapping(value = "/login/captcha.do")
    public ResultVo captcha(HttpServletRequest request,@RequestBody LoginParams params) throws IOException {
        if(params==null || StringUtils.isEmpty(params.getAccessCode())){
            return fail("请刷新页面重新登录");
        }

        String value = redisUserCache.getLoginAccessCode(params.getAccessCode());
        if(StringUtils.isEmpty(value)){
            return fail("验证码超时，请点击重新获取图片验证码");
        }

        HttpHeaders header = new HttpHeaders();
        header.add("Content-Type", "image/gif");

        GifCaptcha captcha = new GifCaptcha(150,40,9);//   gif格式动画验证码
        captcha.alphas();   //生成验证码

        //把内容存储到redis，配置1分钟超时
        String str = captcha.text();
        String code = "";   //要输入的验证码
        String position = "";    //验证码位置

        //4-9的随机数
        int num = new Random().nextInt(3)+4;
        for(int i=0;i<num;i++) {
            //获取字符串位置，可以重复
            int random = (int) (Math.random() * 9 + 1);
            position += random+"";
            code += str.substring(random-1,random);
        }

        //把验证码和位置存储下来，图片验证码1分钟超时
        redisUserCache.setLoginImgCode(params.getAccessCode(),str,code,position);

        return success();
    }

    @GetMapping(value = "/login/captcha.img")
    public ResponseEntity<byte[]> captchaImg(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String accessCode = request.getParameter("accessCode");

        if(StringUtils.isEmpty(accessCode)){
            return new ResponseEntity<>(HttpStatus.OK);
        }

        String value = redisUserCache.getLoginAccessCode(accessCode);
        if(StringUtils.isEmpty(value)){
            return new ResponseEntity<>(HttpStatus.OK);
        }

        String captcha = redisUserCache.getLoginImgCaptcha(accessCode);
        if(StringUtils.isEmpty(captcha)){
            return new ResponseEntity<>(HttpStatus.OK);
        }

        HttpHeaders header = new HttpHeaders();
        header.add("Content-Type", "image/gif");

        GifCaptcha gifCaptcha = new GifCaptcha(150,40,captcha.length());//   gif格式动画验证码
        gifCaptcha.out(response.getOutputStream(),captcha);

        return new ResponseEntity<>(header, HttpStatus.OK);
    }

    @GetMapping(value = "/login/captchaPosition.img")
    public ResponseEntity<byte[]> captchaPosition(HttpServletRequest request,HttpServletResponse response) throws IOException {
        String accessCode = request.getParameter("accessCode");

        if(StringUtils.isEmpty(accessCode)){
            return new ResponseEntity<>(HttpStatus.OK);
        }

        String value = redisUserCache.getLoginAccessCode(accessCode);
        if(StringUtils.isEmpty(value)){
            return new ResponseEntity<>(HttpStatus.OK);
        }

        String postion = redisUserCache.getLoginImgPosition(accessCode);
        if(StringUtils.isEmpty(postion)){
            return new ResponseEntity<>(HttpStatus.OK);
        }

        HttpHeaders header = new HttpHeaders();
        header.add("Content-Type", "image/gif");

        GifCaptcha captcha = new GifCaptcha(260,40,postion.length());//   gif格式动画验证码
        captcha.out(response.getOutputStream(),postion);

        return new ResponseEntity<>(header, HttpStatus.OK);
    }

}
