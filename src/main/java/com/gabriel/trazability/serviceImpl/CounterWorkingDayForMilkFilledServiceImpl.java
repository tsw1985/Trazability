package com.gabriel.trazability.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gabriel.trazability.DAO.CounterWorkingDayForMilkFilledDAO;
import com.gabriel.trazability.service.CounterWorkindDayForMilkFilledService;

@Service
@Transactional
public class CounterWorkingDayForMilkFilledServiceImpl implements CounterWorkindDayForMilkFilledService {

	@Autowired
	private CounterWorkingDayForMilkFilledDAO counterWorkingDayForMilkFilledDAO; 
	
	@Override
	public void increment() {
		counterWorkingDayForMilkFilledDAO.increment();
	}

	@Override
	public String getLastCounter() {
		return counterWorkingDayForMilkFilledDAO.getLastCounter();
	}

	@Override
	public void setLikeClose(Long id) {
		counterWorkingDayForMilkFilledDAO.setLikeClose(id);
	}

	@Override
	public void setLikeOpen(Long id) {
		counterWorkingDayForMilkFilledDAO.setLikeOpen(id);
	}
}