package my.master.socket.netty.demo2.server;

import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

/**
 * netty服务端入门
 * @author -琴兽-
 *
 */
public class Server {

	public static void main(String[] args) {

		//服务类
		ServerBootstrap bootstrap = new ServerBootstrap();
		
		//boss线程监听端口，worker线程负责数据读写
		ExecutorService boss = Executors.newCachedThreadPool();
		ExecutorService worker = Executors.newCachedThreadPool();
		
		//设置niosocket工厂
		bootstrap.setFactory(new NioServerSocketChannelFactory(boss, worker));
		
		//设置管道的工厂
		bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
			
			public ChannelPipeline getPipeline() throws Exception {

				ChannelPipeline pipeline = Channels.pipeline();
				pipeline.addLast("decoder", new RequestDecoder());//重要逻辑：请求解码器
				pipeline.addLast("encoder", new ResponseEncoder());//重要逻辑：请求编码器
				pipeline.addLast("helloHandler", new HelloHandler());
				return pipeline;
			}
		});
		
		bootstrap.bind(new InetSocketAddress(10101));
		
		System.out.println("start!!!");
		
	}

}
