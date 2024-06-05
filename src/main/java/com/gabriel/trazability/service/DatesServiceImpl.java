package com.gabriel.trazability.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component("datesService")
public class DatesServiceImpl implements DatesService {

	@Override
	public Date getDateFromString(String dateFormated) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String strDate = dateFormated;
		Date date = null;
		try 
		{
			date = dateFormat.parse(strDate);
			return date;
		} 
		catch (ParseException ex) 
		{
			ex.printStackTrace();
			return null;
		}
		
	}

	@Override
	public String getDateFromMiliseconds(Long dateInMiliseconds) {
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
	    Date date = new Date(dateInMiliseconds);
	    String strDate = sdfDate.format(date);
	    return strDate;
	}
}