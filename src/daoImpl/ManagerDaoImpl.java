package daoImpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import dao.ManagerDao;
import entity.ApproveRestaurant;
import entity.Finance;
import entity.Manager;
import entity.Member;
import entity.Restaurant;

public class ManagerDaoImpl implements ManagerDao {

	JDBConnect j = new JDBConnect();
	Session session = j.currentSession();

	@Override
	public Manager isValidLogin(String username, String password) throws Exception {
		// TODO Auto-generated method stub
		Manager manager = null;
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			List ms = session.createQuery("FROM Manager").list();
			for (Iterator iterator = ms.iterator(); iterator.hasNext();) {
				Manager m = (Manager) iterator.next();
				String managerName = m.getManagerName();
				String managerPassword = m.getManagerPassword();
				if (username.equals(managerName) && password.equals(managerPassword)) {
					manager = m;
				}
				// System.out.println("member: "+member.getMail());
			}
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		}

		return manager;
	}

	@Override
	public List<ApproveRestaurant> getAllRestaurantApprovalInfo() throws Exception {
		// TODO Auto-generated method stub

		List<ApproveRestaurant> ar = new ArrayList<ApproveRestaurant>();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			List addr = session.createQuery("FROM ApproveRestaurant").list();
			for (Iterator iterator = addr.iterator(); iterator.hasNext();) {
				ApproveRestaurant a = (ApproveRestaurant) iterator.next();
				ar.add(a);
			}
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		}
		return ar;
	}

	@Override
	public boolean forbidApproval(String rid) throws Exception {
		// TODO Auto-generated method stub
		Transaction tx = null;
		ApproveRestaurant approveRestaurant = new ApproveRestaurant();
		try {
			tx = session.beginTransaction();
			List ms = session.createQuery("FROM ApproveRestaurant").list();
			for (Iterator iterator = ms.iterator(); iterator.hasNext();) {
				ApproveRestaurant ar = (ApproveRestaurant) iterator.next();
				String r = ar.getRid();
				if (rid.equals(r)) {
					approveRestaurant = ar;
				}
			}
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		}
		Transaction tx1 = null;
		try {
			tx1 = session.beginTransaction();
			List ms = session.createQuery("FROM ApproveRestaurant").list();
			for (Iterator iterator = ms.iterator(); iterator.hasNext();) {
				ApproveRestaurant ar = (ApproveRestaurant) iterator.next();
				String r = ar.getRid();
				if (rid.equals(r)) {
					session.delete(ar);
				}
			}
			System.out.println("驳回成功");
			tx1.commit();
		} catch (HibernateException e) {
			if (tx1 != null)
				tx1.rollback();
			e.printStackTrace();
		}
		// 还要将原来信息放回去
		Transaction tx2 = null;
		try {
			tx2 = session.beginTransaction();
			Restaurant r = (Restaurant) session.get(Restaurant.class, approveRestaurant.getId());
			r.setState(1);
			System.out.println("返回成功");
			tx2.commit();
		} catch (HibernateException e) {
			if (tx2 != null)
				tx2.rollback();
			e.printStackTrace();
		}

		return true;
	}

	@Override
	public boolean permitApproval(int aid, int id, String rid, String restaurantName, String restaurantPassword,
			String address, String type, String keeper, String phone) throws Exception {
		// TODO Auto-generated method stub
		// modify
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Restaurant r = (Restaurant) session.get(Restaurant.class, id);

			r.setRestaurantName(restaurantName);
			r.setRestaurantPassword(restaurantPassword);
			r.setAddress(address);
			r.setType(type);
			r.setKeeper(keeper);
			r.setPhone(phone);
			r.setState(1);

			session.update(r);
			System.out.println("更新餐厅信息成功");
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		}
		// delete in ar
		Transaction tx1 = null;
		try {
			tx1 = session.beginTransaction();
			List ms = session.createQuery("FROM ApproveRestaurant").list();
			for (Iterator iterator = ms.iterator(); iterator.hasNext();) {
				ApproveRestaurant ar = (ApproveRestaurant) iterator.next();
				int Aid = ar.getAid();
				if (aid == Aid) {
					session.delete(ar);
				}
			}
			System.out.println("更新temp餐厅数据库成功!");
			tx1.commit();
		} catch (HibernateException e) {
			if (tx1 != null)
				tx1.rollback();
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public boolean allForbidApproval() throws Exception {
		// TODO Auto-generated method stub
		List<ApproveRestaurant> ar = getAllRestaurantApprovalInfo();
		boolean b = false;
		if (ar.size() == 0) {
			b = true;
		} else {
			for (int i = 0; i < ar.size(); i++) {
				b = forbidApproval(ar.get(i).getRid());
			}
		}

		return b;
	}

	@Override
	public boolean allPermitApproval() throws Exception {
		// TODO Auto-generated method stub
		List<ApproveRestaurant> ar = getAllRestaurantApprovalInfo();
		boolean b = false;
		if (ar.size() == 0) {
			b = true;
		} else {
			for (int i = 0; i < ar.size(); i++) {
				ApproveRestaurant a = ar.get(i);
				b = permitApproval(a.getAid(), a.getId(), a.getRid(), a.getRestaurantName(), a.getRestaurantPassword(),
						a.getAddress(), a.getType(), a.getKeeper(), a.getPhone());
			}
		}

		return b;
	}

	@Override
	public List<Integer> getRegistMemberNumDivByDef(int startdate, int enddate, int gap) throws Exception {
		// TODO Auto-generated method stub
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Calendar cal = Calendar.getInstance();

		cal.setTime(sdf.parse(String.valueOf(startdate)));
		long time1 = cal.getTimeInMillis();
		cal.setTime(sdf.parse(String.valueOf(enddate)));
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);
		int groupNum = 0;
		if (between_days % gap == 0) {
			groupNum = (int) (between_days / gap);
		} else {
			groupNum = (int) (between_days / gap + 1);
		}
		/*
		 * int groupNum=0; if((enddate-startdate)%gap==0) {
		 * groupNum=(enddate-startdate)/gap; }else { groupNum=(enddate-startdate)/gap+1;
		 * }
		 */

		// 每个gap中注册人数
		List<Integer> result = new ArrayList<Integer>();
		for (int i = 0; i < groupNum; i++) {
			result.add(0);
		}

		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			List ms = session.createQuery("FROM Member").list();
			for (Iterator iterator = ms.iterator(); iterator.hasNext();) {
				Member member = (Member) iterator.next();
				// 得到注册时间Int型
				String date = member.getRegisterTime();
				String[] date1 = date.split("-");
				String date2 = date1[0] + date1[1] + date1[2];
				int date3 = Integer.parseInt(date2);

				if (date3 >= startdate && date3 <= enddate) {
					SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");
					Calendar cal1 = Calendar.getInstance();
					cal1.setTime(sdf1.parse(String.valueOf(startdate)));
					long time3 = cal1.getTimeInMillis();
					cal1.setTime(sdf1.parse(String.valueOf(date3)));
					long time4 = cal1.getTimeInMillis();
					long between_days1 = (time4 - time3) / (1000 * 3600 * 24);

					int aim = (int) (between_days1 / gap);
					// System.out.println(aim);
					// int aim=(date3-startdate)/gap;
					int c = result.get(aim) + 1;
					result.set(aim, c);
				}

			}
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<Integer> getRegistRestaurantNumDivByDef(int startdate, int enddate, int gap) throws Exception {
		// TODO Auto-generated method stub
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Calendar cal = Calendar.getInstance();

		cal.setTime(sdf.parse(String.valueOf(startdate)));
		long time1 = cal.getTimeInMillis();
		cal.setTime(sdf.parse(String.valueOf(enddate)));
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);
		int groupNum = 0;
		if (between_days % gap == 0) {
			groupNum = (int) (between_days / gap);
		} else {
			groupNum = (int) (between_days / gap + 1);
		}
		// 每个gap中注册人数
		List<Integer> result = new ArrayList<Integer>();
		for (int i = 0; i < groupNum; i++) {
			result.add(0);
		}

		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			List ms = session.createQuery("FROM Restaurant").list();
			for (Iterator iterator = ms.iterator(); iterator.hasNext();) {
				Restaurant restaurant = (Restaurant) iterator.next();
				// 得到注册时间Int型
				String date = restaurant.getRegisterTime();
				String[] date1 = date.split("-");
				String date2 = date1[0] + date1[1] + date1[2];
				int date3 = Integer.parseInt(date2);

				if (date3 >= startdate && date3 <= enddate) {
					SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");
					Calendar cal1 = Calendar.getInstance();
					cal1.setTime(sdf1.parse(String.valueOf(startdate)));
					long time3 = cal1.getTimeInMillis();
					cal1.setTime(sdf1.parse(String.valueOf(date3)));
					long time4 = cal1.getTimeInMillis();
					long between_days1 = (time4 - time3) / (1000 * 3600 * 24);

					int aim = (int) (between_days1 / gap);
					System.out.println(aim);
					int c = result.get(aim) + 1;
					result.set(aim, c);
				}

			}
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public Map<String, Integer> getRestaurantCategoryAndNum() throws Exception {
		// TODO Auto-generated method stub
		Map<String, Integer> result = new HashMap<String, Integer>();

		List<String> categorylist = new ArrayList<String>();
		categorylist.add("简餐便当");
		categorylist.add("小吃炸串");
		categorylist.add("面食粥点");
		categorylist.add("汉堡披萨");

		categorylist.add("日韩料理");
		categorylist.add("轻食西餐");
		categorylist.add("其他");
		
		

		// 定下Map基本大小及格式
		//将每个类型置入map，数量均为0， 初始化
		int mapSize = categorylist.size();// 最后map里等量划分类别及数量
		for (int i = 0; i < mapSize; i++) {
			//String aa = String.valueOf(i);
			String aa=categorylist.get(i);
			result.put(aa, 0);
		}
		
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			List ms = session.createQuery("FROM Restaurant").list();
			for (Iterator iterator = ms.iterator(); iterator.hasNext();) {
				Restaurant restaurant = (Restaurant) iterator.next();
				String type=restaurant.getType();
				for(int i=0;i<categorylist.size();i++) {
					if(type.equals(categorylist.get(i))) {
						int num = result.get(type);
						if (result.containsKey(type)) {
							result.put(type, num + 1);
							System.out.println("----------"+type);
							System.out.println(result.get(type));
						}
					}
				}

			}
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public List<Integer> getRestaurantCategoryNum() throws Exception {
		// TODO Auto-generated method stub
		List<String> categorylist = new ArrayList<String>();
		categorylist.add("简餐便当");
		categorylist.add("小吃炸串");
		categorylist.add("面食粥点");
		categorylist.add("汉堡披萨");

		categorylist.add("日韩料理");
		categorylist.add("轻食西餐");
		categorylist.add("其他");
		List<Integer> result = new ArrayList<Integer>();
		result.add(0);
		result.add(0);
		result.add(0);
		result.add(0);
		result.add(0);
		result.add(0);
		result.add(0);
		
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			List ms = session.createQuery("FROM Restaurant").list();
			for (Iterator iterator = ms.iterator(); iterator.hasNext();) {
				Restaurant restaurant = (Restaurant) iterator.next();
				String type=restaurant.getType();
				for(int i=0;i<categorylist.size();i++) {
					if(type.equals(categorylist.get(i))) {
						int a=result.get(i)+1;
						result.set(i, a);
						System.out.println(result.get(i));
					}
				}

			}
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<Double> getFinance(int startdate,int enddate,int gap) throws Exception {
		// TODO Auto-generated method stub
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Calendar cal = Calendar.getInstance();

		cal.setTime(sdf.parse(String.valueOf(startdate)));
		long time1 = cal.getTimeInMillis();
		cal.setTime(sdf.parse(String.valueOf(enddate)));
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);
		int groupNum = 0;
		if (between_days % gap == 0) {
			groupNum = (int) (between_days / gap);
		} else {
			groupNum = (int) (between_days / gap + 1);
		}
		
		List<Double> result = new ArrayList<Double>();
		for (int i = 0; i < groupNum; i++) {
			result.add(0.0);
		}

		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			List ms = session.createQuery("FROM Finance").list();
			for (Iterator iterator = ms.iterator(); iterator.hasNext();) {
				Finance finance = (Finance) iterator.next();
				String date = finance.getDate();
				String[] date1 = date.split("-");
				String date2 = date1[0] + date1[1] + date1[2];
				int date3 = Integer.parseInt(date2);

				int state=finance.getState();
				//日期符合区间且审核过了
				if (date3 >= startdate && date3 <= enddate&&state==1) {
					SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");
					Calendar cal1 = Calendar.getInstance();
					cal1.setTime(sdf1.parse(String.valueOf(startdate)));
					long time3 = cal1.getTimeInMillis();
					cal1.setTime(sdf1.parse(String.valueOf(date3)));
					long time4 = cal1.getTimeInMillis();
					long between_days1 = (time4 - time3) / (1000 * 3600 * 24);

					int aim = (int) (between_days1 / gap);
					double c=result.get(aim)+finance.getAccount();
					//int c = result.get(aim) + 1;
					result.set(aim, c);
				}

			}
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<Finance> getAllFinanceInfo() throws Exception {
		// TODO Auto-generated method stub
		List<Finance> finance = new ArrayList<Finance>();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			List addr = session.createQuery("FROM Finance").list();
			for (Iterator iterator = addr.iterator(); iterator.hasNext();) {
				Finance f = (Finance) iterator.next();
				if(f.getState()==0) {
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
	public boolean permitBalance(int financeId) throws Exception {
		// TODO Auto-generated method stub
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Finance finance = (Finance) session.get(Finance.class, financeId);

			finance.setState(1);

			session.update(finance);
			System.out.println("更新财务信息成功");
			tx.commit();
			session.clear();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public boolean allPermitBalance() throws Exception {
		// TODO Auto-generated method stub
		List<Finance> finance = getAllFinanceInfo();
		boolean b = false;
		if (finance.size() == 0) {
			b = true;
		} else {
			for (int i = 0; i < finance.size(); i++) {
				Finance f = finance.get(i);
				b = permitBalance(f.getFinanceId());
			}
		}

		return b;
	}

}
