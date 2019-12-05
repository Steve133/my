package my.master.socket.netty.demo2.common.module.fuben.response;

import my.master.socket.netty.demo2.common.serial.Serializer;

public class FightResponse extends Serializer{

	/**
	 * 获取金币
	 */
	private int gold;
	
	

	public int getGold() {
		return gold;
	}

	public void setGold(int gold) {
		this.gold = gold;
	}

	@Override
	protected void read() {
		this.gold = readInt();
	}

	@Override
	protected void write() {
		writeInt(gold);
	}
}
