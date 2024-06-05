package com.gabriel.trazability.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Index;

@Entity
@Table
public class Maduration {
	
	private Long id;
	private Date madurationDate;
	private Lote lote;
	private ColdStorage coldStorage;
	
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column
	public Date getMadurationDate() {
		return madurationDate;
	}
	public void setMadurationDate(Date madurationDate) {
		this.madurationDate = madurationDate;
	}
	
	@Index(name = "madurationIndex")
	@ManyToOne(fetch = FetchType.LAZY)
	@Cascade({CascadeType.ALL})
	@JoinColumn(name = "idLote", nullable = false, unique = true)
	public Lote getLote() {
		return lote;
	}
	public void setLote(Lote lote) {
		this.lote = lote;
	}
	
	@Index(name = "madurationIndex")
	@OneToOne(fetch = FetchType.LAZY, orphanRemoval = true)
	@Cascade({CascadeType.ALL})
	@JoinColumn(name = "idCouldStorage", nullable = false, unique = true)
	public ColdStorage getColdStorage() {
		return coldStorage;
	}
	public void setColdStorage(ColdStorage coldStorage) {
		this.coldStorage = coldStorage;
	}
}