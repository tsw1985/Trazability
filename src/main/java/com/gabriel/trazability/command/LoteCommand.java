package com.gabriel.trazability.command;

public class LoteCommand {
	
	private Long idLoteCounter;
	private Long liters;
	private Long idFactoryTank;
	private Long idFilledFactoryTank;
	private Long idOperator;
	private Long cuba;
	private Long pasteurizator;
	
	public Long getIdLoteCounter() {
		return idLoteCounter;
	}
	public void setIdLoteCounter(Long idLoteCounter) {
		this.idLoteCounter = idLoteCounter;
	}
	

	
	public Long getIdFactoryTank() {
		return idFactoryTank;
	}
	public void setIdFactoryTank(Long idFactoryTank) {
		this.idFactoryTank = idFactoryTank;
	}
	public Long getIdFilledFactoryTank() {
		return idFilledFactoryTank;
	}
	public void setIdFilledFactoryTank(Long idFilledFactoryTank) {
		this.idFilledFactoryTank = idFilledFactoryTank;
	}
	public Long getIdOperator() {
		return idOperator;
	}
	public void setIdOperator(Long idOperator) {
		this.idOperator = idOperator;
	}
	public Long getCuba() {
		return cuba;
	}
	public void setCuba(Long cuba) {
		this.cuba = cuba;
	}
	public Long getPasteurizator() {
		return pasteurizator;
	}
	public void setPasteurizator(Long pasteurizator) {
		this.pasteurizator = pasteurizator;
	}
	public Long getLiters() {
		return liters;
	}
	public void setLiters(Long liters) {
		this.liters = liters;
	}
}
