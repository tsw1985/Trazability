package com.gabriel.trazability.facade.impl;

import java.lang.reflect.Type;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gabriel.trazability.DAO.IngredientDetailDAO;
import com.gabriel.trazability.DAO.IngredientDetailUsedInLoteDAO;
import com.gabriel.trazability.DAO.LoteCounterServiceDAO;
import com.gabriel.trazability.DAO.PasteurizatorDAO;
import com.gabriel.trazability.command.IngredientUsedInLoteCommand;
import com.gabriel.trazability.facade.IngredientDetailUsedInLoteFacade;
import com.gabriel.trazability.model.IngredientDetailUsedInLote;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Service
@Transactional
public class IngredientDetailUsedInLoteFacadeImpl implements IngredientDetailUsedInLoteFacade {
	
	@Autowired
	private IngredientDetailUsedInLoteDAO ingredientDetailUsedInLoteDAO; 

	@Autowired
	private IngredientDetailDAO ingredientDetailDAO; 
	
	@Autowired
	private LoteCounterServiceDAO loteCounterServiceDAO;
	
	@Autowired
	private PasteurizatorDAO pasteurizatorDAO;
	
	@Override
	public void saveOrderIngredientDetailByJson(String ingredientDetailCommandOrder , Long idLoteCounter) {
		
		String ingredients = "";
		Gson gson = new Gson();
	    Type typeIngredientDetailCommand = new TypeToken<List<IngredientUsedInLoteCommand>>(){}.getType();
	    List<IngredientUsedInLoteCommand> listOrderFromJson = gson.fromJson(ingredientDetailCommandOrder, typeIngredientDetailCommand);
	    String finalIngredient = "";
	    
	    for(IngredientUsedInLoteCommand elementListOrder : listOrderFromJson ){
	    	ingredients = ingredients  +  elementListOrder.getIdIngredientDetail().toString() + "-";
	    }

	    if( ingredients.length() > 0 ){
	    	finalIngredient = ingredients.substring(0, ingredients.length() - 1);
	    	loteCounterServiceDAO.update(finalIngredient, idLoteCounter);
	    }
	}
	
	
	private IngredientDetailUsedInLote commandToIngredientDetail(IngredientUsedInLoteCommand ingredientDetailCommand)
	{
		IngredientDetailUsedInLote ingredientDetailUsedInLote = new IngredientDetailUsedInLote();
		ingredientDetailUsedInLote.setIngredientDetail(ingredientDetailDAO.get(ingredientDetailCommand.getIdIngredientDetail()));
		ingredientDetailUsedInLote.setLoteCounter(loteCounterServiceDAO.get(ingredientDetailCommand.getLote()));
		
		return ingredientDetailUsedInLote;
	}
}