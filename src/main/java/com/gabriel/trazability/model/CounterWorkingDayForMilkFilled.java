package com.gabriel.trazability.model;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class CounterWorkingDayForMilkFilled {

	//Esta clase incrementara el contador cada vez
	//que el camionero empiece la jordada para ir a 
	//recoger leche.
	
	
	private Long id;
	private String number;
	
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
	
	
	
	
}
