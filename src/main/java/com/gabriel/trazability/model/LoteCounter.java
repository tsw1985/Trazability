package com.gabriel.trazability.model;
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

@Entity
@Table
public class LoteCounter {

	private Long id;
	private String number;
	private Long litersConsumed;
	private String finished;
	private String ingredientsSelected;
	private Long actualPasteurizatorAsigned;
	private String temperatureCuajo;
	private String timeCuajed;
	private Long kgCheese;
	private ChlorinePhSalmuera chlorinePhSalmuera;
	private ChlorinePhStreetWater chlorinePhStreetWater;
	
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	
	@Column
	public Long getLitersConsumed() {
		return litersConsumed;
	}
	public void setLitersConsumed(Long litersConsumed) {
		this.litersConsumed = litersConsumed;
	}
	
	@Column
	public String getFinished() {
		return finished;
	}
	public void setFinished(String finished) {
		this.finished = finished;
	}
	
	@Column
	public String getIngredientsSelected() {
		return ingredientsSelected;
	}
	public void setIngredientsSelected(String ingredientsSelected) {
		this.ingredientsSelected = ingredientsSelected;
	}
	
	@Column(name = "idPasteurizator", nullable = true)
	public Long getActualPasteurizatorAsigned() {
		return actualPasteurizatorAsigned;
	}
	public void setActualPasteurizatorAsigned(Long actualPasteurizatorAsigned) {
		this.actualPasteurizatorAsigned = actualPasteurizatorAsigned;
	}
	
	@Column
	public String getTemperatureCuajo() {
		return temperatureCuajo;
	}
	public void setTemperatureCuajo(String temperatureCuajo) {
		this.temperatureCuajo = temperatureCuajo;
	}
	
	@Column
	public String getTimeCuajed() {
		return timeCuajed;
	}
	public void setTimeCuajed(String timeCuajed) {
		this.timeCuajed = timeCuajed;
	}
	
	@Column
	public Long getKgCheese() {
		return kgCheese;
	}
	public void setKgCheese(Long kgCheese) {
		this.kgCheese = kgCheese;
	}
	
	@Index(name = "ChlorinePhSalmueraIndex")
	@ManyToOne(fetch = FetchType.EAGER)
	@Cascade({CascadeType.ALL})
	@JoinColumn(name = "idChlorinePhSalmuera", nullable = true, unique = false)
	public ChlorinePhSalmuera getChlorinePhSalmuera() {
		return chlorinePhSalmuera;
	}
	public void setChlorinePhSalmuera(ChlorinePhSalmuera chlorinePhSalmuera) {
		this.chlorinePhSalmuera = chlorinePhSalmuera;
	}
	
	@Index(name = "ChlorinePhStreetIndex")
	@ManyToOne(fetch = FetchType.EAGER)
	@Cascade({CascadeType.ALL})
	@JoinColumn(name = "idChlorinePhStreet", nullable = true, unique = false)
	public ChlorinePhStreetWater getChlorinePhStreetWater() {
		return chlorinePhStreetWater;
	}
	public void setChlorinePhStreetWater(ChlorinePhStreetWater chlorinePhStreetWater) {
		this.chlorinePhStreetWater = chlorinePhStreetWater;
	}
	
	
	
}