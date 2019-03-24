package serviceImpl;

import dao.MemberDao;
import dao.RestaurantDao;
import daoImpl.MemberDaoImpl;
import daoImpl.RestaurantDaoImpl;
import entity.Member;
import entity.Restaurant;
import service.ModifyService;

public class ModifyServiceImpl implements ModifyService {

	MemberDao memberDao=new MemberDaoImpl();
	RestaurantDao restaurantDao=new RestaurantDaoImpl();
	
	@Override
	public String getMemberPasswordById(int mid) throws Exception {
		// TODO Auto-generated method stub
		return memberDao.getMemberPasswordById(mid);
	}

	@Override
	public String getMemberEmailById(int mid) throws Exception {
		// TODO Auto-generated method stub
		return memberDao.getMemberEmailById(mid);
	}

	@Override
	public boolean changeMemberPassword(int mid, String password) throws Exception {
		// TODO Auto-generated method stub
		return memberDao.changeMemberPassword(mid, password);
	}

	@Override
	public String getMemberAccountById(int mid) throws Exception {
		// TODO Auto-generated method stub
		return memberDao.getMemberAccountById(mid);
	}

	@Override
	public boolean recharge(int mid, Double amount) throws Exception {
		// TODO Auto-generated method stub
		return memberDao.recharge(mid, amount);
	}

	@Override
	public Member findMemberById(int mid) throws Exception {
		// TODO Auto-generated method stub
		return memberDao.findMemberById(mid);
	}

	@Override
	public Restaurant findRestaurantByRid(String rid) throws Exception {
		// TODO Auto-generated method stub
		return restaurantDao.findRestaurantByRid(rid);
	}

	@Override
	public boolean modifyRestaurantInfo(int id, String rid, String restaurantName, String restaurantPassword,
			String address, String type, String keeper, String phone) throws Exception {
		// TODO Auto-generated method stub
		return restaurantDao.modifyRestaurantInfo(id, rid, restaurantName, restaurantPassword, address, type, keeper, phone);
	}

}
