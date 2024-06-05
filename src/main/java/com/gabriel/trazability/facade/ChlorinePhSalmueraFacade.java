package com.gabriel.trazability.facade;

import com.gabriel.trazability.model.ChlorinePhSalmuera;

public interface ChlorinePhSalmueraFacade {
	
	public void create(Double chlorineValue, Double phValue);
	
	public ChlorinePhSalmuera getLastChlorinePhSalmuera();

}
