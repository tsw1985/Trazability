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

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Index;
import org.hibernate.annotations.Type;

@Entity
@Table
public class Lote {
	
	private Long id;
	private Long liters;
	private Long idFilledFactoryTank;
	private FactoryTank factoryTank;
	private Long saltLote;
	private Operator operator;
	private Cuba cuba;
	private Date loteTime;
	private LoteCounter loteCounter;
	private String deletedLote;
	//private String finishedLote = "N";
	
	private Pasteurizator pasteurizator;

	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column
	public Long getLiters() {
		return liters;
	}
	public void setLiters(Long liters) {
		this.liters = liters;
	}
	
	@Column
	public Long getIdFilledFactoryTank() {
		return idFilledFactoryTank;
	}
	public void setIdFilledFactoryTank(Long idFilledFactoryTank) {
		this.idFilledFactoryTank = idFilledFactoryTank;
	}
	
	@Column
	public Long getSaltLote() {
		return saltLote;
	}
	public void setSaltLote(Long saladeroLote) {
		this.saltLote = saladeroLote;
	}
	
	@Index(name = "operatorIndex")
	@ManyToOne(fetch = FetchType.EAGER)
	@Cascade({CascadeType.ALL})
	@JoinColumn(name = "idOperator", nullable = false, unique = false)
	public Operator getOperator() {
		return operator;
	}
	public void setOperator(Operator operator) {
		this.operator = operator;
	}
	
	@Index(name = "idCubaIndex")
	@ManyToOne(fetch = FetchType.EAGER)
	@Cascade({CascadeType.ALL})
	@JoinColumn(name = "idCuba", nullable = false, unique = false)
	public Cuba getCuba() {
		return cuba;
	}
	public void setCuba(Cuba cuba) {
		this.cuba = cuba;
	}
	
	@Column
	@Type(type="timestamp")
	public Date getLoteTime() {
		return loteTime;
	}
	public void setLoteTime(Date loteTime) {
		this.loteTime = loteTime;
	}
	
	@Index(name = "idLoteCounterIndex")
	@ManyToOne(fetch = FetchType.EAGER)
	@Cascade({CascadeType.ALL})
	@JoinColumn(name = "idLoteCounter", nullable = false, unique = false)
	public LoteCounter getLoteCounter() {
		return loteCounter;
	}
	public void setLoteCounter(LoteCounter loteCounter) {
		this.loteCounter = loteCounter;
	}
	
	@Index(name = "idFactoryTankIndex")
	@ManyToOne(fetch = FetchType.EAGER)
	@Cascade({CascadeType.ALL})
	@JoinColumn(name = "idFactoryTank", nullable = false, unique = false)
	public FactoryTank getFactoryTank() {
		return factoryTank;
	}
	public void setFactoryTank(FactoryTank factoryTank) {
		this.factoryTank = factoryTank;
	}
	
	
	/*@Column
	public String getFinishedLote() {
		return finishedLote;
	}
	public void setFinishedLote(String finishedLote) {
		this.finishedLote = finishedLote;
	}*/
	
	@Index(name = "idPasteurizatorIndex")
	@ManyToOne(fetch = FetchType.EAGER)
	@Cascade({CascadeType.ALL})
	@JoinColumn(name = "idPasteurizator", nullable = false, unique = false)
	public Pasteurizator getPasteurizator() {
		return pasteurizator;
	}
	public void setPasteurizator(Pasteurizator pasteurizator) {
		this.pasteurizator = pasteurizator;
	}
	
	@Column(nullable=true)
	public String getDeletedLote() {
		return deletedLote;
	}
	public void setDeletedLote(String deletedLote) {
		this.deletedLote = deletedLote;
	}

	
	
}