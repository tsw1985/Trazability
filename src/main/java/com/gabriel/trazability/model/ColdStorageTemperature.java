package com.gabriel.trazability.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class ColdStorageTemperature {

	private Long id;
	private Long temperature;
	private Date dateReadTemperature;
	
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column
	public Long getTemperature() {
		return temperature;
	}
	public void setTemperature(Long temperature) {
		this.temperature = temperature;
	}
	
	@Column
	public Date getDateReadTemperature() {
		return dateReadTemperature;
	}
	public void setDateReadTemperature(Date dateReadTemperature) {
		this.dateReadTemperature = dateReadTemperature;
	}
	
}
