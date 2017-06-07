package com.parishram.model;

import java.io.Serializable;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value="session")	
public class UserDetailsModel implements Serializable {
	private static final long serialVersionUID = 1L;
	private String firstName;
	private String lastName;
	// private int age;
	private String age;
	private char gender;
	// private float weight;
	private String weight;
	private String occupation;
	private int religionId;
	private String religion;
	// private BigDecimal salary;
	private String salary;

	public String getReligion() {
		return religion;
	}

	public void setReligion(String religion) {
		this.religion = religion;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public char getGender() {
		return gender;
	}

	public void setGender(char gender) {
		this.gender = gender;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public int getReligionId() {
		return religionId;
	}

	public void setReligionId(int religionId) {
		this.religionId = religionId;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getSalary() {
		return salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}

}
