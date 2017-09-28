package login;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import session.LocalSessions;
import util.HttpUtil;
import util.StringUtil;
import constant.Commons;

/**
 * 客户端登出
 */
@WebServlet("/loginOut")
public class LoginOut extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     */
    public LoginOut() {
        super();
    }

	/**
	 * 登出sso系统
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    if(request.getParameter(Commons.isGobal) == null){
	        /*HttpSession session = request.getSession();
            session.setAttribute(Commons.tokenInfo, null);
            //如果是本应用登出的话就同时把登出请求发送给认证中心
            String url = Commons.ssoUrl+Commons.ssoExit;
            Map<String, String> params = new HashMap<>();
            //通过
            if(session.getAttribute(Commons.globalSessionId) != null){
                String GlobalSessionId  = session.getAttribute(Commons.globalSessionId).toString();
                params.put(Commons.globalSessionId, GlobalSessionId);
            }
            params.put(Commons.loaclSessionId, session.getId());
            HttpUtil.http(url, params);
            //对session有操作的时候，不要调用invalidate
            session.invalidate();
            response.sendRedirect("/SsoClient/login");*/
	        //http://127.0.0.1:8081/SsoClient/loginOut
	        String returnUrl = request.getRequestURL().toString();
	        returnUrl = StringUtil.getUrl(returnUrl)+"/login";
	        response.sendRedirect(Commons.ssoUrl+Commons.ssoExit+"?"+Commons.returnUrl+"="+returnUrl);
	        
        }else{
            //根据穿过来的loaclsessionId登出
            String localSessionId = request.getParameter(Commons.loaclSessionId);
    	    HttpSession session = LocalSessions.getSession(localSessionId);
    	    session.setAttribute(Commons.tokenInfo, null);
    	    session.invalidate();
        }
	}

	/**
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    doGet(request, response);
	}

}
