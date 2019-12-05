package cn.center.visualVM;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 堆泄漏，老年代沾满，或持久代沾满
 * 
 * @author :陈进松
 * @date :2019年9月21日 下午6:30:10
 */
public class OutOfMemoryError {
	private static Logger logger = LoggerFactory.getLogger(OutOfMemoryError.class);

	static Map<String, String> OutOfMemoryErrorMap = new HashMap<String, String>();

	public static void test() {
		try {
			logger.info(new String("进入循环".getBytes("utf-8"), "GBk"));
			int OutOfMemoryError_i = 0;
			while (true) {
				OutOfMemoryErrorMap.put(String.valueOf(OutOfMemoryError_i), "这是" + OutOfMemoryError_i);
				OutOfMemoryError_i++;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
	}
}
