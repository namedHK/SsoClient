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
 * �ͻ��˵ǳ�
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
	 * �ǳ�ssoϵͳ
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    if(request.getParameter(Commons.isGobal) == null){
	        /*HttpSession session = request.getSession();
            session.setAttribute(Commons.tokenInfo, null);
            //����Ǳ�Ӧ�õǳ��Ļ���ͬʱ�ѵǳ������͸���֤����
            String url = Commons.ssoUrl+Commons.ssoExit;
            Map<String, String> params = new HashMap<>();
            //ͨ��
            if(session.getAttribute(Commons.globalSessionId) != null){
                String GlobalSessionId  = session.getAttribute(Commons.globalSessionId).toString();
                params.put(Commons.globalSessionId, GlobalSessionId);
            }
            params.put(Commons.loaclSessionId, session.getId());
            HttpUtil.http(url, params);
            //��session�в�����ʱ�򣬲�Ҫ����invalidate
            session.invalidate();
            response.sendRedirect("/SsoClient/login");*/
	        //http://127.0.0.1:8081/SsoClient/loginOut
	        String returnUrl = request.getRequestURL().toString();
	        returnUrl = StringUtil.getUrl(returnUrl)+"/login";
	        response.sendRedirect(Commons.ssoUrl+Commons.ssoExit+"?"+Commons.returnUrl+"="+returnUrl);
	        
        }else{
            //���ݴ�������loaclsessionId�ǳ�
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
