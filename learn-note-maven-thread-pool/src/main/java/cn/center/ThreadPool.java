package cn.center;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 创建线程池
 * @author :陈进松
 * @date :2019年9月27日 上午11:49:00
 */
public class ThreadPool {
	private static Logger logger = LoggerFactory.getLogger(ThreadPool.class);
	
	
	
	public static void main(String[] args) {
		new ThreadPool().Test();
	}
	
	public void Test() {
		logger.info("==============");
		//返回可用处理器的Java虚拟机的数量,线程数为可用处理器个数+1
		int availableProcessors = Runtime.getRuntime().availableProcessors();
		logger.info("Java虚拟机的数量"+String.valueOf(availableProcessors));
		
		//线程池创建
		ExecutorService es = Executors.newFixedThreadPool(availableProcessors+1, new ThreadFactory() {
			
			public Thread newThread(Runnable r) {
				Thread thread = new Thread(r);
                thread.setName("我的线程");
                return thread;
			}
		});
		for (int i = 0; i < 10 ; i++) {
            es.submit(new Task("我的标号【"+i+"】"));
        }
		es.shutdown();
	}

	class Task implements Callable<Boolean>{
		
	    private  String name;
	    
		public Task(String name) {
	        this.name = name;
		}
		
		public Boolean call() throws Exception {
//			long timeout = (long) (Math.random() * 10);
//            TimeUnit.SECONDS.sleep(timeout);
			logger.info(Thread.currentThread().getName() + "-执行完成..task=" + name +"    耗时：");
			return true;
		}
	}
	
	
	
	public void Test2() {
//		创建一个可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程。
		ExecutorService executorService = Executors.newCachedThreadPool();
//		创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待
		executorService = Executors.newFixedThreadPool(150);
//		创建一个定长线程池，支持定时及周期性任务执行。
		executorService = Executors.newScheduledThreadPool(150);
//		创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序（FIFO， LIFO， 优先级）执行。
		executorService = Executors.newSingleThreadExecutor();
	}
	
	
	
	
	
}
