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
import org.joda.time.DateTime;

@Entity
@Table
public class MilkSentToFactoryTank {
	
	private Long id;
	private CounterWorkingDayForMilkFilled CounterWorkingDayForMilkFilled;
	private TruckTank truckTank;
	private FactoryTank factoryTank;
	private Long liters;
	private Date dateTimeMilkSentToFactoryTank;
	private Date dateTimeMilkFilled;
	private Long numberFilledFactoryTank;
	private String deletedMilkSentToFactoryTank;
	private String confirmated;

	
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Index(name = "counterWorkingDayMilkfilledIndex")
	@ManyToOne(fetch = FetchType.EAGER)
	@Cascade({CascadeType.ALL})
	@JoinColumn(name = "idcounterworkingdaymilkfilled", nullable = false, unique = false)
	public CounterWorkingDayForMilkFilled getCounterWorkingDayForMilkFilled() {
		return CounterWorkingDayForMilkFilled;
	}
	public void setCounterWorkingDayForMilkFilled(
			CounterWorkingDayForMilkFilled counterWorkingDayForMilkFilled) {
		CounterWorkingDayForMilkFilled = counterWorkingDayForMilkFilled;
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
	
	@Index(name = "factorytankIndex")
	@ManyToOne(fetch = FetchType.EAGER)
	@Cascade({CascadeType.ALL})
	@JoinColumn(name = "idFactoryTank", nullable = false, unique = false)
	public FactoryTank getFactoryTank() {
		return factoryTank;
	}
	public void setFactoryTank(FactoryTank factoryTank) {
		this.factoryTank = factoryTank;
	}
	
	@Column
	public Long getLiters() {
		return liters;
	}
	public void setLiters(Long liters) {
		this.liters = liters;
	}
	
	@Column
	@Type(type="timestamp")
	public Date getDateTimeMilkSentToFactoryTank() {
		return dateTimeMilkSentToFactoryTank;
	}
	public void setDateTimeMilkSentToFactoryTank(Date dateTimeMilkSentToFactoryTank) {
		this.dateTimeMilkSentToFactoryTank = dateTimeMilkSentToFactoryTank;
	}
	
	@Column
	@Type(type="timestamp")
	public Date getDateTimeMilkFilled() {
		return dateTimeMilkFilled;
	}
	public void setDateTimeMilkFilled(Date dateTimeMilkFilled) {
		this.dateTimeMilkFilled = dateTimeMilkFilled;
	}
	
	@Column
	public Long getNumberFilledFactoryTank() {
		return numberFilledFactoryTank;
	}
	public void setNumberFilledFactoryTank(Long numberFilledFactoryTank) {
		this.numberFilledFactoryTank = numberFilledFactoryTank;
	}
	
	@Column
	public String getDeletedMilkSentToFactoryTank() {
		return deletedMilkSentToFactoryTank;
	}
	public void setDeletedMilkSentToFactoryTank(
			String deletedMilkSentToFactoryTank) {
		this.deletedMilkSentToFactoryTank = deletedMilkSentToFactoryTank;
	}
	
	@Column
	public String getConfirmated() {
		return confirmated;
	}
	public void setConfirmated(String confirmated) {
		this.confirmated = confirmated;
	}
	
}