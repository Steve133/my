package cn.center.RSA;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.center.DES.HexUtils;


/**
 * RSA加密通用类
 */
public class RSAUtils{
	private static Logger logger = LoggerFactory.getLogger(RSAUtils.class);
	
	public static final String CHARSET = "UTF-8";
    /**
     * 密钥算法
     */
    public static final String ALGORITHM_RSA = "RSA";
    /**
     * RSA 签名算法
     * <br/>
     * SHA256WithRSA
     * MD5withRSA
     * SHA1WithRSA
     */
    public static final String ALGORITHM_RSA_SIGN = "SHA256WithRSA";
    
    public static final int ALGORITHM_RSA_PRIVATE_KEY_LENGTH = 1024;
    
    /**
     * 初始化RSA算法密钥对
     * @param keysize RSA1024已经不安全了,建议2048
     * @return 经过Base64编码后的公私钥Map, 键名分别为publicKey和privateKey
     */
    public static Keys initRSAKey(int keysize) {
//        if (keysize != ALGORITHM_RSA_PRIVATE_KEY_LENGTH) {
//            throw new IllegalArgumentException("RSA1024已经不安全了,请使用" + ALGORITHM_RSA_PRIVATE_KEY_LENGTH + "初始化RSA密钥对");
//        }
        //为RSA算法创建一个KeyPairGenerator对象
        KeyPairGenerator kpg;
        try {
            kpg = KeyPairGenerator.getInstance(ALGORITHM_RSA);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException("No such algorithm-->[" + ALGORITHM_RSA + "]");
        }
        //初始化KeyPairGenerator对象,不要被initialize()源码表面上欺骗,其实这里声明的size是生效的
        kpg.initialize(ALGORITHM_RSA_PRIVATE_KEY_LENGTH);
        //生成密匙对
        KeyPair keyPair = kpg.generateKeyPair();
        //得到公钥
//      RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        Key publicKey = keyPair.getPublic();
        System.out.println("（已经Base64加密）公钥\npublicKey="+Base64.encodeBase64String(publicKey.getEncoded()));
        //得到私钥
//      RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        Key privateKey = keyPair.getPrivate();
        System.out.println("（已经Base64加密）私钥\nprivateKey="+Base64.encodeBase64String(privateKey.getEncoded()));
        return new Keys(publicKey, privateKey);
    }
    
    /**
     * 密钥对
     */
    public static class Keys {

        private Key publicKey;
        private Key privateKey;

        Keys(Key publicKey, Key privateKey) {
            this.publicKey = publicKey;
            this.privateKey = privateKey;
        }

        /**
         * 获取公钥字符串
         * @return
         */
        public String getPublicKeyAsBase64() {
            return Base64.encodeBase64String(publicKey.getEncoded());
        }
        /**
         * 获取私钥字符串
         * @return
         */
        public String getPrivateKeyAsBase64() {
            return Base64.encodeBase64String(privateKey.getEncoded());
        }
    }
    
