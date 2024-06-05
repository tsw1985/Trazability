package com.gabriel.trazability.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Pasteurizator {
	
	private Long id;
	private String description;
	private String run;
	private String pasteurizing;
	private String actualTemperature;
	private Double caudalSecond;
	private String deletedPasteurizator;
	private Long litersServed;
	private Long actualIdLoteCounterForWork;
	private Long actualIdFactoryTankForWork;
	private String ip;
	
	
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Column
	public String getRun() {
		return run;
	}
	public void setRun(String run) {
		this.run = run;
	}
	
	@Column
	public String getPasteurizing() {
		return pasteurizing;
	}
	public void setPasteurizing(String pasteurizing) {
		this.pasteurizing = pasteurizing;
	}
	
	@Column
	public String getActualTemperature() {
		return actualTemperature;
	}
	public void setActualTemperature(String actualTemperature) {
		this.actualTemperature = actualTemperature;
	}

	@Column
	public Double getCaudalSecond() {
		return caudalSecond;
	}
	public void setCaudalSecond(Double caudalSecond) {
		this.caudalSecond = caudalSecond;
	}
	
	@Column
	public String getDeletedPasteurizator() {
		return deletedPasteurizator;
	}
	public void setDeletedPasteurizator(String deletedPasteurizator) {
		this.deletedPasteurizator = deletedPasteurizator;
	}
	
	@Column
	public Long getLitersServed() {
		return litersServed;
	}
	public void setLitersServed(Long litersServed) {
		this.litersServed = litersServed;
	}
	
	@Column
	public Long getActualIdLoteCounterForWork() {
		return actualIdLoteCounterForWork;
	}
	public void setActualIdLoteCounterForWork(Long actualIdLoteCounterForWork) {
		this.actualIdLoteCounterForWork = actualIdLoteCounterForWork;
	}
	
	@Column(nullable = true)
	public Long getActualIdFactoryTankForWork() {
		return actualIdFactoryTankForWork;
	}
	public void setActualIdFactoryTankForWork(Long actualIdFactoryTankForWork) {
		this.actualIdFactoryTankForWork = actualIdFactoryTankForWork;
	}
	
	@Column(nullable = true)
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
}