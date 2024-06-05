package com.gabriel.trazability.command;

import java.util.Date;

public class IngredientDetailCommand {
	
	private Long id;
	private String boughtDate;
	private String expirationDate;
	private String loteNumber;
	private String note;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getBoughtDate() {
		return boughtDate;
	}
	public void setBoughtDate(String boughtDate) {
		this.boughtDate = boughtDate;
	}
	public String getExpirationDate() {
		return expirationDate;
	}
	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}
	public String getLoteNumber() {
		return loteNumber;
	}
	public void setLoteNumber(String loteNumber) {
		this.loteNumber = loteNumber;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
}