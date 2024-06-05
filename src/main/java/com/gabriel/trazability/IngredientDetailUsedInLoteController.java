package com.gabriel.trazability;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gabriel.trazability.facade.IngredientDetailUsedInLoteFacade;

@Controller
public class IngredientDetailUsedInLoteController {
	
	@Autowired
	private IngredientDetailUsedInLoteFacade ingredientDetailUsedInLoteFacade;
	

	//El guardado se hace desde ProducctionController
	
	/*@RequestMapping(value="/lote/ingredientusedinloteorder", method = RequestMethod.POST)
	public @ResponseBody void saveIngredientDetailUsedInLoteFacade(String orderIngredient){
		ingredientDetailUsedInLoteFacade.saveOrderIngredientDetailByJson(orderIngredient);
	}*/

}
