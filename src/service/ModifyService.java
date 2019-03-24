package service;

import entity.Member;
import entity.Restaurant;

public interface ModifyService {
	public String getMemberPasswordById(int mid) throws Exception;
	public String getMemberEmailById(int mid) throws Exception;
	public String getMemberAccountById(int mid) throws Exception;
	public Member findMemberById(int mid) throws Exception;
	
	public boolean changeMemberPassword(int mid,String password) throws Exception;
	public boolean recharge(int mid, Double amount) throws Exception;
	
	public Restaurant findRestaurantByRid(String rid) throws Exception;
	public boolean modifyRestaurantInfo(int id,String rid,String restaurantName,String restaurantPassword,String address,String type,String keeper,String phone) throws Exception;
	
}
