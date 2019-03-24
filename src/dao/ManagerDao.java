package dao;

import java.util.List;
import java.util.Map;

import entity.ApproveRestaurant;
import entity.Finance;
import entity.Manager;

public interface ManagerDao {
	public Manager isValidLogin(String username, String password) throws Exception;
	public List<ApproveRestaurant> getAllRestaurantApprovalInfo() throws Exception;
	public boolean forbidApproval(String rid) throws Exception;
	public boolean permitApproval(int aid,int id,String rid,String restaurantName,String restaurantPassword,String address,String type,String keeper,String phone) throws Exception;
	public boolean allForbidApproval() throws Exception;
	public boolean allPermitApproval() throws Exception;
	
	public List<Integer> getRegistMemberNumDivByDef(int startdate,int enddate,int gap) throws Exception;
	public List<Integer> getRegistRestaurantNumDivByDef(int startdate,int enddate,int gap) throws Exception;
	public Map<String, Integer> getRestaurantCategoryAndNum() throws Exception;
	public List<Integer> getRestaurantCategoryNum() throws Exception;
	public List<Double> getFinance(int startdate,int enddate,int gap) throws Exception;
	
	public List<Finance> getAllFinanceInfo() throws Exception;
	public boolean permitBalance(int financeId) throws Exception;
	public boolean allPermitBalance() throws Exception;
}
