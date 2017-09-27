package constant;


/**
 * 常用的字段名都存在这里维护
 * @author hk
 *
 */
public class Commons {
    
    public static void main(String[] args) {

        System.out.println("...");
    }
    
    public static final String ssoLoginUrl = "/login";
    public static final String ssoCheckUrl = "/auth/verify";
    
    public static final String ssoExit = "/auth/loginOut";
    //需要加完整路径，否则无法实现重定向
    public static final String ssoUrl = "http://127.0.0.1:8081/SsoServer";
    
    /**
     * session中的登出map存放对象
     */
    public static final String loginout = "loginout";
    
    /**
     * 是否是从认证中心穿过来的
     */
    public static final String isGobal = "isGobal";
    
    /**
     * 局部会话的id
     */
    public static final String loaclSessionId = "loaclSessionId";
    
    /**
     * 全局会话的id
     */
    public static final String globalSessionId = "globalSessionId";
    
    /**
     * 访问应用所用到的url
     */
    public static final String returnUrl = "returnUrl";
    
    
    /**
     * 令牌
     */
    public static final String token = "token";
    
    public static final String tokenInfo = "tokenInfo";
    
    public static final String username = "username";

}
