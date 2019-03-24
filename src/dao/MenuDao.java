package dao;

import java.util.List;

import entity.Menu;

public interface MenuDao {
	public boolean addMenu(String rid, String name,String remarks,double price,double currentPrice,int stock,String type,String startTime,String endTime) throws Exception;
	public List<Menu> getAllReleasedMenu(String rid) throws Exception;
	public List<Menu> getAllUnreleasedMenu(String rid) throws Exception;
	public boolean deleteMenu(int meid) throws Exception;
	public Menu findMenuByMeid(int meid) throws Exception;
	
}
