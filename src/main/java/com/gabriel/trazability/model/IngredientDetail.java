package com.gabriel.trazability.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table
public class IngredientDetail {
	
	private Long id;
	private Long lote;
	private Date expirationDate;
	private Date boughtDate;
	private String note;
	private String consumed;
	
	
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column
	public Long	getLote() {		
		return lote;
	}
	public void setLote(Long lote) {
		this.lote = lote;
	}
	
	@Column
	@Type(type="timestamp")
	public Date getExpirationDate() {
		return expirationDate;
	}
	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}
	
	@Column
	@Type(type="timestamp")
	public Date getBoughtDate() {
		return boughtDate;
	}
	public void setBoughtDate(Date boughtDate) {
		this.boughtDate = boughtDate;
	}
	
	@Column
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	
	@Column(nullable = true)
	public String getConsumed() {
		return consumed;
	}
	public void setConsumed(String consumed) {
		this.consumed = consumed;
	}
}