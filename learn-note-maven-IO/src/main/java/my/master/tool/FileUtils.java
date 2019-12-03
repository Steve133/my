package my.master.tool;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("rawtypes")
public class FileUtils {
	private static Logger logger = LoggerFactory.getLogger(FileUtils.class);

	
	
	//-----------------------------------------------------------------------
	/**
	 * 字符流 写文件
	 * @param fileName 文件名，带路径
	 * @param str      写入数据
	 * @param append   是否在文件后追加
	 * @return
	 * @throws Exception
	 */
	public static void writerLine(String fileName, String str, boolean append) throws Exception {
		FileWriter writer = null;
		try {
			File file = new File(fileName);// 文件不存在，自动创建，但是路径不会
			createFilePath(file);
			writer = new FileWriter(file, append);// 追加
			// 向文件写入内容
			writer.append(str);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			throw new Exception("写入文件" + fileName + "异常" + e.getMessage());
		} finally {
			if(writer!=null) {
				writer.close();
			}
		}
	}
	/**
	 * 字符流 写文件
	 * @param fileName 文件名，带路径
	 * @param str      写入数据
	 * @param append   是否在文件后追加
	 * @return
	 * @throws Exception
	 */
	public static void writerLines(String fileName, List list, boolean append) throws Exception {
		FileWriter writer = null;
		try {
			File file = new File(fileName);
			createFilePath(file);
			writer = new FileWriter(file, append);
			
			for (Object object : list) {
				writer.append(String.valueOf(object));
			}
			writer.flush();
			writer.close();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			throw new Exception("写入文件" + fileName + "异常" + e.getMessage());
		} finally {
			if(writer!=null) {
				writer.close();
			}
		}
	}
	
	/**
	 * 字节流 写文件
	 * @param fileName 文件名，带路径
	 * @param str      写入数据
	 * @param append   是否在文件后追加
	 * @return
	 * @throws Exception
	 */
	public static void write(String fileName, String str, boolean append) throws Exception {
		DataOutputStream dos = null;
		BufferedOutputStream bos = null;
		try {
			File file = new File(fileName);// 文件不存在，自动创建，但是路径不会
			createFilePath(file);
			dos = new DataOutputStream(new FileOutputStream(fileName,append));
			bos = new BufferedOutputStream(dos);
			
			byte[] b = str.getBytes();
			writeByte(b,bos,1024);
			
			dos.flush();
			dos.close();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			throw new Exception("写入文件" + fileName + "异常" + e.getMessage());
		} finally {
			if(dos!=null) {
				dos.close();
			}
			if(bos!=null) {
				bos.close();
			}
		}
	}
	
	/**
	 * 往输出流里写数据
	 * <br>
	 * 从内存里写到文件，图片，字节数组
	 * @param b		数据		包含数据的数组
	 * @param bos	输出缓存流	将数据写到流中
	 * @param len	每次写入长度
	 * @throws IOException
	 */
	public static void writeByte(byte[] b,BufferedOutputStream bos,int len) throws IOException{
		// 向文件写入内容
		int blen;
		if (b == null) {
            throw new NullPointerException();
        } else if ((blen=b.length) == 0) {
            return;
        }
		
		int off = 0;
		while(blen-off>len) {
			bos.write(b, off, len);
			off = blen - len;
		}
		bos.write(b, off, blen-off);
	}
	
