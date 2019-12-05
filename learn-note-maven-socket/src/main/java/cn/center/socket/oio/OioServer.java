package cn.center.socket.oio;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 传统socket服务端
 * 
 * @author :陈进松
 * @date :2019年9月27日 上午11:51:25
 */
public class OioServer {

	public static void main(String[] args) throws Exception {

		ExecutorService newCachedThreadPool = Executors.newCachedThreadPool();
		// 创建socket服务,监听8080端口
		ServerSocket server = new ServerSocket(8080);
		System.out.println("服务器启动！");
		while (true) {
			// 获取一个套接字（阻塞）
			final Socket socket = server.accept();
			System.out.println("来个一个新客户端！");
			newCachedThreadPool.execute(new Runnable() {

				public void run() {
					// 业务处理
					handler(socket);
				}
			});

		}
	}

	/**
	 * 读取数据
	 * 
	 * @param socket
	 * @throws Exception
	 */
	public static void handler(Socket socket) {
		try {
			byte[] bytes = new byte[1024];
			InputStream in = socket.getInputStream();

			int len = 0;
			System.out.println("阻塞-读取数据");
			while ((len = in.read(bytes)) != -1) {// 读取数据（阻塞），如果客户端不关闭输出流通道会一直阻塞
				System.out.println(new String(bytes, 0, len));
			}

			// 回写数据
			OutputStream out = socket.getOutputStream();
			out.write("好的".getBytes());
			out.flush();
			out.close();// 关闭的时候会发送“-1”长度的包

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(socket!=null) {
					socket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
