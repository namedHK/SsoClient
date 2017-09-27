package login;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import constant.Commons;
import constant.TokenInfo;

/**
 * Servlet implementation class Login
 */
@WebServlet("/login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Login() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    TokenInfo tokenInfo = (TokenInfo) request.getSession().getAttribute(Commons.tokenInfo);
	    String username = "";
	    if(tokenInfo != null)
	        username = tokenInfo.getUserName();
	        System.out.println("uri = "+request.getRemoteAddr());
	        response.sendRedirect("/SsoClient/login.html?username="+username);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    
	}

}
