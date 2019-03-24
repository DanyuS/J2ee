package daoImpl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import dao.CouponDao;
import dao.MemberDao;
import dao.RestaurantDao;
import entity.Coupon;
import entity.CouponUsage;
import entity.Member;
import entity.Restaurant;

public class CouponDaoImpl implements CouponDao {
	JDBConnect j = new JDBConnect();
	Session session = j.currentSession();
	
	MemberDao memberDao=new MemberDaoImpl();
	RestaurantDao restaurantDao=new RestaurantDaoImpl();

	@Override
	public List<Coupon> getPersonalCoupon(int mid) throws Exception {
		// TODO Auto-generated method stub
		List<Coupon> coupon = new ArrayList<Coupon>();
		Transaction tx = null;
		ArrayList<Integer> cs = new ArrayList<Integer>();
		try {
			tx = session.beginTransaction();
			List cu = session.createQuery("FROM CouponUsage").list();
			for (Iterator iterator = cu.iterator(); iterator.hasNext();) {
				CouponUsage c = (CouponUsage) iterator.next();

				if (mid == c.getMid()&&c.getRemain()==1) {//have and didnt used
					cs.add(c.getCid());
					System.out.println("Coupon id is: "+c.getCid());
				}
			}
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		}
		
		for(int i=0;i<cs.size();i++) {
			Transaction tx1 = null;
			try {
				tx1 = session.beginTransaction();
				List addr = session.createQuery("FROM Coupon").list();
				for (Iterator iterator = addr.iterator(); iterator.hasNext();) {
					Coupon co = (Coupon) iterator.next();
					if(cs.get(i)==co.getCid()) {
						System.out.println("2Coupon id is: "+co.getCid());
						coupon.add(co);
					}
				}
				tx1.commit();
			} catch (HibernateException e) {
				if (tx1 != null)
					tx1.rollback();
				e.printStackTrace();
			}
		}
		
		

		return coupon;
	}

