package cn.center;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.center.io.tool.FileUtils;
import cn.center.tool.MatcherUtils;
import cn.center.webmagic.Biquge;
import cn.center.webmagic.TXManHua;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.processor.PageProcessor;

public class Application {
	private static Logger logger = LoggerFactory.getLogger(Application.class);
	
	public static void main(String[] args) throws InterruptedException, URISyntaxException {
//		new Application().run_acqqcom();
//		new Application().getPageBySeleniumChrome();
		new Application().run_xbiquge6();
	}

	private void run_xbiquge6() {
		String keyword = "";
		String[] urls = {
//			"https://www.xbiquge6.com/15_15338/8564454.html"//	诡秘之主			爱潜水的乌贼
			
//			"https://www.xbiquge6.com/9_9208/9132504.html"//	伏天氏				净无痕
			
//			,"https://www.xbiquge6.com/1_1203/8699579.html"//	凡人修仙之仙界篇		忘语
//			,"https://www.xbiquge6.com/41_41414/2171773.html"//	玄界之门			忘语			完结（已看）
			
//			,"https://www.xbiquge6.com/68_68470/3576329.html"// 斗战狂潮			骷髅精灵

//				,"https://www.xbiquge6.com/88_88556/188859.html"//	龙王大人在上			雨魔
			
//			,"https://www.xbiquge6.com/27_27470/1687000.html"//	开天录				血红
//			,"https://www.xbiquge6.com/76_76449/135872.html"//	万界天尊			血红
//			,"https://www.xbiquge6.com/75_75917/3934221.html"//	巫神纪				血红
			
//			,"https://www.xbiquge6.com/74_74821/3861307.html"// 圣墟				辰东 
//			,"https://www.xbiquge6.com/2_2652/6721847.html"// 	不死不灭			辰东 
//			,"https://www.xbiquge6.com/0_36/1000000.html"// 	完美世界			辰东 
//			,"https://www.xbiquge6.com/2_2317/1213209.html"// 	长生界				辰东 
//			,"https://www.xbiquge6.com/1_1365/8676926.html"// 	遮天				辰东 
//			,"https://www.xbiquge6.com/2_2306/1088710.html"// 	神墓				辰东 
			
//			,"https://www.xbiquge6.com/76_76057/20630.html"// 	我从凡间来			想见江南 
			
//			,"https://www.xbiquge6.com/77_77473/401581.html"// 	牧神记				宅猪			完结（已看） 
			
//			"https://www.xbiquge6.com/32_32972/2189437.html"//	大道朝天			猫腻
			
//			"https://www.xbiquge6.com/30_30535/2020006.html"// 	天下第九			鹅是老五 
//			,"https://www.xbiquge6.com/75_75994/3997986.html"// 不朽凡人			鹅是老五			完结（已看） 
				
//			,"https://www.xbiquge6.com/34_34822/2151010.html"// 三寸人间			 耳根
//			,"https://www.xbiquge6.com/75_75918/3933728.html"// 一念永恒			耳根			完结（已看）
				
//			,"https://www.xbiquge6.com/16_16785/9083031.html"// 大符篆师			小刀锋利		（不好看） 
//			,"https://www.xbiquge6.com/76_76829/207565.html"// 	无疆				小刀锋利		（不好看）
				
//			,"https://www.xbiquge6.com/86_86216/9621.html"// 	沧元图				我吃西红柿		（不好看）  
//			,"https://www.xbiquge6.com/3_3197/8402974.html"// 	飞剑问道			我吃西红柿		（不好看） 
			
//			,"https://www.xbiquge6.com/87_87421/153083.html"//	赝太子				荆柯守		（不好看） 
//			,"https://www.xbiquge6.com/22_22634/1604371.html"//	太初				高楼大厦
//			,"https://www.xbiquge6.com/68_68479/3578504.html"// 天影				萧鼎 
//			,"https://www.xbiquge6.com/68_68000/3500000.html"// 超凡传				萧潜
//			,"https://www.xbiquge6.com/65_65306/4217655.html"// 修真聊天群			圣骑士的传说 
//			,"https://www.xbiquge6.com/83_83940/349327.html"// 	同桌凶猛			柳下挥 
//			,"https://www.xbiquge6.com/5_5973/4080329.html"// 	天醒之路			蝴蝶蓝			断更 
//			"https://www.xbiquge6.com/78_78250/56416.html"//	尘骨				林如渊			断更
			
//			,"https://www.xbiquge6.com/search.php?keyword="+keyword// 搜索
		};
		
		PageProcessor biquge = new Biquge();
        Spider spider = Spider.create(biquge);
        spider.addUrl(urls);
        spider.thread(64);
        
//        JsonFilePipeline jsonFilePipeline = new JsonFilePipeline("D:\\webmagic\\");//输出结果到.json
//        FilePipeline filePipeline = new FilePipeline("D:\\webmagic\\");//保存结果到.html
        Pipeline pipeline = new Pipeline() {

			public void process(ResultItems resultItems, Task task) {
				try{
					String bookname = resultItems.get("bookname");
					logger.info("bookname："+bookname);
					if(StringUtils.isBlank(bookname)) {
						logger.info("异常章节：名称为空"+bookname);
						return;
					}
					String filename="D:\\webmagic\\"+bookname+".txt";
					
					
					String chapter = resultItems.get("chapter");
					logger.info("chapter："+chapter);
					if(StringUtils.isBlank(chapter)) {
						FileUtils.writerLine(filename, "\n"+"异常章节：章节名为空"+"\n", true);
						return;
					}
					
					
					String content = resultItems.get("content");
					if(StringUtils.isBlank(content)) {
						FileUtils.writerLine(filename, chapter+"\n"+"异常章节：章节内容为空"+"\n", true);
						return;
					}
					
					List<String> lines = new ArrayList<String>();
					lines.add(chapter+"\n");
					List<String> find = MatcherUtils.find("[\\u3000|\\u0020|\\u00A0]+[^\\u3000|\\u0020|\\u00A0]+", content);
					for (String s : find) {
						lines.add(s+"\n");
					}
					FileUtils.writerLines(filename, lines, true);
					
				} catch (Exception e) {
					logger.warn("write file error", e);
				} 
			}
        };
//        spider.addPipeline(pipeline);
        
        spider.run();
	}

