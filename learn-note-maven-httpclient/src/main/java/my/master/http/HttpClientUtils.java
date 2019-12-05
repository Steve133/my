package my.master.http;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.json.JSONObject;

/**
 * HttpClient4.5.X以上<br>
 * 其他版本超时写法参考《HttpClient各版本写法说明.txt》
 * @author :陈进松
 * @date :2019年9月19日 下午1:41:35
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class HttpClientUtils {

	private static Logger logger = LoggerFactory.getLogger(HttpClientUtils.class);
	
	/**
	 * <strong>get请求</strong><br>
	 * 使用HttpClient4.5.X
	 * @param url 请求地址
	 * @param param URL路径参数
	 * @param header 请求头
	 * @param connectTimeout 请求超时时间
	 * @param connectRequestTimeout 请求线程超时时间
	 * @param SocketTimeout 请求返回超时时间
	 * @return String 返回状态是200返回response body 否则为null
	 */
	public static String doGet(String url, Map param,Map header,int connectTimeout,int connectRequestTimeout,int SocketTimeout) throws Exception {
		
		// 创建Httpclient对象
		CloseableHttpClient httpClient = HttpClients.createDefault();  
		HttpResponse response = null;
		String resultString = null;
		try {
			// 创建uri
			URIBuilder builder = new URIBuilder(url);
			if (param != null) {
				for (String key : (Set<String>)param.keySet()) {
					builder.addParameter(key, String.valueOf(param.get(key)));
				}
			}
			URI uri = builder.build();

			// 创建http GET请求
			HttpGet httpGet = new HttpGet(uri);
			if(header != null) {
				for (String key : (Set<String>)header.keySet()) {
					httpGet.addHeader(key, String.valueOf(header.get(key)));
				}
			}
			
			RequestConfig requestConfig = RequestConfig.custom()
			        .setConnectTimeout(connectTimeout)//setConnectTimeout：设置连接超时时间，单位毫秒
			        .setConnectionRequestTimeout(connectRequestTimeout)  //setConnectionRequestTimeout：设置从connect Manager(连接池)获取Connection 超时时间，单位毫秒。这个属性是新加的属性，因为目前版本是可以共享连接池的。
			        .setSocketTimeout(SocketTimeout).build();  //setSocketTimeout：请求获取数据的超时时间(即响应时间)，单位毫秒。 如果访问一个接口，多少时间内无法返回数据，就直接放弃此次调用。
			httpGet.setConfig(requestConfig);
			
			showRequestHearders(httpGet);
			// 执行请求
			response = httpClient.execute(httpGet);
			if (response.getStatusLine().getStatusCode() != 200) {
				throw new Exception("请求【"+url+"】返回状态非200："+response.getStatusLine().getStatusCode());
			}
			showResponseHearders(response);
			resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new Exception("请求【"+url+"】异常："+e.getMessage());
		}
		return resultString;
	}
	/**
	 * <strong>get请求</strong><br>
	 * 使用HttpClient4.5.X<br>
	 * header 请求头 为空<br>
	 * connectTimeout 请求超时时间<strong>默认5000毫秒</strong><br>
	 * connectRequestTimeout 请求线程超时时间<strong>默认1000毫秒</strong><br>
	 * SocketTimeout 请求返回超时时间<strong>默认5000毫秒</strong><br>
	 * @param url 请求地址
	 * @param param URL路径参数
	 * @param connectTimeout 请求超时时间
	 * @param connectRequestTimeout 请求线程超时时间
	 * @param SocketTimeout 请求返回超时时间
	 * @return String 返回状态是200返回response body 否则为null
	 * @throws Exception 
	 */
	public static String doGet(String url, Map param,int connectTimeout,int connectRequestTimeout,int SocketTimeout) throws Exception {
		return doGet(url, param, null,connectTimeout,connectRequestTimeout,SocketTimeout);
	}
	/**
	 * <strong>get请求</strong><br>
	 * 使用HttpClient4.5.X<br>
	 * header 请求头 为空<br>
	 * connectTimeout 请求超时时间<strong>默认5000毫秒</strong><br>
	 * connectRequestTimeout 请求线程超时时间<strong>默认1000毫秒</strong><br>
	 * SocketTimeout 请求返回超时时间<strong>默认5000毫秒</strong><br>
	 * @param url 请求地址
	 * @param connectTimeout 请求超时时间
	 * @param connectRequestTimeout 请求线程超时时间
	 * @param SocketTimeout 请求返回超时时间
	 * @return String 返回状态是200返回response body 否则为null
	 * @throws Exception 
	 */
	public static String doGet(String url,int connectTimeout,int connectRequestTimeout,int SocketTimeout) throws Exception {
		return doGet(url, null, null,connectTimeout,connectRequestTimeout,SocketTimeout);
	}
	
	/**
	 * <strong>post请求 模拟表单提交</strong><br>
	 * 使用HttpClient4.5.X
	 * @param url 请求地址
	 * @param param 请求参数
	 * @param header 请求头
	 * @param connectTimeout 请求超时时间
	 * @param connectRequestTimeout 请求线程超时时间
	 * @param SocketTimeout 请求返回超时时间
	 * @return String 返回状态是200返回response body 否则为null
	 */
	public static String doPost(String url, Map param,Map header,int connectTimeout,int connectRequestTimeout,int SocketTimeout) throws Exception {

		// 创建Httpclient对象
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpResponse response = null;
		String resultString = null;
		try {
			// 创建Http Post请求
			HttpPost httpPost = new HttpPost(url);
			if(header != null) {
				for (String key : (Set<String>)header.keySet()) {
					httpPost.addHeader(key, String.valueOf(header.get(key)));
				}
			}
			// 创建参数列表
			if (param != null) {
				List<NameValuePair> paramList = new ArrayList<NameValuePair>();
				for (String key : (Set<String>)param.keySet()) {
					paramList.add(new BasicNameValuePair(key, String.valueOf(param.get(key))));
				}
				// 模拟表单
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList);
				httpPost.setEntity(entity);
			}
			showRequestHearders(httpPost);
			
			RequestConfig requestConfig = RequestConfig.custom()
			        .setConnectTimeout(connectTimeout)//setConnectTimeout：设置连接超时时间，单位毫秒
			        .setConnectionRequestTimeout(connectRequestTimeout)  //setConnectionRequestTimeout：设置从connect Manager(连接池)获取Connection 超时时间，单位毫秒。这个属性是新加的属性，因为目前版本是可以共享连接池的。
			        .setSocketTimeout(SocketTimeout).build();  //setSocketTimeout：请求获取数据的超时时间(即响应时间)，单位毫秒。 如果访问一个接口，多少时间内无法返回数据，就直接放弃此次调用。
			httpPost.setConfig(requestConfig);
			
			// 执行http请求
			response = httpClient.execute(httpPost);
			// 判断返回状态是否为200
			if (response.getStatusLine().getStatusCode() != 200) {
				throw new Exception("请求【"+url+"】返回状态非200："+response.getStatusLine().getStatusCode());
			}
			showResponseHearders(response);
			resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new Exception("请求【"+url+"】异常："+e.getMessage());
		}
		return resultString;
	}
	/**
	 * <strong>post请求 模拟表单提交</strong><br>
	 * 使用HttpClient4.5.X<br>
	 * header 请求头 为空<br>
	 * connectTimeout 请求超时时间<strong>默认5000毫秒</strong><br>
	 * connectRequestTimeout 请求线程超时时间<strong>默认1000毫秒</strong><br>
	 * SocketTimeout 请求返回超时时间<strong>默认5000毫秒</strong><br>
	 * @param url 请求地址
	 * @param param 请求参数
	 * @param connectTimeout 请求超时时间
	 * @param connectRequestTimeout 请求线程超时时间
	 * @param SocketTimeout 请求返回超时时间
	 * @return String 返回状态是200返回response body 否则为null
	 * @throws Exception 
	 */
	public static String doPost(String url, Map param,int connectTimeout,int connectRequestTimeout,int SocketTimeout) throws Exception {
		return doPost(url, param, null,connectTimeout,connectRequestTimeout,SocketTimeout);
	}
	/**
	 * <strong>post请求 模拟表单提交</strong><br>
	 * 使用HttpClient4.5.X<br>
	 * header 请求头 为空<br>
	 * connectTimeout 请求超时时间<strong>默认5000毫秒</strong><br>
	 * connectRequestTimeout 请求线程超时时间<strong>默认1000毫秒</strong><br>
	 * SocketTimeout 请求返回超时时间<strong>默认5000毫秒</strong><br>
	 * @param url 请求地址
	 * @param connectTimeout 请求超时时间
	 * @param connectRequestTimeout 请求线程超时时间
	 * @param SocketTimeout 请求返回超时时间
	 * @return String 返回状态是200返回response body 否则为null
	 * @throws Exception 
	 */
	public static String doPost(String url,int connectTimeout,int connectRequestTimeout,int SocketTimeout) throws Exception {
		return doPost(url, null, null,connectTimeout,connectRequestTimeout,SocketTimeout);
	}
	
	/**
	 * <strong>post json请求</strong>
	 * 使用HttpClient4.5.X
	 * @param url 请求地址
	 * @param json 请求参数
	 * @param header 请求头
	 * @param connectTimeout 请求超时时间
	 * @param connectRequestTimeout 请求线程超时时间
	 * @param SocketTimeout 请求返回超时时间
	 * @return String 返回状态是200返回response body 否则为null
	 */
	public static String doPostJson(String url, String json,Map header,int connectTimeout,int connectRequestTimeout,int SocketTimeout) throws Exception {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpResponse response = null;
		String resultString = null;
		try {
			// 创建Http Post请求
			HttpPost httpPost = new HttpPost(url);
			if(header != null) {
				for (String key : (Set<String>)header.keySet()) {
					httpPost.addHeader(key, String.valueOf(header.get(key)));
				}
			}
			// 创建请求内容
			StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
			showRequestHearders(httpPost);
			
			RequestConfig requestConfig = RequestConfig.custom()
			        .setConnectTimeout(connectTimeout)//setConnectTimeout：设置连接超时时间，单位毫秒
			        .setConnectionRequestTimeout(connectRequestTimeout)  //setConnectionRequestTimeout：设置从connect Manager(连接池)获取Connection 超时时间，单位毫秒。这个属性是新加的属性，因为目前版本是可以共享连接池的。
			        .setSocketTimeout(SocketTimeout).build();  //setSocketTimeout：请求获取数据的超时时间(即响应时间)，单位毫秒。 如果访问一个接口，多少时间内无法返回数据，就直接放弃此次调用。
			httpPost.setConfig(requestConfig);
			
			httpPost.setEntity(entity);
			// 执行http请求
			response = httpClient.execute(httpPost);
			// 判断返回状态是否为200
			if (response.getStatusLine().getStatusCode() != 200) {
				throw new Exception("请求返回状态非200："+response.getStatusLine().getStatusCode());
			}
			resultString = EntityUtils.toString(response.getEntity(), "utf-8");
			showResponseHearders(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultString;
	}
	/**
	 * <strong>post请求  </strong> application/json格式<br>
	 * 使用HttpClient4.5.X<br>
	 * header 请求头 为空<br>
	 * connectTimeout 请求超时时间<strong>默认5000毫秒</strong><br>
	 * connectRequestTimeout 请求线程超时时间<strong>默认1000毫秒</strong><br>
	 * SocketTimeout 请求返回超时时间<strong>默认5000毫秒</strong><br>
	 * @param url 请求地址
	 * @param json 请求参数
	 * @param connectTimeout 请求超时时间
	 * @param connectRequestTimeout 请求线程超时时间
	 * @param SocketTimeout 请求返回超时时间
	 * @return String 返回状态是200返回response body 否则为null
	 * @throws Exception 
	 */
	public static String doPostJson(String url, String json,int connectTimeout,int connectRequestTimeout,int SocketTimeout) throws Exception {
		return doPostJson(url, json, null,connectTimeout,connectRequestTimeout,SocketTimeout);
	}
	
	
	
	public static void main(String[] args) throws Exception {
		String url = "https://manhua.qpic.cn/manhua_detail/0/17_00_20_b85c2dcd63b718a71f4bd23c7e093d41_5101.jpg/0";
		HttpClientUtils.doGetImage(url, null, null, "D:\\我是大神仙\\第二话·广陵君（上）\\i.jpg", 5000, 1000, 5000);
		
		
	}
	public static String doGetImage(String url, Map param,Map header,String fileName,int connectTimeout,int connectRequestTimeout,int SocketTimeout) throws Exception {
		
		// 创建Httpclient对象
		CloseableHttpClient httpClient = HttpClients.createDefault();  
		CloseableHttpResponse response = null;
		String resultString = null;
		try {
			// 创建uri
			URIBuilder builder = new URIBuilder(url);
			if (param != null) {
				for (String key : (Set<String>)param.keySet()) {
					builder.addParameter(key, String.valueOf(param.get(key)));
				}
			}
			URI uri = builder.build();

			// 创建http GET请求
			HttpGet httpGet = new HttpGet(uri);
			if(header != null) {
				for (String key : (Set<String>)header.keySet()) {
					httpGet.addHeader(key, String.valueOf(header.get(key)));
				}
			}
			
			RequestConfig requestConfig = RequestConfig.custom()
			        .setConnectTimeout(connectTimeout)//setConnectTimeout：设置连接超时时间，单位毫秒
			        .setConnectionRequestTimeout(connectRequestTimeout)  //setConnectionRequestTimeout：设置从connect Manager(连接池)获取Connection 超时时间，单位毫秒。这个属性是新加的属性，因为目前版本是可以共享连接池的。
			        .setSocketTimeout(SocketTimeout).build();  //setSocketTimeout：请求获取数据的超时时间(即响应时间)，单位毫秒。 如果访问一个接口，多少时间内无法返回数据，就直接放弃此次调用。
			httpGet.setConfig(requestConfig);
			
//			showRequestHearders(httpGet);
			// 执行请求
			response = httpClient.execute(httpGet);
			if (response.getStatusLine().getStatusCode() != 200) {
				throw new Exception("请求【"+url+"】返回状态非200："+response.getStatusLine().getStatusCode());
			}
//			showResponseHearders(response);
			
			HttpEntity entity = response.getEntity();
			FileUtils.copyToFile(entity.getContent(), new File(fileName));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new Exception("请求【"+url+"】异常："+e.getMessage());
		} finally {
			if(httpClient != null) {
				httpClient.close();
			}
			if(response != null) {
				response.close();
			}
		}
		return resultString;
	}
	
	
	
	
	/**
	 * 展示请求头
	 * @param httpGet
	 */
	private static void showRequestHearders(HttpGet httpGet) {
		Header[] allHeaders = httpGet.getAllHeaders();
		logger.info("请求头：");
		for (Header h : allHeaders) {
			logger.info(h.getName()+" : "+h.getValue());
		}
		logger.info("");
	}
	private static void showRequestHearders(HttpPost httpGet) {
		Header[] allHeaders = httpGet.getAllHeaders();
		logger.info("请求头：");
		for (Header h : allHeaders) {
			logger.info(h.getName()+" : "+h.getValue());
		}
		logger.info("");
	}
	/**
	 * 展示返回头
	 * @param httpGet
	 */
	private static void showResponseHearders(HttpResponse response) {
		Header[] allHeaders = response.getAllHeaders();
		logger.info("返回头：");
		for (Header h : allHeaders) {
			logger.info(h.getName()+" : "+h.getValue());
		}
	}
}
