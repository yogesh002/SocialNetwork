package com.parishram.model;

import java.io.Serializable;

public class AddressDetailsModel implements Serializable {
	private static final long serialVersionUID = -401768556514015299L;
	private String country;
	private String state;
	private String city;
	private int zip;

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

	public int getZip() {
		return zip;
	}

	public void setZip(int zip) {
		this.zip = zip;
	}
}
