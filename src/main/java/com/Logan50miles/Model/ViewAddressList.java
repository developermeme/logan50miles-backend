package com.Logan50miles.Model;

import java.util.List;

public class ViewAddressList {
private int userid;
private String uPhone;
private String uName;
private List<AddressTemp> address;

public ViewAddressList() {
	
}

public int getUserid() {
	return userid;
}

public void setUserid(int userid) {
	this.userid = userid;
}

public String getuPhone() {
	return uPhone;
}

public void setuPhone(String uPhone) {
	this.uPhone = uPhone;
}

public String getuName() {
	return uName;
}

public void setuName(String uName) {
	this.uName = uName;
}

public List<AddressTemp> getAddress() {
	return address;
}

public void setAddress(List<AddressTemp> address) {
	this.address = address;
}

}
