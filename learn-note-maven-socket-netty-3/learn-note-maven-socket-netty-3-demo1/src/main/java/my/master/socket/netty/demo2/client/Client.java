package my.master.socket.netty.demo2.client;

import java.net.InetSocketAddress;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;

import my.master.socket.netty.demo2.common.model.Request;
import my.master.socket.netty.demo2.common.serial.Serializer;
/**
 * netty客户端入门
 * @author -琴兽-
 *
 */
public class Client {

	public static void main(String[] args) throws InterruptedException {
		new Client().run();
	}
		
		
	public void run() throws InterruptedException {
		//服务类
		ClientBootstrap bootstrap = new  ClientBootstrap();
		
		//线程池
		ExecutorService boss = Executors.newCachedThreadPool();
		ExecutorService worker = Executors.newCachedThreadPool();
		
		//socket工厂
		bootstrap.setFactory(new NioClientSocketChannelFactory(boss, worker));
		
		//管道工厂
		bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
			
			public ChannelPipeline getPipeline() throws Exception {
				ChannelPipeline pipeline = Channels.pipeline();
				pipeline.addLast("decoder", new ResponseDecoder());
				pipeline.addLast("encoder", new RequestEncoder());
				pipeline.addLast("hiHandler", new HiHandler());
				return pipeline;
			}
		});
		
		//连接服务端
		ChannelFuture connect = bootstrap.connect(new InetSocketAddress("127.0.0.1", 10101));
		Channel channel = connect.sync().getChannel();
		
		System.out.println("client start");
		
		Scanner scanner = new Scanner(System.in);
		while(true){
			System.out.println("请输入");
			int fubenId = Integer.parseInt(scanner.nextLine());
			int count = Integer.parseInt(scanner.nextLine());
			
			FightRequest fightRequest = new FightRequest();
			fightRequest.setFubenId(fubenId);
			fightRequest.setCount(count);
			
			Request request = new Request();
			request.setModule((short) 1);
			request.setCmd((short) 1);
			request.setData(fightRequest.getBytes());
			//发送请求
			channel.write(request);
		}
	}

	class FightRequest extends Serializer{
		/**
		 * 副本id
		 */
		private int fubenId;
		/**
		 * 次数
		 */
		private int count;

		public int getFubenId() {
			return fubenId;
		}
		public void setFubenId(int fubenId) {
			this.fubenId = fubenId;
		}
		public int getCount() {
			return count;
		}
		public void setCount(int count) {
			this.count = count;
		}
		@Override
		protected void read() {
			this.fubenId = readInt();
			this.count = readInt();
		}
		@Override
		protected void write() {
			writeInt(fubenId);
			writeInt(count);
		}
	}
}
