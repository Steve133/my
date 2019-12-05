import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.center.tool.MatcherUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

public class XpthTest implements PageProcessor {
	private static Logger logger = LoggerFactory.getLogger(XpthTest.class);
	
	public static void main(String[] args) {
//		String keyword = "第一";
//		String url = "https://www.xbiquge6.com/search.php?keyword="+keyword;
		String url = "https://www.xbiquge6.com/65_65306/3390400.html";
		PageProcessor xpthTest = new XpthTest();
        Spider spider = Spider.create(xpthTest);
        spider.addUrl(url);
        spider.thread(5);
        spider.run();
    }
	
    // 抓取网站的相关配置，包括编码、抓取间隔、重试次数等
    private Site site = Site.me().setRetryTimes(3).setSleepTime(100);
    public Site getSite() {
        return site;
    }
    
    public void process(Page page) {
    	Html html = page.getHtml();
    	
    	String content = html.xpath("/html/body/script").toString();
//		System.out.println(content);
    	
    	String find = (String) MatcherUtils.findStr("(set\\().*?(\\))", content);
    	String[] split = find.split(",");
		for (String string : split) {
			System.out.println(string.replace("'", "").trim());
		}
    	
    	
//    	List<String> find = MatcherUtils.find(".*?\\s{4}", content);
//    	for (String string : find) {
//    		System.out.println(string);
//		}
    }
	
}
