package com.java.thread.dynamic.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 
 * @author yxd 类说明：用户账户实体类
 * 
 * 
 */
public class UserAccount {

	private final String name;
	private double money;

	public UserAccount(String name, double money) {
		this.name = name;
		this.money = money;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public String getName() {
		return name;
	}

	public double getAmount() {
		return money;
	}

	// 资金转入
	public void addMoney(double amount) {
		money = money + amount;
	}

	// 资金转出
	public boolean flyMoney(double amount) {
		if(money<amount) {
			return false;
		}else {
			money = money - amount;
			return true;
		}
	}

	@Override
	public String toString() {
		return "UserAccount [name=" + name + ", money=" + money + "]";
	}
	
	// 获取显示锁对象
	private final Lock lock = new ReentrantLock();
	
	public Lock getLock() {
		return lock;
	}
	
	

}
