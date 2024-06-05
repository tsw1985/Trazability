package com.gabriel.trazability.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table
public class ChlorinePhSalmuera {

	private Long id;
	private Double ph;
	private Double chlorine;
	private Date dateReaded;
	
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column
	public Double getPh() {
		return ph;
	}
	public void setPh(Double ph) {
		this.ph = ph;
	}
	
	@Column
	public Double getChlorine() {
		return chlorine;
	}
	public void setChlorine(Double chlorine) {
		this.chlorine = chlorine;
	}
	
	@Column
	@Type(type="timestamp")
	public Date getDateReaded() {
		return dateReaded;
	}
	public void setDateReaded(Date dateReaded) {
		this.dateReaded = dateReaded;
	}
}
