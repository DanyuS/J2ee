package entity;

public class Coupon {
	private int cid;
	private String couponId;//restaurantID+"-"+order
	private String couponName;
	private double minPayment;
	private double discount;
	private String restaurantRange;
	private String startTime;
	private String endTime;
	private int level;
	
	public int getCid() {
		return cid;
	}
	public void setCid(int cid) {
		this.cid = cid;
	}
	public String getCouponId() {
		return couponId;
	}
	public void setCouponId(String couponId) {
		this.couponId = couponId;
	}
	public String getCouponName() {
		return couponName;
	}
	public void setCouponName(String couponName) {
		this.couponName = couponName;
	}
	public double getMinPayment() {
		return minPayment;
	}
	public void setMinPayment(double minPayment) {
		this.minPayment = minPayment;
	}
	public double getDiscount() {
		return discount;
	}
	public void setDiscount(double discount) {
		this.discount = discount;
	}
	public String getRestaurantRange() {
		return restaurantRange;
	}
	public void setRestaurantRange(String restaurantRange) {
		this.restaurantRange = restaurantRange;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	

}