	/**
	 * SeleniumChrome 针对动态数据 动态加载
	 * <br>
	 * 缺点 消耗内存大，爬取缓慢，单线程
	 * @throws InterruptedException
	 */
	public void getPageBySeleniumChrome() throws InterruptedException {
		String url = "https://ac.qq.com/ComicView/index/id/621058/cid/1?fromPrev=1";
		new cn.center.selenium.TXManHua().mySelenium(url);
	}
	
	
	
	
	/**
	 * 腾信漫画
	 */
	private void run_acqqcom() {
		logger.info("开始爬取...");
		//开始爬去页面，自动下一页爬取
		String[] urls = {"https://ac.qq.com/ComicView/index/id/621058/cid/251?fromPrev=1"};//我是大神仙
		
		Spider spider = Spider.create(new TXManHua()).addUrl(urls).thread(5);
		spider.run();//启动，会阻塞当前线程执行
		if (spider.isExitWhenComplete()) {
			spider.stop();
			logger.info("spider已经结束");
		}
	}
	
	
	
	
	
	
	
	
//	调用process底层代码
//	<!-- lang: java -->
//	private void processRequest(Request request) {
//	    Page page = downloader.download(request, this);
//	    if (page == null) {
//	        sleep(site.getSleepTime());
//	        return;
//	    }
//	    pageProcessor.process(page);
//	    addRequest(page);
//	    for (Pipeline pipeline : pipelines) {
//	        pipeline.process(page, this);
//	    }
//	    sleep(site.getSleepTime());
//	}
	/**
	 * 样例
	 */
	private void demo() {
		long startTime, endTime;
		logger.info("开始爬取...");
		startTime = System.currentTimeMillis();

		String[] urls = {"https://ac.qq.com/ComicView/index/id/621058/cid/1?fromPrev=1"//我是大神仙
			,"https://ac.qq.com/ComicView/index/id/624570/cid/85"};//传武
		
		PageProcessor wsdsx = new TXManHua();
	
		Spider spider = Spider.create(wsdsx);//create(PageProcessor) 创建Spider
		spider.addUrl(urls);		//addUrl(String…)	添加初始的URL
//		Request requests = new Request("https://ac.qq.com/ComicView/index/id/621058/cid/1?fromPrev=1");
//		如果需要post请求
//		request.setMethod(HttpConstant.Method.POST);
//		request.setRequestBody(HttpRequestBody.json("{'id':1}","utf-8"));
//		spider.addRequest(requests);	//添加初始的Request
		spider.thread(5);//开启5个线程    个数按照  处理器+1个数 设置
		spider.run();//启动，会阻塞当前线程执行
//		spider.start();//
//		spider.runAsync();
//		spider.stop();	//停止爬虫
//		spider.test("https://ac.qq.com/ComicView/index/id/621058/cid/1?fromPrev=1");	//抓取一个页面进行测试
//		spider..pipeline(new FilePipeline("/data/webmagic/test/"));
//		spider.addPipeline(new Pipeline() {
//			
//			public void process(ResultItems resultItems, Task task) {
//				//遍历所有结果，输出到控制台，上面例子中的"author"、"name"、"readme"都是一个key，其结果则是对应的value
//		        for (Map.Entry<String, Object> entry : resultItems.getAll().entrySet()) {
//		            System.out.println(entry.getKey() + ":\t" + entry.getValue());
//		        }
//			}
//		});	//添加一个Pipeline，一个Spider可以有多个Pipeline
//		DuplicateRemovedScheduler setDuplicateRemover = new QueueScheduler().setDuplicateRemover(new BloomFilterDuplicateRemover(10000000));//10000000是估计的页面数量
//		spider.setScheduler(setDuplicateRemover);	//设置Scheduler，一个Spider只能有个一个Scheduler
//		spider.downloader(new SeleniumDownloader("/Users/yihua/Downloads/chromedriver"));
//		Downloader Downloader;
//		spider.setDownloader(Downloader);	//设置Downloader，一个Spider只能有个一个Downloader
//		spider.get("https://ac.qq.com/ComicView/index/id/621058/cid/1?fromPrev=1");	//同步调用，并直接取得结果
//		List<String> list = null;
//		spider.getAll(list );	//同步调用，并直接取得一堆结果
		//配置代理
//		HttpClientDownloader httpClientDownloader = new HttpClientDownloader();
//	    httpClientDownloader.setProxyProvider(SimpleProxyProvider.from(new Proxy("101.101.101.101",8888,"username","password")));
//	    spider.setDownloader(httpClientDownloader);
		
		if (spider.isExitWhenComplete()) {
			spider.stop();
			logger.info("spider已经结束");
		}
		endTime = System.currentTimeMillis();
//		logger.info("爬取结束，耗时约" + ((endTime - startTime) / 1000) + "秒，抓取了" + count + "条记录");
	}
}
