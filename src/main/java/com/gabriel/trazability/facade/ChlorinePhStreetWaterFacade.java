package com.gabriel.trazability.facade;

import com.gabriel.trazability.model.ChlorinePhStreetWater;


public interface ChlorinePhStreetWaterFacade {
	
	public ChlorinePhStreetWater getLastChlorinePh();

	void create(Double chlorineValue, Double phValue);

}
