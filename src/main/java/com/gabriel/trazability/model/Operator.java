package com.gabriel.trazability.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Operator {
	
	@Id
	@GeneratedValue
	private Long id;

	private String nameOperator;
	private String cif;
	private String address;
	private String phone;
	private String deletedOperator;
	
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column
	public String getNameOperator() {
		return nameOperator;
	}
	public void setNameOperator(String nameOperator) {
		this.nameOperator = nameOperator;
	}

	
	
	@Column
	public String getCif() {
		return cif;
	}
	public void setCif(String cif) {
		this.cif = cif;
	}
	
	@Column
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	@Column
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@Column
	public String getDeletedOperator() {
		return deletedOperator;
	}
	public void setDeletedOperator(String deletedOperator) {
		this.deletedOperator = deletedOperator;
	}
	
}
