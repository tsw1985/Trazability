package com.gabriel.trazability.facade;

public interface IngredientDetailUsedInLoteFacade {
	
	public void saveOrderIngredientDetailByJson(String ingredientDetailCommandLikeJson, Long idLoteCounter);

}
