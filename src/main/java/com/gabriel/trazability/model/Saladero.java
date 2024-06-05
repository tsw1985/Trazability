package com.gabriel.trazability.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table
public class Saladero {
	
	private Long id;
	private Date dateSaladero;
	private Long saltLote;
	private Lote lote;
	
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="idLote")
	public Lote getLote() {
		return lote;
	}
	public void setLote(Lote lote) {
		this.lote = lote;
	}
	
	
	@Column
	public Date getDateSaladero() {
		return dateSaladero;
	}
	public void setDateSaladero(Date dateSaladero) {
		this.dateSaladero = dateSaladero;
	}
	
	@Column
	public Long getSaltLote() {
		return saltLote;
	}
	public void setSaltLote(Long saltLote) {
		this.saltLote = saltLote;
	}

}
