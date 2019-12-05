package my.master.socket.netty.demo4;

import java.nio.ByteBuffer;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;

public class ChannerBufferTest {

//	ChannelBuffer的模型图如下：
//
//	 +-------------------+------------------+------------------+
//	 | discardable bytes |  readable bytes  |  writable bytes  |
//	 |                   |     (CONTENT)    |                  |
//	 +-------------------+------------------+------------------+
//	 |                   |                  |                  |
//	 0      <=      readerIndex   <=   writerIndex    <=    capacity
//	
//	如上图所示，一个ChannelBuffer被划分为三个部分：
//	discardable：表示已经  读过  的内容缓冲区
//	readable：表示  可读  的内容缓冲区
//	writable：表示  可写  的内容缓冲区
//	
//	ChannelBuffer的这三个缓冲区由2个内部控制指针来控制：
//	readerIndex：控制读缓冲区首地址
//	writerIndex：控制写缓冲区首地址
//	因此，ChannelBuffer提供的大部分操作都是围绕readerIndex和writerIndex来进行的。
	
//	ChannelBuffer的常用方法如下：
//	============================================
//	read()/skip()
//	从readerIndex读出或跳过指定数目的字节，同时readerIndex = readerIndex + byteNumber.如果readerIndex > capacity，表示读取下标越界，会抛出IndexOutOfBoundsException异常
//	
//	readable()：boolean
//	如果buffer有可读内容（此时writerIndex > readerIndex）,则返回true，否则返回false
//	
//	readerIndex():int
//	返回readerIndex
//	
//	readableBytes():int
//	返回可读的字节数目(writerIndex - readerIndex)
//	============================================
//	write();
//	写入指定数目的字节，同时writerIndex = writerIndex + byteNumber. 如果writerIndex > capacity，表示写入下标越界，会抛出IndexOutOfBoundsException异常
//
//	writable():boolean
//	如果buffer有可写入空间（此时writerIndex < capacity），则返回true，否则返回false。
//
//	writerIndex(): int
//	返回writerIndex
//
//	writeableIndex():int
//	返回可写入的字节数（capacity - writerIndex）
//	
//	
//	
//	
//	discardReadBytes()
//	丢弃已读的内容
//	调用discardReadBytes()之前：
//
//	+-------------------+------------------+------------------+
//	| discardable bytes |  readable bytes  |  writable bytes  |
//	+-------------------+------------------+------------------+
//	|                   |                  |                  |
//	0      <=      readerIndex   <=   writerIndex    <=    capacity
//
//	调用 discardReadBytes()方法后
//
//	+------------------+--------------------------------------+
//	|  readable bytes  |    writable bytes (got more space)   |
//	+------------------+--------------------------------------+
//	|                  |                                      |
//	readerIndex (0) <= writerIndex (decreased)    <=      capacity
//	
//	
//	
//	
//	clear()
//	丢弃所有的数据，并将readerIndex和writerIndex重置为0。
//
//	调用clear()之前
//	 +-------------------+------------------+------------------+
//	 | discardable bytes |  readable bytes  |  writable bytes  |
//	 +-------------------+------------------+------------------+
//	 |                   |                  |                  |
//	 0      <=      readerIndex   <=   writerIndex    <=    capacity
//
//	 调用clear()之后
//
//	 +---------------------------------------------------------+ |             
//	                 writable bytes (got more space)             | 
//	 +---------------------------------------------------------+ |                                                         | 
//	0 = readerIndex = writerIndex            <=            capacity
//	
//	
//	markReaderIndex()
//	markWriterIndex()
//	保存readerIndex或writerIndex的状态
//	
//	
//	resetReaderIndex()
//	resetWriterIndex()
//	重置readerIndex或writerIndex为最后一次保存的状态，如果没有保存过，则置为0
//	
//	
//	duplicate()
//	slice()
//	拷贝和源目标共享buffer的数据区域，但是拷贝有自己的readerIndex和writerIndex以及markIndex，实际上只是拷贝了控制指针，数据区还是与源buffer共享。
//
//	
//	copy()
//	拷贝整个buffer，包括控制指针和数据区
	
