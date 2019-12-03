package my.master.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StreamDemo {
	private static Logger logger = LoggerFactory.getLogger(StreamDemo.class);

	public static void main(String[] args) {
//		输入输出目标对象是内存空间
//		输入流：从文件中或者其他地方读取数据输入到内存中
//		输出流：把内存中的内容输出到其他地方
		
//		字节输入流
		
		// InputStream是字节输入流的抽象基类
		InputStream is;
		// 主要用来操作文件输入流
		FileInputStream fis;
		// BufferedInputStream不是InputStream的直接实现子类，是FilterInputStream的子类
//		带有缓冲的意思，普通的读是从硬盘里面读，而带有缓冲区之后，BufferedInputStream已经提前将数据封装到内存中，内存中操作数据要快，所以它的效率要要非缓冲的要高。
		BufferedInputStream bis;
		
		
//		字节输出流
		OutputStream os;
		FileOutputStream fos;
		BufferedOutputStream bos;
	}

	//FileInputStream示例
	public void Test_FileInputStream() throws IOException {
		FileInputStream fis = new FileInputStream("D://a.txt");
		byte[] buff = new byte[1024];
		int len;
		while ( (len = fis.read(buff)) != -1) {
			logger.info(new String(buff, 0, len));
		}
		fis.close();
	}
	//BufferedInputStream示例
	public void Test_BufferedInputStream() throws IOException {
		FileInputStream fis = new FileInputStream("D://a.txt");
		BufferedInputStream bis = new BufferedInputStream(fis);
		byte[] buff = new byte[1024];
		int len;
		while ((len = bis.read(buff)) != -1) {
			logger.info(new String(buff, 0, len));
		}
		bis.close();
		fis.close();
	}
	
	public void Test_DataInputStream() throws IOException {
		DataInputStream dis = new DataInputStream(new FileInputStream("D://a.txt"));
		byte[] buff = new byte[1024];
		int len;
		while ((len = dis.read(buff)) != -1) {
			logger.info(new String(buff, 0, len));
		}
		dis.close();
	}
	
	
	
	//FileOutputStream示例
	public void Test_FileOutputStream() throws IOException {
		FileOutputStream fos = new FileOutputStream("D://a.txt");
		fos.write("FileOutputStream write.".getBytes());
		fos.close();
	}
	//FileOutputStream示例
	public void Test_BufferedOutputStream() throws IOException {
		FileOutputStream fos = new FileOutputStream("D://a.txt");
		BufferedOutputStream bos= new BufferedOutputStream(fos);
		bos.write("BufferedOutputStream write.".getBytes());
		bos.close();
		fos.close();
	}
}
