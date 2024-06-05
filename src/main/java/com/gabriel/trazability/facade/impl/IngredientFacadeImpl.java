package com.gabriel.trazability.facade.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gabriel.trazability.DAO.IngredientDAO;
import com.gabriel.trazability.command.IngredientCommand;
import com.gabriel.trazability.command.IngredientDetailCommand;
import com.gabriel.trazability.facade.IngredientFacade;
import com.gabriel.trazability.model.Ingredient;
import com.gabriel.trazability.model.IngredientDetail;
import com.gabriel.trazability.service.DatesService;

@Service
@Transactional
public class IngredientFacadeImpl implements IngredientFacade {

	@Autowired
	private IngredientDAO ingredientDAO;
	
	@Autowired
	private DatesService datesService;
	
	@Override
	public void create(IngredientCommand ingredientCommand) {
		ingredientDAO.create(commandToIngredient(ingredientCommand));
	}
	
	private Ingredient commandToIngredient(IngredientCommand ingredientCommand)
	{
		Ingredient ingredient = new Ingredient();
		ingredient.setDeletedIngredient(ingredientCommand.getDeletedIngredient());
		ingredient.setDescription(ingredientCommand.getDescription());
		ingredient.setDeletedIngredient("0");
		return ingredient;
	}
	

	@Override
	public void delete(Long id) {
		
		// Hay que comprobar si ese ingrediente se esta usando en algun lote, 
		// en caso de que se este usando , no dejar eliminar y enviar una excepcion.
		
		
		ingredientDAO.delete(id);
	}

	@Override
	public void update(IngredientCommand ingredientCommand, Long id) {
			Ingredient ingredient = new Ingredient();
			ingredient.setDescription(ingredientCommand.getDescription());
			
			ingredientDAO.update(ingredient, id);
	}

	@Override
	public Ingredient get(Long id) {
		return ingredientDAO.get(id);

	}

	@Override
	public List<Ingredient> getAllIngredient() {
		return ingredientDAO.getAllIngredient();
	}

	@Override
	public void addIngredientDetail(IngredientDetailCommand ingredientDetailCommand) {
		
		IngredientDetail ingredientDetail  = new IngredientDetail();
		ingredientDetail.setBoughtDate(datesService.getDateFromString(ingredientDetailCommand.getBoughtDate()));
		ingredientDetail.setExpirationDate(datesService.getDateFromString(ingredientDetailCommand.getExpirationDate()));
		ingredientDetail.setLote(Long.valueOf(ingredientDetailCommand.getLoteNumber()));
		ingredientDetail.setNote(ingredientDetailCommand.getNote());
		ingredientDetail.setConsumed("NOCONSUMED");
		
		ingredientDAO.addIngredientDetail(ingredientDetail);
	}
	
	
	
	@Override
	public void updateIngredientDetail(IngredientDetailCommand ingredientDetailCommand) {
		
		IngredientDetail ingredientDetail  = new IngredientDetail();
		ingredientDetail.setBoughtDate(datesService.getDateFromString(ingredientDetailCommand.getBoughtDate()));
		ingredientDetail.setExpirationDate(datesService.getDateFromString(ingredientDetailCommand.getExpirationDate()));
		ingredientDetail.setLote(Long.valueOf(ingredientDetailCommand.getLoteNumber()));
		ingredientDetail.setNote(ingredientDetailCommand.getNote());
		
		ingredientDAO.update(ingredientDetail, ingredientDetailCommand.getId());
	}
	
	
	

	@Override
	public IngredientDetail getLastIngredientDetail(Long idIngredient) {
		return ingredientDAO.getLastIngredientDetail(idIngredient);
	}
	
	@Override
	public List<IngredientDetail> getAllIngredientDetailOrderByIdDesc()
	{
		return ingredientDAO.getAllIngredientDetailOrderByIdDesc();
	}
	
	
}
