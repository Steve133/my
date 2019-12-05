package cn.center.webmagic;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.center.http.HttpClientUtils;
import cn.center.tool.JsInvokUtils;
import cn.center.tool.MatcherUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

/**
 * 
 * 参考 http://webmagic.io/docs/zh/posts/ch4-basic-page-processor/pageprocessor.html
 * @author :陈进松
 * @date :2019年9月24日 下午7:41:01
 */
public class TXManHua implements PageProcessor {
	private static Logger logger = LoggerFactory.getLogger(TXManHua.class);

	// 部分一：抓取网站的相关配置，包括编码、、
	private Site site = Site.me()
			.setRetryTimes(3)//重试次数等
//			.setCycleRetryTimes(1)	设置循环重试次数,0.3.0版本加入的机制,下载失败的url重新放入队列尾部重试，直到达到重试次数
			.setSleepTime(1000)//抓取间隔
//			.setCharset("utf-8")
//			.setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36")
//			.setTimeOut(3000)//设置超时时间，单位是毫秒
//			.addCookie(String,String)//添加一条cookie
//			.setDomain("ac.qq.com")//设置域名，需设置域名后，addCookie才可生效
//			.addHeader("Referer","https://github.com")//添加一条addHeader
//			.setHttpProxy(new HttpHost("127.0.0.1",8080))//设置Http代理	0.7.1版本后淘汰，spider里实现代理
			;

	
	/**
	 * process是定制爬虫逻辑的核心接口，在这里编写抽取逻辑
	 * 页面元素的抽取
	 * 
	 * 1、XPath
	 *  page.getHtml().xpath("//h1[@class='entry-title public']/strong/a/text()")
	 *  
	 *  2、CSS选择器
	 *  
	 *  
	 *  3、正则表达式
	 *   page.addTargetRequests(page.getHtml().links().regex("(https://github\\.com/\\w+/\\w+)").all());
	 *   
	 *   4、JsonPath
	 *   
	 *   
	 *   连接发现
	 *   page.addTargetRequests(page.getHtml().links().regex("(https://github\\.com/\\w+/\\w+)").all());
	 *   用于获取所有满足"(https:/ /github\.com/\w+/\w+)"这个正则表达式的链接，page.addTargetRequests()则将这些链接加入到待抓取的队列中去。
	 *   
	 */
	public void process(Page page) {
		try {
			// 部分二：定义如何抽取页面信息，并保存下来
			Selectable url = page.getUrl();
			System.out.println("");
			logger.info("url： " + String.valueOf(url));
			
			
			Html html = page.getHtml();
			
			
			Map<String, Object> func = getContent(html);
			//标题
			String title = func.get("title").toString();
			//章节
			String cTitle = func.get("cTitle").toString();
			//图片地址 数组
			JSONArray picture = JSONArray.fromObject(func.get("jsonArray"));
			
			int index = 0;//章节图片序号
			for (Object object : picture) {
				JSONObject fromObject = JSONObject.fromObject(object);
				String pic_url = fromObject.getString("url");
				//保存图片到本地，尝试3次，
				for(int i = 0; i< 3 ;i++) {
					try {
						Thread.sleep(2000);
						HttpClientUtils.doGetImage(pic_url, null, null, "D:\\"+title+"\\"+cTitle+"\\"+index+".jpg" , 5000, 1000, 5000);
					} catch (Exception e) {
						if(i==2) {
							logger.info(pic_url+" 请求异常："+e.getMessage());
							return;
						}else {
							continue;
						}
					}
					break;
				}
				index++;
				Thread.sleep(1000);
			}
			
			//部分三：从页面发现单个后续url地址来抓取
			String nextUrl= html.xpath("//*[@id=\"mainControlNext\"]/@href").get();//*[@id="mainControlNext"]
			if(!nextUrl.contains("ComicView")) {
				return;//结束
			}
			page.addTargetRequest("https://ac.qq.com"+nextUrl);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(page.getUrl()+" 异常："+e.getMessage(), e);
			return;
		}
	}
	
	/**
	 * 获取正文
	 * <br>
	 * 章节数据已经加密，获取DATA与nonce解密
	 * @param html
	 * @return
	 */
	private Map<String, Object> getContent(Html html) {
		//截取nonce
		String nonce = html.xpath("/html/body/script[4]").get();
		CharSequence nonce_chars = MatcherUtils.findStr("=.*?\";", nonce);
		//去除nonce字符串杂质
		if(nonce_chars.toString().contains("document")) {
			if(nonce_chars.toString().contains("document.children")) {
				nonce_chars = nonce_chars.toString().replaceAll("document.children", "1");//document.children	document.getElementsByTagName('html')
			}
			if(nonce_chars.toString().contains("document")) {
				List<String> find = MatcherUtils.find("document.*?[)]+", nonce_chars);
				for (String documentStr : find) {
					nonce_chars = nonce_chars.toString().replace(documentStr, "1");
				}
			}
		}
		if(nonce_chars.toString().contains("window")) {
			if(nonce_chars.toString().contains("window.Array")) {
				nonce_chars = nonce_chars.toString().replaceAll("window.Array", "1");//window.Array
			}
			if(nonce_chars.toString().contains("window")) {
				List<String> find = MatcherUtils.find("window.*?[)]+", nonce_chars);
				for (String windowStr : find) {
					nonce_chars = nonce_chars.toString().replace(windowStr, "1");
				}
			}
		}
		nonce = JsInvokUtils.trans(nonce_chars.toString().substring(1, nonce_chars.length() - 1));
		logger.info("nonce:" + nonce);
		logger.info("");

		
		//截取DATA
		String DATA = html.xpath("/html/body/script[7]").get();
		CharSequence data_chars = MatcherUtils.findStr("\'[A-Za-z0-9\\/\\=]+(\'|)", DATA);
		
		
		//使用动态js代码解密数据
		Map<String, Object> func = JsInvokUtils.func(data_chars.toString().substring(1, data_chars.length() - 1), nonce);
		return func;
	}

	public Site getSite() {
		return site;
	}
}