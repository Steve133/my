package cn.center.Base64;

import java.util.Base64;

public class Base64Test {
	
	
	
	public static void main(String[] args) throws Exception {
		final String text = "1234abcd";
		final byte[] textByte = text.getBytes("UTF-8");
		
		//编码
		String encodeBase64 = Base64.getEncoder().encodeToString(textByte);
		System.out.println("java.util.Base64：\n"+encodeBase64);
		
		encodeBase64 = org.apache.commons.net.util.Base64.encodeBase64String(textByte);
		System.out.println("org.apache.commons.net.util.Base64：\n"+encodeBase64);
		
		encodeBase64 = org.apache.commons.codec.binary.Base64.encodeBase64String(textByte);
		System.out.println("org.apache.commons.codec.binary.Base64：\n"+encodeBase64);
		
		encodeBase64 = new org.apache.commons.codec.binary.Base64().encodeAsString(textByte);
		System.out.println("org.apache.commons.codec.binary.Base64：\n"+encodeBase64);
		
		encodeBase64 = new String(it.sauronsoftware.base64.Base64.encode(textByte),"utf-8");
		System.out.println("it.sauronsoftware.base64.Base64：\n"+encodeBase64);
	}


}
