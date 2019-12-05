package my.master.socket.netty.demo4;

import java.net.Socket;

public class Client {

	public static void main(String[] args) throws Exception {

		Socket socket = new Socket("127.0.0.1", 10101);
		
		socket.getOutputStream().write("hello".getBytes());
		
		socket.close();
	}

}