	public static void main(String[] args) {
//		ChannelBuffer的创建
//		buffer(int) 分配一个新的固定容量堆缓冲区。默认长度256 
		ChannelBuffer heapBuffer    = ChannelBuffers.buffer(128);
//		directBuffer(int) 分配一个新的固定容量直接缓冲区。
		ChannelBuffer directBuffer  = ChannelBuffers.directBuffer(256);
//		dynamicBuffer(int) 分配一个新的动态容量堆缓冲区，其容量会根据写入操作的需要自动增加。
		ChannelBuffer dynamicBuffer = ChannelBuffers.dynamicBuffer(512);
		ChannelBuffer wrappedBuffer = ChannelBuffers.wrappedBuffer(new byte[128], new byte[256]);
//		根据具体组数创建一个缓冲区
		ChannelBuffer copiedBuffer = ChannelBuffers.copiedBuffer(ByteBuffer.allocate(128));
		
		
		
		System.out.println("创建10个容量缓存区");
		ChannelBuffer buffer = ChannelBuffers.buffer( 10 );
		System.out.print("返回可读的字节数目: " + buffer.readableBytes( )+"\t");
		System.out.print("读缓存首地址 " + buffer.readerIndex( )+"\t");
		System.out.print("返回可写的字节数目: " + buffer.writableBytes( )+"\t");
		System.out.print("写缓存首地址 " + buffer.writerIndex( )+"\n");
		
		buffer.writeInt( -32523523 );//写入指定数目的字节，同时writerIndex = writerIndex + byteNumber. 
		
		System.out.println("写入一个integer：-32523523");
		System.out.print("返回可读的字节数目: " + buffer.readableBytes( )+"\t");
		System.out.print("读缓存首地址 " + buffer.readerIndex( )+"\t");
		System.out.print("返回可写的字节数目: " + buffer.writableBytes( )+"\t");
		System.out.print("写缓存首地址 " + buffer.writerIndex( )+"\n");
		
		System.out.println("取出数据："+buffer.readInt());
		System.out.print("返回可读的字节数目: " + buffer.readableBytes( )+"\t");
		System.out.print("读缓存首地址 " + buffer.readerIndex( )+"\t");
		System.out.print("返回可写的字节数目: " + buffer.writableBytes( )+"\t");
		System.out.print("写缓存首地址 " + buffer.writerIndex( )+"\n");
//		buffer.writeInt( 10 );
//		System.out.println("写入两个integer");
//		System.out.println("返回可读的字节数目: " + buffer.readableBytes( ));
//		System.out.println("读缓存首地址 " + buffer.readerIndex( ));
//		System.out.println("返回可写的字节数目: " + buffer.writableBytes( ));
//		System.out.println("写缓存首地址 " + buffer.writerIndex( ));
//		
//		int i = buffer.readInt( );
//		System.out.println("读一个integer值: " + i);
//		System.out.println("返回可读的字节数目: " + buffer.readableBytes( ));
//		System.out.println("读缓存首地址 " + buffer.readerIndex( ));
//		System.out.println("返回可写的字节数目: " + buffer.writableBytes( ));
//		System.out.println("写缓存首地址 " + buffer.writerIndex( ));
//		
//		buffer.discardReadBytes( );
//		System.out.println("after discard read bytes");
//		System.out.println("返回可读的字节数目: " + buffer.readableBytes( ));
//		System.out.println("读缓存首地址 " + buffer.readerIndex( ));
//		System.out.println("返回可写的字节数目: " + buffer.writableBytes( ));
//		System.out.println("写缓存首地址 " + buffer.writerIndex( ));
//		
//		buffer.resetReaderIndex( );
//		System.out.println("after reset reader index");
//		System.out.println("返回可读的字节数目: " + buffer.readableBytes( ));
//		System.out.println("读缓存首地址 " + buffer.readerIndex( ));
//		System.out.println("返回可写的字节数目: " + buffer.writableBytes( ));
//		System.out.println("写缓存首地址 " + buffer.writerIndex( ));
//		
//		buffer.resetWriterIndex( );
//		System.out.println("after reset writer index");
//		System.out.println("返回可读的字节数目: " + buffer.readableBytes( ));
//		System.out.println("读缓存首地址 " + buffer.readerIndex( ));
//		System.out.println("返回可写的字节数目: " + buffer.writableBytes( ));
//		System.out.println("写缓存首地址 " + buffer.writerIndex( ));
	}
}
