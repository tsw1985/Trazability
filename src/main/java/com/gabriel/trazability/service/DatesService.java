package com.gabriel.trazability.service;

import java.util.Date;

public interface DatesService {
	
	public Date getDateFromString(String dateFormated);
	
	public String getDateFromMiliseconds(Long dateInMiliseconds);

}
