package com.java.thread.dynamic.lock;

public class NormaITransfer implements ITransfer {

	@Override
	public void transfer(UserAccount from, UserAccount to, double amount) throws InterruptedException {

		// 获取线程名称
		String threadName = Thread.currentThread().getName();

		// 银行业务，转账必须锁定两个账户
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
