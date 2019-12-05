package my.master.socket.netty.demo4;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.CharBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;

public class ByteBufferTest {

	public static void main(String[] args) throws IOException {
//		有七中缓冲区类型
		ByteBuffer bb = null;
//		CharBuffer cb;
//		DoubleBuffer db;
//		FloatBuffer fb;
//		IntBuffer ib;
//		LongBuffer lb;
//		ShortBuffer sb;
		
		
		//等同于allocate
		byte[] Byte = "hello".getBytes();
//		bb = bb.wrap(Byte);
		
		bb = ByteBuffer.allocate(4+Byte.length);
		
		bb.putInt(Byte.length);
		bb.put(Byte);
		System.out.println(bb);
		//缓冲区的四个基本属性
//		int capacity = bb.capacity();//容量，缓存区最大容量，创建时设定，并不能被改变
//		int limit = bb.limit();//上界，缓存区现存元素的个数
//		int position = bb.position();//位置，下一个要读或写的位置。位置由get(),put()更新
//		Buffer mark = bb.mark();//标记，一个备忘位置，mark设置mark=position，reset设定position=mark，未设定前时undifined
//		System.out.println(capacity);
//		System.out.println(limit);
//		System.out.println(position);
//		System.out.println(mark);
		
		
//		System.out.println("reset:"+bb.reset());
//		System.out.println("getInt:"+bb.getInt());
		
//		复制缓冲区
//		ByteBuffer duplicate = bb.duplicate();
//		System.out.println(duplicate.capacity());
//		System.out.println(duplicate.limit());
//		System.out.println(duplicate.position());
//		System.out.println(duplicate.mark());
	}
}
