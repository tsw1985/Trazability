package com.gabriel.trazability.DAO;

import java.util.List;

import com.gabriel.trazability.model.Operator;

public interface OperatorDAO {
	
public void create(Operator operator);
	
	public void update(Long id,Operator operator);
	
	public void delete(Long id);
	
	public Operator get(Long id);
	
	public List<Operator> getAllOperator();

}
