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
import org.apache.commons.httpclient.methods.PostMethod;

import util.StringUtil;

import com.google.gson.Gson;

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
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
            HttpServletRequest request = (HttpServletRequest) servletRequest;
            HttpServletResponse response = (HttpServletResponse) servletResponse;    
            HttpSession session = ((HttpServletRequest)request).getSession();
            System.out.println("client-id = "+session.getId());
            String returnUrl = ((HttpServletRequest)request).getRequestURL().toString();
            //如果能从session取到tokenInfo，直接可以能够登录-----登出也没必要做控制，不让登出也能够获取令牌
            if(session.getAttribute(Commons.tokenInfo) != null || returnUrl.contains("loginOut")){
                //下个过滤、、没有的话访问原网页
                chain.doFilter(request, response);
            }else{
                //如果有令牌就去认证中心校验
                String token = request.getParameter(Commons.token);
                if(StringUtil.isEmpty(token)){
                    //没有的话就登录
                    String url = ssoUrl+ssoLoginUrl+"?"+Commons.returnUrl+"="+returnUrl;
                    response.sendRedirect(url);
                    return;
                }else{
                    String url = ssoUrl+ssoCheckUrl;
                    HttpClient httpClient = new HttpClient();
                    PostMethod method = new PostMethod(url);
                    //将全局会话的Id告知认证中心
                    String globalSessionId = request.getParameter(Commons.globalSessionId);
                    //便于最后注销时使用
                    session.setAttribute(Commons.globalSessionId, globalSessionId);
                    method.setParameter(Commons.returnUrl, returnUrl);
                    method.setParameter(Commons.token, token);
                    method.setParameter(Commons.globalSessionId, globalSessionId);
                    method.setParameter(Commons.loaclSessionId, session.getId());
                    //返回200为正常状态值，将得到的资源保存在局部会话中
                    int status = httpClient.executeMethod(method) ;
                    if(status == 200){
                        String tokenInfoJson = method.getResponseBodyAsString();
                        Gson gson = new Gson();
                        TokenInfo tokeInfo = gson.fromJson(tokenInfoJson, TokenInfo.class);
                        session.setAttribute(Commons.tokenInfo, tokeInfo);
                        chain.doFilter(request, response);
                    }else{
                        //令牌可能超过使用时期或者是假的，重新访问登录界面
                        System.out.println("令牌失效。。");
                        //TODO 这里最好清空认证记录，重新登录
                        response.sendRedirect(ssoUrl+ssoLoginUrl+"?"+Commons.returnUrl+"="+returnUrl);
                    }
                    method.releaseConnection();
                }
            }
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
        ssoLoginUrl = "/login";
        ssoCheckUrl = "/auth/verify";
        //需要加完整路径，否则无法实现重定向
        ssoUrl = "http://127.0.0.1:8081/SsoServer";
    }

}
