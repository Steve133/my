package cn.center;

/**
 * 这是一个测试：Java虚拟机最多支持多少个线程
 * 创建线程计数
 * 测试在不同虚拟机运行内存下，线程创建个数
 * @author :陈进松
 * @date :2019年9月22日 下午11:59:14
 */
public class ThreadTest {
	private static Object s = new Object();
	private static int count = 0;

	public static void main(String[] argv) {
		for (;;) {
			new Thread(new Runnable() {
				public void run() {
					synchronized (s) {
						count += 1;
						System.err.println("New thread #" + count);
					}
					for (;;) {
						try {
							Thread.sleep(1000);
						} catch (Exception e) {
							System.err.println(e);
						}
					}
				}
			}).start();
		}
	}
}
//-Xms2m -Xmx2m -Xss1024k
//2 mb --> 5744 threads
//4 mb --> 5743 threads
//8 mb --> 5735 threads
//12 mb --> 5724 threads
//16 mb --> 5712 threads
//24 mb --> 5687 threads
//32 mb --> 5662 threads
//48 mb --> 5610 threads
//64 mb --> 5561 threads
//96 mb --> 5457 threads
//128 mb --> 5357 threads
//192 mb --> 5190 threads
//256 mb --> 5014 threads
//384 mb --> 4606 threads
//512 mb --> 4202 threads
//768 mb --> 3388 threads
//1024 mb --> 2583 threads

//结果显示
//堆大小和最大线程数却是呈反比例关系
//以32位Windows系统为例，每一个进程的用户地址空间是2G，假如每个线程栈的大小是128K，最多会有16384(=2*1024*1024 / 128)个线程
//线程数量的多少与堆内存、栈内存的大小有着直接的关系，只不过栈内存更加明显一些。在操作系统中，一个进程的内存大小是有限制的，这个限制称为地址空间，比如32位的Windows操作系统最大的地址空间约为2G多一点，操作系统会将进程内存的大小控制在最大地址空间以内