    /**
     * Base64公钥字符串【转】公钥对象
     * @param pubk
     * @return
     * @throws Exception
     */
    public static Key getPublicKeyFromX509(String pubk) throws Exception {
    	byte[] encodedKey = Base64.decodeBase64(pubk);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(encodedKey);
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_RSA);
        Key publicKey = keyFactory.generatePublic(x509KeySpec);
    	return publicKey;
    }
    /**
     * Base64私钥字符串【转】私钥对象
     * @param algorithm
     * @param prik
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unused")
	private static Key getPrivateKeyFromPKCS8(String prik) throws Exception {
    	byte[] encodedKey = Base64.decodeBase64(prik);
    	PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(encodedKey);
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_RSA);
        Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
        return privateKey;
    }
    
    
    //=====16进制编码 加密解密方法=============================================
    /**
	 * RSA算法加密    经过hex编码的密文字符串
	 * @param data
	 * @param key
	 * @return 经过BigInteger编码的密文字符串
	 * @throws Exception
	 */
    public static String encrypt_hex(String data,Key key) throws Exception {
		Cipher cipher = Cipher.getInstance(ALGORITHM_RSA);
		cipher.init(Cipher.ENCRYPT_MODE, key);
		byte[] miwen = cipher.doFinal(  data.getBytes(CHARSET)  );
		return HexUtils.byte2hex(miwen);
	}
    public static String encrypt_hex(byte[] data,Key key) throws Exception {
    	Cipher cipher = Cipher.getInstance(ALGORITHM_RSA);
    	cipher.init(Cipher.ENCRYPT_MODE, key);
    	byte[] miwen = cipher.doFinal(  data  );
    	return HexUtils.byte2hex(miwen);
    }

	/**
	 * RSA算法解密   经过hex解码密文的字符串
	 * @param data 十六进制串
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] decrypt_hex(String data,Key key) throws Exception {
		byte[] miwen = HexUtils.hex2byte(data);
		Cipher cipher = Cipher.getInstance(ALGORITHM_RSA);
		cipher.init(Cipher.DECRYPT_MODE, key);
		byte[] result = cipher.doFinal(miwen);
		return result;
	}
    
	
    //=====BigInteger编码 加密解密方法=============================================
	/**
	 * RSA算法加密    经过BigInteger编码的密文字符串
	 * @param data
	 * @param key
	 * @return 经过BigInteger编码的密文字符串
	 * @throws Exception
	 */
    public static String encrypt_BigInteger(String data,Key key) throws Exception {
		Cipher cipher = Cipher.getInstance(ALGORITHM_RSA);
		cipher.init(Cipher.ENCRYPT_MODE, key);
		byte[] miwen = cipher.doFinal(data.getBytes(CHARSET));
		return String.valueOf(new BigInteger(miwen));
	}

	/**
	 * RSA算法解密   经过BigInteger解码密文的字符串
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String decrypt_BigInteger(String data,Key key) throws Exception {
		byte[] miwen = new BigInteger(data).toByteArray();
		Cipher cipher = Cipher.getInstance(ALGORITHM_RSA);
		cipher.init(Cipher.DECRYPT_MODE, key);
		byte[] result = cipher.doFinal(miwen);
		return new String(result);
	}
	
    //=====Base64编码 加密解密方法=============================================
	/**
     * RSA算法	公钥加密数据
     * @param data 待加密的明文字符串
     * @param pubk  RSA公钥字符串
     * @return RSA公钥加密后的经过Base64编码的密文字符串
	 * @throws Exception 
     */
    public static String RSAEncryptByPublicKey(String data, String pubk) throws Exception {
        try {
            //通过X509编码的Key指令获得公钥对象
        	byte[] encodedKey = Base64.decodeBase64(pubk);
            X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(encodedKey);
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_RSA);
            Key publicKey = keyFactory.generatePublic(x509KeySpec);
            
            
            Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
//          方法1.分段加解密  与方法2效果相同
//            return Base64.encodeBase64String(rsaSplitCodec(cipher, Cipher.ENCRYPT_MODE, data.getBytes(CHARSET)));
//          方法2.cipher.doFinal加解密  与方法1效果相同
            return Base64.encodeBase64String(cipher.doFinal(data.getBytes(CHARSET)));
        } catch (Exception e) {
            throw new Exception("加密字符串[" + data + "]时遇到异常", e);
        }
    }
    /**
     * RSA算法	公钥解密数据
     *
     * @param data 待解密的经过Base64编码的密文字符串
     * @param pubk  RSA公钥字符串
     * @return RSA公钥解密后的明文字符串
     * @throws Exception 
     */
    public static String RSADecryptByPublicKey(String data, String pubk) throws Exception {
        try {
            //通过X509编码的Key指令获得公钥对象
            X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(Base64.decodeBase64(pubk));
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_RSA);
            Key publicKey = keyFactory.generatePublic(x509KeySpec);
            
            
            Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
          //方法1.分段加解密  与方法2效果相同
            return new String(rsaSplitCodec(cipher, Cipher.DECRYPT_MODE, Base64.decodeBase64(data)), CHARSET);
          //方法2.cipher.doFinal加解密  与方法1效果相同
//            byte[] encryptedData = Base64.decodeBase64(data);
//            byte[] doFinal = cipher.doFinal(encryptedData);
//            return new String(doFinal, CHARSET);
        } catch (Exception e) {
            throw new Exception("解密字符串[" + data + "]时遇到异常", e);
        }
    }
    
    
    /**
     * RSA算法	私钥加密数据
     * @param data 待加密的明文字符串
     * @param prik  RSA私钥字符串
     * @return RSA私钥加密后的经过Base64编码的密文字符串
     * @throws Exception 
     */
    public static String RSAEncryptByPrivateKey(String data, String prik) throws Exception {
        try {
            //通过PKCS#8编码的Key指令获得私钥对象
            PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(prik));
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_RSA);
            Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
            Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            
//            方法1.分段加解密  与方法2效果相同
            return Base64.encodeBase64String(rsaSplitCodec(cipher, Cipher.ENCRYPT_MODE, data.getBytes(CHARSET)));
