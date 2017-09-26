package constant;


/**
 * token对应的资源
 * @author hk
 *
 */
public class TokenInfo {
    private String UserName;
    private String ssoClientId;
    private String globalId;
    
    public String getUserName() {
    
        return UserName;
    }
    
    public void setUserName(String userName) {
    
        UserName = userName;
    }
    
    public String getSsoClientId() {
    
        return ssoClientId;
    }
    
    public void setSsoClientId(String ssoClientId) {
    
        this.ssoClientId = ssoClientId;
    }
    
    public String getGlobalId() {
    
        return globalId;
    }
    
    public void setGlobalId(String globalId) {
    
        this.globalId = globalId;
    }

}
