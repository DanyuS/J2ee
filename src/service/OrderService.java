package service;

import java.util.List;

import entity.Cart;
import entity.Coupon;
import entity.Menu;
import entity.Orders;
import entity.Restaurant;

public interface OrderService {
	public List<Coupon> getPersonalCoupon(int mid) throws Exception;
	public boolean deleteCoupon(int mid, int cid) throws Exception;
	public List<Coupon> getAllUnaccetpedCoupon(int mid) throws Exception;
	public boolean addCoupon(int mid, int cid) throws Exception;
	
	public boolean addMenu(String rid,String name,String remarks,double price,double currentPrice,int stock,String type,String startTime,String endTime) throws Exception;
	public List<Menu> getAllReleasedMenu(String rid) throws Exception;
	public List<Menu> getAllUnreleasedMenu(String rid) throws Exception;
	public boolean deleteMenu(int meid) throws Exception;
	
	public List<Restaurant> getAllRestaurant() throws Exception;
	
	public boolean addMenuToCart(int mid, String rid, int meid) throws Exception;
	public List<Cart> getCart(int mid) throws Exception;
	
	public List<Coupon> getRestaurantCoupon(int mid, String rid) throws Exception;
	
	public boolean payAccount(int mid,double actualPrice) throws Exception;
	
	public boolean addToCurrentOrder(int mid, String addrId, double price) throws Exception;
	
	public List<Restaurant> getRestaurantBySearch(String sch) throws Exception;
	
	public List<Coupon> getRestaurantCouponById(int id) throws Exception;//根据餐厅ID查找该餐厅所有优惠券
	public boolean restaurantAddCoupon(int id,String couponName,double minPayment,double discount,int level,String startTime,String endTime) throws Exception;
	public boolean deleteCouponByCid(int cid) throws Exception;
	
	public List<Orders> getOrderToSend(String rid) throws Exception;
	public boolean sendOrder(int oid) throws Exception;
	
	public List<Orders> MemberGetAllOrder(int mid) throws Exception;
	
	public boolean refund(int mid, int oid) throws Exception;
	public boolean laterPayAccount(int mid, int oid) throws Exception;
	
	public boolean deleteCart(int mid, int meid) throws Exception;
	public boolean confirmOrder(int oid) throws Exception;
	
	public boolean addToCurrentOrderWithoutPay(int mid,String addrId,double price) throws Exception;
}
