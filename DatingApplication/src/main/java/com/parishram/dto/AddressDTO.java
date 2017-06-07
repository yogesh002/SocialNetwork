package com.parishram.dto;

import java.io.Serializable;

import org.springframework.stereotype.Component;
@Component
public class AddressDTO implements Serializable {
	private static final long serialVersionUID = 2645790342892980737L;
	private int loginId;
	private String country;
	private String state;
	private String city;
	private int zipCode;

	public int getLoginId() {
		return loginId;
	}

	public void setLoginId(int loginId) {
		this.loginId = loginId;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public int getZipCode() {
		return zipCode;
	}

	public void setZipCode(int zipCode) {
		this.zipCode = zipCode;
	}

}
