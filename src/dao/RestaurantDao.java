package dao;

import java.util.List;

import entity.Finance;
import entity.Orders;
import entity.Restaurant;

public interface RestaurantDao {
	public Restaurant isValidLogin(String username, String password) throws Exception;
	public String registerByRestaurant(String username, String password, String code) throws Exception;
	public Restaurant findRestaurantByRid(String rid) throws Exception;
	public Restaurant findRestaurantById(int id) throws Exception;
	public boolean modifyRestaurantInfo(int id,String rid,String restaurantName,String restaurantPassword,String address,String type,String keeper,String phone) throws Exception;
	public List<Restaurant> getAllRestaurant() throws Exception;
	
	public List<Finance> getAllRestaurantFinanceInfo(String rid) throws Exception;
	public List<Orders> getSubscribeOrder(String rid) throws Exception;
	public List<Orders> getUnsubscribeOrder(String rid) throws Exception;
	public List<Orders> getSubscribeOrderTime(String rid) throws Exception;
	public List<Orders> getSubscribeOrderAccount(String rid) throws Exception;
	public List<Orders> getSubscribeOrderMember(String rid) throws Exception;
	public List<Orders> getUnsubscribeOrderTime(String rid) throws Exception;
	public List<Orders> getUnsubscribeOrderAccount(String rid) throws Exception;
	public List<Orders> getUnsubscribeOrderMember(String rid) throws Exception;
	
	public List<Restaurant> getRestaurantBySearch(String sch) throws Exception;//关键字搜索
	
	public List<Orders> getOrderToSend(String rid) throws Exception;//待派送订单
	public boolean sendOrder(int oid) throws Exception;//派送订单
}
