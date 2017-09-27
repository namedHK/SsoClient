package session;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;




public class LocalSessions {
    private static Map<String,HttpSession> sessions = 
                new HashMap<>();
    
    public static void addSession(String sessionId,HttpSession session){
    	sessions.put(sessionId, session);
    }
    
    public static HttpSession getSession(String sessionId){
    	return sessions.get(sessionId);
    }
    
    public static void delSession(String sessionId){
    	sessions.remove(sessionId);
    }
    
    

}
