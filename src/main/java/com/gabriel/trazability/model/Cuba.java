package com.gabriel.trazability.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Cuba {

	private Long id;
	private Long capacity;
	private Long actualCapacity;
	private String description;
	private String clean;
	private String deletedCuba;
	private Long idLoteCounter;
	private String bloqued;

	
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Column
	public String getClean() {
		return clean;
	}
	public void setClean(String clean) {
		this.clean = clean;
	}
	
	@Column
	public String getDeletedCuba() {
		return deletedCuba;
	}
	public void setDeletedCuba(String deletedCuba) {
		this.deletedCuba = deletedCuba;
	}
	
	@Column(nullable=true)
	public Long getIdLoteCounter() {
		return idLoteCounter;
	}
	public void setIdLoteCounter(Long idLoteCounter) {
		this.idLoteCounter = idLoteCounter;
	}
	
	@Column(nullable=true)
	public String getBloqued() {
		return bloqued;
	}
	public void setBloqued(String bloqued) {
		this.bloqued = bloqued;
	}
}