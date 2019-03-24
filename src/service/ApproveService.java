package service;

import java.util.List;

import entity.ApproveRestaurant;
import entity.Finance;

public interface ApproveService {
	public List<ApproveRestaurant> getAllRestaurantApprovalInfo() throws Exception;
	public boolean forbidApproval(String rid) throws Exception;
	public boolean permitApproval(int aid,int id,String rid,String restaurantName,String restaurantPassword,String address,String type,String keeper,String phone) throws Exception;
	public boolean allForbidApproval() throws Exception;
	public boolean allPermitApproval() throws Exception;
	
	public List<Finance> getAllFinanceInfo() throws Exception;
	public boolean permitBalance(int financeId) throws Exception;
	public boolean allPermitBalance() throws Exception;
}
