package daoImpl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import dao.MemberDao;
import entity.DeliveryAddress;
import entity.Finance;
import entity.Member;
import entity.Orders;

public class MemberDaoImpl implements MemberDao {

	JDBConnect j = new JDBConnect();
	Session session = j.currentSession();

	MailUtil mailUtil = new MailUtil();

	@Override
	public Member isValidLogin(String username, String password) throws Exception {
		Member member = null;
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			List ms = session.createQuery("FROM Member").list();
			for (Iterator iterator = ms.iterator(); iterator.hasNext();) {
				Member m = (Member) iterator.next();
				String memberName = m.getMemberName();
				String memberPassword = m.getMemberPassword();
				// System.out.print("Login MemberName: " + m.getMemberName());
				// System.out.print("MemberPassword: " + m.getMemberPassword());
				if (username.equals(memberName) && password.equals(memberPassword) && m.getState() == 1) {// 用户密码正确且没有注销
					member = m;
				}
				// System.out.println("member: "+member.getMail());
			}
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			// session.close();
		}

		return member;
	}

	@Override
	public Member findMemberById(int mid) throws Exception {
		// TODO Auto-generated method stub
		Member member = null;
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			List ms = session.createQuery("FROM Member").list();
			for (Iterator iterator = ms.iterator(); iterator.hasNext();) {
				Member m = (Member) iterator.next();
				int id = m.getMid();
				if (mid == id) {
					member = m;
				}
			}
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		}

		return member;
	}

	@Override
	public Member findMemberByCode(String code) throws Exception {
		// TODO Auto-generated method stub
		Member member = null;
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			List ms = session.createQuery("FROM Member").list();
			for (Iterator iterator = ms.iterator(); iterator.hasNext();) {
				Member m = (Member) iterator.next();
				String mcode = m.getCode();
				System.out.print("code: " + m.getCode());
				if (code.equals(mcode)) {
					member = m;
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

		return member;
	}

	@Override
	public void updateMemberInfo(Member member) throws Exception {
		// TODO Auto-generated method stub
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Member m = (Member) session.get(Member.class, member.getMid());
			m.setState(1);
			session.update(m);
			System.out.println("更新成功");
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			// session.close();
		}
	}

	@Override
	public String registerByMember(String username, String password, String email, String code) throws Exception {
		// TODO Auto-generated method stub
		String registerInfo = "true";
		String errorinfo = "";
		if (isSpecialChar(username)) {
			registerInfo = "invalid";// 含有特殊字符
			errorinfo = "用户名中含有非法字符!";
			System.out.println("用户名中含有非法字符！");
			return errorinfo;
		} else {
			Transaction tx = null;
			try {
				tx = session.beginTransaction();
				List ms = session.createQuery("FROM Member").list();
				for (Iterator iterator = ms.iterator(); iterator.hasNext();) {
					Member m = (Member) iterator.next();
					String memberName = m.getMemberName();
					System.out.print("Register MemberName: " + m.getMemberName());
					if (username.equals(memberName)) {
						registerInfo = "false";
						errorinfo = "用户名已被注册";
						break;
					}
					String mEmail = m.getMail();
					System.out.print("Register Email: " + m.getMail());
					if (email.equals(mEmail)) {
						registerInfo = "false";
						errorinfo = "邮箱已被注册";
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
				mailUtil.sendMail(email, code);
				int state = 0;

				Transaction tx1 = null;
				Integer memberID = null;
				try {
					tx1 = session.beginTransaction();
					System.out.print("Success Register MemberName: " + username);
					Member member = new Member();
					member.setMemberName(username);
					member.setMemberPassword(password);
					;
					member.setMail(email);
					member.setLevel(1);
					member.setIdentity("会员");
					member.setState(0);
					member.setCode(code);
					member.setAccount(0.0);
					member.setConsumption(0.0);
					memberID = (Integer) session.save(member);
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
		}

		return registerInfo;
	}

	public static boolean isSpecialChar(String str) {
		String regEx = "[ _`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|\n|\r|\t";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.find();
	}

	@Override
	public String getMemberPasswordById(int mid) throws Exception {
		// TODO Auto-generated method stub
		Member member = null;
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			List ms = session.createQuery("FROM Member").list();
			for (Iterator iterator = ms.iterator(); iterator.hasNext();) {
				Member m = (Member) iterator.next();
				int id = m.getMid();
				if (mid == id) {
					member = m;
				}
			}
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		}
		System.out.print("MemberGetPasswordServlet password: " + member.getMemberPassword());
		return member.getMemberPassword();
	}

	@Override
	public String getMemberEmailById(int mid) throws Exception {
		// TODO Auto-generated method stub
		Member member = null;
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			List ms = session.createQuery("FROM Member").list();
			for (Iterator iterator = ms.iterator(); iterator.hasNext();) {
				Member m = (Member) iterator.next();
				int id = m.getMid();
				if (mid == id) {
					member = m;
				}
			}
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		}
		System.out.print("MemberGetPasswordServlet mail: " + member.getMail());
		return member.getMail();
	}

	@Override
	public String getMemberAccountById(int mid) throws Exception {
		// TODO Auto-generated method stub
		Member member = null;
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			List ms = session.createQuery("FROM Member").list();
			for (Iterator iterator = ms.iterator(); iterator.hasNext();) {
				Member m = (Member) iterator.next();
				int id = m.getMid();
				if (mid == id) {
					member = m;
				}
			}
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		}
		// System.out.print("MemberGetPasswordServlet mail: " + member.getMail());
		String result = String.valueOf(member.getAccount());
		return result;
	}

	@Override
	public boolean changeMemberPassword(int mid, String password) throws Exception {
		// TODO Auto-generated method stub
		Member member = findMemberById(mid);
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Member m = (Member) session.get(Member.class, member.getMid());
			m.setMemberPassword(password);
			session.update(m);
			System.out.println("更新密码成功");
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		}

		return true;
	}

	@Override
	public List<DeliveryAddress> getAddress(int mid) throws Exception {
		// TODO Auto-generated method stub
		List<DeliveryAddress> da = new ArrayList<DeliveryAddress>();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			List addr = session.createQuery("FROM DeliveryAddress").list();
			for (Iterator iterator = addr.iterator(); iterator.hasNext();) {
				DeliveryAddress d = (DeliveryAddress) iterator.next();
				String id = d.getAddrId();
				if (id.split("-")[0].equals(String.valueOf(mid))) {// judge id
					da.add(d);
					System.out.println(mid + ":  Address id:  " + id);
				}
			}
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		}

		return da;
	}

	@Override
	public boolean deleteMemberAddress(int mid, String addrid) throws Exception {
		// TODO Auto-generated method stub
		Transaction tx1 = null;
		int ind = 0;
		DeliveryAddress da = new DeliveryAddress();
		try {
			tx1 = session.beginTransaction();
			List ms = session.createQuery("FROM DeliveryAddress").list();
			for (Iterator iterator = ms.iterator(); iterator.hasNext();) {
				DeliveryAddress d = (DeliveryAddress) iterator.next();
				String addr = d.getAddrId();
				if (addrid.equals(addr)) {
					ind = d.getId();
					// System.out.println("delete address id is: "+ind);
					// da=d;
					session.delete(d);
				}
			}

			tx1.commit();
		} catch (HibernateException e) {
			if (tx1 != null)
				tx1.rollback();
			e.printStackTrace();
		}

		/*
		 * Transaction tx = null; try{ tx = session.beginTransaction(); DeliveryAddress
		 * da = (DeliveryAddress)session.get(DeliveryAddress.class, ind);
		 * session.delete(da); System.out.println("删除成功"); tx.commit(); }catch
		 * (HibernateException e) { if (tx!=null) tx.rollback(); e.printStackTrace(); }
		 */

		return true;
	}

	@Override
	public DeliveryAddress getAddressByAddrid(String addrid) throws Exception {
		// TODO Auto-generated method stub
		DeliveryAddress da = new DeliveryAddress();
		// System.out.println(addrid);
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			List ms = session.createQuery("FROM DeliveryAddress").list();
			for (Iterator iterator = ms.iterator(); iterator.hasNext();) {
				DeliveryAddress d = (DeliveryAddress) iterator.next();
				String ad = d.getAddrId();
				if (addrid.equals(ad)) {
					da = d;
				}
			}
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		}
		// System.out.println("single address id :"+da.getAddrId());
		return da;
	}

	@Override
	public boolean addMemberAddress(int mid, String name, String phone, String province, String city, String district,
			String street) throws Exception {
		// TODO Auto-generated method stub

		String deliveryAddressID = "";

		Transaction tx = null;
		ArrayList<Integer> ids = new ArrayList<Integer>();
		try {
			tx = session.beginTransaction();
			List ms = session.createQuery("FROM DeliveryAddress").list();
			for (Iterator iterator = ms.iterator(); iterator.hasNext();) {
				DeliveryAddress da = (DeliveryAddress) iterator.next();
				if (da.getAddrId().split("-")[0].equals(String.valueOf(mid))) {
					ids.add(Integer.valueOf(da.getAddrId().split("-")[1]));
					// System.out.println("addrid----"+Integer.valueOf(da.getAddrId().split("-")[1]));
				}
			}
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		}

		if (ids.size() == 0) {
			deliveryAddressID = String.valueOf(mid) + "-1";
		} else {
			int maxNum = ids.get(0);
			for (int i = 0; i < ids.size(); i++) {
				if (maxNum < ids.get(i)) {
					maxNum = ids.get(i);
				}
			}
			int mm = maxNum + 1;
			deliveryAddressID = String.valueOf(mid) + "-" + String.valueOf(mm);
			// System.out.println("finally id is:"+deliveryAddressID);
		}

		Transaction tx1 = null;
		Integer addressID = null;
		try {
			tx1 = session.beginTransaction();
			DeliveryAddress deliveryAddress = new DeliveryAddress();
			deliveryAddress.setAddrId(deliveryAddressID);
			;
			deliveryAddress.setName(name);
			deliveryAddress.setPhone(phone);
			deliveryAddress.setProvince(province);
			deliveryAddress.setCity(city);
			deliveryAddress.setDistrict(district);
			deliveryAddress.setStreet(street);
			addressID = (Integer) session.save(deliveryAddress);
			// add
			tx1.commit();
		} catch (HibernateException e) {
			if (tx1 != null)
				tx1.rollback();
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public boolean modifyMemberAddress(String addrid, String name, String phone, String province, String city,
			String district, String street) throws Exception {
		// TODO Auto-generated method stub
		DeliveryAddress da = new DeliveryAddress();
		da = getAddressByAddrid(addrid);
		int daid = da.getId();

		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			DeliveryAddress d = (DeliveryAddress) session.get(DeliveryAddress.class, daid);

			d.setName(name);
			d.setPhone(phone);
			d.setProvince(province);
			d.setCity(city);
			d.setDistrict(district);
			d.setStreet(street);

			session.update(d);
			System.out.println("更新地址成功");
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public boolean recharge(int mid, Double amount) throws Exception {
		// TODO Auto-generated method stub
		Member member = findMemberById(mid);
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Member m = (Member) session.get(Member.class, member.getMid());
			double a = m.getAccount();
			double b = a + amount;
			m.setAccount(b);
			session.update(m);
			System.out.println("更新余额成功");
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		}

		return true;
	}

	@Override
	public int updateLevel(Member member) throws Exception {
		// TODO Auto-generated method stub
		int level = member.getLevel();
		double consumption = member.getConsumption();
		if (consumption <= 100) {
			level = 1;
			System.out.println("level----------" + level);
		} else if (consumption > 100 && consumption <= 200) {
			level = 2;
			System.out.println("level----------" + level);
		} else if (consumption > 200 && consumption <= 500) {
			level = 3;
			System.out.println("level----------" + level);
		} else if (consumption > 500 && consumption <= 800) {
			level = 4;
			System.out.println("level----------" + level);
		} else if (consumption > 800 && consumption <= 1000) {
			level = 5;
			System.out.println("level----------" + level);
		} else {
			level = (int) (consumption / 200);
			System.out.println("level----------" + level);
		}
		return level;

	}

	@Override
	public List<Finance> getAllMemberFinanceInfo(int mid) throws Exception {
		// TODO Auto-generated method stub
		List<Finance> finance = new ArrayList<Finance>();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			List addr = session.createQuery("FROM Finance").list();
			for (Iterator iterator = addr.iterator(); iterator.hasNext();) {
				Finance f = (Finance) iterator.next();
				if (f.getState() == 1 && f.getMid() == mid) {
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
	public List<Orders> getOrder(int mid) throws Exception {
		// TODO Auto-generated method stub
		List<Orders> order = new ArrayList<Orders>();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			List addr = session.createQuery("FROM Orders").list();
			for (Iterator iterator = addr.iterator(); iterator.hasNext();) {
				Orders o = (Orders) iterator.next();
				if (o.getState() == 3 && o.getMid() == mid) {
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
	public List<Orders> getOrderTime(int mid) throws Exception {
		// TODO Auto-generated method stub
		List<Orders> order = new ArrayList<Orders>();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			List addr = session.createQuery("FROM Orders order by arrivalTime desc").list();
			for (Iterator iterator = addr.iterator(); iterator.hasNext();) {
				Orders o = (Orders) iterator.next();
				if (o.getState() == 3 && o.getMid() == mid) {
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
	public List<Orders> getOrderAccount(int mid) throws Exception {
		// TODO Auto-generated method stub
		List<Orders> order = new ArrayList<Orders>();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			List addr = session.createQuery("FROM Orders order by price desc").list();
			for (Iterator iterator = addr.iterator(); iterator.hasNext();) {
				Orders o = (Orders) iterator.next();
				if (o.getState() == 3 && o.getMid() == mid) {
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
	public List<Orders> getOrderRestaurant(int mid) throws Exception {
		// TODO Auto-generated method stub
		List<Orders> order = new ArrayList<Orders>();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			List addr = session.createQuery("FROM Orders order by rid desc").list();
			for (Iterator iterator = addr.iterator(); iterator.hasNext();) {
				Orders o = (Orders) iterator.next();
				if (o.getState() == 3 && o.getMid() == mid) {
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
	public boolean logoff(int mid) throws Exception {
		// TODO Auto-generated method stub
		Member member = findMemberById(mid);
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Member m = (Member) session.get(Member.class, member.getMid());
			m.setState(0);
			session.update(m);
			System.out.println("注销成功");
			// System.out.println(m.getState());
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		}

		return true;
	}

	@Override
	public List<Orders> getAllOrder(int mid) throws Exception {
		// TODO Auto-generated method stub
		List<Orders> order = new ArrayList<Orders>();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			List addr = session.createQuery("FROM Orders").list();
			for (Iterator iterator = addr.iterator(); iterator.hasNext();) {
				Orders o = (Orders) iterator.next();
				if (o.getMid() == mid) {
					if (o.getState() == 1 || o.getState() == 0) {
						order.add(o);
						System.out.println("lalala----------" + o.getOid());
					}
				}
			}
			tx.commit();
			session.clear();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		}
		return order;
	}

}