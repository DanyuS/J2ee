package service;

import entity.Manager;
import entity.Member;
import entity.Restaurant;

public interface LoginService {
	public Member memberLogin(String username, String password) throws Exception;
	public Manager managerLogin(String username, String password) throws Exception;
	public Restaurant restaurantLogin(String username, String password) throws Exception;
	
	public Member memberLoginByCode(String code) throws Exception;
}
