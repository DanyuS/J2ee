package dao;

import java.util.List;

import entity.Cart;
import entity.Orders;

public interface OrderDao {
	public boolean addMenuToCart(int mid,String rid, int meid) throws Exception;
	public List<Cart> getCart(int mid) throws Exception;
	
	public boolean payAccount(int mid,double actualPrice) throws Exception;
	public boolean addToCurrentOrder(int mid,String addrId,double price) throws Exception;
	
	public Orders findOrderByOid(int oid) throws Exception;
	
	public boolean refund(int mid,int oid) throws Exception;
	public boolean laterPayAccount(int mid,int oid) throws Exception;
	
	public boolean deleteCart(int mid,int meid) throws Exception;
	public boolean confirmOrder(int oid) throws Exception;
	
	public boolean addToCurrentOrderWithoutPay(int mid,String addrId,double price) throws Exception;
	public void deleteMemberCart(int mid) throws Exception;//把购物车清空
}
