package my.master.socket.netty.demo3;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;

public class MyDecoder extends FrameDecoder {

	@Override
	protected Object decode(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer) throws Exception {
		//获取 可读字节数目
		int readableBytes = buffer.readableBytes();
		
		if(readableBytes > 4){//已读长度大于4
			//防止Socket字节流攻击
			if(readableBytes > 2048){//已读长度大于2048
				System.out.println("跳过长度："+readableBytes);
				buffer.skipBytes(readableBytes);//buffer跳过已读数据
			}
			
			//保存readerIndex的状态
			buffer.markReaderIndex();//与32行呼应
			
			//第一次读取得到  数据长度
			int length = buffer.readInt();//Integer为4个字节，发包时定义了前四个字节存放‘数据长度’，所以这个用read'Int'方法，得到的返回值即‘数据长度’
			
			//可读的字节数目 小于 lenth（剩余的字节数目没有达到  第五次读取得到数据长度）    
			if(readableBytes < length){
				//重置readerIndex为最后一次保存的状态，如果没有保存过，则置为0
				buffer.resetReaderIndex();//与24行呼应
				return null;
			}
			
			//读数据
			byte[] bytes = new byte[length];
			buffer.readBytes(bytes);
			//往下传递对象
			return new String(bytes);
		}
		//缓存当前剩余的buffer数据，等待剩下数据包到来
		return null;
	}

}
