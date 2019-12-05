package cn.center.socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	private static Logger logger = LoggerFactory.getLogger(Server.class);
	
	public static void main(String[] args) {
		new Server().socketServer();
	}

	private void socketServer() {
		int port = 8080;
		int sotimeout = 8000;
		ServerSocket server = null;
		Socket socket = null;
		try {
			// 监听指定的端口
			logger.info("socket server is starting");
			server = new ServerSocket(port);
			logger.info("port:"+port+",waiting for accept");
			logger.info("连接超时：:"+sotimeout/1000+"s");
			while (true) {
				socket = server.accept();
				socket.setSoTimeout(sotimeout);//超时时间
				
				logger.info("监听端口，执行任务");
				socketTask(socket);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}finally{
            if (socket!=null) {
                try {
                	socket.close();
                } catch (IOException e) {
                	logger.error(e.getMessage(),e);
                }
                if (server!=null) {
                	try {
                		server.close();
                	} catch (IOException e) {
                		logger.error(e.getMessage(),e);
                	}
                }
            }
        }
		logger.info("socket服务异常关闭");
	}

	
	private static int sockettimeout;
	private static int connecttimeout;
	private void socketTask(Socket sot) {
		//开始时间
		long time = System.currentTimeMillis();
		String remoteAddr = sot.getInetAddress().getHostAddress() + ":" + sot.getLocalPort();
		logger.info(remoteAddr + "连接\n");
		
		DataInputStream in = null;
		OutputStream out = null;
		try {
			// 建立好连接后，从socket中获取输入流，并建立缓冲区进行读取
			in = new DataInputStream(sot.getInputStream());
			// 读取Socket传输数据
			//读取方式1：特定字节包含 数据总长度
			int s = 254;
			int e = 274;
			String read = read(in,s,e);
//			String read = read2(in);
			
			
			
			if(read == null) {
				logger.info(remoteAddr + "->socket接收请求数据异常");
			}
			//2.解析数据
			logger.info(remoteAddr + "->socket请求数据:" + read);
			
			
			
			//3发出转发返回数据
			String respose = "这是返回数据！";
			logger.info(remoteAddr + "->socket返回数据:" + respose);
			byte[] sendBytes = respose.getBytes();
			
			out = sot.getOutputStream();
			out.write(sendBytes);
			out.flush();
			out.close();
			in.close();
			sot.close();
			//记录耗时
			logger.info(remoteAddr + "->socket close 耗时:" + (System.currentTimeMillis() - time) +" 毫秒");
		} catch (IOException e) {
			logger.error("socketTask异常："+e.getMessage(),e);
		} finally {
			try {
				if (in != null) {
					in.close();
				}
				if (out != null) {
					out.close();
				}
				if (sot != null) {
					sot.close();
				}
			} catch (IOException e) {
				logger.info("连接回收异常："+e.getMessage());
				logger.info(remoteAddr + "->socket close 耗时:" + (System.currentTimeMillis() - time) +" 毫秒");
			}
		}
	}

	
	private String read2(DataInputStream in) {
		InputStreamReader inputStreamReader = null;
		BufferedReader bufferedReader = null;
		try {
			inputStreamReader = new InputStreamReader(in);// 提高效率，将自己字节流转为字符流
			bufferedReader = new BufferedReader(inputStreamReader);// 加入缓冲区
			StringBuffer sb = new StringBuffer();
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				sb.append(line);
			}
			inputStreamReader.close();
			bufferedReader.close();
			return sb.toString();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} finally {
			try {
				if(bufferedReader!=null) {
					bufferedReader.close();
				}
				if(inputStreamReader!=null) {
					inputStreamReader.close();
				}
			} catch (IOException e) {
				logger.error("字节流关闭异常："+e.getMessage(),e);
			}
		}
		return null;
	}

	/**
	 * 
	 * @param in
	 * @param s 截取数据总长度的起始位置
	 * @param e 截取数据总长度的结束位置
	 * @return
	 */
	private String read(DataInputStream in, int s, int e) {
		//合包字节集
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		
		try {
			int total = 0;//总长度
			/**满足连个条件跳出循环
			 * 1总长度 不等于0 
			 * 2总长度 等于bos长度 **/
			int off = 0;
			while(total == 0 || total != bos.size()) {
				byte[] b = new byte[1024];
				int len = in.read(b, 0, b.length);
				bos.write(b, 0, len);
				off+=len;
				
				if(total == 0 && bos.size() >= e) {
					byte[] copy = new byte[e];
					/**
					 * src从0开始读
					 * copy从0开始写
					 * 读取写入的长度：length；写入的长度length
					 */
					System.arraycopy(bos.toByteArray(), 0, copy, 0, copy.length);
					String dataStr = new String(copy,"GBK");
					total = Integer.parseInt(dataStr.substring(s, e).trim());
				}
			}
			if(bos.toByteArray().length > 0) {
				String string = new String(bos.toByteArray(), 0, bos.toByteArray().length, "GBK");
				bos.close();
				return string;
			}
		} catch (NumberFormatException e1) {
			logger.error(e1.getMessage(),e1);
		} catch (UnsupportedEncodingException e1) {
			logger.error(e1.getMessage(),e1);
		} catch (IOException e1) {
			logger.error(e1.getMessage(),e1);
		}finally {
			try {
				if(bos != null) {
					bos.close();
				}
			} catch (IOException e2) {
				logger.error("ByteArrayOutputStream字节流关闭异常："+e2.getMessage(),e2);
			}
		}
		return null;
	}
	
	
	
	
	
}
