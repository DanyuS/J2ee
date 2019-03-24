package dao;

import java.util.List;

import entity.DeliveryAddress;
import entity.Finance;
import entity.Member;
import entity.Orders;

public interface MemberDao {

	public Member isValidLogin(String username, String password) throws Exception;
	
	public Member findMemberById(int mid) throws Exception;
	public Member findMemberByCode(String code) throws Exception;
	public void updateMemberInfo(Member member) throws Exception;
	
	public String registerByMember(String username, String password,String email, String code) throws Exception;
	
	public String getMemberPasswordById(int mid) throws Exception;
	public String getMemberEmailById(int mid) throws Exception;
	public String getMemberAccountById(int mid) throws Exception;
	
	public boolean changeMemberPassword(int mid,String password) throws Exception;
	
	public List<DeliveryAddress> getAddress(int mid) throws Exception;
	public boolean deleteMemberAddress(int mid, String addrid) throws Exception;
	public boolean addMemberAddress(int mid,String name,String phone,String province,String city,String district,String street) throws Exception;
	public DeliveryAddress getAddressByAddrid(String addrid) throws Exception;
	public boolean modifyMemberAddress(String addrid,String name,String phone,String province,String city,String district,String street) throws Exception;
	
	public boolean recharge(int mid,Double amount) throws Exception;
	
	public int updateLevel(Member member) throws Exception;
	
	public List<Finance> getAllMemberFinanceInfo(int mid) throws Exception;
	public List<Orders> getOrder(int mid) throws Exception;
	public List<Orders> getOrderTime(int mid) throws Exception;
	public List<Orders> getOrderAccount(int mid) throws Exception;
	public List<Orders> getOrderRestaurant(int mid) throws Exception;
	
	public boolean logoff(int mid) throws Exception;
	
	public List<Orders> getAllOrder(int mid) throws Exception;
}
