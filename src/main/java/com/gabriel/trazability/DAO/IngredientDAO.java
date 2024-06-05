package com.gabriel.trazability.DAO;

import java.util.List;

import com.gabriel.trazability.model.Ingredient;
import com.gabriel.trazability.model.IngredientDetail;

public interface IngredientDAO {

	public void create(Ingredient ingredient); 
	
	public IngredientDetail getLastIngredientDetail(Long idIngredient);
	
	public void addIngredientDetail(IngredientDetail ingredientDetail);

	public void delete(Long id) ;

	public void update(Ingredient ingredient, Long id); 

	public Ingredient get(Long id) ;

	public List<Ingredient> getAllIngredient();

	public List<IngredientDetail> getAllIngredientDetailOrderByIdDesc();

	void update(IngredientDetail ingredientDetailUpdated, Long id);
	
}