//          方法2.cipher.doFinal加解密  与方法1效果相同
//            return Base64.encodeBase64String(cipher.doFinal(data.getBytes(CHARSET)));
        } catch (Exception e) {
            throw new Exception("加密字符串[" + data + "]时遇到异常", e);
        }
    }
    /**
     * RSA算法	私钥解密数据
     * @param data 待解密的经过Base64编码的密文字符串
     * @param prik  RSA私钥字符串
     * @return RSA私钥解密后的明文字符串
     * @throws Exception 
     */
    public static String RSADecryptByPrivateKey(String data, String prik) throws Exception {
        try {
            //通过PKCS#8编码的Key指令获得私钥对象
            PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(prik));
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_RSA);
            Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
            Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(Cipher.DECRYPT_MODE, privateKey);

            //方法1.分段加解密  与方法2效果相同
            return new String(rsaSplitCodec(cipher, Cipher.DECRYPT_MODE, Base64.decodeBase64(data)), CHARSET);
            //方法2.cipher.doFinal加解密  与方法1效果相同
//            byte[] encryptedData = Base64.decodeBase64(data);
//            byte[] doFinal = cipher.doFinal(encryptedData);
//            return new String(doFinal, CHARSET);
        } catch (Exception e) {
            throw new Exception("解密字符串[" + data + "]时遇到异常", e);
        }
    }
    
    
    /**
     * RSA算法	数字签名
     *
     * @param data 待签名的明文字符串
     * @param prik  RSA私钥字符串
     * @return RSA私钥签名后的经过Base64编码的字符串
     * @throws Exception 
     */
    public static String RSASignByPrivateKey(String data, String prik) throws Exception {
        try {
            //通过PKCS#8编码的Key指令获得私钥对象
            PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(prik));
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_RSA);
            PrivateKey privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
            
            Signature signature = Signature.getInstance(ALGORITHM_RSA_SIGN);
            signature.initSign(privateKey);
            signature.update(data.getBytes(CHARSET));
            return Base64.encodeBase64String(signature.sign());
        } catch (Exception e) {
            throw new Exception("签名字符串[" + data + "]时遇到异常", e);
        }
    }
    /**
     * RSA算法	校验数字签名
     *
     * @param data 参与签名的明文字符串
     * @param pubk  RSA公钥字符串
     * @param sign RSA签名得到的经过Base64编码的字符串
     * @return true--验签通过,false--验签未通过
     * @throws Exception 
     */
    public static boolean RSAverifyByPublicKey(String data, String pubk, String sign) throws Exception {
        try {
            //通过X509编码的Key指令获得公钥对象
            X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(Base64.decodeBase64(pubk));
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_RSA);
            PublicKey publicKey = keyFactory.generatePublic(x509KeySpec);
            
            Signature signature = Signature.getInstance(ALGORITHM_RSA_SIGN);
            signature.initVerify(publicKey);
            signature.update(data.getBytes(CHARSET));
            return signature.verify(Base64.decodeBase64(sign));
        } catch (Exception e) {
            throw new Exception("验签字符串[" + data + "]时遇到异常", e);
        }
    }
    
    
    /**
     * RSA算法分段加解密数据
     * @param cipher 初始化了加解密工作模式后的javax.crypto.Cipher对象
     * @param opmode 加解密模式,值为javax.crypto.Cipher.ENCRYPT_MODE/DECRYPT_MODE
     * @return 加密或解密后得到的数据的字节数组
     */
    private static byte[] rsaSplitCodec(Cipher cipher, int opmode, byte[] datas) throws Exception {
    	//RSA最大加解密明文大小
    	int maxBlock = 0;
        if (opmode == Cipher.DECRYPT_MODE) {
            maxBlock = ALGORITHM_RSA_PRIVATE_KEY_LENGTH / 8;
        } else {
            maxBlock = ALGORITHM_RSA_PRIVATE_KEY_LENGTH / 8 - 11;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] buff;
        int i = 0;
        try {
            while (datas.length > offSet) {
                if (datas.length - offSet > maxBlock) {
                    buff = cipher.doFinal(datas, offSet, maxBlock);
                } else {
                    buff = cipher.doFinal(datas, offSet, datas.length - offSet);
                }
                out.write(buff, 0, buff.length);
                i++;
                offSet = i * maxBlock;
            }
        } catch (Exception e) {
            throw new RuntimeException("加解密阀值为[" + maxBlock + "]的数据时发生异常", e);
        }
        byte[] resultDatas = out.toByteArray();
        IOUtils.closeQuietly(out);
        return resultDatas;
    }
    
    
    
    //=====密钥*文件=============================================
    /**
     * 将密钥写入文件
     * @param path 密钥路径  D:/RSA/public.key
     * @param key
     */
	@SuppressWarnings("unused")
	private static void write(String path, Key key) throws Exception {
		File file = new File(path);
		if (!file.getParentFile().exists()) {
			boolean creat = file.getParentFile().mkdirs();
			if (!creat) {
				System.out.println("创建文件目录异常！");
				return;
			}
		}
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(new FileOutputStream(path));
			oos.writeObject(key);

			if (null != oos)
				try {
					oos.close();
				} catch (IOException e) {
					oos = null;
				}
		} catch (Exception e) {
			logger.error("密钥写入异常", e);

			if (null != oos)
				try {
					oos.close();
				} catch (IOException ee) {
					oos = null;
				}
		} finally {
			if (null != oos)
				try {
					oos.close();
				} catch (IOException e) {
					oos = null;
				}
		}
	}
    
    /**
     * 读取本地密钥文件
     * @param path 密钥路径 D:/RSA/public.key
     * @return
     */
    public static Key resolveKey(String path) throws Exception {
		Key key = null;
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		try {
			fis = FileUtils.openInputStream(new File(path));
			ois = new ObjectInputStream(fis);
			key = (Key) ois.readObject();
			return key;
		} catch (FileNotFoundException e) {
			logger.error("私钥文件找不到", e);
		} catch (IOException e) {
			logger.error("文件输入错误", e);
		} catch (ClassNotFoundException e) {
			logger.error("类文件找不到", e);
		} catch (Exception e) {
			logger.error("解析异常", e);
		} finally {
			IOUtils.closeQuietly(ois);
			IOUtils.closeQuietly(fis);
		}
		return null;
	}
    
    
    
    
    public static void main(String[] args) throws Exception {
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
//    	String pubk = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCgZxPUT8MSwetS7KX8zplah8Pb1JyLDKZCZGMiR6vSJMQ3ppfG-OE59bsRAUpBkPrpLKbdUTGGMsRhMl55Hv6x5nqkcmg5pkUhXWvqnM39J2EXJcDM_rImyl8E75hVmY-1rYow3DsEmiMFbOb0dsmDjNcirFBG8M6dybfrHbUiqQIDAQABMIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCgZxPUT8MSwetS7KX8zplah8Pb1JyLDKZCZGMiR6vSJMQ3ppfG-OE59bsRAUpBkPrpLKbdUTGGMsRhMl55Hv6x5nqkcmg5pkUhXWvqnM39J2EXJcDM_rImyl8E75hVmY-1rYow3DsEmiMFbOb0dsmDjNcirFBG8M6dybfrHbUiqQIDAQAB";
//		String prik = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCDYYuVbgIds5YBnkv2VtpFbC4Bb4tDgSKNcRtKQALr1ybDXbUv7Iqoiqb1IcceuN9iEIi9D3h2zF/shQtYwMP5ztoBFHoqWbdPDlDM/KcK0FDHZ925zqlTeMwyW6BqDrDY3wK+V3HflmZmPOmYO46FJmzAhQekITVb8saUvxFkYn2/Yh0OnUqNQJ+REu2Z1gKIJoA4uNde5Ss81Ea4A4IO0qKKRc+mmj08L0VWwFt6IQTwetHYmYFKxdl19lsqzmxfETEhZGtrbpCDvfsUqRPyAuoCTJrJWO8jAKb5EzNbbiGn8ZLNwJfUp4pGnW1wTsPrWNZb1V0FROA5KSCjlmUbAgMBAAECggEAOpS2Do0cuG2pB1Oz/0U+0hGoec9ow1LDO1ohlvvv0YT/rcEM1y+brAcW5tqG1LYONMW2ksZhKmd6phW0t4BMYgjPwAeHaXbTe5aghKZrPTEhs6gmf4EckzGbqyCb6G5ggHL/4CtRhSakxhWG+vAjXk+B0l3+yDpedn9L+GsxsBZmOAb2I9L+3InSfTOq93ZENpcNBtVYj+2X5/j7dkJOCNBgS/BClK6ZW5NxPsE5rzA3Zwa0PFUXbb12XVg3hv2F2+wn+MDtN0uSUk1NHPdeEMMv7DsPGOZATFhOgt3USdMAsJYDzHiVaniRbka/62E66qTGVtNPhk/yZBZWXdNLUQKBgQD+DM/9zR2A+UENP+1WlZ14x2qrvubA2GsVJmRe8MeUS3736Kk/mNHfbqQIZKeG24rxXLexlVGoO1grM7jheX535zQBR/FB9mfx6Tfi//21UXMolhxVUEkdBsf+164o8nMnzP4JovPqGVZdPfDsvJPeNMbpF3U8vQb8qFfplZaQPwKBgQCEY7K+rEet1DB/s15SUveXJiJrR8/0qdm8MOLZMJuS1iQNV5SNESdmdO7WiJBJAhbQ4Zb6vb27SOPBDUBb04ETlygOJgJKqFaNzVIURr2HqQxb9p0eNWa28oiQEFOCbqnU3zhj4M4HXDIKuGQNL0dF+ZK94ExgdxAHupS+9sl0JQKBgQDK0K+7juTe8h5c1YxEc20j3w/pzJ2rlxrl56B5qv+qBBt5DuUPQJFMLqgFi1Wy89IYnkyIGQz3oWjiFIikeLneP1XlL+oLHwwctHNJZdTFzCCDZlN4SIAWDDPd35HKXsRQIfcs+kp/uXqZNboBRQtHehrSO0f9FWteiYallCZ/QQKBgBZICU9aA35YFgmIELneSNlhi61umqtc4s5vk95l9ekSrWKpeND9MBpoV8I3ncEL3vcs4JI22PXqYJqfNlO+Fx1K9WzcLMqP5nFbOxM6jK+GXhQkP3FUH4Nu8lj8xnFPWrn/D6iPr11BwRJY0k39xQsb4/ydAX3CzCng1yQEuQcZAoGBAPe7el4KZxX9zx+uwsj55yJN/p8FfPNDhAjbx6CK07jxTRHbouLniFmCYkyLeEjzTym/mI5qHxYWSLclHd5REPBLsmq1kW/4jdliDBVenh6ymu5ALSbo9Qf2u7KZU5JtALN06BNXN+xhs2kRVD3T6YJwlBaBwXhD5RfhsY0kAy5N";
//		
//		String data = "{\"a\":\"1\",\"b\":\"2\",\"c\":\"3\"}";
//    	
//    	String rsaEncryptByPublicKey = RSAEncryptByPublicKey(data, pubk);
//    	System.out.println("加密数据："+rsaEncryptByPublicKey);
//    	String rsaDecryptByPrivateKey = RSADecryptByPrivateKey(rsaEncryptByPublicKey, prik);
//    	System.out.println("解密："+rsaDecryptByPrivateKey);
//    	
//    	
//    	String rsaEncryptByPrivateKey = RSAEncryptByPrivateKey(data, prik);
//    	System.out.println("加密数据："+rsaEncryptByPrivateKey);
//    	String rsaDecryptByPublicKey = RSADecryptByPublicKey(rsaEncryptByPrivateKey, pubk);
//    	System.out.println("解密："+rsaDecryptByPublicKey);
//    	
//    	
//    	rsaEncryptByPublicKey = encrypt_BigInteger(data, getPublicKeyFromX509(pubk));
//    	System.out.println("加密数据："+rsaEncryptByPublicKey);
//    	rsaDecryptByPrivateKey = decrypt_BigInteger(rsaEncryptByPublicKey, getPrivateKeyFromPKCS8(prik));
//    	System.out.println("解密："+rsaDecryptByPrivateKey);
//    	
//    	data = DESedeUtils.generateStringByKey(32);
//    	System.out.println("\ndata："+data);
//    	byte[] data_hex = HexUtils.hex2byte(data);
//    	rsaEncryptByPublicKey = encrypt_hex(data_hex, getPublicKeyFromX509(pubk));
//    	System.out.println("加密数据："+rsaEncryptByPublicKey);
//    	byte[] decrypt_hex = decrypt_hex(rsaEncryptByPublicKey, getPrivateKeyFromPKCS8(prik));
//    	System.out.println("解密："+HexUtils.byte2hex(decrypt_hex));
    	
    	
    	//=====初始化密钥=========================
    	Keys initRSAKey = initRSAKey(ALGORITHM_RSA_PRIVATE_KEY_LENGTH);
    	
    	//=====密钥写入文件====================
    	//1生成密钥
    	Key privateKey = initRSAKey.privateKey;
    	Key publicKey = initRSAKey.publicKey;
    	/*//2写入文件
    	write("D:/private.key", privateKey);
    	write("D:/public.key", publicKey);
    	//3读取文件
    	Key priKey = resolveKey("D:/private.key");
    	Key pubKey = resolveKey("D:/public.key");*/
//    	//输出
    	System.out.println("priKey:" + Base64.encodeBase64URLSafeString(privateKey.getEncoded()));
    	System.out.println("pubKey:" + Base64.encodeBase64URLSafeString(publicKey.getEncoded()));
	}
}
