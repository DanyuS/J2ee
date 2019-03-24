package service;

import java.util.List;
import java.util.Map;

import entity.Finance;
import entity.Orders;

public interface GetStatisticsService {
	public List<Integer> getRegistMemberNumDivByDef(int startdate, int enddate, int gap) throws Exception;
	public List<Integer> getRegistRestaurantNumDivByDef(int startdate,int enddate,int gap) throws Exception;
	public Map<String, Integer> getRestaurantCategoryAndNum() throws Exception;
	public List<Integer> getRestaurantCategoryNum() throws Exception;
	public List<Double> getFinance(int startdate,int enddate,int gap) throws Exception;
	
	public List<Finance> getAllRestaurantFinanceInfo(String rid) throws Exception;
	public List<Orders> getSubscribeOrder(String rid) throws Exception;
	public List<Orders> getUnsubscribeOrder(String rid) throws Exception;
	public List<Orders> getSubscribeOrderTime(String rid) throws Exception;
	public List<Orders> getSubscribeOrderAccount(String rid) throws Exception;
	public List<Orders> getSubscribeOrderMember(String rid) throws Exception;
	public List<Orders> getUnsubscribeOrderTime(String rid) throws Exception;
	public List<Orders> getUnsubscribeOrderAccount(String rid) throws Exception;
	public List<Orders> getUnsubscribeOrderMember(String rid) throws Exception;
	
	public List<Finance> getAllMemberFinanceInfo(int mid) throws Exception;
	public List<Orders> getOrder(int mid) throws Exception;
	public List<Orders> getOrderTime(int mid) throws Exception;
	public List<Orders> getOrderAccount(int mid) throws Exception;
	public List<Orders> getOrderRestaurant(int mid) throws Exception;
}
