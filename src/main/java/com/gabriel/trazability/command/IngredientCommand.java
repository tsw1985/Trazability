package com.gabriel.trazability.command;

public class IngredientCommand {
	
	private Long id;
	private String deletedIngredient;
	private String description;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDeletedIngredient() {
		return deletedIngredient;
	}
	public void setDeletedIngredient(String deletedIngredient) {
		this.deletedIngredient = deletedIngredient;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

}
