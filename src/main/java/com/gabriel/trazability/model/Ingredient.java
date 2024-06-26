package com.gabriel.trazability.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table
public class Ingredient {
	
	private Long id;
	private String deletedIngredient;
	private String description;
	
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
	public String getDeletedIngredient() {
		return deletedIngredient;
	}
	public void setDeletedIngredient(String deletedIngredient) {
		this.deletedIngredient = deletedIngredient;
	}
}