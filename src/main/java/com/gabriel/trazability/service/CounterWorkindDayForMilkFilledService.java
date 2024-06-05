package com.gabriel.trazability.service;

public interface CounterWorkindDayForMilkFilledService {

	public void increment();
	
	public String getLastCounter();
	
	public void setLikeClose(Long id);
	
	public void setLikeOpen(Long id);

	
}
