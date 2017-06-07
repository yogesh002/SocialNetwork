package com.parishram.dto;

import java.io.Serializable;

public class UserDetailsDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private EducationDTO educationDTO;
	private AddressDTO addressDTO;
	private PersonDTO personalDetailsDTO;
	public EducationDTO getEducationDTO() {
		return educationDTO;
	}
	public void setEducationDTO(EducationDTO educationDTO) {
		this.educationDTO = educationDTO;
	}
	public AddressDTO getAddressDTO() {
		return addressDTO;
	}
	public void setAddressDTO(AddressDTO addressDTO) {
		this.addressDTO = addressDTO;
	}
	public PersonDTO getPersonalDetailsDTO() {
		return personalDetailsDTO;
	}
	public void setPersonalDetailsDTO(PersonDTO personalDetailsDTO) {
		this.personalDetailsDTO = personalDetailsDTO;
	}
	
	
}
