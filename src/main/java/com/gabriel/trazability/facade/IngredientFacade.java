package com.gabriel.trazability.facade;

import java.util.List;

import com.gabriel.trazability.command.IngredientCommand;
import com.gabriel.trazability.command.IngredientDetailCommand;
import com.gabriel.trazability.model.Ingredient;
import com.gabriel.trazability.model.IngredientDetail;

public interface IngredientFacade {
	
	public void create(IngredientCommand ingredient);
	
	public void addIngredientDetail(IngredientDetailCommand ingredientDetail);
	
	public void delete(Long id);
	
	public void update(IngredientCommand ingredientCommand, Long id);
	
	public Ingredient get(Long id);
	
	public IngredientDetail getLastIngredientDetail(Long idIngredient);
	
	public List<Ingredient> getAllIngredient();

	public List<IngredientDetail> getAllIngredientDetailOrderByIdDesc();

	void updateIngredientDetail(IngredientDetailCommand ingredientDetailCommand);

}
