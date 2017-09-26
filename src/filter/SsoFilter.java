package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;

import com.google.gson.Gson;

import util.StringUtil;
import constant.Commons;
import constant.TokenInfo;


public class SsoFilter implements Filter{
    String ssoCheckUrl = "";
    String ssoLoginUrl = "";
    String ssoUrl = "";
    
    @Override
    public void destroy() {
        
    }

    /* 
     * 所有的访问都过滤掉，如果没有本地会话就通过认证中心校验
     * 
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
            HttpSession session = ((HttpServletRequest)request).getSession();
            String returnUrl = ((HttpServletRequest)request).getRequestURL().toString();
            //如果能从session取到用户名，直接可以能够登录
            if(session.getAttribute(Commons.username) != null){
                //下个过滤、、没有的话访问原网页
                chain.doFilter(request, response);
            }else{
                //如果有令牌就去认证中心校验
                String token = request.getParameter(Commons.token);
                if(StringUtil.isEmpty(token)){
                    //没有的话就登录
                    ((HttpServletResponse)response).sendRedirect(ssoUrl+ssoLoginUrl+"?"+Commons.returnUrl+"="+returnUrl);
                }else{
                    HttpClient httpClient = new HttpClient();
                    PostMethod method = new PostMethod(ssoUrl);
                    method.setParameter(Commons.returnUrl, returnUrl);
                    //返回200为正常状态值，将得到的资源保存在局部会话中
                    if(httpClient.executeMethod(method) == 200){
                        String tokenInfoJson = method.getResponseBody().toString();
                        Gson gson = new Gson();
                        TokenInfo tokeInfo = gson.fromJson(tokenInfoJson, TokenInfo.class);
                        session.setAttribute(Commons.tokenInfo, tokeInfo);
                        chain.doFilter(request, response);
                    }else{
                        //令牌可能超过使用时期或者是假的，重新访问登录界面
                        System.out.println("令牌失效。。");
                        ((HttpServletResponse)response).sendRedirect(ssoUrl+ssoLoginUrl+"?"+Commons.returnUrl+"="+returnUrl);
                    }
                }
            }
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
        ssoLoginUrl = "/login";
        ssoCheckUrl = "/verify";
        ssoUrl = "127.0.0.1:8080/SsoServer";
    }

}
