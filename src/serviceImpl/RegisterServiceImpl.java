package serviceImpl;

import dao.MemberDao;
import dao.RestaurantDao;
import daoImpl.MemberDaoImpl;
import daoImpl.RestaurantDaoImpl;
import service.RegisterService;

public class RegisterServiceImpl implements RegisterService {

	MemberDao memberDao=new MemberDaoImpl();
	RestaurantDao restaurant=new RestaurantDaoImpl();
	
	@Override
	public String registerByMember(String username, String password, String email, String code) throws Exception {
		// TODO Auto-generated method stub
		return memberDao.registerByMember(username, password, email, code);
	}

	@Override
	public String registerByRestaurant(String username, String password, String code) throws Exception {
		// TODO Auto-generated method stub
		return restaurant.registerByRestaurant(username, password, code);
	}

	@Override
	public boolean logoff(int mid) throws Exception {
		// TODO Auto-generated method stub
		return memberDao.logoff(mid);
	}

}
