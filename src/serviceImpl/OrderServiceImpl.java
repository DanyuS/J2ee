package serviceImpl;

import java.util.List;

import dao.CouponDao;
import dao.MemberDao;
import dao.MenuDao;
import dao.OrderDao;
import dao.RestaurantDao;
import daoImpl.CouponDaoImpl;
import daoImpl.MemberDaoImpl;
import daoImpl.MenuDaoImpl;
import daoImpl.OrderDaoImpl;
import daoImpl.RestaurantDaoImpl;
import entity.Cart;
import entity.Coupon;
import entity.Menu;
import entity.Orders;
import entity.Restaurant;
import service.OrderService;

public class OrderServiceImpl implements OrderService {
	
	CouponDao couponDao=new CouponDaoImpl();
	MenuDao menuDao=new MenuDaoImpl();
	RestaurantDao restaurantDao=new RestaurantDaoImpl();
	OrderDao orderDao=new OrderDaoImpl();
	MemberDao memberDao=new MemberDaoImpl();

	@Override
	public List<Coupon> getPersonalCoupon(int mid) throws Exception {
		// TODO Auto-generated method stub
		return couponDao.getPersonalCoupon(mid);
	}

	@Override
	public boolean deleteCoupon(int mid, int cid) throws Exception {
		// TODO Auto-generated method stub
		return couponDao.deleteCoupon(mid, cid);
	}

	@Override
	public List<Coupon> getAllUnaccetpedCoupon(int mid) throws Exception {
		// TODO Auto-generated method stub
		return couponDao.getAllUnaccetpedCoupon(mid);
	}

	@Override
	public boolean addCoupon(int mid, int cid) throws Exception {
		// TODO Auto-generated method stub
		return couponDao.addCoupon(mid, cid);
	}

	@Override
	public boolean addMenu(String rid, String name, String remarks, double price, double currentPrice, int stock,
			String type, String startTime, String endTime) throws Exception {
		// TODO Auto-generated method stub
		return menuDao.addMenu(rid, name, remarks, price, currentPrice, stock, type, startTime, endTime);
	}

	@Override
	public List<Menu> getAllReleasedMenu(String rid) throws Exception {
		// TODO Auto-generated method stub
		return menuDao.getAllReleasedMenu(rid);
	}

	@Override
	public List<Menu> getAllUnreleasedMenu(String rid) throws Exception {
		// TODO Auto-generated method stub
		return menuDao.getAllUnreleasedMenu(rid);
	}

	@Override
	public boolean deleteMenu(int meid) throws Exception {
		// TODO Auto-generated method stub
		return menuDao.deleteMenu(meid);
	}

	@Override
	public List<Restaurant> getAllRestaurant() throws Exception {
		// TODO Auto-generated method stub
		return restaurantDao.getAllRestaurant();
	}

	@Override
	public boolean addMenuToCart(int mid, String rid, int meid) throws Exception {
		// TODO Auto-generated method stub
		return orderDao.addMenuToCart(mid, rid, meid);
	}

	@Override
	public List<Cart> getCart(int mid) throws Exception {
		// TODO Auto-generated method stub
		return orderDao.getCart(mid);
	}

	@Override
	public List<Coupon> getRestaurantCoupon(int mid, String rid) throws Exception {
		// TODO Auto-generated method stub
		return couponDao.getRestaurantCoupon(mid, rid);
	}

	@Override
	public boolean payAccount(int mid, double actualPrice) throws Exception {
		// TODO Auto-generated method stub
		return orderDao.payAccount(mid, actualPrice);
	}

	@Override
	public boolean addToCurrentOrder(int mid, String addrId, double price) throws Exception {
		// TODO Auto-generated method stub
		return orderDao.addToCurrentOrder(mid, addrId, price);
	}

	@Override
	public List<Restaurant> getRestaurantBySearch(String sch) throws Exception {
		// TODO Auto-generated method stub
		return restaurantDao.getRestaurantBySearch(sch);
	}

	@Override
	public List<Coupon> getRestaurantCouponById(int id) throws Exception {
		// TODO Auto-generated method stub
		return couponDao.getRestaurantCouponById(id);
	}

	@Override
	public boolean restaurantAddCoupon(int id, String couponName, double minPayment, double discount, int level,
			String startTime, String endTime) throws Exception {
		// TODO Auto-generated method stub
		return couponDao.restaurantAddCoupon(id, couponName, minPayment, discount, level, startTime, endTime);
	}

	@Override
	public boolean deleteCouponByCid(int cid) throws Exception {
		// TODO Auto-generated method stub
		return couponDao.deleteCouponByCid(cid);
	}

	@Override
	public List<Orders> getOrderToSend(String rid) throws Exception {
		// TODO Auto-generated method stub
		return restaurantDao.getOrderToSend(rid);
	}

	@Override
	public boolean sendOrder(int oid) throws Exception {
		// TODO Auto-generated method stub
		return restaurantDao.sendOrder(oid);
	}

	@Override
	public List<Orders> MemberGetAllOrder(int mid) throws Exception {
		// TODO Auto-generated method stub
		return memberDao.getAllOrder(mid);
	}

	@Override
	public boolean refund(int mid, int oid) throws Exception {
		// TODO Auto-generated method stub
		return orderDao.refund(mid, oid);
	}

	@Override
	public boolean laterPayAccount(int mid, int oid) throws Exception {
		// TODO Auto-generated method stub
		return orderDao.laterPayAccount(mid, oid);
	}

	@Override
	public boolean deleteCart(int mid, int meid) throws Exception {
		// TODO Auto-generated method stub
		return orderDao.deleteCart(mid, meid);
	}

	@Override
	public boolean confirmOrder(int oid) throws Exception {
		// TODO Auto-generated method stub
		return orderDao.confirmOrder(oid);
	}

	@Override
	public boolean addToCurrentOrderWithoutPay(int mid, String addrId, double price) throws Exception {
		// TODO Auto-generated method stub
		return orderDao.addToCurrentOrderWithoutPay(mid, addrId, price);
	}

}
