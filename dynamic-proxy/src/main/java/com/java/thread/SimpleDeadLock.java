package com.java.thread;


/**
 * 简单顺序死锁：获取锁的顺序问题产生的死锁 （获取顺序不一致）
 * 动态死锁：不产生简单顺序死锁的情况下获取参数顺序不一致导致的死锁 （例如：两个人同时转账）
 * @param args
 */
public class SimpleDeadLock {

	
	private static Object first = new Object();
	private static Object second = new Object();
	
	private static void firstSecond() throws InterruptedException {
		String threadName = Thread.currentThread().getName();
		synchronized (first) {
			System.out.println(threadName+"get first");
			Thread.sleep(100);
			synchronized (second) {
				System.out.println(threadName+"get second");
			}
		}
	}
	
	private static void secondFirst() throws InterruptedException{
		String threadName = Thread.currentThread().getName();
		synchronized (second) {
			System.out.println(threadName+"get second");
			Thread.sleep(100);
			synchronized (first) {
				System.out.println(threadName+"get first");
			}
		}
	}
	
	
	// 子线程
	private static class SubThread extends Thread{
		
		private String name;
		
		public SubThread(String name){
			this.name=name;
		}

		@Override
		public void run() {
			Thread.currentThread().setName(name);
			try {
				secondFirst();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		
		
	}
	
	public static void main(String[] args) throws InterruptedException {
		Thread.currentThread().setName("MainThread");
		SubThread subThread = new SubThread("SubThread");
		subThread.start();
		firstSecond();
	}
	
	
	
	
}
