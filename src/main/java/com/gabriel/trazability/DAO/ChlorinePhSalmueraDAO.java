package com.gabriel.trazability.DAO;

import com.gabriel.trazability.model.ChlorinePhSalmuera;

public interface ChlorinePhSalmueraDAO {
	
	public void create(ChlorinePhSalmuera chlorinePhSalmuera);
	
	public ChlorinePhSalmuera getLastSalmueraChlorinePh();

}
