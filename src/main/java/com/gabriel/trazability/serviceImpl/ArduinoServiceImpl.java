package com.gabriel.trazability.serviceImpl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gabriel.trazability.facade.PasteurizatorFacade;
import com.gabriel.trazability.service.ArduinoService;

@Service
public class ArduinoServiceImpl implements ArduinoService {

	@Autowired
	private PasteurizatorFacade pasteurizatorFacade;
	
	@Override
	public String turnOnPasteurizator(String parameter) {
			return getResponsePasteurizator(parameter);
	}

	@Override
	public String turnOffPasteurizator(String parameter) {
		return getResponsePasteurizator(parameter);
	}

	@Override
	public String getRunningStatePasteurizator(String parameter) {
		return getResponsePasteurizator(parameter);
	}

	@Override
	public String getTemperaturePasteurizator(String parameter) {
		return getResponsePasteurizator(parameter);
	}
	
	@Override
	public String isPasteurizing(String parameter) {
		return getResponsePasteurizator(parameter);
	}
	
	
	
	private String getResponsePasteurizator(String parameter)
	{
	      try{
	 		  
	    	 URL url;
		     HttpURLConnection conn;
		     BufferedReader rd;
		     String line;
		     String result = "";

	    	 url = new URL("http://192.168.1.45:80?"+parameter);
	         conn = (HttpURLConnection) url.openConnection();
	         conn.setRequestMethod("GET");
	         rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	         
	         while ((line = rd.readLine()) != null) {
	            result += line;
	         }
	         rd.close();
	         return result;
	      } 
	      catch(Exception e)
	      {
	    	  return "Exception ->" + e.toString();
	      }
	}

	@Override
	public void saveActualTemperature(Long id) {
			pasteurizatorFacade.saveActualTemperature(id);
		
	}
}