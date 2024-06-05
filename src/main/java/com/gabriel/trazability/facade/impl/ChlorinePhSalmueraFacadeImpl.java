package com.gabriel.trazability.facade.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gabriel.trazability.DAO.ChlorinePhSalmueraDAO;
import com.gabriel.trazability.facade.ChlorinePhSalmueraFacade;
import com.gabriel.trazability.model.ChlorinePhSalmuera;

@Service
@Transactional
public class ChlorinePhSalmueraFacadeImpl implements ChlorinePhSalmueraFacade{
	
	@Autowired
	private ChlorinePhSalmueraDAO chlorinePhSalmueraDAO;

	@Override
	public void create(Double chlorineValue, Double phValue){

		ChlorinePhSalmuera chlorinePhSalmuera = new ChlorinePhSalmuera();
		chlorinePhSalmuera.setChlorine(chlorineValue);
		chlorinePhSalmuera.setPh(phValue);
		chlorinePhSalmuera.setDateReaded(new Date());
		
		chlorinePhSalmueraDAO.create(chlorinePhSalmuera);
	}

	@Override
	public ChlorinePhSalmuera getLastChlorinePhSalmuera() {
		return chlorinePhSalmueraDAO.getLastSalmueraChlorinePh();
	}

}
