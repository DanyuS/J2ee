package daoImpl;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import dao.MenuDao;
import entity.Menu;

public class MenuDaoImpl implements MenuDao {
	
	JDBConnect j = new JDBConnect();
	Session session = j.currentSession();

	@Override
	public boolean addMenu(String rid, String name, String remarks, double price, double currentPrice, int stock, String type,
			String startTime, String endTime) throws Exception {
		// TODO Auto-generated method stub
		Transaction tx = null;
		Integer menuId = null;
		try {
			tx = session.beginTransaction();
			Menu menu = new Menu();
			menu.setRid(rid);
			menu.setName(name);
			menu.setRemarks(remarks);
			menu.setPrice(price);
			menu.setCurrentPrice(currentPrice);
			menu.setStock(stock);
			menu.setType(type);
			menu.setStartTime(startTime);
			menu.setEndTime(endTime);
			menuId = (Integer) session.save(menu);
			// add
			tx.commit();
			//j.closeSession();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			//j.closeSession();
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public List<Menu> getAllReleasedMenu(String rid) throws Exception {
		// TODO Auto-generated method stub
		Date date = new Date(); 
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String now = format.format(date);
        System.out.println("current time is: "+now);
		
		List<Menu> menu=new ArrayList<Menu>();
		//Transaction tx = null;
		try{
	    	//tx = session.beginTransaction();
	        List addr = session.createQuery("FROM Menu").list();
				for (Iterator iterator = addr.iterator(); iterator.hasNext();) {
					Menu me = (Menu) iterator.next();
					SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
					Date date1 = null;
					date1 = format.parse(me.getStartTime());
					Date date2 = null;
					date2 = format.parse(me.getEndTime());
					int ct1 = date.compareTo(date1);
					int ct2 = date.compareTo(date2);//date小于date2返回-1，date大于date2返回1，相等返回0
			        if(ct1!=-1&&ct2!=1&&rid.equals(me.getRid())) {
			        	menu.add(me);
			        }
				}
				//tx.commit();
				session.beginTransaction().commit();
			}catch (HibernateException e) {
				//if (tx!=null)
					//tx.rollback();
				e.printStackTrace();
			}
		return menu;
	}

	@Override
	public List<Menu> getAllUnreleasedMenu(String rid) throws Exception {
		// TODO Auto-generated method stub
		Date date = new Date(); 
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String now = format.format(date);
        System.out.println("current time is: "+now);
		
		List<Menu> menu=new ArrayList<Menu>();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			List addr = session.createQuery("FROM Menu").list();
			for (Iterator iterator = addr.iterator(); iterator.hasNext();) {
				Menu me = (Menu) iterator.next();
				SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
				Date date1 = null;
				date1 = format.parse(me.getStartTime());
				Date date2 = null;
				date2 = format.parse(me.getEndTime());
				int ct1 = date.compareTo(date1);
				// int ct2 = date.compareTo(date2);//date小于date2返回-1，date大于date2返回1，相等返回0
				if (ct1 == -1 && rid.equals(me.getRid())) {
					menu.add(me);
					System.out.println("Unreleased menu id: " + me.getMeid());
				}
			}
			tx.commit();
			// j.closeSession();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			// j.closeSession();
			e.printStackTrace();
		}
		return menu;
	}

	@Override
	public boolean deleteMenu(int meid) throws Exception {
		// TODO Auto-generated method stub
		Transaction tx1 = null;
		try {
			tx1 = session.beginTransaction();
			List ms = session.createQuery("FROM Menu").list();
			for (Iterator iterator = ms.iterator(); iterator.hasNext();) {
				Menu m = (Menu) iterator.next();
				int mmeid=m.getMeid();
				if (meid==mmeid) {
					session.delete(m);
				}
			}
			System.out.println("删除菜单成功");
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
	public Menu findMenuByMeid(int meid) throws Exception {
		// TODO Auto-generated method stub
		Menu menu = null;
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			List ms = session.createQuery("FROM Menu").list();
			for (Iterator iterator = ms.iterator(); iterator.hasNext();) {
				Menu m = (Menu) iterator.next();
				int id=m.getMeid();
				if (meid==id) {
					menu = m;
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

		return menu;
	}

}
