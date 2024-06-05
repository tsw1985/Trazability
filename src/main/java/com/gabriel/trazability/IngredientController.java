package com.gabriel.trazability;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gabriel.trazability.command.IngredientCommand;
import com.gabriel.trazability.command.IngredientDetailCommand;
import com.gabriel.trazability.facade.IngredientFacade;
import com.gabriel.trazability.model.Ingredient;
import com.gabriel.trazability.model.IngredientDetail;

@Controller
public class IngredientController {
	
	@Autowired
	private IngredientFacade ingredientFacade;
	
	
	@RequestMapping(value = "/ingredient/loadpageingredient", method = RequestMethod.GET)
	public String getPage(HttpServletRequest request,	HttpServletResponse response) {
		return "ingredient/ingredient";
	}
	
		
	@RequestMapping(value = "/ingredient/create", method = RequestMethod.POST)
	public void create(@ModelAttribute IngredientCommand ingredientCommand,HttpServletRequest request,	HttpServletResponse response) {
		ingredientFacade.create(ingredientCommand);
	}
	
	
	@RequestMapping(value = "/ingredient/update", method = RequestMethod.POST)
	public void update(@ModelAttribute IngredientDetailCommand ingredientCommand,HttpServletRequest request,	HttpServletResponse response) {

		ingredientFacade.updateIngredientDetail(ingredientCommand);
	}
	
	
	
	@RequestMapping(value = "/ingredient/adddetail", method = RequestMethod.POST)
	public void addDetailIngredient(@ModelAttribute IngredientDetailCommand ingredientdetailCommand,HttpServletRequest request,	HttpServletResponse response) {
		ingredientFacade.addIngredientDetail(ingredientdetailCommand);
	}
	
	
	@RequestMapping(value="/ingredient/delete/{id}",method= RequestMethod.GET)
	public @ResponseBody void delete(@PathVariable Long id){
		ingredientFacade.delete(id);
	}
	
	
	@RequestMapping(value="/ingredient/getallingredient", method = RequestMethod.GET)
	public @ResponseBody List<Ingredient> getListAllIngredient(){
		return ingredientFacade.getAllIngredient();
	}
	
	
	@RequestMapping(value = "/ingredient/getingredient/{id}", method = RequestMethod.GET)
	public @ResponseBody Ingredient getIngredient(@PathVariable Long id) {
		 return ingredientFacade.get(id);
	}
	
	
	@RequestMapping(value = "/ingredient/getingredientdetail/{id}", method = RequestMethod.GET)
	public @ResponseBody IngredientDetail getLastIngredientDetail(@PathVariable Long id) {
		 return ingredientFacade.getLastIngredientDetail(id);
	}
	
	
	@RequestMapping(value = "/ingredient/getallingredientdetailorderbyiddesc", method = RequestMethod.GET)
	public @ResponseBody List<IngredientDetail> getAllIngredientDetailOrderByIdDesc() {
		 return ingredientFacade.getAllIngredientDetailOrderByIdDesc();
	}
	
}