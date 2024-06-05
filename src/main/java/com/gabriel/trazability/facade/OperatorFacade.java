package com.gabriel.trazability.facade;

import java.util.List;

import com.gabriel.trazability.command.OperatorCommand;
import com.gabriel.trazability.model.Operator;

public interface OperatorFacade {
	
	public void create(OperatorCommand operatorCommand);
	
	public void update(OperatorCommand operatorCommand);
	
	public void delete(Long id);
	
	public Operator getOperator(Long id);
	
	public List<Operator> getAllOperator();

}
