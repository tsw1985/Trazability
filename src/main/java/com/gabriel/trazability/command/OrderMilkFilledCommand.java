package com.gabriel.trazability.command;

public class OrderMilkFilledCommand {
	
	private Long id;
	private String idProvider;
	private String idOperator;
	private String idTruckTank;
	private String litersFilled;
	private String incidence;
	private String tankProvider;
	private String correction;
	private String display;
	private String numberWorkDayMilkFilled;
	
	public String getNumberWorkDayMilkFilled() {
		return numberWorkDayMilkFilled;
	}
	public void setNumberWorkDayMilkFilled(String numberWorkDayMilkFilled) {
		this.numberWorkDayMilkFilled = numberWorkDayMilkFilled;
	}
	public String getDisplay() {
		return display;
	}
	public void setDisplay(String display) {
		this.display = display;
	}
	public String getIdProvider() {
		return idProvider;
	}
	public void setIdProvider(String idProvider) {
		this.idProvider = idProvider;
	}
	public String getIdOperator() {
		return idOperator;
	}
	public void setIdOperator(String idOperator) {
		this.idOperator = idOperator;
	}
	public String getIdTruckTank() {
		return idTruckTank;
	}
	public void setIdTruckTank(String idTruckTank) {
		this.idTruckTank = idTruckTank;
	}
	public String getLitersFilled() {
		return litersFilled;
	}
	public void setLitersFilled(String litersFilled) {
		this.litersFilled = litersFilled;
	}
	public String getIncidence() {
		return incidence;
	}
	public void setIncidence(String incidence) {
		this.incidence = incidence;
	}
	public String getTankProvider() {
		return tankProvider;
	}
	public void setTankProvider(String tankProvider) {
		this.tankProvider = tankProvider;
	}
	public String getCorrection() {
		return correction;
	}
	public void setCorrection(String correction) {
		this.correction = correction;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
}
