package com.gabriel.trazability.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Index;

@Entity
@Table
public class WarningMaximumFactoryTank {

	private Long id;
	private Long maximum;
	private Long minimum;
	private FactoryTank factoryTank;
	
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column
	public Long getMaximum() {
		return maximum;
	}
	public void setMaximum(Long maximum) {
		this.maximum = maximum;
	}
	
	@Column
	public Long getMinimum() {
		return minimum;
	}
	public void setMinimum(Long minimum) {
		this.minimum = minimum;
	}
	
	@Index(name = "factoryTankIndex")
	@OneToOne(fetch = FetchType.LAZY, orphanRemoval = true)
	@Cascade({CascadeType.ALL})
	@JoinColumn(name = "idFactoryTank", nullable = false, unique = true)
	public FactoryTank getFactoryTank() {
		return factoryTank;
	}
	public void setFactoryTank(FactoryTank factoryTank) {
		this.factoryTank = factoryTank;
	}
	
	
}
