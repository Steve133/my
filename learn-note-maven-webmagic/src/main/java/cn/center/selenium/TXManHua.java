package cn.center.selenium;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 爬取 腾讯漫画  
 * <br>
 * 方式：SeleniumChrome
 * @author :陈进松
 * @date :2019年9月25日 下午3:53:35
 */
public class TXManHua {
	private static Logger logger = LoggerFactory.getLogger(TXManHua.class);
	
	public static void main(String[] args) {
		
	}
	
	//chrome 驱动位置
	private final String chromedrive = "D:/Download/chorm_explor_download/chromedriver_win32 75.0.3770.8/chromedriver.exe";

	private WebDriver driver = null;
	private JavascriptExecutor js = null;
	
	
	private void process() throws InterruptedException {
		try {
			//滑动页面，加载完所有动态数据
			Thread.sleep(1000);
			List<WebElement> findElements = driver.findElements(By.xpath("//*[@id=\"comicContain\"]/li[@*]/img"));//找到所有图片标签
			for (WebElement webElement : findElements) {
				js.executeScript("arguments[0].scrollIntoView();",webElement);//拉到指定位置
				String attribute = webElement.getAttribute("src");
				logger.info(attribute);
				System.out.println("移至下一张图");
				Thread.sleep(2000);
			}
			
			//下一页
			WebElement wm =driver.findElement ( By.xpath ( "//*[@id=\"mainControlNext\"]" ) );
			js.executeScript("arguments[0].scrollIntoView();",wm);//拉到指定位置
			Thread.sleep(2000);
			JavaScriptClick(wm);
			
			
			//获取整个页面
//      WebElement webElement = webDriver.findElement(By.xpath("/html"));
//      String attribute = webElement.getAttribute("outerHTML");
     
			//获取单个元素
//  	WebElement webElement = webDriver.findElement(By.xpath("//*[@id=\"comicContain\"]/li[1]/img"));
//  	String attribute = webElement.getAttribute("src");
//  	logger.info(attribute);
			Thread.sleep(4000);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return;
		}
		process();
	}
	
	
	/**
	 * 	selenium驱动chrome
	 * @return
	 * @throws InterruptedException 
	 */
	public String mySelenium(String url) throws InterruptedException {
		
		//启动chome驱动
		System.getProperties().setProperty("webdriver.chrome.driver", chromedrive);
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);//超时时间
        
		//加载页面
		driver.get(url);
		js = (JavascriptExecutor)driver;
		
		
		process();
		
        
        driver.close();
        driver.quit();
        return null;
	}
	
	/**
	 * js 点击函数
	 * @param element
	 * @throws Exception 
	 */
	private void JavaScriptClick(WebElement element) throws Exception {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		try {
			// 判断传入的element元素是否处于可单击状态，以及是否能显示在页面上
			if (element.isEnabled() && element.isDisplayed()) {
				// 执行JavaScript语句arguments[0].click();
				//argumets[0]表示第一个参数，即element
				js.executeScript("arguments[0].click();", element);
			} else {
				throw new Exception("页面上的元素无法进行单击操作");
			}
		} catch (StaleElementReferenceException e) {
			throw new Exception("页面元素没有附加在网页中"+e.getMessage());
		} catch (NoSuchElementException e) {
			throw new Exception("在页面中没有找到要操作的元素"+e.getMessage());
		} catch (Exception e) {
			throw new Exception("无法完成单击动作"+e.getMessage());
		}
	}
}
