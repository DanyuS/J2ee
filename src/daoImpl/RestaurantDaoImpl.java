package daoImpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import dao.MenuDao;
import dao.RestaurantDao;
import entity.ApproveRestaurant;
import entity.Finance;
import entity.Menu;
import entity.Orders;
import entity.Restaurant;

public class RestaurantDaoImpl implements RestaurantDao {

	JDBConnect j = new JDBConnect();
	Session session = j.currentSession();

	MenuDao menuDao = new MenuDaoImpl();
	// OrderDao orderDao=new OrderDaoImpl();

	@Override
	public Restaurant isValidLogin(String username, String password) throws Exception {
		// TODO Auto-generated method stub
		Restaurant restaurant = null;
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			List ms = session.createQuery("FROM Restaurant").list();
			for (Iterator iterator = ms.iterator(); iterator.hasNext();) {
				Restaurant r = (Restaurant) iterator.next();
				String rid = r.getRid();
				String restaurantPassword = r.getRestaurantPassword();
				if (username.equals(rid) && password.equals(restaurantPassword) && r.getState() == 1) {
					restaurant = r;
				}
				// System.out.println("member: "+member.getMail());
			}
			tx.commit();

		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		}
		return restaurant;
	}

	@Override
	public String registerByRestaurant(String username, String password, String code) throws Exception {
		// TODO Auto-generated method stub
		String registerInfo = "true";
		String errorinfo = "";
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			List ms = session.createQuery("FROM Restaurant").list();
			for (Iterator iterator = ms.iterator(); iterator.hasNext();) {
				Restaurant r = (Restaurant) iterator.next();
				String restaurantName = r.getRestaurantName();
				System.out.print("Register restaurantName: " + r.getRestaurantName());
				if (username.equals(restaurantName)) {
					registerInfo = "false";
					errorinfo = "餐厅名已被注册";
					break;
				}

			}
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			// session.close();
		}

		if (registerInfo.equals("false")) {
			System.out.println(errorinfo);
			return errorinfo;
		} else {// 都没有问题
			int state = 0;

			Transaction tx1 = null;
			Integer restaurantID = null;
			try {
				tx1 = session.beginTransaction();
				System.out.print("SUccess Register restaurantName: " + username);
				Restaurant restaurant = new Restaurant();
				restaurant.setRid(code);
				restaurant.setRestaurantName(username);
				restaurant.setRestaurantPassword(password);
				/*
				 * Calendar cal=Calendar.getInstance(); int y=cal.get(Calendar.YEAR); int
				 * m=cal.get(Calendar.MONTH)+1; int d=cal.get(Calendar.DATE);
				 * 
				 * String date=String.valueOf(y)+"-"+String.valueOf(m)+"-"+String.valueOf(d);
				 */
				Date date = new Date();
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				String str = format.format(date);
				restaurant.setRegisterTime(str);
				restaurant.setIdentity("餐厅");
				restaurant.setState(1);
				restaurantID = (Integer) session.save(restaurant);
				tx1.commit();
			} catch (HibernateException e) {
				if (tx1 != null)
					tx1.rollback();
				e.printStackTrace();
			} finally {
				// session.close();
			}

			System.out.println("注册成功");
		}
		/*
		 * if (isSpecialChar(username)) { registerInfo = "invalid";// 含有特殊字符 errorinfo =
		 * "餐厅名中含有非法字符!"; System.out.println("餐厅名中含有非法字符！"); return registerInfo; } else
		 * {
		 * 
		 * }
		 */

		return registerInfo;
	}

	public static boolean isSpecialChar(String str) {
		String regEx = "[ _`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|\n|\r|\t";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.find();
	}

	@Override
	public Restaurant findRestaurantByRid(String rid) throws Exception {
		// TODO Auto-generated method stub
		Restaurant restaurant = null;
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			List ms = session.createQuery("FROM Restaurant").list();
			for (Iterator iterator = ms.iterator(); iterator.hasNext();) {
				Restaurant r = (Restaurant) iterator.next();
				String rrid = r.getRid();
				if (rid.equals(rrid)) {
					restaurant = r;
				}
			}
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		}
		return restaurant;
	}

	@Override
	public Restaurant findRestaurantById(int id) throws Exception {
		// TODO Auto-generated method stub
		Restaurant restaurant = null;
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			List ms = session.createQuery("FROM Restaurant").list();
			for (Iterator iterator = ms.iterator(); iterator.hasNext();) {
				Restaurant r = (Restaurant) iterator.next();
				int iid = r.getId();
				if (id == iid) {
					restaurant = r;
				}
			}
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		}
		return restaurant;
	}

	@Override
	public boolean modifyRestaurantInfo(int id, String rid, String restaurantName, String restaurantPassword,
			String address, String type, String keeper, String phone) throws Exception {
		// TODO Auto-generated method stub
		// 其实还没有修改，还需要审批
		Restaurant restaurant = findRestaurantById(id);
		Transaction tx = null;
		Integer aid = null;
		try {
			tx = session.beginTransaction();
			ApproveRestaurant ar = new ApproveRestaurant();
			ar.setId(restaurant.getId());
			ar.setRid(rid);
			ar.setRestaurantName(restaurantName);
			ar.setRestaurantPassword(restaurantPassword);
			ar.setAddress(address);
			ar.setType(type);
			ar.setKeeper(keeper);
			ar.setPhone(phone);
			ar.setRegisterTime(restaurant.getRegisterTime());
			ar.setIdentity(restaurant.getIdentity());
			ar.setAccount(restaurant.getAccount());
			ar.setState(0);
			aid = (Integer) session.save(ar);
			System.out.println("添加到审批库成功");
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		}

		// 还要改Restaurant的state
		Transaction tx1 = null;
		try {
			tx1 = session.beginTransaction();
			Restaurant r = (Restaurant) session.get(Restaurant.class, restaurant.getId());
			r.setState(0);
			session.update(r);
			System.out.println("更新restaurant状态成功");
			tx1.commit();
		} catch (HibernateException e) {
			if (tx1 != null)
				tx1.rollback();
			e.printStackTrace();
		}

		return true;
	}

	@Override
	public List<Restaurant> getAllRestaurant() throws Exception {
		// TODO Auto-generated method stub
		// 所有状态不为0的餐厅
		List<Restaurant> restaurant = new ArrayList<Restaurant>();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			List addr = session.createQuery("FROM Restaurant").list();
			for (Iterator iterator = addr.iterator(); iterator.hasNext();) {
				Restaurant r = (Restaurant) iterator.next();
				if (r.getState() == 1) {
					restaurant.add(r);
				}
			}
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		}
		return restaurant;
	}

	@Override
	public List<Finance> getAllRestaurantFinanceInfo(String rid) throws Exception {
		// TODO Auto-generated method stub
		List<Finance> finance = new ArrayList<Finance>();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			List addr = session.createQuery("FROM Finance").list();
			for (Iterator iterator = addr.iterator(); iterator.hasNext();) {
				Finance f = (Finance) iterator.next();
				if (f.getState() == 1 && f.getRid().equals(rid)) {
					finance.add(f);
				}
			}
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		}
		return finance;
	}

	@Override
	public List<Orders> getSubscribeOrder(String rid) throws Exception {
		// TODO Auto-generated method stub
		List<Orders> order = new ArrayList<Orders>();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			List addr = session.createQuery("FROM Orders").list();
			for (Iterator iterator = addr.iterator(); iterator.hasNext();) {
				Orders o = (Orders) iterator.next();
				if (o.getState() == 3 && o.getRid().equals(rid)) {
					order.add(o);
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
	public List<Orders> getUnsubscribeOrder(String rid) throws Exception {
		// TODO Auto-generated method stub
		List<Orders> order = new ArrayList<Orders>();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			List addr = session.createQuery("FROM Orders").list();
			for (Iterator iterator = addr.iterator(); iterator.hasNext();) {
				Orders o = (Orders) iterator.next();
				if (o.getState() == 4 && o.getRid().equals(rid)) {
					order.add(o);
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
	public List<Orders> getSubscribeOrderTime(String rid) throws Exception {
		// TODO Auto-generated method stub
		// 时间降序
		List<Orders> order = new ArrayList<Orders>();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			List addr = session.createQuery("FROM Orders order by arrivalTime desc").list();
			for (Iterator iterator = addr.iterator(); iterator.hasNext();) {
				Orders o = (Orders) iterator.next();
				if (o.getState() == 3 && o.getRid().equals(rid)) {
					order.add(o);
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
	public List<Orders> getSubscribeOrderAccount(String rid) throws Exception {
		// TODO Auto-generated method stub
		// 金额降序
		List<Orders> order = new ArrayList<Orders>();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			List addr = session.createQuery("FROM Orders order by price desc").list();
			for (Iterator iterator = addr.iterator(); iterator.hasNext();) {
				Orders o = (Orders) iterator.next();
				if (o.getState() == 3 && o.getRid().equals(rid)) {
					order.add(o);
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
	public List<Orders> getSubscribeOrderMember(String rid) throws Exception {
		// TODO Auto-generated method stub
		// 会员降序
		List<Orders> order = new ArrayList<Orders>();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			List addr = session.createQuery("FROM Orders order by level desc").list();
			for (Iterator iterator = addr.iterator(); iterator.hasNext();) {
				Orders o = (Orders) iterator.next();
				if (o.getState() == 3 && o.getRid().equals(rid)) {
					order.add(o);
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
	public List<Orders> getUnsubscribeOrderTime(String rid) throws Exception {
		// TODO Auto-generated method stub
		List<Orders> order = new ArrayList<Orders>();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			List addr = session.createQuery("FROM Orders order by arrivalTime desc").list();
			for (Iterator iterator = addr.iterator(); iterator.hasNext();) {
				Orders o = (Orders) iterator.next();
				if (o.getState() == 4 && o.getRid().equals(rid)) {
					order.add(o);
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
	public List<Orders> getUnsubscribeOrderAccount(String rid) throws Exception {
		// TODO Auto-generated method stub
		List<Orders> order = new ArrayList<Orders>();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			List addr = session.createQuery("FROM Orders order by price desc").list();
			for (Iterator iterator = addr.iterator(); iterator.hasNext();) {
				Orders o = (Orders) iterator.next();
				if (o.getState() == 4 && o.getRid().equals(rid)) {
					order.add(o);
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
	public List<Orders> getUnsubscribeOrderMember(String rid) throws Exception {
		// TODO Auto-generated method stub
		List<Orders> order = new ArrayList<Orders>();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			List addr = session.createQuery("FROM Orders order by level desc").list();
			for (Iterator iterator = addr.iterator(); iterator.hasNext();) {
				Orders o = (Orders) iterator.next();
				if (o.getState() == 4 && o.getRid().equals(rid)) {
					order.add(o);
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
	public List<Restaurant> getRestaurantBySearch(String sch) throws Exception {
		// TODO Auto-generated method stub
		List<Restaurant> restaurant = getAllRestaurant();
		List<Restaurant> result = getAllRestaurant();
		for (int i = 0; i < restaurant.size(); i++) {
			if (restaurant.get(i).getRestaurantName().contains(sch) || restaurant.get(i).getType().contains(sch)) {// 餐厅名和餐厅类型含有关键字
				result.add(restaurant.get(i));
				System.out.println("-------------------" + restaurant.get(i).getRestaurantName());
			} else {// 否则我们看看菜？
				List<Menu> menu = menuDao.getAllReleasedMenu(restaurant.get(i).getRid());
				for (int j = 0; j < menu.size(); j++) {
					if (menu.get(j).getName().contains(sch)) {
						result.add(restaurant.get(i));
						System.out.println(restaurant.get(i).getRestaurantName());
					}
				}
			}
		}

		return result;
	}

	@Override
	public List<Orders> getOrderToSend(String rid) throws Exception {
		// TODO Auto-generated method stub
		List<Orders> order = new ArrayList<Orders>();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			List addr = session.createQuery("FROM Orders").list();
			for (Iterator iterator = addr.iterator(); iterator.hasNext();) {
				Orders o = (Orders) iterator.next();
				if (o.getState() == 1 && o.getRid().equals(rid)) {
					order.add(o);
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
	public boolean sendOrder(int oid) throws Exception {
		// TODO Auto-generated method stub
		// Orders order=orderDao.findOrderByOid(oid);
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Orders o = (Orders) session.get(Orders.class, oid);

			o.setState(2);

			session.update(o);
			System.out.println("派送成功");
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		}
		

		return true;
	}

}
