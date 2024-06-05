package com.gabriel.trazability.facade.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gabriel.trazability.facade.InformationFacade;

@Service
@Transactional
public class InformationFacadeImpl implements InformationFacade {

	@Override
	public String getProvidersFromLote(Long loteNumber) {
		// TODO Auto-generated method stub
		return null;
	}

}
