package my.master.matcher;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class Test {
	private static Logger logger = LoggerFactory.getLogger(Test.class);
	public static void main(String[] args) throws IOException {

//		String fileName = "D:\\Work\\WorkSpace\\space_pycharm\\cjsscrapy\\cjsscrapy\\spiders\\万古仙穹copy.txt";
		String fileName = "D:\\Work\\WorkSpace\\space_eclipse_sts\\learn-note-maven-webmagic\\logs\\e3.log";
		new Test().test(fileName);
//		new Test().writeFile("test");
	}

	private void test(String fileName) throws IOException {
		FileReader reader = new FileReader(new File(fileName));
		BufferedReader br = new BufferedReader(reader);
		String line;
		while ((line = br.readLine()) != null) {
//			logger.info(line);
			matcherTest2(line);

		}
		br.close();
		reader.close();

	}

	private void matcherTest2(String line) {
		String regex = ".*?(window\\.).*?";// 匹配a-z A-Z
		Matcher m = Pattern.compile(regex).matcher(line);
		if (m.matches()) {
			logger.info(line);
		}
	}
	private void matcherTest(String line) {
		String regex = ".*[a-zA-Z]+.*";// 匹配a-z A-Z
		Matcher m = Pattern.compile(regex).matcher(line);
		if (m.matches()) {
			logger.info(line);
		}
	}
	
	
	private void writeFile(String line) throws IOException {
		String path = this.getClass().getResource("/").getPath();
		FileWriter writer = new FileWriter(new File("./"));
		writer.write(line);
		
		writer.close();
	}
}
