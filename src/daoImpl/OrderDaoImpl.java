package daoImpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import dao.MemberDao;
import dao.MenuDao;
import dao.OrderDao;
import dao.RestaurantDao;
import entity.Cart;
import entity.DeliveryAddress;
import entity.Finance;
import entity.Manager;
import entity.Member;
import entity.Menu;
import entity.Orders;
import entity.Restaurant;

public class OrderDaoImpl implements OrderDao {

	JDBConnect j = new JDBConnect();
	Session session = j.currentSession();

	
	MenuDao menuDao = new MenuDaoImpl();
	MemberDao memberDao = new MemberDaoImpl();
	RestaurantDao restaurantDao = new RestaurantDaoImpl();

	// public static final String MAP_KEY = "1bbf8e9ea91d481c9c9cffd2d36b3a8d";
	MapUtil mapUtil = new MapUtil();

	@Override
	public boolean addMenuToCart(int mid, String rid, int meid) throws Exception {
		// TODO Auto-generated method stub
		Menu m = menuDao.findMenuByMeid(meid);
		boolean result=false;
		Transaction tx1 = null;
		try {
			tx1 = session.beginTransaction();
			List ms = session.createQuery("FROM Cart").list();
			for (Iterator iterator = ms.iterator(); iterator.hasNext();) {
				Cart c = (Cart) iterator.next();
				int mid1=c.getMid();
				String rid1=c.getRid();
				int meid1=c.getMeid();
				if(mid==mid1&&rid.equals(rid1)&&meid==meid1) {
					System.out.println("重复啦！！！");
					result=false;
				}else {
					result=true;
					System.out.println("没有重复啊！！！");
				
				}
			}
			tx1.commit();
		} catch (HibernateException e) {
			if (tx1 != null)
				tx1.rollback();
			e.printStackTrace();
		} 
		
		
		
		if(result) {
			Transaction tx = null;
			Integer cartId = null;
			try {
				tx = session.beginTransaction();
				Cart cart = new Cart();
				cart.setMid(mid);
				cart.setRid(rid);
				cart.setMeid(meid);
				cart.setName(m.getName());
				cart.setCurrentPrice(m.getCurrentPrice());
				cart.setStock(m.getStock());
				cart.setNum(1);
				cart.setTotalPrice(m.getCurrentPrice());
				cartId = (Integer) session.save(cart);
				tx.commit();
				//j.closeSession();
			} catch (HibernateException e) {
				if (tx != null)
					tx.rollback();
				//j.closeSession();
				e.printStackTrace();
			}
		}
		
		return result;
	}

