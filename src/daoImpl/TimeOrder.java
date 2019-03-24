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
//public class TimeOrder implements ServletContextListener {

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
				/*SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date date1 = null;
				date1 = format.parse(o.getArrivalTime());
				Date date2 = null;
				int ct1 = date.compareTo(date1);*/
				// int ct2 = date.compareTo(date2);//date小于date2返回-1，date大于date2返回1，相等返回0
				if (o.getState() == 0) {
				//if (ct1 == -1 && o.getState() == 0) {
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

	/*public void contextInitialized(ServletContextEvent event) {
		timer = new Timer();
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			public void run() {
				System.out.println("开始查询超时未支付订单...");
				Date date = new Date();
				Transaction tx = null;
				try {
					tx = session.beginTransaction();
					List addr = session.createQuery("FROM Orders").list();
					for (Iterator iterator = addr.iterator(); iterator.hasNext();) {
						Orders o = (Orders) iterator.next();
						//SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						//Date date1 = null;
						//date1 = format.parse(o.getArrivalTime());
						//Date date2 = null;
						//int ct1 = date.compareTo(date1);
						// int ct2 = date.compareTo(date2);//date小于date2返回-1，date大于date2返回1，相等返回0
						if (o.getState() == 0) {
						//if (ct1 == -1 && o.getState() == 0) {
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
		}, 1000, 120000);
	}*/

	// 销毁监听器，停止执行任务

	/*public void contextDestroyed(ServletContextEvent event) {
		// 确保正在执行的任务是此计时器所执行的最后一个任务。
		timer.cancel();
	}*/

}
/*Connection conn = null;
ResultSet rs = null;
PreparedStatement st = null;
try {
	conn = DbUtil.getConnection();
	conn.setAutoCommit(false);
	java.util.Date date = new Date();
	System.out.println("开始查询超时未支付订单...");
	rs = DbUtil.getResultSet(conn,
			"select id, last_payment from mat_info where pay_status=0 and last_payment <'"+ date + "'");
	while (rs.next()) {
		System.out.println("timeout order id is"+ rs.getString("id"));
		st = conn.prepareStatement("update mat_info set room_id ='' where pay_status=0 and id='"+ rs.getString("id") + "'");
		st.execute();
	}
	conn.commit();
} catch (Exception e) {
	System.out.println(e);
	try {
		conn.rollback();
	} catch (SQLException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	e.printStackTrace();
}*/