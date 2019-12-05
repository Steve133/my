package cn.center.matcher;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MatcherTest {
	private static Logger logger = LoggerFactory.getLogger(MatcherTest.class);
	
	public static void main(String[] args) {
		
		new MatcherTest().test2();
	}
	
	void test() {
		String str = "这是一个正则a语句";
		String regex = "^.*[a-zA-Z]+.*$";//匹配a-z A-Z
		
		logger.info("===========> 测试字符串：" + str);
		Matcher m = Pattern.compile(regex).matcher(str);
		if (m.matches()) {
			logger.info(str);
		}
	}
	void test2() {
		String[] strArr = new String[] { "999-99-9999",
				"999999999",
				"!@#$%^&*()_+{}[]|\"'?/:;<>,.", "！￥……（）——：；“”‘’《》，。？、", "不要啊",
				"やめて", "韩佳人", "한가인" };
		
		for (String str : strArr) {
			logger.info("===========> 测试字符串：" + str);
			
			String regex = "[0-9]{3}.*[0-9]{2}.*[0-9]{4}";//匹配0-9
			Matcher m = Pattern.compile(regex).matcher(str);
			if (m.matches()) {
				logger.info(str);
			}
		}
	}
	void test3() {
		String str = "3gh3gh4g3h4gv5gv65v";
		String regular = "\\d+[a-zA-Z]+";
		Pattern p=Pattern.compile(regular); 
		Matcher m=p.matcher(str);
		while(m.find()) {
			m.group();
			System.out.println(m.group());
		}
	}
}
