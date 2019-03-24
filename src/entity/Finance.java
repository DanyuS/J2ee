package entity;

public class Finance {
	private int financeId;
	private String rid;
	private int mid;
	private String date;
	private double account;
	private int state;
	public int getFinanceId() {
		return financeId;
	}
	public void setFinanceId(int financeId) {
		this.financeId = financeId;
	}
	public String getRid() {
		return rid;
	}
	public void setRid(String rid) {
		this.rid = rid;
	}
	public int getMid() {
		return mid;
	}
	public void setMid(int mid) {
		this.mid = mid;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public double getAccount() {
		return account;
	}
	public void setAccount(double account) {
		this.account = account;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	
}
