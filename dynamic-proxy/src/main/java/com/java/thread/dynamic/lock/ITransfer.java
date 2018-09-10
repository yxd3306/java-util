package com.java.thread.dynamic.lock;

public interface ITransfer {
	
	/**
	 * 
	 * @param from 转出账户
	 * @param to 转入账户
	 * @param amount 转账金额
	 */
	void transfer(UserAccount from, UserAccount to, double amount) throws InterruptedException;

}
