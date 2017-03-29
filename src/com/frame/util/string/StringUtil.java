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
	  * ����ĸ��д
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
	 * ������ݴ����Ƿ�����Ƿ��ַ���
	 * 
	 * @param str
	 * @return [true]|[false] ����|������
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
	 * getHideEmailPrefix - �����ʼ���ַǰ׺��
	 * 
	 * @param email
	 *            - EMail�����ַ ����: linwenguo@koubei.com �ȵ�...
	 * @return ����������ǰ׺�ʼ���ַ, �� *********@koubei.com.
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
	 * repeat - ͨ��Դ�ַ����ظ�����N������µ��ַ�����
	 * 
	 * @param src
	 *            - Դ�ַ��� ����: �ո�(" "), �Ǻ�("*"), "�㽭" �ȵ�...
	 * @param num
	 *            - �ظ����ɴ���
	 * @return ���������ɵ��ظ��ַ���
	 * @version 1.0 (2006.10.10) Wilson Lin
	 **************************************************************************/
	public static String repeat(String src, int num) {
		StringBuffer s = new StringBuffer();
		for (int i = 0; i < num; i++)
			s.append(src);
		return s.toString();
	}

	/**
	 * �����û������URL��ַ�������û���棩 Ŀǰֻ�����http��www��ͷ��URL��ַ
	 * ���������õ�������ʽ�����������ڶ������ϸ�ĵط�����:ѭ����listҳ���
	 * 
	 * @param str
	 *            ��Ҫ������ַ���
	 * @return ���ش������ַ���
	 */
	public static String removeURL(String str) {
		if (str != null)
			str = str.toLowerCase()
					.replaceAll("(http|www|com|cn|org|\\.)+", "");
		return str;
	}

	/**
	 * Wapҳ��ķǷ��ַ����
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

			str = str.replace('$', '��');

			str = str.replaceAll("&amp;", "��");
			str = str.replace('&', '��');

			str = str.replace('<', '��');

			str = str.replace('>', '��');

		}
		return str;
	}

	/**
	 * �ַ���תfloat ����쳣����0.00
	 * 
	 * @param s
	 *            ������ַ���
	 * @return ת�����float
	 */
	public static Float toFloat(String s) {
		try {
			return Float.parseFloat(s);
		} catch (NumberFormatException e) {
			return new Float(0);
		}
	}

	/**
	 * ҳ����ȥ���ַ����еĿո񡢻س������з����Ʊ��
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
	 * ת������
	 * 
	 * @param s
	 *            Դ�ַ���
	 * @param fencode
	 *            Դ�����ʽ
	 * @param bencode
	 *            Ŀ������ʽ
	 * @return Ŀ�����
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
	 * �޸ģ������� �޸�ʱ��:2007/3/1
	 * 
	 * @param str
	 * @return
	 ************************************************************************* 
	 */
	public static String removeHTMLLableExe(String str) {
		str = stringReplace(str, ">\\s*<", "><");
		str = stringReplace(str, "&nbsp;", " ");// �滻�ո�
		str = stringReplace(str, "<br ?/?>", "\n");// ȥ<br><br />
		str = stringReplace(str, "<([^<>]+)>", "");// ȥ��<>�ڵ��ַ�
		str = stringReplace(str, "\\s\\s\\s*", " ");// ������հױ��һ���ո�
		str = stringReplace(str, "^\\s*", "");// ȥ��ͷ�Ŀհ�
		str = stringReplace(str, "\\s*$", "");// ȥ��β�Ŀհ�
		str = stringReplace(str, " +", " ");
		return str;
	}

	/**
	 * ��ȥhtml��ǩ
	 * 
	 * @param str
	 *            Դ�ַ���
	 * @return Ŀ���ַ���
	 */
	public static String removeHTMLLable(String str) {
		str = stringReplace(str, "\\s", "");// ȥ��ҳ���Ͽ��������ַ�
		str = stringReplace(str, "<br ?/?>", "\n");// ȥ<br><br />
		str = stringReplace(str, "<([^<>]+)>", "");// ȥ��<>�ڵ��ַ�
		str = stringReplace(str, "&nbsp;", " ");// �滻�ո�
		str = stringReplace(str, "&(\\S)(\\S?)(\\S?)(\\S?);", "");// ȥ<br><br />
		return str;
	}

	/**
	 * ȥ��HTML��ǩ֮����ַ���
	 * 
	 * @param str
	 *            Դ�ַ���
	 * @return Ŀ���ַ���
	 */
	public static String removeOutHTMLLable(String str) {
		str = stringReplace(str, ">([^<>]+)<", "><");
		str = stringReplace(str, "^([^<>]+)<", "<");
		str = stringReplace(str, ">([^<>]+)$", ">");
		return str;
	}
	
	/**
	 * 
	 * �ַ����滻
	 * 
	 * @param str
	 *            Դ�ַ���
	 * @param sr
	 *            ������ʽ��ʽ
	 * @param sd
	 *            �滻�ı�
	 * @return �����
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
	 * �õ��ַ������Ӵ�λ������
	 * 
	 * @param str
	 *            �ַ���
	 * @param sub
	 *            �Ӵ�
	 * @param b
	 *            true�Ӵ�ǰ��,false�Ӵ����
	 * @return �ַ������Ӵ�λ������
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
	 * ����������ʽ�ָ��ַ���
	 * 
	 * @param str
	 *            Դ�ַ���
	 * @param ms
	 *            ������ʽ
	 * @return Ŀ���ַ�����
	 */
	public static String[] splitString(String str, String ms) {
		String regEx = ms;
		Pattern p = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
		String[] sp = p.split(str);
		return sp;
	}
	
	/**
	 * *************************************************************************
	 * ��Ҫͨ��URL��������ݽ��б���
	 * 
	 * @param Դ�ַ���
	 * @return �������������
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
	 * @param ����
	 *            &#31119;test&#29031;&#27004;&#65288;&#21271;&#22823;&#38376;&#24635
	 *            ;&#24215;&#65289;&#31119;
	 * @return �������������
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
	 * ���ͷ���(ͨ��)����listת�����ԡ�,��������ַ��� ����ʱע�����ͳ�ʼ�����������ͣ� �磺List<Integer> intList =
	 * new ArrayList<Integer>(); ���÷�����StringUtil.listTtoString(intList);
	 * Ч�ʣ�list��4����Ϣ��1000000�ε���ʱ��Ϊ850ms����
	 * 
	 * @serialData 2008-01-09
	 * @param <T>
	 *            ����
	 * @param list
	 *            list�б�
	 * @return �ԡ�,��������ַ���
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
	 * ����������ת�����ԡ�,��������ַ���
	 * 
	 * @serialData 2008-01-08
	 * @param a
	 *            ����a
	 * @return �ԡ�,��������ַ���
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
	 * �ж����������ظ�
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
	 * AsciiתΪChar
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
	 * ���ֽ���ת����16����
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
	 * ��16����ת�����ֽ���
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
	 * �ж��Ƿ�λ����,����Ϊ��
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
	 * ҳ��ķǷ��ַ����
	 * 
	 * @date 2007-11-29
	 * @param str
	 * @return
	 */
	public static String replaceStr(String str) {
		if (str != null && str.length() > 0) {
			str = str.replaceAll("~", ",");
			str = str.replaceAll(" ", ",");
			str = str.replaceAll("��", ",");
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
			str = str.replaceAll("��", ",");
			str = str.replaceAll("`", ",");
			str = str.replaceAll("��", ",");
			str = str.replaceAll("��", ",");
			str = str.replaceAll("��", ",");
			str = str.replaceAll("��", ",");
			str = str.replaceAll("��", ",");
			str = str.replaceAll("��", ",");
			str = str.replaceAll("��", ",");
			str = str.replaceAll("��", ",");
			str = str.replaceAll("��", ",");
			str = str.replaceAll("��", ",");
			str = str.replaceAll("��", ",");
			str = str.replaceAll("��", ",");
			str = str.replaceAll("��", ",");
			str = str.replaceAll("��", ",");
			str = str.replaceAll("��", ",");
			str = str.replaceAll("��", ",");
			str = str.replaceAll("��", ",");
			str = str.replaceAll("��", ",");
			str = str.replaceAll("��", ",");
			str = str.replaceAll("��", ",");
			str = str.replaceAll("��", ",");
			str = str.replaceAll("��", ",");
			str = str.replaceAll("��", ",");
			str = str.replaceAll("��", ",");
			str = str.replaceAll("��", ",");
			str = str.replaceAll("��", ",");
			str = str.replaceAll("��", ",");
			str = str.replaceAll("��", ",");
			str = str.replaceAll("��", ",");
			str = str.replaceAll("��", ",");
			str = str.replaceAll("��", ",");
			str = str.replaceAll("��", ",");
			str = str.replaceAll("����", ",");
			str = str.replaceAll("��", ",");
			str = str.replaceAll("��", ",");
			str = str.replaceAll("����", ",");
			str = str.replaceAll("-", ",");
			str = str.replaceAll("��", ",");
			str = str.replaceAll("��", ",");
			str = str.replaceAll("��", ",");
			str = str.replaceAll("��", ",");
			str = str.replaceAll("��", ",");
			str = str.replaceAll("��", ",");
			str = str.replaceAll("��", ",");
			str = str.replaceAll("��", ",");
			str = str.replaceAll("��", ",");
		}
		return str;
	}
	
	/**
	 * ȡurl���keyword����ѡ�������������������վ��������
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
	 * �����ַ�������map��ֵ��(����a=1&b=2 => a=1,b=2)
	 * 
	 * @param query
	 *            Դ�����ַ���
	 * @param split1
	 *            ��ֵ��֮��ķָ���������&��
	 * @param split2
	 *            key��value֮��ķָ���������=��
	 * @param dupLink
	 *            �ظ��������Ĳ���ֵ֮������ӷ������Ӻ���ַ�����Ϊ�ò����Ĳ���ֵ����Ϊnull
	 *            null���������ظ����������֣��򿿺�Ĳ���ֵ�Ḳ�ǵ���ǰ�Ĳ���ֵ��
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
	 * ��list �ô���ķָ�����װΪString
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
	 * ���ݴ���ķָ����,�Ѵ�����ַ����ָ�ΪList�ַ���
	 * 
	 * @param slipStr
	 *            �ָ����ַ���
	 * @param src
	 *            �ַ���
	 * @return �б�
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
	 * �ж��Ƿ��ǿ��ַ��� null��"" ������ true
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
