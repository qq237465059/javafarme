package com.frame.util.string;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
	 /**
	  * 首字母大写
	  * 
	  * @param name
	  * @return
	  */
    public static String captureName(String name) {
        char[] cs=name.toCharArray();
        cs[0]-=32;
        return String.valueOf(cs);
    }

	/**
	 * 检查数据串中是否包含非法字符集
	 * 
	 * @param str
	 * @return [true]|[false] 包含|不包含
	 */
	public static boolean check(String str) {
		String sIllegal = "'\"";
		int len = sIllegal.length();
		if (null == str)
			return false;
		for (int i = 0; i < len; i++) {
			if (str.indexOf(sIllegal.charAt(i)) != -1)
				return true;
		}

		return false;
	}

	/***************************************************************************
	 * getHideEmailPrefix - 隐藏邮件地址前缀。
	 * 
	 * @param email
	 *            - EMail邮箱地址 例如: linwenguo@koubei.com 等等...
	 * @return 返回已隐藏前缀邮件地址, 如 *********@koubei.com.
	 * @version 1.0 (2006.11.27) Wilson Lin
	 **************************************************************************/
	public static String getHideEmailPrefix(String email) {
		if (null != email) {
			int index = email.lastIndexOf('@');
			if (index > 0) {
				email = repeat("*", index).concat(email.substring(index));
			}
		}
		return email;
	}

	/***************************************************************************
	 * repeat - 通过源字符串重复生成N次组成新的字符串。
	 * 
	 * @param src
	 *            - 源字符串 例如: 空格(" "), 星号("*"), "浙江" 等等...
	 * @param num
	 *            - 重复生成次数
	 * @return 返回已生成的重复字符串
	 * @version 1.0 (2006.10.10) Wilson Lin
	 **************************************************************************/
	public static String repeat(String src, int num) {
		StringBuffer s = new StringBuffer();
		for (int i = 0; i < num; i++)
			s.append(src);
		return s.toString();
	}

	/**
	 * 过滤用户输入的URL地址（防治用户广告） 目前只针对以http或www开头的URL地址
	 * 本方法调用的正则表达式，不建议用在对性能严格的地方例如:循环及list页面等
	 * 
	 * @param str
	 *            需要处理的字符串
	 * @return 返回处理后的字符串
	 */
	public static String removeURL(String str) {
		if (str != null)
			str = str.toLowerCase()
					.replaceAll("(http|www|com|cn|org|\\.)+", "");
		return str;
	}

	/**
	 * Wap页面的非法字符检查
	 * 
	 * @date 2007-06-29
	 * @param str
	 * @return
	 */
	public static String replaceWapStr(String str) {
		if (str != null) {
			str = str.replaceAll("<span class=\"keyword\">", "");
			str = str.replaceAll("</span>", "");
			str = str.replaceAll("<strong class=\"keyword\">", "");
			str = str.replaceAll("<strong>", "");
			str = str.replaceAll("</strong>", "");

			str = str.replace('$', '＄');

			str = str.replaceAll("&amp;", "＆");
			str = str.replace('&', '＆');

			str = str.replace('<', '＜');

			str = str.replace('>', '＞');

		}
		return str;
	}

	/**
	 * 字符串转float 如果异常返回0.00
	 * 
	 * @param s
	 *            输入的字符串
	 * @return 转换后的float
	 */
	public static Float toFloat(String s) {
		try {
			return Float.parseFloat(s);
		} catch (NumberFormatException e) {
			return new Float(0);
		}
	}

	/**
	 * 页面中去除字符串中的空格、回车、换行符、制表符
	 * 
	 * @date 2007-08-17
	 * @param str
	 * @return
	 */
	public static String replaceBlank(String str) {
		if (str != null) {
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(str);
			str = m.replaceAll("");
		}
		return str;
	}
	/**
	 * 
	 * 转换编码
	 * 
	 * @param s
	 *            源字符串
	 * @param fencode
	 *            源编码格式
	 * @param bencode
	 *            目标编码格式
	 * @return 目标编码
	 */
	public static String changCoding(String s, String fencode, String bencode) {
		try {
			String str = new String(s.getBytes(fencode), bencode);
			return str;
		} catch (UnsupportedEncodingException e) {
			return s;
		}
	}

	/**
	 * *************************************************************************
	 * 修改：刘黎明 修改时间:2007/3/1
	 * 
	 * @param str
	 * @return
	 ************************************************************************* 
	 */
	public static String removeHTMLLableExe(String str) {
		str = stringReplace(str, ">\\s*<", "><");
		str = stringReplace(str, "&nbsp;", " ");// 替换空格
		str = stringReplace(str, "<br ?/?>", "\n");// 去<br><br />
		str = stringReplace(str, "<([^<>]+)>", "");// 去掉<>内的字符
		str = stringReplace(str, "\\s\\s\\s*", " ");// 将多个空白变成一个空格
		str = stringReplace(str, "^\\s*", "");// 去掉头的空白
		str = stringReplace(str, "\\s*$", "");// 去掉尾的空白
		str = stringReplace(str, " +", " ");
		return str;
	}

	/**
	 * 除去html标签
	 * 
	 * @param str
	 *            源字符串
	 * @return 目标字符串
	 */
	public static String removeHTMLLable(String str) {
		str = stringReplace(str, "\\s", "");// 去掉页面上看不到的字符
		str = stringReplace(str, "<br ?/?>", "\n");// 去<br><br />
		str = stringReplace(str, "<([^<>]+)>", "");// 去掉<>内的字符
		str = stringReplace(str, "&nbsp;", " ");// 替换空格
		str = stringReplace(str, "&(\\S)(\\S?)(\\S?)(\\S?);", "");// 去<br><br />
		return str;
	}

	/**
	 * 去掉HTML标签之外的字符串
	 * 
	 * @param str
	 *            源字符串
	 * @return 目标字符串
	 */
	public static String removeOutHTMLLable(String str) {
		str = stringReplace(str, ">([^<>]+)<", "><");
		str = stringReplace(str, "^([^<>]+)<", "<");
		str = stringReplace(str, ">([^<>]+)$", ">");
		return str;
	}
	
	/**
	 * 
	 * 字符串替换
	 * 
	 * @param str
	 *            源字符串
	 * @param sr
	 *            正则表达式样式
	 * @param sd
	 *            替换文本
	 * @return 结果串
	 */
	public static String stringReplace(String str, String sr, String sd) {
		String regEx = sr;
		Pattern p = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(str);
		str = m.replaceAll(sd);
		return str;
	}
	
	/**
	 * 
	 * 得到字符串的子串位置序列
	 * 
	 * @param str
	 *            字符串
	 * @param sub
	 *            子串
	 * @param b
	 *            true子串前端,false子串后端
	 * @return 字符串的子串位置序列
	 */
	public static int[] getSubStringPos(String str, String sub, boolean b) {
		// int[] i = new int[(new Integer((str.length()-stringReplace( str , sub
		// , "" ).length())/sub.length())).intValue()] ;
		String[] sp = null;
		int l = sub.length();
		sp = splitString(str, sub);
		if (sp == null) {
			return null;
		}
		int[] ip = new int[sp.length - 1];
		for (int i = 0; i < sp.length - 1; i++) {
			ip[i] = sp[i].length() + l;
			if (i != 0) {
				ip[i] += ip[i - 1];
			}
		}
		if (b) {
			for (int j = 0; j < ip.length; j++) {
				ip[j] = ip[j] - l;
			}
		}
		return ip;
	}
	
	/**
	 * 
	 * 根据正则表达式分割字符串
	 * 
	 * @param str
	 *            源字符串
	 * @param ms
	 *            正则表达式
	 * @return 目标字符串组
	 */
	public static String[] splitString(String str, String ms) {
		String regEx = ms;
		Pattern p = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
		String[] sp = p.split(str);
		return sp;
	}
	
	/**
	 * *************************************************************************
	 * 用要通过URL传输的内容进行编码
	 * 
	 * @param 源字符串
	 * @return 经过编码的内容
	 ************************************************************************* 
	 */
	public static String URLEncode(String src) {
		String return_value = "";
		try {
			if (src != null) {
				return_value = URLEncoder.encode(src, "GBK");

			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return_value = src;
		}

		return return_value;
	}

	/**
	 * *************************************************************************
	 * 
	 * @param 传入
	 *            &#31119;test&#29031;&#27004;&#65288;&#21271;&#22823;&#38376;&#24635
	 *            ;&#24215;&#65289;&#31119;
	 * @return 经过解码的内容
	 ************************************************************************* 
	 */
	public static String getGBK(String str) {

		return transfer(str);
	}

	public static String transfer(String str) {
		Pattern p = Pattern.compile("&#\\d+;");
		Matcher m = p.matcher(str);
		while (m.find()) {
			String old = m.group();
			str = str.replaceAll(old, getChar(old));
		}
		return str;
	}
	
	public static String getChar(String str) {
		String dest = str.substring(2, str.length() - 1);
		char ch = (char) Integer.parseInt(dest);
		return "" + ch;
	}
	
	/**
	 * 泛型方法(通用)，把list转换成以“,”相隔的字符串 调用时注意类型初始化（申明类型） 如：List<Integer> intList =
	 * new ArrayList<Integer>(); 调用方法：StringUtil.listTtoString(intList);
	 * 效率：list中4条信息，1000000次调用时间为850ms左右
	 * 
	 * @serialData 2008-01-09
	 * @param <T>
	 *            泛型
	 * @param list
	 *            list列表
	 * @return 以“,”相隔的字符串
	 */
	public static <T> String listTtoString(List<T> list) {
		if (list == null || list.size() < 1)
			return "";
		Iterator<T> i = list.iterator();
		if (!i.hasNext())
			return "";
		StringBuilder sb = new StringBuilder();
		for (;;) {
			T e = i.next();
			sb.append(e);
			if (!i.hasNext())
				return sb.toString();
			sb.append(",");
		}
	}

	/**
	 * 把整形数组转换成以“,”相隔的字符串
	 * 
	 * @serialData 2008-01-08
	 * @param a
	 *            数组a
	 * @return 以“,”相隔的字符串
	 */
	public static String intArraytoString(int[] a) {
		if (a == null)
			return "";
		int iMax = a.length - 1;
		if (iMax == -1)
			return "";
		StringBuilder b = new StringBuilder();
		for (int i = 0;; i++) {
			b.append(a[i]);
			if (i == iMax)
				return b.toString();
			b.append(",");
		}
	}
	
	/**
	 * 判断文字内容重复
	 * 
	 * @Date 2008-04-17
	 */
	public static boolean isContentRepeat(String content) {
		int similarNum = 0;
		int forNum = 0;
		int subNum = 0;
		int thousandNum = 0;
		String startStr = "";
		String nextStr = "";
		boolean result = false;
		float endNum = (float) 0.0;
		if (content != null && content.length() > 0) {
			if (content.length() % 1000 > 0)
				thousandNum = (int) Math.floor(content.length() / 1000) + 1;
			else
				thousandNum = (int) Math.floor(content.length() / 1000);
			if (thousandNum < 3)
				subNum = 100 * thousandNum;
			else if (thousandNum < 6)
				subNum = 200 * thousandNum;
			else if (thousandNum < 9)
				subNum = 300 * thousandNum;
			else
				subNum = 3000;
			for (int j = 1; j < subNum; j++) {
				if (content.length() % j > 0)
					forNum = (int) Math.floor(content.length() / j) + 1;
				else
					forNum = (int) Math.floor(content.length() / j);
				if (result || j >= content.length())
					break;
				else {
					for (int m = 0; m < forNum; m++) {
						if (m * j > content.length()
								|| (m + 1) * j > content.length()
								|| (m + 2) * j > content.length())
							break;
						startStr = content.substring(m * j, (m + 1) * j);
						nextStr = content.substring((m + 1) * j, (m + 2) * j);
						if (startStr.equals(nextStr)) {
							similarNum = similarNum + 1;
							endNum = (float) similarNum / forNum;
							if (endNum > 0.4) {
								result = true;
								break;
							}
						} else
							similarNum = 0;
					}
				}
			}
		}
		return result;
	}

	/**
	 * Ascii转为Char
	 * 
	 * @param asc
	 * @return
	 */
	public static String AsciiToChar(int asc) {
		String TempStr = "A";
		char tempchar = (char) asc;
		TempStr = String.valueOf(tempchar);
		return TempStr;
	}
	
	/**
	 * 把字节码转换成16进制
	 */
	public static String byte2hex(byte bytes[]) {
		StringBuffer retString = new StringBuffer();
		for (int i = 0; i < bytes.length; ++i) {
			retString.append(Integer.toHexString(0x0100 + (bytes[i] & 0x00FF))
					.substring(1).toUpperCase());
		}
		return retString.toString();
	}

	/**
	 * 把16进制转换成字节码
	 * 
	 * @param hex
	 * @return
	 */
	public static byte[] hex2byte(String hex) {
		byte[] bts = new byte[hex.length() / 2];
		for (int i = 0; i < bts.length; i++) {
			bts[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2),
					16);
		}
		return bts;
	}
	
	/**
	 * 判断是否位数字,并可为空
	 * 
	 * @param src
	 * @return
	 */
	public static boolean isNumericAndCanNull(String src) {
		Pattern numericPattern = Pattern.compile("^[0-9]+$");
		if (src == null || src.equals(""))
			return true;
		boolean return_value = false;
		if (src != null && src.length() > 0) {
			Matcher m = numericPattern.matcher(src);
			if (m.find()) {
				return_value = true;
			}
		}
		return return_value;
	}

	/**
	 * 页面的非法字符检查
	 * 
	 * @date 2007-11-29
	 * @param str
	 * @return
	 */
	public static String replaceStr(String str) {
		if (str != null && str.length() > 0) {
			str = str.replaceAll("~", ",");
			str = str.replaceAll(" ", ",");
			str = str.replaceAll("　", ",");
			str = str.replaceAll(" ", ",");
			str = str.replaceAll("`", ",");
			str = str.replaceAll("!", ",");
			str = str.replaceAll("@", ",");
			str = str.replaceAll("#", ",");
			str = str.replaceAll("\\$", ",");
			str = str.replaceAll("%", ",");
			str = str.replaceAll("\\^", ",");
			str = str.replaceAll("&", ",");
			str = str.replaceAll("\\*", ",");
			str = str.replaceAll("\\(", ",");
			str = str.replaceAll("\\)", ",");
			str = str.replaceAll("-", ",");
			str = str.replaceAll("_", ",");
			str = str.replaceAll("=", ",");
			str = str.replaceAll("\\+", ",");
			str = str.replaceAll("\\{", ",");
			str = str.replaceAll("\\[", ",");
			str = str.replaceAll("\\}", ",");
			str = str.replaceAll("\\]", ",");
			str = str.replaceAll("\\|", ",");
			str = str.replaceAll("\\\\", ",");
			str = str.replaceAll(";", ",");
			str = str.replaceAll(":", ",");
			str = str.replaceAll("'", ",");
			str = str.replaceAll("\\\"", ",");
			str = str.replaceAll("<", ",");
			str = str.replaceAll(">", ",");
			str = str.replaceAll("\\.", ",");
			str = str.replaceAll("\\?", ",");
			str = str.replaceAll("/", ",");
			str = str.replaceAll("～", ",");
			str = str.replaceAll("`", ",");
			str = str.replaceAll("！", ",");
			str = str.replaceAll("＠", ",");
			str = str.replaceAll("＃", ",");
			str = str.replaceAll("＄", ",");
			str = str.replaceAll("％", ",");
			str = str.replaceAll("︿", ",");
			str = str.replaceAll("＆", ",");
			str = str.replaceAll("×", ",");
			str = str.replaceAll("（", ",");
			str = str.replaceAll("）", ",");
			str = str.replaceAll("－", ",");
			str = str.replaceAll("＿", ",");
			str = str.replaceAll("＋", ",");
			str = str.replaceAll("＝", ",");
			str = str.replaceAll("｛", ",");
			str = str.replaceAll("［", ",");
			str = str.replaceAll("｝", ",");
			str = str.replaceAll("］", ",");
			str = str.replaceAll("｜", ",");
			str = str.replaceAll("＼", ",");
			str = str.replaceAll("：", ",");
			str = str.replaceAll("；", ",");
			str = str.replaceAll("＂", ",");
			str = str.replaceAll("＇", ",");
			str = str.replaceAll("＜", ",");
			str = str.replaceAll("，", ",");
			str = str.replaceAll("＞", ",");
			str = str.replaceAll("．", ",");
			str = str.replaceAll("？", ",");
			str = str.replaceAll("／", ",");
			str = str.replaceAll("·", ",");
			str = str.replaceAll("￥", ",");
			str = str.replaceAll("……", ",");
			str = str.replaceAll("（", ",");
			str = str.replaceAll("）", ",");
			str = str.replaceAll("——", ",");
			str = str.replaceAll("-", ",");
			str = str.replaceAll("【", ",");
			str = str.replaceAll("】", ",");
			str = str.replaceAll("、", ",");
			str = str.replaceAll("”", ",");
			str = str.replaceAll("’", ",");
			str = str.replaceAll("《", ",");
			str = str.replaceAll("》", ",");
			str = str.replaceAll("“", ",");
			str = str.replaceAll("。", ",");
		}
		return str;
	}
	
	/**
	 * 取url里的keyword（可选择参数）参数，用于整站搜索整合
	 * 
	 * @param params
	 * @param qString
	 * @return
	 */
	public static String getKeyWord(String params, String qString) {
		String keyWord = "";
		if (qString != null) {
			String param = params + "=";
			int i = qString.indexOf(param);
			if (i != -1) {
				int j = qString.indexOf("&", i + param.length());
				if (j > 0) {
					keyWord = qString.substring(i + param.length(), j);
				}
			}
		}
		return keyWord;
	}

	/**
	 * 解析字符串返回map键值对(例：a=1&b=2 => a=1,b=2)
	 * 
	 * @param query
	 *            源参数字符串
	 * @param split1
	 *            键值对之间的分隔符（例：&）
	 * @param split2
	 *            key与value之间的分隔符（例：=）
	 * @param dupLink
	 *            重复参数名的参数值之间的连接符，连接后的字符串作为该参数的参数值，可为null
	 *            null：不允许重复参数名出现，则靠后的参数值会覆盖掉靠前的参数值。
	 * @return map
	 */
	public static Map<String, String> parseQuery(String query, char split1,
			char split2, String dupLink) {
		if (!isEmpty(query) && query.indexOf(split2) > 0) {
			Map<String, String> result = new HashMap<String,String>();

			String name = null;
			String value = null;
			String tempValue = "";
			int len = query.length();
			for (int i = 0; i < len; i++) {
				char c = query.charAt(i);
				if (c == split2) {
					value = "";
				} else if (c == split1) {
					if (!isEmpty(name) && value != null) {
						if (dupLink != null) {
							tempValue = result.get(name);
							if (tempValue != null) {
								value += dupLink + tempValue;
							}
						}
						result.put(name, value);
					}
					name = null;
					value = null;
				} else if (value != null) {
					value += c;
				} else {
					name = (name != null) ? (name + c) : "" + c;
				}
			}

			if (!isEmpty(name) && value != null) {
				if (dupLink != null) {
					tempValue = result.get(name);
					if (tempValue != null) {
						value += dupLink + tempValue;
					}
				}
				result.put(name, value);
			}

			return result;
		}
		return null;
	}
	
	public static boolean isNotEmpty(String str) {
		if (str != null && !str.equals(""))
			return true;
		else
			return false;
	}

	/**
	 * 将list 用传入的分隔符组装为String
	 * 
	 * @param list
	 * @param slipStr
	 * @return String
	 */
	public static String listToStringSlipStr(List<?> list, String slipStr) {
		StringBuffer returnStr = new StringBuffer();
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				returnStr.append(list.get(i)).append(slipStr);
			}
		}
		if (returnStr.toString().length() > 0)
			return returnStr.toString().substring(0,
					returnStr.toString().lastIndexOf(slipStr));
		else
			return "";
	}

	/**
	 * 根据传入的分割符号,把传入的字符串分割为List字符串
	 * 
	 * @param slipStr
	 *            分隔的字符串
	 * @param src
	 *            字符串
	 * @return 列表
	 */
	public static List<String> stringToStringListBySlipStr(String slipStr,
			String src) {

		if (src == null)
			return null;
		List<String> list = new ArrayList<String>();
		String[] result = src.split(slipStr);
		for (int i = 0; i < result.length; i++) {
			list.add(result[i]);
		}
		return list;
	}
	
	/**
	 * 判断是否是空字符串 null和"" 都返回 true
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isEmpty(String s) {
		if (s != null && !s.equals("")) {
			return false;
		}
		return true;
	}
	
}
