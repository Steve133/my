package cn.center.webmagic;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.utils.URIBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.center.tool.FileUtils;
import cn.center.tool.MatcherUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

/**
 * 笔趣阁 xbiquge6
 * @author :陈进松
 * @date :2019年9月25日 下午6:42:24
 */
public class Biquge implements PageProcessor {
	private static Logger logger = LoggerFactory.getLogger(Biquge.class);
	
	public static void main(String[] args) {
    }
	
    // 抓取网站的相关配置，包括编码、抓取间隔、重试次数等
    private Site site = Site.me().setRetryTimes(5).setSleepTime(500).setTimeOut(5000);
    public Site getSite() {
        return site;
    }
    
    public void process(Page page) {
    	Html html = page.getHtml();
    	
    	if(page.getUrl().regex("(\\.html)").match()) {//章节页
    		String bookname = html.xpath("//*[@class=\"con_top\"]/a[3]/text()").toString();
    		
    		String chapter = html.xpath("//*[@class=\"bookname\"]/h1/text()").toString();
    		
    		String content = html.xpath("//*[@id=\"content\"]/text()").toString();
    		
    		
			try {
				logger.info("bookname："+bookname);
				if(StringUtils.isBlank(bookname)) {
					logger.info("异常章节：名称为空"+bookname);
					return;
				}
				String filename="D:\\webmagic\\"+bookname+".txt";
				
				
				logger.info("chapter："+chapter);
				if(StringUtils.isBlank(chapter)) {
					FileUtils.writerLine(filename, "\n"+"异常章节：章节名为空"+"\n", true);
					return;
				}
				
				
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
				logger.error("读写文件报错："+e.getMessage(), e);
				return;
			}
    		
    		
    		
    		String nextpage = html.xpath("//*[@class=\"bottem2\"]/a[3]/@href").toString();
    		if(nextpage.endsWith(".html")) {
    			try {
    				String url = page.getUrl().toString();
    				URI uri = new URIBuilder(url).build();
    				String path = uri.getPath();
    				String nextUrl = url.replace(path, nextpage);
    				page.addTargetRequest(nextUrl);
    			} catch (URISyntaxException e) {
    				logger.error("生成下一页URL异常："+e.getMessage(), e);
    				return;
    			}
    		}else {
    			logger.info(bookname+" >>>>爬取结束！！！");
    		}
    	}
//    	else if(page.getUrl().regex("https://www.xbiquge6.com/search.php").match()) {//搜索页
//    		List<String> all = html.xpath("//*[@class=\"result-game-item-info\"]/p[1]/span[2]/text()").all();
//        	for (String content : all) {
//        		System.out.println(content);
//    		}
//    		
//        	List<String> all2 = html.xpath("//*[@class=\"result-list\"]/div/div[2]/h3/a/@title").all();
//        	for (String content : all2) {
//        		System.out.println(content);
//    		}
//        	
//        	int index = 1;
//    		String indexUrl = html.xpath("//*[@class=\"result-list\"]/div["+index+"]/div[2]/h3/a/@href").toString();
//    		
//    	}
//    	else {
//    		//设置skip之后，这个页面的结果不会被Pipeline处理
////    		page.setSkip(true);
//    		
//    		//保存结果author，这个结果会最终保存到ResultItems中
//    		page.putField("author", html.xpath("//*[@id=\"info\"]/p[1]/text()").toString());
//    		page.putField("bookname", html.xpath("//*[@id=\"info\"]/h1/text()").toString());
//    		
//    		List<String> all = html.xpath("//*[@id=\"list\"]/dl/dd/a/@href").all();//*[@id="list"]/dl/dd[1]/a
//    		for (int i = 0; i < all.size(); i++) {
//    			all.set(i, "https://www.xbiquge6.com"+all.get(i));
//    		}
//    		
////    		System.out.println(Arrays.asList(all));
//    		page.addTargetRequests(all);
//    	}
    }

    
}
