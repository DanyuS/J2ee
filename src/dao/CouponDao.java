package dao;

import java.util.List;

import entity.Coupon;

public interface CouponDao {
	public List<Coupon> getPersonalCoupon(int mid) throws Exception;
	public boolean deleteCoupon(int mid, int cid) throws Exception;
	public List<Coupon> getAllUnaccetpedCoupon(int mid) throws Exception;
	public boolean addCoupon(int mid, int cid) throws Exception;//cid就是couponID
	public List<Coupon> getRestaurantCoupon(int mid, String rid) throws Exception;
	
	public List<Coupon> getRestaurantCouponById(int id) throws Exception;//根据餐厅ID查找该餐厅所有优惠券
	public boolean restaurantAddCoupon(int id,String couponName,double minPayment,double discount,int level,String startTime,String endTime) throws Exception;
	public boolean deleteCouponByCid(int cid) throws Exception;
}
