package com.gzsoftware.pet.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log; 
import org.apache.commons.logging.LogFactory;

/**
 * Common Utils
 * @author pango leung
 *
 */
public class CommonUtil {
	
	private static Log log = LogFactory.getLog(CommonUtil.class);
	
	/**
	 * 转换String为Int
	 * 
	 * @param str
	 * @return
	 */
	public static int stringToInt(String str) {
		int result = 0;
		try {
			result = Integer.parseInt(str);
		} catch (Exception e) {
			log.info("[COMMON] String 转换成 int 出错 !");
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 转换date 为 String
	 * 
	 * @param date
	 * @return
	 */
	public static String dateToString(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String result = "";
		try {
			if (date != null) {
				result = sdf.format(date);
			}
		} catch (Exception e) {
			log.info("[COMMON] 时间转换成字符串出错!");
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 用于前台获得简历标题的日期
	 * 
	 * @return
	 */
	public static String getTitleDateString() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String result = "";
		Date date = new Date();
		try {
			if (date != null) {
				result = sdf.format(date);
			}
		} catch (Exception e) {
			log.info("[COMMON] 时间转换成字符串出错!");
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 用于前台获得简历标题的日期
	 * 
	 * @return
	 */
	public static String getTitleDateTimeString() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhMMss");
		String result = "";
		Date date = new Date();
		try {
			if (date != null) {
				result = sdf.format(date);
			}
		} catch (Exception e) {
			log.info("[COMMON] 时间转换成字符串出错!");
			e.printStackTrace();
		}
		return result;
	}



	/**
	 * 字符串转换成时间
	 * 
	 * @param str
	 * @return
	 */
	public static Date stringToDate(String str) {
		if (str == null || str.trim().equals(""))
			return null;
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			if (str.indexOf("/") >= 4) {
				String[] ymd = str.split("/");
				str = ymd[0] + "-" + ymd[1] + "-" + ymd[2];
			} else if (str.indexOf("/") >= 1 && str.indexOf("/") <= 2) {
				String[] ymd = str.split("/");
				str = ymd[2] + "-" + ymd[0] + "-" + ymd[1];
			}
			if (str.indexOf(":") > 0) {
				date = sdf2.parse(str);
			} else {
				date = sdf1.parse(str);
			}
		} catch (Exception e) {
			log.info("[COMMON] 字符串转换成时间出错!");
			e.printStackTrace();
		}
		return date;
	}

	public static HashSet<Integer> idStrToList(String idStr) {
		HashSet<Integer> ids = new HashSet<Integer>();
		if ((idStr != null) && (!idStr.equals(""))) {
			String[] idArr = idStr.split(",");
			for (String id : idArr) {
				ids.add(Integer.parseInt(id));
			}
		}
		return ids;
	}

	/**
	 * 判断一个字符串是否为空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isStrEmpty(String str) {
		if (str == null || str.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	

	
	public static InetAddress getInetAddress() {
		try {
			return InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getIP(String name) {
		InetAddress address = null;
		try {
			address = InetAddress.getByName(name);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return address.getHostAddress().toString();
	}

	public static String getHostIp(InetAddress netAddress) {
		if (null == netAddress) {
			return null;
		}
		String ip = netAddress.getHostAddress(); // get the ip address
		return ip;
	}
	
	public static final char UNDERLINE='_';  
	   public static String camelToUnderline(String param){  
	       if (param==null||"".equals(param.trim())){  
	           return "";  
	       }  
	       int len=param.length();  
	       StringBuilder sb=new StringBuilder(len);  
	       for (int i = 0; i < len; i++) {  
	           char c=param.charAt(i);  
	           if (Character.isUpperCase(c)){  
	               sb.append(UNDERLINE);  
	               sb.append(Character.toLowerCase(c));  
	           }else{  
	               sb.append(c);  
	           }  
	       }  
	       return sb.toString();  
	   }  
	   public static String underlineToCamel(String param){  
	       if (param==null||"".equals(param.trim())){  
	           return "";  
	       }  
	       int len=param.length();  
	       StringBuilder sb=new StringBuilder(len);  
	       for (int i = 0; i < len; i++) {  
	           char c=param.charAt(i);  
	           if (c==UNDERLINE){  
	              if (++i<len){  
	                  sb.append(Character.toUpperCase(param.charAt(i)));  
	              }  
	           }else{  
	               sb.append(c);  
	           }  
	       }  
	       return sb.toString();  
	   }  
	   public static String underlineToCamel2(String param){  
	       if (param==null||"".equals(param.trim())){  
	           return "";  
	       }  
	       StringBuilder sb=new StringBuilder(param);  
	       Matcher mc= Pattern.compile("_").matcher(param);  
	       int i=0;  
	       while (mc.find()){  
	           int position=mc.end()-(i++);  
	           //String.valueOf(Character.toUpperCase(sb.charAt(position)));  
	           sb.replace(position-1,position+1,sb.substring(position,position+1).toUpperCase());  
	       }  
	       return sb.toString();  
	   }  

		/**
		 * 使用java正则表达式去掉多余的.与0
		 * @param s
		 * @return
		 */
		public static String subZeroAndDot(String s) {
			if (s.indexOf(".") > 0) {
				s = s.replaceAll("0+?$", "");// 去掉多余的0
				s = s.replaceAll("[.]$", "");// 如最后一位是.则去掉
			}
			return s;
		}

}
