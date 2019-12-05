package cn.center.tool;

/**
 * unicode编码工具类
 * @author :陈进松
 * @date :2019年9月19日 下午4:53:30
 */
public class UnicodeUtils {
	public static void main(String[] args) {
		String s = "{\"errno\":0,\"request_id\":6050859632110054201,\"total_count\":1,\"fans_list\":[{\"type\":1,\"fans_uname\":\"\\u968f**\\u98ce\\u96e8\",\"avatar_url\":\"https:\\/\\/ss0.bdstatic.com\\/7Ls0a8Sm1A5BphGlnYG\\/sys\\/portrait\\/item\\/netdisk.1.1e7ad6fa.ptNlT55npZYY58nSDoEpOg.jpg\",\"intro\":\"\",\"user_type\":0,\"is_vip\":0,\"follow_count\":0,\"fans_count\":0,\"follow_time\":1497414684,\"pubshare_count\":0,\"fans_uk\":2937837205,\"album_count\":0,\"fans_suk\":\"JGMm-EDIeH64ep78OyOB3A\"}]}";
		System.out.println(decodeUnicode(s));

	}

	/**
	 * 中文转unicode编码
	 */
	public static String gbEncoding(final String gbString) {
		char[] utfBytes = gbString.toCharArray();
		String unicodeBytes = "";
		for (int i = 0; i < utfBytes.length; i++) {
			String hexB = Integer.toHexString(utfBytes[i]);
			if (hexB.length() <= 2) {
				hexB = "00" + hexB;
			}
			unicodeBytes = unicodeBytes + "\\u" + hexB;
		}
		return unicodeBytes;
	}

	/**
	 * unicode编码转中文
	 * <br>
	 * 优化过
	 */
	public static String decodeUnicode(final String dataStr) {
		int start = 0;
		int end = 0;
		final StringBuffer buffer = new StringBuffer();
		while (start > -1) {
			end = dataStr.indexOf("\\u", start);
			if (end == -1) {
				buffer.append(dataStr.substring(start, dataStr.length())).toString();
				break;
			}

			buffer.append(dataStr.substring(start, end));
			String charStr = dataStr.substring(end + 2, end + 6);
			char letter = (char) Integer.parseInt(charStr, 16); // 16进制parse整形字符串。
			buffer.append(new Character(letter).toString());
			start = end + 6;
		}
		return buffer.toString();
	}

}