package com.gabriel.trazability.model;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table
public class FactoryTank {
	
	private Long id;
	private String description;
	private Long capacity;
	private Long actualCapacity;
	private Long idFilled;
	private Long idPickUp;
	private String clean;
	private Long temperature;
	private String deletedFactoryTank;
	private String selectedActive;
	private String bloqued;
	private Long actualLoteCounterForWork;
	
	
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Column
	public Long getCapacity() {
		return capacity;
	}
	public void setCapacity(Long capacity) {
		this.capacity = capacity;
	}
	
	@Column
	public Long getActualCapacity() {
		return actualCapacity;
	}
	public void setActualCapacity(Long actualCapacity) {
		this.actualCapacity = actualCapacity;
	}
	
	@Column
	public Long getIdFilled() {
		return idFilled;
	}
	public void setIdFilled(Long idFilled) {
		this.idFilled = idFilled;
	}
	
	@Column
	public Long getIdPickUp() {
		return idPickUp;
	}
	public void setIdPickUp(Long idPickUp) {
		this.idPickUp = idPickUp;
	}
	
	@Column
	public String getClean() {
		return clean;
	}
	public void setClean(String clean) {
		this.clean = clean;
	}
	
	@Column
	public Long getTemperature() {
		return temperature;
	}
	public void setTemperature(Long temperature) {
		this.temperature = temperature;
	}
	
	@Column
	public String getDeletedFactoryTank() {
		return deletedFactoryTank;
	}
	public void setDeletedFactoryTank(String deletedFactoryTank) {
		this.deletedFactoryTank = deletedFactoryTank;
	}
	
	@Column
	public String getSelectedActive() {
		return selectedActive;
	}
	public void setSelectedActive(String selectedActive) {
		this.selectedActive = selectedActive;
	}
	
	@Column
	public String getBloqued() {
		return bloqued;
	}
	public void setBloqued(String bloqued) {
		this.bloqued = bloqued;
	}
	
	@Column
	public Long getActualLoteCounterForWork() {
		return actualLoteCounterForWork;
	}
	public void setActualLoteCounterForWork(Long actualLoteCounterForWork) {
		this.actualLoteCounterForWork = actualLoteCounterForWork;
	}

}