	@Override
	public boolean deleteCoupon(int mid, int cid) throws Exception {
		// TODO Auto-generated method stub
		Transaction tx1 = null;
		int ind=0;
		//Coupon coupon=new Coupon();
		try {
			tx1 = session.beginTransaction();
			List ms = session.createQuery("FROM CouponUsage").list();
			for (Iterator iterator = ms.iterator(); iterator.hasNext();) {
				CouponUsage cu = (CouponUsage) iterator.next();
				int cid1=cu.getCid();
				int mid1=cu.getMid();
				if (mid==mid1&&cid==cid1) {
					session.delete(cu);
				}
			}
			System.out.println("删除优惠券成功");
			tx1.commit();
		} catch (HibernateException e) {
			if (tx1 != null)
				tx1.rollback();
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public List<Coupon> getAllUnaccetpedCoupon(int mid) throws Exception {
		// TODO Auto-generated method stub
		
		Member m=memberDao.findMemberById(mid);
		
		List<Coupon> coupon = new ArrayList<Coupon>();
		Transaction tx = null;
		ArrayList<Integer> cs = new ArrayList<Integer>();
		//得到所有拥有的优惠券的cid
		try {
			tx = session.beginTransaction();
			List cu = session.createQuery("FROM CouponUsage").list();
			for (Iterator iterator = cu.iterator(); iterator.hasNext();) {
				CouponUsage c = (CouponUsage) iterator.next();

				if (mid == c.getMid()) {
					cs.add(c.getCid());
					System.out.println("Coupon id is: "+c.getCid());
				}
			}
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		}
		//all coupon
		Transaction tx2 = null;
		try {
			tx2 = session.beginTransaction();
			List addr = session.createQuery("FROM Coupon").list();
			for (Iterator iterator = addr.iterator(); iterator.hasNext();) {
				Coupon co = (Coupon) iterator.next();
				if(m.getLevel()>=co.getLevel()) {
					coupon.add(co);
				}
			}
			tx2.commit();
		} catch (HibernateException e) {
			if (tx2 != null)
				tx2.rollback();
			e.printStackTrace();
		}
		
		//all unaccepted coupon
		for(int i=0;i<cs.size();i++) {
			Transaction tx1 = null;
			try {
				tx1 = session.beginTransaction();
				List addr = session.createQuery("FROM Coupon").list();
				for (Iterator iterator = addr.iterator(); iterator.hasNext();) {
					Coupon co = (Coupon) iterator.next();
					if(cs.get(i)==co.getCid()) {
						System.out.println("2Coupon id is: "+co.getCid());
						coupon.remove(co);
					}
				}
				tx1.commit();
			} catch (HibernateException e) {
				if (tx1 != null)
					tx1.rollback();
				e.printStackTrace();
			}
		}
		return coupon;
	}

	@Override
	public boolean addCoupon(int mid, int cid) throws Exception {
		// TODO Auto-generated method stub
		Transaction tx = null;
		Integer ID = null;
		
		try {
			tx = session.beginTransaction();
			CouponUsage cu=new CouponUsage();
			cu.setCid(cid);
			cu.setMid(mid);
			cu.setRemain(1);
			ID=(Integer)session.save(cu); 
			//add
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public List<Coupon> getRestaurantCoupon(int mid, String rid) throws Exception {
		// TODO Auto-generated method stub
		//先根据rid找Restaurant的id
		Restaurant restaurant=restaurantDao.findRestaurantByRid(rid);
		int id=restaurant.getId();
		//再根据Restaurant的id找CouponID
		//且mid和remain为1(方法中已经判断过了)
		List<Coupon> coupon=getPersonalCoupon(mid);
		List<Coupon> result=new ArrayList<Coupon>();
		for(int i=0;i<coupon.size();i++) {
			String cid=coupon.get(i).getCouponId();
			String[] c=cid.split("-");
			String d=c[0];
			if(d.equals(String.valueOf(id))) {
				result.add(coupon.get(i));
				System.out.println(cid);
			}
		}
		
		return result;
	}

	@Override
	public List<Coupon> getRestaurantCouponById(int id) throws Exception {
		// TODO Auto-generated method stub
		List<Coupon> result=new ArrayList<Coupon>();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			List addr = session.createQuery("FROM Coupon").list();
			for (Iterator iterator = addr.iterator(); iterator.hasNext();) {
				Coupon co = (Coupon) iterator.next();
				String couponId=co.getCouponId();
				String[] c=couponId.split("-");
				String d=c[0];
				if(d.equals(String.valueOf(id))) {
					result.add(co);
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
	public boolean restaurantAddCoupon(int id, String couponName, double minPayment, double discount, int level,
			String startTime, String endTime) throws Exception {
		// TODO Auto-generated method stub
		List<Coupon> result=getRestaurantCouponById(id);
		int a=0;
		if(result.size()!=0) {
			String b=result.get(result.size()-1).getCouponId();//得到x-x
			String[] c=b.split("-");
			String d=c[1];
			a=Integer.parseInt(d)+1;
			System.out.println("-------------"+a);
			
		}else {
			a=1;
		}
		Restaurant restaurant=restaurantDao.findRestaurantById(id);
		String couponId=String.valueOf(id)+"-"+String.valueOf(a);
		System.out.println("-------------"+couponId);
		Transaction tx = null;
		Integer ID = null;
		try {
			tx = session.beginTransaction();
			Coupon coupon=new Coupon();
			coupon.setCouponId(couponId);
			coupon.setRestaurantRange(restaurant.getRestaurantName());
			coupon.setCouponName(couponName);
			coupon.setMinPayment(minPayment);
			coupon.setDiscount(discount);
			coupon.setStartTime(startTime);
			coupon.setEndTime(endTime);
			coupon.setLevel(level);
			
			ID=(Integer)session.save(coupon); 
			//add
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public boolean deleteCouponByCid(int cid) throws Exception {
		// TODO Auto-generated method stub
		Transaction tx1 = null;
		int ind=0;
		//Coupon coupon=new Coupon();
		try {
			tx1 = session.beginTransaction();
			List ms = session.createQuery("FROM Coupon").list();
			for (Iterator iterator = ms.iterator(); iterator.hasNext();) {
				Coupon c = (Coupon) iterator.next();
				int cid1=c.getCid();
				if (cid==cid1) {
					session.delete(c);
				}
			}
			System.out.println("餐厅删除优惠券成功");
			tx1.commit();
		} catch (HibernateException e) {
			if (tx1 != null)
				tx1.rollback();
			e.printStackTrace();
		}
		return true;
	}


}
