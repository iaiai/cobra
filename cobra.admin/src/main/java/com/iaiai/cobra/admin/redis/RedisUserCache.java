package com.iaiai.cobra.admin.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.iaiai.cobra.admin.core.Attribute;
import com.iaiai.cobra.admin.system.controller.vo.OnlineVo;
import com.iaiai.cobra.admin.web.controller.LoginController;
import com.iaiai.cobra.common.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * Package: com.iaiai.cobra.admin.core.util
 * Author: iaiai
 * Create Time: 2020/11/6 上午9:19
 * QQ: 176291935
 * Url: http://iaiai.iteye.com
 * Email: 176291935@qq.com
 * Description: 用户信息的redis工具
 */
@Component
public class RedisUserCache {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public static final long loginTimeout = 1 * 60 * 60 * 24 * 30;  //秒，过时1月

    //存储请求码
    public void setLoginAccessCode(String accessCode){
        long timeout = 1 * 60 * 3;  //秒，过时3分钟
        //存储到redis，验证的时候只要判断是否有值即可
        redisTemplate.opsForValue().set(String.format("%s:%s",Attribute.RedisKey.loginAccessCode,accessCode),accessCode,timeout, TimeUnit.SECONDS);
    }

    //返回请求码
    public String getLoginAccessCode(String accessCode){
        return redisTemplate.opsForValue().get(String.format("%s:%s",Attribute.RedisKey.loginAccessCode,accessCode));
    }

    //登录时的图片验证码
    public void setLoginImgCode(String accessCode,String randomStr,String code,String position){
        //把验证码和位置存储下来，图片验证码1分钟超时
        redisTemplate.opsForValue().set(String.format("%s:%s.captcha",Attribute.RedisKey.loginAccessCode,accessCode),randomStr,1*60,TimeUnit.SECONDS);
        redisTemplate.opsForValue().set(String.format("%s:%s.code",Attribute.RedisKey.loginAccessCode,accessCode),code,1*60,TimeUnit.SECONDS);
        redisTemplate.opsForValue().set(String.format("%s:%s.position",Attribute.RedisKey.loginAccessCode,accessCode),position,1*60,TimeUnit.SECONDS);
    }

    //获取登录的验证码
    public String getLoginImgCode(String accessCode){
        return redisTemplate.opsForValue().get(String.format("%s:%s.code",Attribute.RedisKey.loginAccessCode,accessCode));
    }

    //获取登录的验证码
    public String getLoginImgCaptcha(String accessCode){
        return redisTemplate.opsForValue().get(String.format("%s:%s.captcha",Attribute.RedisKey.loginAccessCode,accessCode));
    }

    //获取登录的验证码位置
    public String getLoginImgPosition(String accessCode){
        return redisTemplate.opsForValue().get(String.format("%s:%s.position",Attribute.RedisKey.loginAccessCode,accessCode));
    }

    //存储登录信息
    public void setLogin(String token,OnlineVo onlineSession) throws JsonProcessingException {
        redisTemplate.opsForValue().set(Attribute.RedisKey.tokenKey + ":" + token, JsonUtil.getInstance().serialize(onlineSession), loginTimeout, TimeUnit.SECONDS);
        redisTemplate.opsForValue().set(String.format("%s:%s:%s",Attribute.RedisKey.userKey,onlineSession.getUser().getId(),token), token, loginTimeout, TimeUnit.SECONDS);
    }

    //获取登录信息
    public String getLoginByToken(String token){
        return redisTemplate.opsForValue().get(Attribute.RedisKey.tokenKey + ":" + token);
    }

    //删除登录信息
    public void delLogin(String token,String userId){
        redisTemplate.delete(Attribute.RedisKey.tokenKey + ":" + token);
        redisTemplate.delete(Attribute.RedisKey.onlineKey + ":" + token);
        redisTemplate.delete(String.format("%s:%s:%s",Attribute.RedisKey.userKey,userId,token));
    }

    //设置在线信息
    public void setOnline(String token,OnlineVo onlineVo) throws JsonProcessingException {
        long onlineTimeout = 1 * 60 * 30;  //秒,30分钟

        redisTemplate.opsForValue().set(Attribute.RedisKey.tokenKey + ":" + token, JsonUtil.getInstance().serialize(onlineVo), LoginController.timeout, TimeUnit.SECONDS);
        redisTemplate.opsForValue().set(String.format("%s:%s:%s",Attribute.RedisKey.userKey,onlineVo.getUser().getId(),token), token, LoginController.timeout, TimeUnit.SECONDS);
        redisTemplate.opsForValue().set(Attribute.RedisKey.onlineKey + ":" + token, onlineVo.getLastTime(), onlineTimeout, TimeUnit.SECONDS);    //设置当前在线人数
    }

    //获取在线人数的key信息
    public Set<String> getOnlineKey() {
        return redisTemplate.keys(Attribute.RedisKey.onlineKey + ":*");
    }

    //获取用户的key信息
    public Set<String> getUserKey(String userId) {
        return redisTemplate.keys(String.format("%s:%s:*",Attribute.RedisKey.userKey,userId));
    }

    //获取key信息
    public String getKey(String key) {
        return redisTemplate.opsForValue().get(key);
    }

}
