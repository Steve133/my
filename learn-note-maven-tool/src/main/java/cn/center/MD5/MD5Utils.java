package cn.center.MD5;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.center.DES.HexUtils;

public class MD5Utils {
	private static Logger logger = LoggerFactory.getLogger(MD5Utils.class);
	
	public static void main(String[] args) throws Exception {
//		new MD5Utils().test();
	}
	
	//打印字符串的byte
	private void test() {
		String s = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz+-*/%欢迎关岭";
		int i = 0;
		for (byte b : s.getBytes()) {
			int z = b&0xff;
			System.out.println(s.charAt(i)+" "+b+" "+ z);
			i++;
		}
	}

	/**
     * MD5加密<br>
     * 加密结果 字节数组 转16进制字符串
     * @param s 字符
     * @return
	 * @throws Exception 
     */
	public static String MD5toHex(String s) throws Exception {
	    try {
	        MessageDigest md = MessageDigest.getInstance("MD5");
	        byte[] bytes = md.digest(s.getBytes("utf-8"));//生成16位字符
	        return HexUtils.byte2hex(bytes);//得到32位16进制字符
	    }
	    catch (Exception e) {
	    	logger.error(e.getMessage(), e);
	        throw new Exception("MD5加密 异常："+e.getMessage());
	    }
	}
	
	
}
