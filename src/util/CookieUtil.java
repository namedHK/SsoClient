package util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;


public class CookieUtil {
    public static String getVal(HttpServletRequest request,String name){
        Cookie[] cookies = request.getCookies();
        for(Cookie c : cookies){
            if(c.getName().equals(name)){
                return c.getValue();
            }
        }
        return name;
    }
}
