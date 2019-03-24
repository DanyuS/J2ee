package serviceImpl;

import java.util.List;
import java.util.Map;

import dao.ManagerDao;
import dao.MemberDao;
import dao.RestaurantDao;
import daoImpl.ManagerDaoImpl;
import daoImpl.MemberDaoImpl;
import daoImpl.RestaurantDaoImpl;
import entity.Finance;
import entity.Orders;
import service.GetStatisticsService;

public class GetStatisticsServiceImpl implements GetStatisticsService {

	ManagerDao managerDao=new ManagerDaoImpl();
	RestaurantDao restaurantDao=new RestaurantDaoImpl();
	MemberDao memberDao=new MemberDaoImpl();
	
	@Override
	public List<Integer> getRegistMemberNumDivByDef(int startdate, int enddate, int gap) throws Exception {
		// TODO Auto-generated method stub
		return managerDao.getRegistMemberNumDivByDef(startdate, enddate, gap);
	}

	@Override
	public List<Integer> getRegistRestaurantNumDivByDef(int startdate, int enddate, int gap) throws Exception {
		// TODO Auto-generated method stub
		return managerDao.getRegistRestaurantNumDivByDef(startdate, enddate, gap);
	}

	@Override
	public Map<String, Integer> getRestaurantCategoryAndNum() throws Exception {
		// TODO Auto-generated method stub
		return managerDao.getRestaurantCategoryAndNum();
	}

	@Override
	public List<Integer> getRestaurantCategoryNum() throws Exception {
		// TODO Auto-generated method stub
		return managerDao.getRestaurantCategoryNum();
	}

	@Override
	public List<Double> getFinance(int startdate, int enddate, int gap) throws Exception {
		// TODO Auto-generated method stub
		return managerDao.getFinance(startdate, enddate, gap);
	}

	@Override
	public List<Finance> getAllRestaurantFinanceInfo(String rid) throws Exception {
		// TODO Auto-generated method stub
		return restaurantDao.getAllRestaurantFinanceInfo(rid);
	}

	@Override
	public List<Orders> getSubscribeOrder(String rid) throws Exception {
		// TODO Auto-generated method stub
		return restaurantDao.getSubscribeOrder(rid);
	}

	@Override
	public List<Orders> getUnsubscribeOrder(String rid) throws Exception {
		// TODO Auto-generated method stub
		return restaurantDao.getUnsubscribeOrder(rid);
	}

	@Override
	public List<Orders> getSubscribeOrderTime(String rid) throws Exception {
		// TODO Auto-generated method stub
		return restaurantDao.getSubscribeOrderTime(rid);
	}

	@Override
	public List<Orders> getSubscribeOrderAccount(String rid) throws Exception {
		// TODO Auto-generated method stub
		return restaurantDao.getSubscribeOrderAccount(rid);
	}

	@Override
	public List<Orders> getSubscribeOrderMember(String rid) throws Exception {
		// TODO Auto-generated method stub
		return restaurantDao.getSubscribeOrderMember(rid);
	}

	@Override
	public List<Orders> getUnsubscribeOrderTime(String rid) throws Exception {
		// TODO Auto-generated method stub
		return restaurantDao.getUnsubscribeOrderTime(rid);
	}

	@Override
	public List<Orders> getUnsubscribeOrderAccount(String rid) throws Exception {
		// TODO Auto-generated method stub
		return restaurantDao.getUnsubscribeOrderAccount(rid);
	}

	@Override
	public List<Orders> getUnsubscribeOrderMember(String rid) throws Exception {
		// TODO Auto-generated method stub
		return restaurantDao.getUnsubscribeOrderMember(rid);
	}

	@Override
	public List<Finance> getAllMemberFinanceInfo(int mid) throws Exception {
		// TODO Auto-generated method stub
		return memberDao.getAllMemberFinanceInfo(mid);
	}

	@Override
	public List<Orders> getOrder(int mid) throws Exception {
		// TODO Auto-generated method stub
		return memberDao.getOrder(mid);
	}

	@Override
	public List<Orders> getOrderTime(int mid) throws Exception {
		// TODO Auto-generated method stub
		return memberDao.getOrderTime(mid);
	}

	@Override
	public List<Orders> getOrderAccount(int mid) throws Exception {
		// TODO Auto-generated method stub
		return memberDao.getOrderAccount(mid);
	}

	@Override
	public List<Orders> getOrderRestaurant(int mid) throws Exception {
		// TODO Auto-generated method stub
		return memberDao.getOrderRestaurant(mid);
	}

}
