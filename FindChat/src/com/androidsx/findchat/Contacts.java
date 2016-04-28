package com.androidsx.findchat;

import java.io.Serializable;

public class Contacts implements Serializable {

	String name, number;
	int id;
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	Boolean isSelected = false;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Boolean getIsSelected() {
		return isSelected;
	}

	public void setIsSelected(Boolean isSelected) {
		this.isSelected = isSelected;
	}

	
}
