package com.java.thread.dynamic.lock;

import java.util.Random;

/**
 * 使用显示锁解决动态死锁
 * 
 * @author yxd
 *
 */
// 需要添加事物
public class SafeTransferTry implements ITransfer {

	@Override
	public void transfer(UserAccount from, UserAccount to, double amount) throws InterruptedException {
		String threadName = Thread.currentThread().getName();
		Random r = new Random();
		while (true) {
			// getLock().tryLock()尝试获取锁 返回true or false
			// 如果锁被其他对象获取 返回false 没有被获取 返回true继续执行下一步
			if (from.getLock().tryLock()) {
				System.out.println(threadName + "get " + from.getName());
				try {
					if (to.getLock().tryLock()) {
						try {
							System.out.println(threadName + "get " + to.getName());
							// 转账核心业务处理
							if (from.flyMoney(amount)) {
								to.addMoney(amount);
								System.out.println(from.getName() + "转出：" + amount + "元钱到" + to.getName() + "的账户，"
										+ to.getName() + "账户余额为：" + to.getMoney());
								System.out.println(from.getName() + "账户余额为：" + from.getMoney());
								// 转账成功，结束循环。
								break;
							} else {
								System.out.println(from + "账户的余额不足。");
								// 转账不成功成功，结束循环。
								break;
							}
						} finally {
							// getLock().unlock() 释放锁
							to.getLock().unlock();
						}
					}
				} finally {
					// getLock().unlock() 释放锁
					from.getLock().unlock();
				}
			}
			// 错开循环时间 2ms
			Thread.sleep(r.nextInt(2));
		}
	}

}
