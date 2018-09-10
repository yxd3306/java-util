package com.java.thread.dynamic.lock;

/**
 * 解决线程安全问题（动态死锁）
 * @author yxd
 *
 */
public class SafeTransfer implements ITransfer {

	private static Object tieLock = new Object();

	@Override
	public void transfer(UserAccount from, UserAccount to, double amount) throws InterruptedException {

		// 获取线程名称
		String threadName = Thread.currentThread().getName();

		// System.identityHashCode() 产生重复HashCode的几率为千万分之一
		long fromHashCode = System.identityHashCode(from);
		long toHashCode = System.identityHashCode(to);

		// 银行业务，转账必须锁定两个账户
		if (fromHashCode < toHashCode) {
			synchronized (from) {
				System.out.println(threadName + "get " + from.getName());
				Thread.sleep(100);
				synchronized (to) {
					System.out.println(threadName + "get " + to.getName());
					from.flyMoney(amount);
					to.addMoney(amount);
				}
			}
		} else if (toHashCode < fromHashCode) {
			synchronized (to) {
				System.out.println(threadName + "get " + to.getName());
				Thread.sleep(100);
				synchronized (from) {
					System.out.println(threadName + "get " + from.getName());
					from.flyMoney(amount);
					to.addMoney(amount);
				}
			}
		} else {
			// 产生重复的HashCode
			synchronized (tieLock) {
				synchronized (from) {
					System.out.println(threadName + "get " + from.getName());
					Thread.sleep(100);
					synchronized (to) {
						System.out.println(threadName + "get " + to.getName());
						from.flyMoney(amount);
						to.addMoney(amount);
					}
				}
			}
		}

	}

}
