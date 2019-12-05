package my.master.socket.netty.demo2.common.serial;


import java.nio.ByteOrder;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
/**
 * buff工厂
 * @author -琴兽-
 *
 */
public class BufferFactory {
	
	public static ByteOrder BYTE_ORDER = ByteOrder.BIG_ENDIAN;

	/**
	 * 获取一个buffer
	 * 
	 * @return
	 */
	public static ChannelBuffer getBuffer() {
		ChannelBuffer dynamicBuffer = ChannelBuffers.dynamicBuffer();//分配一个新的动态容量堆缓冲区，其容量会根据写入操作的需要自动增加
		return dynamicBuffer;
	}

	/**
	 * 将数据写入buffer
	 * @param bytes
	 * @return
	 */
	public static ChannelBuffer getBuffer(byte[] bytes) {
		ChannelBuffer copiedBuffer = ChannelBuffers.copiedBuffer(bytes);
		return copiedBuffer;
	}

}
