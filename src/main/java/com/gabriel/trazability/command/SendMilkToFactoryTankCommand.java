package com.gabriel.trazability.command;

public class SendMilkToFactoryTankCommand {
	private Long idMilkFilled;
	private String dateEnterSystem;
	private Long idTruckTank;
	private Long factoryTank;
	private Long litersFilled;
	
	public Long getIdMilkFilled() {
		return idMilkFilled;
	}
	public void setIdMilkFilled(Long idMilkFilled) {
		this.idMilkFilled = idMilkFilled;
	}
	public String getDateEnterSystem() {
		return dateEnterSystem;
	}
	public void setDateEnterSystem(String dateEnterSystem) {
		this.dateEnterSystem = dateEnterSystem;
	}
	public Long getIdTruckTank() {
		return idTruckTank;
	}
	public void setIdTruckTank(Long idTruckTank) {
		this.idTruckTank = idTruckTank;
	}
	public Long getFactoryTank() {
		return factoryTank;
	}
	public void setFactoryTank(Long factoryTank) {
		this.factoryTank = factoryTank;
	}
	public Long getLitersFilled() {
		return litersFilled;
	}
	public void setLitersFilled(Long litersFilled) {
		this.litersFilled = litersFilled;
	}  
}