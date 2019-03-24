package serviceImpl;

import dao.ManagerDao;
import dao.MemberDao;
import dao.RestaurantDao;
import daoImpl.ManagerDaoImpl;
import daoImpl.MemberDaoImpl;
import daoImpl.RestaurantDaoImpl;
import entity.Manager;
import entity.Member;
import entity.Restaurant;
import service.LoginService;

public class LoginServiceImpl implements LoginService {

	MemberDao memberDao=new MemberDaoImpl();
	ManagerDao managerDao=new ManagerDaoImpl();
	RestaurantDao restaurantDao=new RestaurantDaoImpl();
	
	@Override
	public Member memberLogin(String username, String password) throws Exception {
		// TODO Auto-generated method stub
		return memberDao.isValidLogin(username, password);
	}

	@Override
	public Manager managerLogin(String username, String password) throws Exception {
		// TODO Auto-generated method stub
		return managerDao.isValidLogin(username, password);
	}

	@Override
	public Restaurant restaurantLogin(String username, String password) throws Exception {
		// TODO Auto-generated method stub
		return restaurantDao.isValidLogin(username, password);
	}

	@Override
	public Member memberLoginByCode(String code) throws Exception {
		// TODO Auto-generated method stub
		return memberDao.findMemberByCode(code);
	}

}
