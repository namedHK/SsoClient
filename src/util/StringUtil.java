package util;

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
}
