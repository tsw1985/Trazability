package com.gabriel.trazability.DAO;

import java.util.List;

import com.gabriel.trazability.model.MilkFilled;

public interface MilkFilledDAO {
	
	public MilkFilled get(Long id);
	
	public void update(Long id, MilkFilled miklFilledUpdated);
	
	public void delete(Long id);
	
	public void create(MilkFilled milkFilled);
	
	public List<MilkFilled> getTotalMilkFilledFromANumberWorkDayMilkFilled(Long number);
	
	public Long getTotalSumLitersMilkFilledByNumberWorkDayMilkFilled(Long number);
	
	public List<MilkFilled> getDataMilkFilledOpen();
	
}
