package cn.center.DES;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class HexUtils {
	private static Logger logger = LoggerFactory.getLogger(HexUtils.class);

	/**
	 * 十六进制串【转】byte数组
	 * 
	 * @return the array of byte
	 */
	public static final byte[] hex2byte(String hex) throws Exception {
		if (hex.length() % 2 != 0) {
			throw new IllegalArgumentException("不合法十六进制串：" + hex);
		}
		try {
			char[] arr = hex.toCharArray();
			byte[] b = new byte[hex.length() / 2];
			for (int i = 0, j = 0, l = hex.length(); i < l; i++, j++) {
				String swap = "" + arr[i++] + arr[i];
				int byteint = Integer.parseInt(swap, 16) & 0xFF;
				b[j] = new Integer(byteint).byteValue();
			}
			return b;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new Exception("hex2byte(hex) 异常：" + e.getMessage());
		}
	}

	/**
	 * 字节数组【转】十六进制字符串
	 * 
	 * @param b byte[] 需要转换的字节数组
	 * @return String 十六进制字符串
	 * @throws Exception
	 */
	public static final String byte2hex(byte[] b) throws Exception {
		if (b == null) {
			throw new IllegalArgumentException("字节数组  is null! ");
		}
		try {
			String ret = "";
			for (int n = 0; n < b.length; n++) {
				String stmp = Integer.toHexString(b[n] & 0xff);//MD5加密后存在负数，取与后，转16进制，少位前补0
				if (stmp.length() == 1) {
					ret = ret + "0" + stmp;
				} else {
					ret = ret + stmp;
				}
			}
			return ret.toUpperCase();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new Exception("byte2hex(b) 异常：" + e.getMessage());
		}
	}

	/**
	 * 字节数组转16进制字符串
	 * 
	 * @param b
	 * @return
	 * @throws Exception
	 */
	public static String byte2hex2(byte[] b) throws Exception {
		try {
			// 十六进制的字符
//			char[] chars = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
			final char[] hex_chars = "0123456789ABCDEF".toCharArray();

			StringBuilder sb = new StringBuilder(b.length * 2);
			// 处理成十六进制的字符串(通常)
			for (int i = 0; i < b.length; i++) {
				sb.append(hex_chars[(b[i] >> 4) & 0x0f]);//得到十六进制十位
				sb.append(hex_chars[b[i] & 0x0f]);//得到十六进制个位
			}
			return sb.toString();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new Exception("字节数组转16进制字符串 异常：" + e.getMessage());
		}
	}
}
