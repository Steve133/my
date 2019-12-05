package my.master.socket.netty.demo1;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;

/**
 * 解码器
 * <pre>
 * 数据包格式
 * +——----——+——-----——+——----——
 * 	 数据      	|  长度         	 |   数据      	
 * +——----——+——-----——+——----——
 * </pre>
 * @author :陈进松
 * @date :2019年9月28日 下午11:22:55
 */
public class MyDecoder extends FrameDecoder {

	@Override
	protected Object decode(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer) throws Exception {
		//获取 可读字节数目
		int readableBytes = buffer.readableBytes();
		System.out.println("可读字节数目："+readableBytes);
		if(readableBytes<258) {
			return null;
		}
		
		//保存readerIndex的状态
		buffer.markReaderIndex();//与32行呼应
		
		//读数据
		byte[] bytes = new byte[258];
		buffer.readBytes(bytes);
		int length = Integer.parseInt(new String(bytes,254,4));
		System.out.println("数据长度："+length);
		
		if(readableBytes < length){
//			//重置readerIndex为最后一次保存的状态，如果没有保存过，则置为0
			buffer.resetReaderIndex();//与24行呼应
			System.out.println("重置");
			return null;
		}
		
		buffer.resetReaderIndex();
		System.out.println("读数据");
		bytes = new byte[length];
		buffer.readBytes(bytes);
		return new String(bytes);
	}

}
