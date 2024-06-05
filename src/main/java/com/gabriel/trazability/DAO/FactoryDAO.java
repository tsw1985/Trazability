package com.gabriel.trazability.DAO;

import com.gabriel.trazability.model.Factory;

public interface FactoryDAO {

	public void create(Factory factory);
	
	public Factory getFactoryById(Long id);
}
