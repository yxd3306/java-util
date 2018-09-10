package com.java.thread.dynamic.lock;


@SuppressWarnings("unused")
public class PayCompany {

	// 负责转账业务
	private static class TransferThread extends Thread{
		
		private String name;
		private UserAccount from;
		private UserAccount to;
		private double amount;
		private ITransfer transfer;
		
		
		public TransferThread(String name, UserAccount from, UserAccount to, double amount, ITransfer transfer) {
			this.name=name;
			this.from=from;
			this.to=to;
			this.amount=amount;
			this.transfer=transfer;
		}


		@Override
		public void run() {
			Thread.currentThread().setName(name);
			try {
				transfer.transfer(from, to, amount);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		UserAccount yxd = new UserAccount("喻湘东", 2000);
		UserAccount xxx = new UserAccount("熊锡锡", 3000);
		UserAccount fjh = new UserAccount("方几何", 3000);
		// NormaITransfer transfer = new NormaITransfer();
		// SafeTransfer transfer = new SafeTransfer();
		SafeTransferTry transfer = new SafeTransferTry();
		
		
		// 相同账户同时转账给对方，不作处理会产生动态死锁，获取参数的顺序不一样
		TransferThread yxdToXxx = new TransferThread("喻湘东_TO_熊锡锡", yxd, xxx, 3000, transfer);
		TransferThread xxxToYxd = new TransferThread("熊锡锡_TO_喻湘东", xxx, yxd, 3000, transfer);
		TransferThread fjhToYxd = new TransferThread("方几何_TO_喻湘东", fjh, yxd, 1000, transfer);
		
		yxdToXxx.start();
		xxxToYxd.start();
		fjhToYxd.start();
	}
	
	
}
