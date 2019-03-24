package service;

public interface RegisterService {
	public String registerByMember(String username, String password,String email, String code) throws Exception;
	public String registerByRestaurant(String username, String password, String code) throws Exception;
	public boolean logoff(int mid) throws Exception;
}
