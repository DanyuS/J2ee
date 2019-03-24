package daoImpl;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import entity.Orders;

public class TimeOrder extends TimerTask {

	private Timer timer = null;

	JDBConnect j = new JDBConnect();
	Session session = j.currentSession();
	// 初始化监听器，创建实例，执行任务
	
	public void run() {
		System.out.println("开始查询超时未支付订单...");
		Date date = new Date();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			List addr = session.createQuery("FROM Orders").list();
			for (Iterator iterator = addr.iterator(); iterator.hasNext();) {
				Orders o = (Orders) iterator.next();
				if (o.getState() == 0) {
					System.out.println("超时啦！！！");
					o.setState(4);
					session.update(o);
					System.out.println("取消啦！！！");
				}
			}
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		}

	}

}
