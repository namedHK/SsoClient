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
     * ���еķ��ʶ����˵������û�б��ػỰ��ͨ����֤����У��
     * 
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
            HttpServletRequest request = (HttpServletRequest) servletRequest;
            HttpServletResponse response = (HttpServletResponse) servletResponse;    
            HttpSession session = ((HttpServletRequest)request).getSession();
            System.out.println("client-id = "+session.getId());
            String returnUrl = ((HttpServletRequest)request).getRequestURL().toString();
            //����ܴ�sessionȡ��tokenInfo��ֱ�ӿ����ܹ���¼-----�ǳ�Ҳû��Ҫ�����ƣ����õǳ�Ҳ�ܹ���ȡ����
            if(session.getAttribute(Commons.tokenInfo) != null || returnUrl.contains("loginOut")){
                //�¸����ˡ���û�еĻ�����ԭ��ҳ
                chain.doFilter(request, response);
            }else{
                //��������ƾ�ȥ��֤����У��
                String token = request.getParameter(Commons.token);
                if(StringUtil.isEmpty(token)){
                    //û�еĻ��͵�¼
                    String url = ssoUrl+ssoLoginUrl+"?"+Commons.returnUrl+"="+returnUrl;
                    response.sendRedirect(url);
                    return;
                }else{
                    String url = ssoUrl+ssoCheckUrl;
                    HttpClient httpClient = new HttpClient();
                    PostMethod method = new PostMethod(url);
                    //��ȫ�ֻỰ��Id��֪��֤����
                    String globalSessionId = request.getParameter(Commons.globalSessionId);
                    //�������ע��ʱʹ��
                    session.setAttribute(Commons.globalSessionId, globalSessionId);
                    method.setParameter(Commons.returnUrl, returnUrl);
                    method.setParameter(Commons.token, token);
                    method.setParameter(Commons.globalSessionId, globalSessionId);
                    method.setParameter(Commons.loaclSessionId, session.getId());
                    //����200Ϊ����״ֵ̬�����õ�����Դ�����ھֲ��Ự��
                    int status = httpClient.executeMethod(method) ;
                    if(status == 200){
                        String tokenInfoJson = method.getResponseBodyAsString();
                        Gson gson = new Gson();
                        TokenInfo tokeInfo = gson.fromJson(tokenInfoJson, TokenInfo.class);
                        session.setAttribute(Commons.tokenInfo, tokeInfo);
                        chain.doFilter(request, response);
                    }else{
                        //���ƿ��ܳ���ʹ��ʱ�ڻ����Ǽٵģ����·��ʵ�¼����
                        System.out.println("����ʧЧ����");
                        //TODO ������������֤��¼�����µ�¼
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
        //��Ҫ������·���������޷�ʵ���ض���
        ssoUrl = "http://127.0.0.1:8081/SsoServer";
    }

}