	/**
	 * 字符流 读文件
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public static String readLine(String fileName) throws Exception {
		File file = new File(fileName);// 文件不存在，自动创建，但是路径不会
		if (!file.exists()) {
			throw new Exception("文件不存在：" + fileName);
		}
		StringBuilder sb = new StringBuilder();
		FileReader reader = null;
		BufferedReader br = null;
		try {
			reader = new FileReader(file);
			br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言
			String line = null;
			while ((line = br.readLine()) != null) {
				// 一次读入一行数据
				sb.append(line);
				logger.info(line);
			}
			return sb.toString();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			throw new Exception("读取文件" + fileName + "异常" + e.getMessage());
		} finally {
			if(br!=null) {
				br.close();
			}
			if(reader!=null) {
				reader.close();
			}
		}
	}
	/**
	 * 字节流 读文件
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public static String read(String fileName) throws Exception {
		File file = new File(fileName);// 文件不存在，自动创建，但是路径不会
		if (!file.exists()) {
			throw new Exception("文件不存在：" + fileName);
		}
		DataInputStream dis = null;
		BufferedInputStream bis = null;
		ByteArrayOutputStream bao = null;
		try {
			dis = new DataInputStream(new FileInputStream(fileName));
			bis = new BufferedInputStream(dis);
			bao = new ByteArrayOutputStream();
			
			readByte(bis, bao, 1024);
			
			dis.close();
			bis.close();
			
			return new String(bao.toByteArray(), 0, bao.toByteArray().length, "utf-8");
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			throw new Exception("读取文件" + fileName + "异常" + e.getMessage());
		} finally {
			if(bao!=null) {
				bao.close();
			}
			if(dis!=null) {
				dis.close();
			}
			if(bis!=null) {
				bis.close();
			}
		}
	}
	
	/**
	 * 读取流数据到byte数组
	 * @param bis 输入流	数据	从文件，接收到的数据写到内存对象中
	 * @param bao 
	 * @param length 每次读取长度
	 * @throws IOException 
	 */
	public static void readByte(BufferedInputStream bis,ByteArrayOutputStream bao,int length) throws IOException{
		byte[] buff = new byte[length];
		int len;//真实读取长度
		int off = 0;
		while ((len = bis.read(buff)) != -1) {
			logger.info(new String(buff, 0, len));
			bao.write(buff, off, len);
			off += len;
		}
	}
	
	/**
	 * 判断父路径存不存在，
	 * <br>
	 * 不存在则创建全路径
	 * @param file
	 * @throws Exception
	 */
	public static void createFilePath(File file) throws Exception {
		if (!file.getParentFile().exists()) {// 判断父目录路径是否存在
			file.getParentFile().mkdirs();// 不存在则创建父目录
			file.createNewFile();
		}
	}

	/**
	 * 展示指定文件夹下的所有文件
	 * <br>
	 * 不展示文件夹下的文件夹中文件
	 * @param filepath /结尾
	 * @return
	 * @throws Exception 
	 */
	public static String[] showFilePath(String filepath) throws Exception {
		try {
			File file = new File(filepath);
			if (!file.isDirectory()) {
				throw new Exception("路径不是文件夹："+filepath);
			} else if (file.isDirectory()) {
				logger.info("文件夹:" + file.getName());
				String[] filelist = file.list();
				return filelist;
			}
		} catch (FileNotFoundException e) {
			logger.info("展示某个文件夹下的所有文件 异常：" + e.getMessage());
			throw new Exception("展示某个文件夹下的所有文件 异常："+e.getMessage());
		}
		return new String[0];
	}
	
	/**
	 * 删除指定文件夹下的所有<Strong>文件夹</Strong>和<Strong>文件</Strong>
	 * @param delpath D:\test   注意：没有\结尾
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static boolean deleteFile(String delpath) throws FileNotFoundException, IOException {
		try {
			File file = new File(delpath);
			if (!file.isDirectory()) {
				file.delete();
			} else if (file.isDirectory()) {
				//文件夹
				String[] filelist = file.list();
				
				for (int i = 0; i < filelist.length; i++) {
					//下层文件
					String nextPath = delpath + "\\" + filelist[i];
					File nextfile = new File(nextPath);
					if (!nextfile.isDirectory()) {
						nextfile.delete();
					} else if (nextfile.isDirectory()) {
						deleteFile(nextPath);
					}
				}
				file.delete();
			}
			return true;
		} catch (FileNotFoundException e) {
			logger.info("deletefile()   Exception:" + e.getMessage());
		}
		return false;
	}
}
