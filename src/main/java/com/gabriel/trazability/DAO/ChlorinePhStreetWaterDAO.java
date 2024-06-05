package com.gabriel.trazability.DAO;

import com.gabriel.trazability.model.ChlorinePhStreetWater;

public interface ChlorinePhStreetWaterDAO {
	
	public ChlorinePhStreetWater getLastChlorinePh();
	
	public void setPh(ChlorinePhStreetWater value);

	void create(ChlorinePhStreetWater value);

}
