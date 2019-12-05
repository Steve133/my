package my.master.socket.netty.demo3;

import java.net.Socket;
import java.nio.ByteBuffer;

public class Client {

	public static void main(String[] args) throws Exception {
		Socket socket = new Socket("127.0.0.1", 10101);
		String message = "hello";
//		message += i;
		byte[] bytes = message.getBytes();
		ByteBuffer buffer = ByteBuffer.allocate(4 + bytes.length);
		buffer.putInt(bytes.length);// 消息长度
		buffer.put(bytes);// 消息正文
		System.out.println(buffer);
		byte[] array = buffer.array();
		for(int i=0; i<1000; i++){
			socket.getOutputStream().write(array);
		}
			
		socket.close();
	}

}
