package my.master.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReaderWriterDemo {
	private static Logger logger = LoggerFactory.getLogger(ReaderWriterDemo.class);

	public static void main(String[] args) {
//		输入输出目标对象是内存空间
//		输入流：从文件中或者其他地方读取数据输入到内存中
//		输出流：把内存中的内容输出到其他地方
		
		
//		字符输入流

		// 字符输入流的抽象基类
		Reader reader;
		// 可以把InputStream中的字节数据流根据字符编码方式转成字符数据流
		InputStreamReader inputStreamReader;
		//可以把FileInputStream中的字节数据转成根据字符编码方式转成字符数据流
		FileReader fileReader;
//		可以把字符输入流进行封装，将数据进行缓冲，提高读取效率
		BufferedReader bufferedReader;
		
		
		
//		字节输出流
		Writer writer;
		OutputStreamWriter outputStreamWriter;
		FileWriter fileWriter;
		BufferedWriter bufferedWriter;
	}

	//InputStreamReader示例
	public void Test_FileInputStream() throws IOException {
		InputStreamReader isr = new InputStreamReader(new FileInputStream("D://a.txt"));
		int ch;
		while ( (ch = isr.read()) != -1) {
			System.out.println((char)ch);
		}
		isr.close();
	}
	//FileReader示例
	public void Test_FileReader() throws IOException {
		FileReader reader = new FileReader("D://a.txt");
		char[] cbuf = new char[1024];
		int len;
		while ( (len = reader.read(cbuf)) != -1) {
			logger.info(new String(cbuf, 0, len));
		}
		reader.close();
	}
	//FileReader示例
	public void Test_BufferedReader() throws IOException {
		FileReader reader = new FileReader("D://a.txt");
		BufferedReader breader = new BufferedReader(reader);
		String line;
		while ( (line = breader.readLine()) != null) {
			logger.info(line);
		}
		reader.close();
	}
	
	
	
	//OutputStreamWriter示例
	public void Test_OutputStreamWriter() throws IOException {
		OutputStream out = new FileOutputStream("D://a.txt");
		OutputStreamWriter osw = new OutputStreamWriter(out);
		osw.write("OutputStreamWriter write.");
		osw.close();
	}
	//FileWriter示例
	public void Test_FileWriter() throws IOException {
		FileWriter write = new FileWriter("D://a.txt");
		write.write("FileWriter write.");
		write.close();
	}
	//BufferedWriter示例
	public void Test_BufferedWriter() throws IOException {
		FileWriter write = new FileWriter("D://a.txt");
		BufferedWriter bwrite = new BufferedWriter(write);
		bwrite.write("FileWriter write.");
		bwrite.close();
		write.close();
	}
}
