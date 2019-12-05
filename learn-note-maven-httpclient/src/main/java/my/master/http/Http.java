package my.master.http;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * HttpURLConnection方式请求
 * @author :陈进松
 * @date :2019年9月19日 下午4:14:31
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class Http {
	private static Logger logger = LoggerFactory.getLogger(Http.class);
	
	/**
	 * <strong>get请求</strong><br>
	 * @param url 请求地址
	 * @param param 请求参数
	 * @param header 请求头
	 * @param connectTimeout 请求超时时间
	 * @param SocketTimeout 请求返回超时时间
	 * @return String 返回状态是200返回response body 否则为null
	 */
	public static String doGet(String url, Map param,Map header,int connectTimeout,int SocketTimeout) throws Exception {
		String resultString = null;
		HttpURLConnection conn = null;
		BufferedReader reader = null;
		try {
			System.setProperty("sun.net.client.defaultConnectTimeout", String.valueOf(connectTimeout));//连接主机的超时时间（单位：毫秒） 
			System.setProperty("sun.net.client.defaultReadTimeout", String.valueOf(SocketTimeout));//从主机读取数据的超时时间（单位：毫秒）
			
			// 创建uri
			URIBuilder builder = new URIBuilder(url);
			if (param != null) {
				for (String key : (Set<String>)param.keySet()) {
					builder.addParameter(key, String.valueOf(param.get(key)));
				}
			}
			URI Uri = builder.build();
			URL Url = Uri.toURL();// 服务器的域名
			conn = (HttpURLConnection) Url.openConnection();// 打开连接
			
			conn.setDoInput(true);// 设置是否从httpUrlConnection读入，默认情况下是true;
			conn.setUseCaches(false);// Post 请求不能使用缓存
			conn.setInstanceFollowRedirects(true);
			
			// 设置请求头参数
			if (header != null) {
				for (String key : (Set<String>)header.keySet()) {
					conn.setRequestProperty(key, String.valueOf(header.get(key)));
				}
			}else {
				conn.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
				conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
				conn.setRequestProperty("connection", "Keep-Alive");
				conn.setRequestProperty("Charsert", "UTF-8");			
				conn.setRequestProperty("Upgrade-Insecure-Requests", "1");			
			}
			conn.connect();// 连接，从Url.openConnection()至此的配置必须要在connect之前完成
			
//		 	定义BufferedReader输入流来读取URL的响应
			reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			StringBuffer buffer = new StringBuffer();
			String line = null;
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			resultString = buffer.toString();
			reader.close();
			conn.disconnect();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new Exception("请求【"+url+"】异常："+e.getMessage());
		} finally {
			if (reader != null) {
				reader.close();
			}
			if (conn != null) {
				conn.disconnect();
			}
		}
		return resultString;
	}
	
	/**
	 * <strong>post请求  模拟表单提交</strong><br>
	 * 使用HttpClient4.5.X
	 * @param url 请求地址
	 * @param param 请求参数
	 * @param header 请求头
	 * @param connectTimeout 请求超时时间
	 * @param SocketTimeout 请求返回超时时间
	 * @return String 返回状态是200返回response body 否则为null
	 */
	public static String doPost(String url, Map param,Map header,int connectTimeout,int SocketTimeout) throws Exception {
		String resultString = null;
		HttpURLConnection conn = null;
		BufferedReader reader = null;
		try {
			System.setProperty("sun.net.client.defaultConnectTimeout", String.valueOf(connectTimeout));//连接主机的超时时间（单位：毫秒） 
			System.setProperty("sun.net.client.defaultReadTimeout", String.valueOf(SocketTimeout));//从主机读取数据的超时时间（单位：毫秒）
			
			URL Url = new URL(url);// 服务器的域名
			conn = (HttpURLConnection) Url.openConnection();// 打开连接
			
			conn.setRequestMethod("POST");// 默认是 GET方式// 设置为POST
			// 设置是否向connection输出// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);// http正文内，因此需要设为true，默认情况下是false;
						
			conn.setDoInput(true);// 设置是否从httpUrlConnection读入，默认情况下是true;
			conn.setUseCaches(false);// Post 请求不能使用缓存
			conn.setInstanceFollowRedirects(true);
			
			// 设置请求头参数
			if (header != null) {
				for (String key : (Set<String>)header.keySet()) {
					conn.setRequestProperty(key, String.valueOf(header.get(key)));
				}
			}else {
				conn.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
				conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
				conn.setRequestProperty("connection", "Keep-Alive");
				conn.setRequestProperty("Charsert", "UTF-8");			
				conn.setRequestProperty("Upgrade-Insecure-Requests", "1");			
			}
			conn.connect();// 连接，从Url.openConnection()至此的配置必须要在connect之前完成
			
			
			if (param != null) {
				//数据形式：a=1&b=2
				List<NameValuePair> paramList = new ArrayList<NameValuePair>();
				for (String key : (Set<String>)param.keySet()) {
					paramList.add(new BasicNameValuePair(key, String.valueOf(param.get(key))));
				}
				// 模拟表单
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList);
				entity.writeTo(conn.getOutputStream());
			}
			
//		 	定义BufferedReader输入流来读取URL的响应
			reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			StringBuffer buffer = new StringBuffer();
			String line = null;
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			resultString = buffer.toString();
			reader.close();
			conn.disconnect();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new Exception("请求【"+url+"】异常："+e.getMessage());
		} finally {
			if (reader != null) {
				reader.close();
			}
			if (conn != null) {
				conn.disconnect();
			}
		}
		return resultString;
	}
	
	public static String doPostFile(String url, String fileName, Map header,int connectTimeout,int SocketTimeout) throws Exception {
		OutputStream out = null;
		String resultString = null;
		HttpURLConnection conn = null;
		BufferedReader reader = null;
		InputStream in = null;
		try {
			System.setProperty("sun.net.client.defaultConnectTimeout", String.valueOf(connectTimeout));//连接主机的超时时间（单位：毫秒） 
			System.setProperty("sun.net.client.defaultReadTimeout", String.valueOf(SocketTimeout));//从主机读取数据的超时时间（单位：毫秒）
			
			URL Url = new URL(url);// 服务器的域名
			conn = (HttpURLConnection) Url.openConnection();// 打开连接
			
			conn.setRequestMethod("POST");// 默认是 GET方式// 设置为POST
			// 设置是否向connection输出// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);// http正文内，因此需要设为true，默认情况下是false;
			
			conn.setDoInput(true);// 设置是否从httpUrlConnection读入，默认情况下是true;
			conn.setUseCaches(false);// Post 请求不能使用缓存
			conn.setInstanceFollowRedirects(true);
			
			// 设置请求头参数
			if (header != null) {
				for (String key : (Set<String>)header.keySet()) {
					conn.setRequestProperty(key, String.valueOf(header.get(key)));
				}
			}else {
				conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
				conn.setRequestProperty("connection", "Keep-Alive");
				conn.setRequestProperty("Charsert", "UTF-8");			
			}
			conn.connect();// 连接，从Url.openConnection()至此的配置必须要在connect之前完成
			
			
			out = new DataOutputStream(conn.getOutputStream());//connection.getOutputStream会隐含的进行connect。
			// 数据输入流,用于读取文件数据
			in = new DataInputStream(new FileInputStream(new File(fileName)));
			byte[] bufferOut = new byte[1024];
			int bytes = 0;
			// 每次读1KB数据,并且将文件数据写入到输出流中
			while ((bytes = in.read(bufferOut)) != -1) {
				out.write(bufferOut, 0, bytes);
			}
			out.flush();
	        out.close();
	        
//			 定义BufferedReader输入流来读取URL的响应
			reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			StringBuffer buffer = new StringBuffer();
			String line = null;
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			resultString = buffer.toString();
			in.close();
			reader.close();
			conn.disconnect();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new Exception("请求【"+url+"】异常："+e.getMessage());
		} finally {
			if (out != null) {
				out.close();
			}
			if (in != null) {
				in.close();
			}
			if (reader != null) {
				reader.close();
			}
			if (conn != null) {
				conn.disconnect();
			}
		}
		return resultString;
	}
}