	@Override
	public List<Cart> getCart(int mid) throws Exception {
		// TODO Auto-generated method stub
		List<Cart> cart = new ArrayList<Cart>();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			List addr = session.createQuery("FROM Cart").list();
			for (Iterator iterator = addr.iterator(); iterator.hasNext();) {
				Cart c = (Cart) iterator.next();
				if (mid == c.getMid()) {
					cart.add(c);
				}
			}
			tx.commit();
			//j.closeSession();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			//j.closeSession();
			e.printStackTrace();
		}
		return cart;
	}

	@Override
	public boolean payAccount(int mid, double actualPrice) throws Exception {
		// TODO Auto-generated method stub
		// 这时还没有退款需求
		Member member = memberDao.findMemberById(mid);
		int level=memberDao.updateLevel(member);
		boolean result = false;
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Member m = (Member) session.get(Member.class, member.getMid());
			double a = m.getAccount();
			if (actualPrice <= a) {
				double b = a - actualPrice;
				m.setAccount(b);
				double c = m.getConsumption() + actualPrice;
				m.setConsumption(c);
				m.setLevel(level);
				session.update(m);
				System.out.println("更新付款余额成功");
				result = true;
			} else {
				result = false;
			}
			tx.commit();
			//j.closeSession();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			//j.closeSession();
			e.printStackTrace();
		}
		// 经理10%
		Transaction tx1 = null;
		try {
			tx1 = session.beginTransaction();
			Manager m = (Manager) session.get(Manager.class, 1);
			double a = m.getAccount() + actualPrice * 0.1;
			m.setAccount(a);
			session.update(m);
			tx1.commit();
			//j.closeSession();
		} catch (HibernateException e) {
			if (tx1 != null)
				tx1.rollback();
			//j.closeSession();
			e.printStackTrace();
		}
		// 然后是餐厅获得这笔钱90%
		List<Cart> c = getCart(mid);
		String rid = c.get(0).getRid();
		Restaurant restaurant = restaurantDao.findRestaurantByRid(rid);
		Transaction tx2 = null;
		try {
			tx2 = session.beginTransaction();
			Restaurant r = (Restaurant) session.get(Restaurant.class, restaurant.getId());
			double a = r.getAccount() + actualPrice * 0.9;
			r.setAccount(a);
			session.update(r);
			tx2.commit();
			//j.closeSession();
		} catch (HibernateException e) {
			if (tx2 != null)
				tx2.rollback();
			//j.closeSession();
			e.printStackTrace();
		}
		
		// 最后原价记录到进账信息里，可以乘以百分比给大家用
		Transaction tx3 = null;
		Integer financeId = null;
		
		Date date = new Date(); 
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String str = format.format(date);
		
		try {
			tx3 = session.beginTransaction();
			Finance finance=new Finance();
			finance.setRid(rid);
			finance.setMid(mid);
			finance.setDate(str);
			finance.setAccount(actualPrice);
			financeId = (Integer) session.save(finance);
			System.out.println("进账啦！！！！！");
			tx3.commit();
			//j.closeSession();
		} catch (HibernateException e) {
			if (tx3 != null)
				tx3.rollback();
			//j.closeSession();
			e.printStackTrace();
		}

		return result;
	}

	@Override
	public boolean addToCurrentOrder(int mid, String addrId, double price) throws Exception {
		// TODO Auto-generated method stub
		boolean result = false;
		Member member=memberDao.findMemberById(mid);
		// 根据mid得到cart和remarks
		List<Cart> c = getCart(mid);
		List<Cart> cart = new ArrayList<Cart>();
		for (int i = 0; i < c.size(); i++) {
			if (c.get(i).getNum() != 0) {
				cart.add(c.get(i));
			}
		} // 得到菜品啦！

		if (cart.size() != 0) {
			result = true;
			// 获取菜品信息
			String remarks = "";
			for (int i = 0; i < cart.size(); i++) {
				remarks = remarks + cart.get(i).getName() + " x " + cart.get(i).getNum() + "	";
			}
			// 根据cart得到rid
			String rid = cart.get(0).getRid();
			// 根据rid得到addr
			Restaurant restaurant = restaurantDao.findRestaurantByRid(rid);
			String restarantAddress = restaurant.getAddress();
			DeliveryAddress deliveryAddress = memberDao.getAddressByAddrid(addrId);
			String memberAddress = deliveryAddress.getProvince() + deliveryAddress.getCity()
					+ deliveryAddress.getDistrict() + deliveryAddress.getStreet();
			// long dis = getDistance(restarantAddress,memberAddress);
			//String distance = mapUtil.getDistance(restarantAddress, memberAddress);
			System.out.println("distance is---------" + restarantAddress);
			System.out.println("distance is---------" + memberAddress);
			//System.out.println("distance is---------" + distance);
			// 根据地址距离判断时间
			// 以后再加上具体时间吧
			/*
			 * Date date = new Date(); SimpleDateFormat format = new
			 * SimpleDateFormat("HH:mm:ss"); String now = format.format(date);
			 * System.out.println("current time is: "+now);
			 */
			
			Date date = new Date();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String str = format.format(date);
			
			double arrivalTime = 20 / 20;// 电动车20km/h 要改

			int seconds = (int) (arrivalTime * 60 * 60);

			// 加到数据库啦！！

			Transaction tx = null;
			Integer orderId = null;
			try {
				tx = session.beginTransaction();
				Orders order = new Orders();
				order.setRid(rid);
				order.setRestaurantName(restaurant.getRestaurantName());
				order.setRestaurantAddress(restarantAddress);
				order.setMid(mid);
				order.setMemberName(member.getMemberName());
				order.setMemberAddress(memberAddress);
				order.setRemarks(remarks);
				order.setPrice(price);
				order.setArrivalTime(str);
				//order.setArrivalTime(String.valueOf(arrivalTime));
				order.setSeconds(seconds);
				order.setState(1);
				orderId = (Integer) session.save(order);
				tx.commit();
				//j.closeSession();
			} catch (HibernateException e) {
				if (tx != null)
					tx.rollback();
				//j.closeSession();
				e.printStackTrace();
			}

			// 然后减库存
			//减库存
			for (int i = 0; i < cart.size(); i++) {
				Transaction tx1 = null;
				try {
					tx1 = session.beginTransaction();
					Menu m = (Menu) session.get(Menu.class, cart.get(i).getMeid());
					m.setStock(m.getStock()-cart.get(i).getNum());
					session.update(m);
					System.out.println("更新库存成功");
					tx1.commit();
				} catch (HibernateException e) {
					if (tx1 != null)
						tx1.rollback();
					e.printStackTrace();
				}
			}
			//清空购物车
			deleteMemberCart(mid);
			

		}

		return result;
	}

	@Override
	public Orders findOrderByOid(int oid) throws Exception {
		// TODO Auto-generated method stub
		Orders order = null;
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			List ms = session.createQuery("FROM Orders").list();
			for (Iterator iterator = ms.iterator(); iterator.hasNext();) {
				Orders o = (Orders) iterator.next();
				int id=o.getOid();
				if (oid==id) {
					order=o;
				}
			}
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} 
		return order;
	}

	@Override
	public boolean refund(int mid, int oid) throws Exception {
		// TODO Auto-generated method stub
		//退98%
		Orders order=findOrderByOid(oid);
		double actualPrice=order.getPrice();
		Member member = memberDao.findMemberById(mid);
		boolean result = false;
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Member m = (Member) session.get(Member.class, member.getMid());
			double a = m.getAccount();
			double b = a + actualPrice*0.98;
			m.setAccount(b);
			double c = m.getConsumption() - actualPrice*0.98;
			m.setConsumption(c);
			session.update(m);
			System.out.println("更新退款付款余额成功");
			result = true;

			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		}
		// 经理还9%
		Transaction tx1 = null;
		try {
			tx1 = session.beginTransaction();
			Manager m = (Manager) session.get(Manager.class, 1);
			double a = m.getAccount() - actualPrice * 0.09;
			m.setAccount(a);
			session.update(m);
			tx1.commit();
		} catch (HibernateException e) {
			if (tx1 != null)
				tx1.rollback();
			e.printStackTrace();
		}
		// 然后是餐厅还获得这笔钱89%
		//List<Cart> c = getCart(mid);
		String rid = order.getRid();
		Restaurant restaurant = restaurantDao.findRestaurantByRid(rid);
		Transaction tx2 = null;
		try {
			tx2 = session.beginTransaction();
			Restaurant r = (Restaurant) session.get(Restaurant.class, restaurant.getId());
			double a = r.getAccount() - actualPrice * 0.89;
			r.setAccount(a);
			session.update(r);
			tx2.commit();
		} catch (HibernateException e) {
			if (tx2 != null)
				tx2.rollback();
			e.printStackTrace();
		}
		
		// 最后原价记录到进账信息里，可以乘以百分比给大家用
		Transaction tx3 = null;
		Integer financeId = null;
		
		Date date = new Date(); 
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String str = format.format(date);
		
		try {
			tx3 = session.beginTransaction();
			Finance finance=new Finance();
			finance.setRid(rid);
			finance.setMid(mid);
			finance.setDate(str);
			double ap=-1*actualPrice;
			finance.setAccount(ap);
			financeId = (Integer) session.save(finance);
			tx3.commit();
		} catch (HibernateException e) {
			if (tx3 != null)
				tx3.rollback();
			e.printStackTrace();
		}
		//Orders order=findOrderByOid(oid);
		Transaction tx4 = null;
		try {
			tx4 = session.beginTransaction();
			Orders o = (Orders) session.get(Orders.class, order.getOid());
			o.setState(4);
			session.update(o);
			System.out.println("更新状态成功");
			tx4.commit();
		} catch (HibernateException e) {
			if (tx4 != null)
				tx4.rollback();
			e.printStackTrace();
		}
		//减库存？
		return result;
	}

	@Override
	public boolean laterPayAccount(int mid, int oid) throws Exception {
		// TODO Auto-generated method stub
		Member member=memberDao.findMemberById(mid);
		Orders order=findOrderByOid(oid);
		boolean result=payAccount(mid,order.getPrice());
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Orders o = (Orders) session.get(Orders.class, order.getOid());
			o.setState(1);
			session.update(o);
			System.out.println("更新状态成功");
			result = true;
			memberDao.updateLevel(member);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public boolean deleteCart(int mid, int meid) throws Exception {
		// TODO Auto-generated method stub
		Transaction tx1 = null;
		try {
			tx1 = session.beginTransaction();
			List ms = session.createQuery("FROM Cart").list();
			for (Iterator iterator = ms.iterator(); iterator.hasNext();) {
				Cart c = (Cart) iterator.next();
				int mmeid=c.getMeid();
				int mmid=c.getMid();
				if (mid==mmid&&meid==mmeid) {
					session.delete(c);
				}
			}
			System.out.println("删除购物车成功");
			tx1.commit();
			j.closeSession();
		} catch (HibernateException e) {
			if (tx1 != null)
				tx1.rollback();
			j.closeSession();
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public boolean confirmOrder(int oid) throws Exception {
		// TODO Auto-generated method stub
		//会员确认收货
		Orders order=findOrderByOid(oid);
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Orders o = (Orders) session.get(Orders.class, order.getOid());
			o.setState(3);
			session.update(o);
			System.out.println("更新状态成功");
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public boolean addToCurrentOrderWithoutPay(int mid, String addrId, double price) throws Exception {
		// TODO Auto-generated method stub
		boolean result = false;
		Member member=memberDao.findMemberById(mid);
		// 根据mid得到cart和remarks
		List<Cart> c = getCart(mid);
		List<Cart> cart = new ArrayList<Cart>();
		for (int i = 0; i < c.size(); i++) {
			if (c.get(i).getNum() != 0) {
				cart.add(c.get(i));
			}
		} // 得到菜品啦！

		if (cart.size() != 0) {
			result = true;
			// 获取菜品信息
			String remarks = "";
			for (int i = 0; i < cart.size(); i++) {
				remarks = remarks + cart.get(i).getName() + " x " + cart.get(i).getNum() + ";";
			}
			// 根据cart得到rid
			String rid = cart.get(0).getRid();
			// 根据rid得到addr
			Restaurant restaurant = restaurantDao.findRestaurantByRid(rid);
			String restarantAddress = restaurant.getAddress();
			DeliveryAddress deliveryAddress = memberDao.getAddressByAddrid(addrId);
			String memberAddress = deliveryAddress.getProvince() + deliveryAddress.getCity()
					+ deliveryAddress.getDistrict() + deliveryAddress.getStreet();
			
			
			Date date = new Date();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String str = format.format(date);
			
			double arrivalTime = 20 / 20;// 电动车20km/h 要改

			int seconds = (int) (arrivalTime * 60 * 60);

			// 加到数据库啦！！

			Transaction tx = null;
			Integer orderId = null;
			try {
				tx = session.beginTransaction();
				Orders order = new Orders();
				order.setRid(rid);
				order.setRestaurantName(restaurant.getRestaurantName());
				order.setRestaurantAddress(restarantAddress);
				order.setMid(mid);
				order.setMemberName(member.getMemberName());
				order.setMemberAddress(memberAddress);
				order.setRemarks(remarks);
				order.setPrice(price);
				order.setArrivalTime(str);
				//order.setArrivalTime(String.valueOf(arrivalTime));
				order.setSeconds(seconds);
				order.setState(0);
				orderId = (Integer) session.save(order);
				tx.commit();
				//j.closeSession();
			} catch (HibernateException e) {
				if (tx != null)
					tx.rollback();
				//j.closeSession();
				e.printStackTrace();
			}

			// 然后减库存
			//减库存
			for (int i = 0; i < cart.size(); i++) {
				Transaction tx1 = null;
				try {
					tx1 = session.beginTransaction();
					Menu m = (Menu) session.get(Menu.class, cart.get(i).getMeid());
					m.setStock(m.getStock()-cart.get(i).getNum());
					session.update(m);
					System.out.println("更新库存成功");
					tx1.commit();
				} catch (HibernateException e) {
					if (tx1 != null)
						tx1.rollback();
					e.printStackTrace();
				}
			}
			
			
			deleteMemberCart(mid);
		}
		Timer t=new Timer();
        //在120秒后执行run方法
        t.schedule(new TimeOrder(), 120000);
		return result;
	}

	@Override
	public void deleteMemberCart(int mid) throws Exception {
		// TODO Auto-generated method stub
		List<Cart> cart =getCart(mid);
		for(int i=0;i<cart.size();i++) {
			deleteCart(mid,cart.get(i).getMeid());
		}
	}

}
