package cn.center.socket.UDP;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.sf.json.JSONObject;

/**
 * 广播
 * 
 * 先建立一个 UDP接收端，定义自身广播8888
 * 然后 广播8888 发送一个包含自己 IP 地址和端口信息的广播，广播的端口号为 14099
 * @author :陈进松
 * @date :2019年9月23日 下午12:03:01
 */
public class BroadCastListenerAndSend {
	private static Logger logger = LoggerFactory.getLogger(BroadCastListenerAndSend.class);

	static int myBroadCast_port = 8888;
	public static void main(String[] args) {
		DatagramSocket ds = null;
		try {
			ds = new DatagramSocket(myBroadCast_port);
		} catch (SocketException e) {
			e.printStackTrace();
			return;
		}
		
		// 对本机的8888端口进行监控,可以通过8888端口ds对象发送或接收数据
		// UDP 接收端就会收到来自各方触动客户端的回复
		new BroadCastListenerAndSend().Listener(ds);
		new BroadCastListenerAndSend().sendPacket(ds);
	}

	
	String service_ip = "192.168.0.168";
	int service_port = 14099;// 广播服务端口
	// 发包
	private void sendPacket(final DatagramSocket ds) {
		new Thread(new Runnable() {
			public void run() {
				DatagramPacket p = null;
				try {
					// 发送一个包含自己 IP地址和端口信息，端口可以接收消息
					JSONObject object = new JSONObject();
					object.put("ip", InetAddress.getLocalHost().getHostAddress().toString());
					object.put("port", 8888);
					String msg = object.toString();
					
					
					byte[] bytes = msg.getBytes();
					p = new DatagramPacket(bytes, bytes.length, new InetSocketAddress(service_ip, service_port));
					ds.send(p);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	//收听广播，监听接收UDP返回包，
	private void Listener(final DatagramSocket ds) {
		new Thread(new Runnable() {
			public void run() {
				try {
					while (true) {
						
						DatagramPacket p = new DatagramPacket(new byte[1024], 1024);
						ds.receive(p);// 对接收到的数据打印
						
						String string = new String(p.getData(), 0, p.getLength());
						logger.info(string);
						
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
}
