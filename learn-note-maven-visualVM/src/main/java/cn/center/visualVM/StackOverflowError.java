package cn.center.visualVM;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 栈溢出 栈深度大于虚拟机提供的最大栈深度导致（递归）
 * 
 * @author :陈进松
 * @date :2019年9月21日 下午6:31:32
 */
public class StackOverflowError {
	private static Logger logger = LoggerFactory.getLogger(StackOverflowError.class);

	public static void test() {
		new StackOverflowError().test2();
	}
	public void test2() {
		try {
			logger.info(new String("into reversion".getBytes("utf-8"), "GBk"));
			while (true) {
				StackOverflowErrorClass stackOverflowErrorClass = new StackOverflowErrorClass();
				test();
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	public class StackOverflowErrorClass{
		String id;
		String name;
	}

}
