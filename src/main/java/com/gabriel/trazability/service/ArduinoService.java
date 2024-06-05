package com.gabriel.trazability.service;

public interface ArduinoService {
	
	public String turnOnPasteurizator(String parameter);
	
	public String turnOffPasteurizator(String parameter);
	
	public String getRunningStatePasteurizator(String parameter);
	
	public String getTemperaturePasteurizator(String parameter);
	
	public String isPasteurizing(String parameter);
	
	public void saveActualTemperature(Long id);
	

}
