package com.gabriel.trazability.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Index;

@Entity
@Table
public class Pasteurization {
	
	private Long id;
	private LoteCounter loteCounter;
	
	private Double temperature;
	private Date date;
	
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Index(name = "loteCounterIndex")
	@ManyToOne(fetch = FetchType.LAZY)
	@Cascade({CascadeType.ALL})
	@JoinColumn(name = "idLoteCounter", nullable = false, unique = false)
	public LoteCounter getLoteCounter() {
		return loteCounter;
	}
	public void setLoteCounter(LoteCounter loteCounter) {
		this.loteCounter = loteCounter;
	}
	
	
	
	
	
	public Double getTemperature() {
		return temperature;
	}
	public void setTemperature(Double temperature) {
		this.temperature = temperature;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	

}
