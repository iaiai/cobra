package com.iaiai.cobra.admin.core;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Package: com.iaiai.cobra.admin.core
 * Author: iaiai
 * Create Time: 2019/12/20 9:39 下午
 * QQ: 176291935
 * Url: http://iaiai.iteye.com
 * Email: 176291935@qq.com
 * Description:
 */
public final class Attribute {

    public final static String redisPrefix = "com.iaiai.cobra.admin"; //redis存储前缀

    //全局的，不允许变的，所有项目都可以用的
    public static final class Global{
        public final static String ipAddress = "com.iaiai.ip";   //全局的ip地址，不超时的，格式为[ipAddress:ip - 存储地址]
    }

    public static final class RedisKey{
        public final static String tokenKey = Attribute.redisPrefix + ":login:user:token";   //值为token用户的对象
        public final static String userKey = Attribute.redisPrefix + ":login:user:id";   //值为user用户的对象
        public final static String onlineKey = Attribute.redisPrefix + ":login:user:online";   //在线用户
        public final static String loginAccessCode = Attribute.redisPrefix + ":login:access.code";   //值为user用户的对象
    }

    //这个属性需要注意，存储到session中以后分布式集群会有问题
    public static final class Session{
        public final static String permissions = "__SESSION_PERMISSIONS";   //值为menu用户的所有菜单权限
        public final static String userAgent = "__SESSION_USER_AGENT";   //用户的ua头信息
        public final static String lastRequestTime = "__SESSION_LAST_REQUEST_TIME";   //最后一次请求时间
        public final static String loginTime = "__SESSION_LOGIN_TIME";   //登录时间
        public final static String ip = "__SESSION_IP";   //ip
        public final static String requestUrl = "__SESSION_REQUEST_URL";   //最后请求的url




        private static final Map<String, HttpSession> sessions = new HashMap<>();
        public static int getCount(){return sessions.size();}
        public static void add(HttpSession session){sessions.put(session.getId(),session);}
        public static Map<String, HttpSession> get(){return sessions;}
        public static void remove(String sessionId){sessions.remove(sessionId);}
    }

}
