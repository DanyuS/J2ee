package service;

import java.util.List;

import entity.DeliveryAddress;

public interface ManageInformationService {
	public List<DeliveryAddress> getAddress(int mid) throws Exception;
	public boolean deleteMemberAddress(int mid, String addrid) throws Exception;
	public boolean addMemberAddress(int mid,String name,String phone,String province,String city,String district,String street) throws Exception;
	public DeliveryAddress getAddressByAddrid(String addrid) throws Exception;
	public boolean modifyMemberAddress(String addrid,String name,String phone,String province,String city,String district,String street) throws Exception;
}
