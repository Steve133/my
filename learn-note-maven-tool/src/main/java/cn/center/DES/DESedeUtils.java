package cn.center.DES;

import java.security.Key;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

import org.apache.commons.codec.binary.Base64;

/**
 * DES加密 解密算法
 * 查看过DESedeKeySpec源代码可以发现：3DES需要24字节(bytes)的秘钥
 */
public class DESedeUtils {

    /** 算法名称 */
    public static final String KEY_ALGORITHM = "DESEDE";
    /** 算法名称/加密模式/填充方式 */
    public static final String CIPHER_ALGORITHM = "DESEDE/CBC/PKCS5Padding";
    /** 字符编码 ： 字符串转字节或字节转字符串时 一定要加上编码，否则可能出现乱码*/
    private final static String ENCODE = "UTF-8";
    /** Wrong IV length: must be 8 bytes long */
    private static String DES_IV = "JM23456*";

    /** 获取秘钥对象 */
    private static SecretKey keyGenerator(String keyStr) throws Exception {

        byte input[] = HexStringToBytes(keyStr);
        // 从原始密钥数据创建DESKeySpec对象
        // DESKeySpec desKey = new DESKeySpec(input);
        DESedeKeySpec KeySpec = new DESedeKeySpec(input);
        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(KEY_ALGORITHM);
        SecretKey securekey = keyFactory.generateSecret(KeySpec);

        return securekey;
    }

    private static int parse(char c) {
        if (c >= 'a')
            return (c - 'a' + 10) & 0x0f;
        if (c >= 'A')
            return (c - 'A' + 10) & 0x0f;
        return (c - '0') & 0x0f;
    }

    /** 从十六进制字符串到字节(二进制 )数组转换 */
    private static byte[] HexStringToBytes(String hexstr) {
        byte[] results = new byte[hexstr.length() / 2];

        for (int i = 0; i < results.length; i++) {
            char c0 = hexstr.charAt(i * 2 + 0);
            char c1 = hexstr.charAt(i * 2 + 1);
            results[i] = (byte) ((parse(c0) << 4) | parse(c1));
        }

        return results;
    }

    /** 加密数据 */
    public static String encrypt(String data,String key) throws Exception {
        // 获取秘钥对象
        Key deskey = keyGenerator(key);
        // 实例化Cipher对象，它用于完成实际的加密操作
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        // 生成初始化向量
        IvParameterSpec iv = new IvParameterSpec(DES_IV.getBytes("UTF-8"));
        // 用密钥初始化Cipher对象，设置为加密模式
        cipher.init(Cipher.ENCRYPT_MODE, deskey, iv);
        // 执行加密操作
        byte[] results = cipher.doFinal(data.getBytes(ENCODE));

        // 执行加密操作。加密后的结果通常都会用Base64编码进行传输
        return Base64.encodeBase64String(results);
    }

    public static final String ALLCHAR = "0123456789ABCDEF";
    
    /**
     * 返回一个定长的随机字符串(只包含大小写字母、数字)
     * @param length
     * @return
     */
    public static String generateStringByKey(int length) {
    	StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(ALLCHAR.charAt(random.nextInt(ALLCHAR.length())));
        }
        return sb.toString();
    }
    
    /** 解密数据 */
    public static String decrypt(String data,String key) throws Exception {
        // 获取秘钥对象
        Key deskey = keyGenerator(key);
        // 实例化Cipher对象，它用于完成实际的解密操作
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        // 生成初始化向量
        IvParameterSpec iv = new IvParameterSpec(DES_IV.getBytes("UTF-8"));
        // 初始化Cipher对象，设置为解密模式
        cipher.init(Cipher.DECRYPT_MODE, deskey, iv);
        // 16进制转byte数组
        byte[] results = HexUtils.hex2byte(data);
        // 执行解密操作
        results = cipher.doFinal(results);
        // 变成字符串
        return new String(results, ENCODE);
    }

    public static void main(String[] args) throws Exception {
    	
//    	返回解密数据：{"respCode":"00","smk":"BB1CC7E8993F7BFBB09B0D5FCBBF094F","smkCheckValue":"9007F737","merName":"中免测试","sekCheckValue":"700512BC","respMsg":"success","sek":"4744263D5754CA00B5C59D163BBF7070","procType":"23"}
//    	sek：4744263D5754CA00B5C59D163BBF7070
    	String TMK = "8C19E5F379F4F2C09321BCBFD061BCA3";
		TMK = TMK + TMK.substring(0,16);
		System.out.println("TMK："+ TMK);
		
    	
    	String sek = "4744263D5754CA00B5C59D163BBF7070";
		System.out.println("sek："+ sek);
		sek = decrypt(sek, TMK);
		System.out.println("sek："+ sek);
		
//		String smk = result.getString("smk");
//		System.out.println("smk："+ smk);
//		smk = decrypt(smk, TMK);
//		System.out.println("smk："+ smk);
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
//    	String DEFAULT_KEY = "2D4A537C5531C37A7BC61833E9C23C8A2D4A537C5531C37A";
//    	String source = "{\"acqInsCode\":\"61040116\",\"batchNo\":\"520441\",\"cardNo\":\"6214835920672688\",\"couponId\":\"751040197341380073\",\"localTime\":\"20170908152949\",\"localTimeZone\":\"GMT+08:00\",\"merCode\":\"104565309000099\",\"procType\":\"24\",\"resvFld\":\"710584000001\",\"serviceEntryMode\":\"219\",\"termId\":\"2\",\"traceNo\":\"520441\",\"transAmt\":\"10.23\",\"transCurr\":\"156\"}";
////        String source = "123456";
////        System.out.println("原文: " + source);
//        String encryptData = encrypt(source,DEFAULT_KEY);
//        System.out.println("加密后: " + encryptData);
//        
//        System.out.println(byte2hex(encryptData.getBytes()));
//        String decryptData = decrypt(encryptData);
//        System.out.println("解密后: " + decryptData);
//    	String smk = "2D4A537C5531C37A7BC61833E9C23C8A";
//		smk = smk + smk.substring(0,16);
//		System.out.println(smk);
//		byte[] encryptMode = encrypt(smk.getBytes(), a.getBytes());
//		System.out.println(byte2hex(encryptMode));
    }
}
