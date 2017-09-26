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
     * ���еķ��ʶ����˵������û�б��ػỰ��ͨ����֤����У��
     * 
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
            HttpSession session = ((HttpServletRequest)request).getSession();
            String returnUrl = ((HttpServletRequest)request).getRequestURL().toString();
            //����ܴ�sessionȡ���û�����ֱ�ӿ����ܹ���¼
            if(session.getAttribute(Commons.username) != null){
                //�¸����ˡ���û�еĻ�����ԭ��ҳ
                chain.doFilter(request, response);
            }else{
                //��������ƾ�ȥ��֤����У��
                String token = request.getParameter(Commons.token);
                if(StringUtil.isEmpty(token)){
                    //û�еĻ��͵�¼
                    ((HttpServletResponse)response).sendRedirect(ssoUrl+ssoLoginUrl+"?"+Commons.returnUrl+"="+returnUrl);
                }else{
                    HttpClient httpClient = new HttpClient();
                    PostMethod method = new PostMethod(ssoUrl);
                    method.setParameter(Commons.returnUrl, returnUrl);
                    //����200Ϊ����״ֵ̬�����õ�����Դ�����ھֲ��Ự��
                    if(httpClient.executeMethod(method) == 200){
                        String tokenInfoJson = method.getResponseBody().toString();
                        Gson gson = new Gson();
                        TokenInfo tokeInfo = gson.fromJson(tokenInfoJson, TokenInfo.class);
                        session.setAttribute(Commons.tokenInfo, tokeInfo);
                        chain.doFilter(request, response);
                    }else{
                        //���ƿ��ܳ���ʹ��ʱ�ڻ����Ǽٵģ����·��ʵ�¼����
                        System.out.println("����ʧЧ����");
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
