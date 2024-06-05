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
import org.hibernate.annotations.Type;


@Entity
@Table
public class MilkFilled {
	
	private Long id;
	private Provider provider;
	private Date filledDate;
	private Operator operator;
	private Long totalFilled;
	private String display;
	private String incidence;
	private String correction;
	private String providerTank;
	private String numberWorkDayMilkFilled;
	private String deleteMilkFilled;
	private TruckTank truckTank;
	
	
	@Column
	public String getProviderTank() {
		return providerTank;
	}
	public void setProviderTank(String providerTank) {
		this.providerTank = providerTank;
	}
	
	
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	
	@Index(name = "truckTankIndex")
	@ManyToOne(fetch = FetchType.EAGER)
	@Cascade({CascadeType.ALL})
	@JoinColumn(name = "idTruckTank", nullable = false, unique = false)
	public TruckTank getTruckTank() {
		return truckTank;
	}
	public void setTruckTank(TruckTank truckTank) {
		this.truckTank = truckTank;
	}
	
	
	
	@Index(name = "providerIndex")
	@ManyToOne(fetch = FetchType.EAGER)
	@Cascade({CascadeType.ALL})
	@JoinColumn(name = "idProvider", nullable = false, unique = false)
	public Provider getProvider() {
		return provider;
	}
	public void setProvider(Provider provider) {
		this.provider = provider;
	}

	
	@Column
	@Type(type="timestamp")
	public Date getFilledDate() {
		return filledDate;
	}
	public void setFilledDate(Date filledDate) {
		this.filledDate = filledDate;
	}
	
	
	@Index(name = "operatorIndex")
	@OneToOne(fetch = FetchType.EAGER, orphanRemoval = true)
	@Cascade({CascadeType.ALL})
	@JoinColumn(name = "idOperator", nullable = false, unique = false)
	public Operator getOperator() {
		return operator;
	}
	public void setOperator(Operator operator) {
		this.operator = operator;
	}
	@Column
	public Long getTotalFilled() {
		return totalFilled;
	}
	public void setTotalFilled(Long totalFilled) {
		this.totalFilled = totalFilled;
	}
	
	
	@Column
	public String getDisplay() {
		return display;
	}
	public void setDisplay(String display) {
		this.display = display;
	}
	@Column
	public String getIncidence() {
		return incidence;
	}
	public void setIncidence(String incidence) {
		this.incidence = incidence;
	}
	@Column
	public String getCorrection() {
		return correction;
	}
	public void setCorrection(String correction) {
		this.correction = correction;
	}
	
	@Column
	public String getNumberWorkDayMilkFilled() {
		return numberWorkDayMilkFilled;
	}
	public void setNumberWorkDayMilkFilled(String numberWorkDayMilkFilled) {
		this.numberWorkDayMilkFilled = numberWorkDayMilkFilled;
	}
	
	@Column(nullable=true)
	public String getDeleteMilkFilled() {
		return deleteMilkFilled;
	}
	public void setDeleteMilkFilled(String deleteMilkFilled) {
		this.deleteMilkFilled = deleteMilkFilled;
	}
}
