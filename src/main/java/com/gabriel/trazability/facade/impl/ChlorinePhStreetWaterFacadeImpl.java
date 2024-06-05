package com.gabriel.trazability.facade.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gabriel.trazability.DAO.ChlorinePhStreetWaterDAO;
import com.gabriel.trazability.facade.ChlorinePhStreetWaterFacade;
import com.gabriel.trazability.model.ChlorinePhStreetWater;

@Service
@Transactional
public class ChlorinePhStreetWaterFacadeImpl implements	ChlorinePhStreetWaterFacade {
	
	@Autowired
	private ChlorinePhStreetWaterDAO chlorinePhStreetWaterDAO;

	@Override
	public void create(Double chlorineValue, Double phValue) {
		
		ChlorinePhStreetWater chlorinePhStreetWater = new ChlorinePhStreetWater();
		chlorinePhStreetWater.setChlorine(chlorineValue);
		chlorinePhStreetWater.setPh(phValue);
		chlorinePhStreetWater.setDateReaded(new Date());
		chlorinePhStreetWaterDAO.create(chlorinePhStreetWater);
	}

	@Override
	public ChlorinePhStreetWater getLastChlorinePh() {
		return chlorinePhStreetWaterDAO.getLastChlorinePh();
	}
}