package util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Title:StringUtil
 * @Description:String�?单工具类
 * @author 张颖�?
 * @date 2017�?8�?25日下�?4:42:46
 * @version 1.0
 */
public class StringUtil {
	/**
	 * @Title:是否为空
	 * @Description:是否为空，包括空字符串，或�?�空�?
	 * @author 张颖�?
	 * @date 2017�?8�?25日下�?4:43:44
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		if (str == null || str.trim().length() == 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @Title:是否不为�?
	 * @Description:是否包含有效内容的字符串，与isEmpty()相反
	 * @author 张颖�?
	 * @date 2017�?8�?25日下�?4:43:35
	 * @param str
	 * @return
	 */
	public static boolean isUnEmpty(String str) {
		return !isEmpty(str);
	}
	
	/**
	 * 截取正则匹配的字符串
	 * @author hek
	 * @date 2017年9月27日下午1:35:22
	 * @param regex
	 * @param source
	 * @return
	 */
	public static String getMatcher(String regex, String source) {  
	        String result = "";  
	        Pattern pattern = Pattern.compile(regex);  
	        Matcher matcher = pattern.matcher(source);  
	        while (matcher.find()) {  
	            result = matcher.group(0);
	        }  
	        return result;  
	    }  
	
	/**
	 * 得到客户端的访问路径，不带资源
	 * @author hek
	 * @date 2017年9月27日下午1:38:15
	 * @return
	 */
	public static String getUrl(String returnUrl){
	    String regex = "\\S*//\\S*/\\S*/";
	    String url = getMatcher(regex, returnUrl);
	    url = url.substring(0,url.length()-1);
	    return url;
	}
	
	    
	public static void main(String[] args) {
	        String url = "http://172.12.1.123/test/txt";
	        //String regex = "(\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3})";
	        String regex = "\\S*//\\S*/\\S*/";
//	        String regex = "(\\d{1,3}\\.){1,3}(\\d{1,3})";
	        System.out.println(getMatcher(regex,url));
	    }
}
