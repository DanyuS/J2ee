package serviceImpl;

import java.util.List;

import dao.MemberDao;
import daoImpl.MemberDaoImpl;
import entity.DeliveryAddress;
import service.ManageInformationService;

public class ManageInformationServiceImpl implements ManageInformationService {

	MemberDao memberDao=new MemberDaoImpl();
	
	@Override
	public List<DeliveryAddress> getAddress(int mid) throws Exception {
		// TODO Auto-generated method stub
		return memberDao.getAddress(mid);
	}

	@Override
	public boolean deleteMemberAddress(int mid, String addrid) throws Exception {
		// TODO Auto-generated method stub
		return memberDao.deleteMemberAddress(mid, addrid);
	}

	@Override
	public boolean addMemberAddress(int mid, String name, String phone, String province, String city, String district,
			String street) throws Exception {
		// TODO Auto-generated method stub
		return memberDao.addMemberAddress(mid, name, phone, province, city, district, street);
	}

	@Override
	public DeliveryAddress getAddressByAddrid(String addrid) throws Exception {
		// TODO Auto-generated method stub
		return memberDao.getAddressByAddrid(addrid);
	}

	@Override
	public boolean modifyMemberAddress(String addrid, String name, String phone, String province, String city,
			String district, String street) throws Exception {
		// TODO Auto-generated method stub
		return memberDao.modifyMemberAddress(addrid, name, phone, province, city, district, street);
	}

}
