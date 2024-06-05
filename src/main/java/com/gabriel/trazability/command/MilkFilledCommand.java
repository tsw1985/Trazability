package com.gabriel.trazability.command;

import java.util.Date;

public class MilkFilledCommand {
	
	private Long id;
	private Long provider;
	private Date filledDate;
	private Long operator;
	private Long totalFilled;
	private String display;
	private String incidence;
	private String correction;
	private String providerTank;
	private String numberWorkDayMilkFilled;
	private String deleteMilkFilled;
	private Long truckTank;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getProvider() {
		return provider;
	}
	public void setProvider(Long provider) {
		this.provider = provider;
	}
	public Date getFilledDate() {
		return filledDate;
	}
	public void setFilledDate(Date filledDate) {
		this.filledDate = filledDate;
	}
	public Long getOperator() {
		return operator;
	}
	public void setOperator(Long operator) {
		this.operator = operator;
	}
	public Long getTotalFilled() {
		return totalFilled;
	}
	public void setTotalFilled(Long totalFilled) {
		this.totalFilled = totalFilled;
	}
	public String getDisplay() {
		return display;
	}
	public void setDisplay(String display) {
		this.display = display;
	}
	public String getIncidence() {
		return incidence;
	}
	public void setIncidence(String incidence) {
		this.incidence = incidence;
	}
	public String getCorrection() {
		return correction;
	}
	public void setCorrection(String correction) {
		this.correction = correction;
	}
	public String getProviderTank() {
		return providerTank;
	}
	public void setProviderTank(String providerTank) {
		this.providerTank = providerTank;
	}
	public String getNumberWorkDayMilkFilled() {
		return numberWorkDayMilkFilled;
	}
	public void setNumberWorkDayMilkFilled(String numberWorkDayMilkFilled) {
		this.numberWorkDayMilkFilled = numberWorkDayMilkFilled;
	}
	public String getDeleteMilkFilled() {
		return deleteMilkFilled;
	}
	public void setDeleteMilkFilled(String deleteMilkFilled) {
		this.deleteMilkFilled = deleteMilkFilled;
	}
	public Long getTruckTank() {
		return truckTank;
	}
	public void setTruckTank(Long truckTank) {
		this.truckTank = truckTank;
	}


}
