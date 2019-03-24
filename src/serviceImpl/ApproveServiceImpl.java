package serviceImpl;

import java.util.List;

import dao.ManagerDao;
import daoImpl.ManagerDaoImpl;
import entity.ApproveRestaurant;
import entity.Finance;
import service.ApproveService;

public class ApproveServiceImpl implements ApproveService {

	ManagerDao managerDao=new ManagerDaoImpl();
	
	@Override
	public List<ApproveRestaurant> getAllRestaurantApprovalInfo() throws Exception {
		// TODO Auto-generated method stub
		return managerDao.getAllRestaurantApprovalInfo();
	}

	@Override
	public boolean forbidApproval(String rid) throws Exception {
		// TODO Auto-generated method stub
		return managerDao.forbidApproval(rid);
	}

	@Override
	public boolean permitApproval(int aid, int id, String rid, String restaurantName, String restaurantPassword,
			String address, String type, String keeper, String phone) throws Exception {
		// TODO Auto-generated method stub
		return managerDao.permitApproval(aid, id, rid, restaurantName, restaurantPassword, address, type, keeper, phone);
	}

	@Override
	public boolean allForbidApproval() throws Exception {
		// TODO Auto-generated method stub
		return managerDao.allForbidApproval();
	}

	@Override
	public boolean allPermitApproval() throws Exception {
		// TODO Auto-generated method stub
		return managerDao.allPermitApproval();
	}

	@Override
	public List<Finance> getAllFinanceInfo() throws Exception {
		// TODO Auto-generated method stub
		return managerDao.getAllFinanceInfo();
	}

	@Override
	public boolean permitBalance(int financeId) throws Exception {
		// TODO Auto-generated method stub
		return managerDao.permitBalance(financeId);
	}

	@Override
	public boolean allPermitBalance() throws Exception {
		// TODO Auto-generated method stub
		return managerDao.allPermitBalance();
	}

}
