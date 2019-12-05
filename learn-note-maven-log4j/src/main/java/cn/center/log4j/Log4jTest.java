package cn.center.log4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Log4jTest {
	private static Logger logger = LoggerFactory.getLogger(Log4jTest.class);

	public static void main(String[] args) {
		logger.info("test");
		try {
			int i = 1 / 0;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
}
