package com.gabriel.trazability.model.sql.query;

import java.security.Timestamp;

public class PasteurizatorData {
	
	private String description;
	private Timestamp date;
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Timestamp getDate() {
		return date;
	}
	public void setDate(Timestamp date) {
		this.date = date;
	}

}
