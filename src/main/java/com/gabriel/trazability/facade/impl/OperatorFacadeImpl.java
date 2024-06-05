package com.gabriel.trazability.facade.impl;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gabriel.trazability.DAO.OperatorDAO;
import com.gabriel.trazability.command.OperatorCommand;
import com.gabriel.trazability.facade.OperatorFacade;
import com.gabriel.trazability.model.Operator;

@Service
@Transactional
public class OperatorFacadeImpl implements OperatorFacade {

	@Autowired
	private OperatorDAO operatorDAO;
	
	@Override
	public void create(OperatorCommand operatorCommand) {
		operatorDAO.create(commandToOperator(operatorCommand));
	}
	
	
	private Operator commandToOperator(OperatorCommand operatorCommand)
	{
		Operator operator = new Operator();
		operator.setNameOperator(operatorCommand.getName());
		operator.setAddress(operatorCommand.getAddress());
		operator.setCif(operatorCommand.getCif());
		operator.setPhone(operatorCommand.getPhone());
		operator.setDeletedOperator("0");
		return operator;
	}

	@Override
	public void update(OperatorCommand operatorCommand) {
		
		Operator operator = new Operator();
		operator.setNameOperator(operatorCommand.getName());
		operator.setAddress(operatorCommand.getAddress());
		operator.setCif(operatorCommand.getCif());
		operator.setPhone(operatorCommand.getPhone());
		operatorDAO.update(operatorCommand.getId(), operator);
	}

	@Override
	public Operator getOperator(Long id) {
		return operatorDAO.get(id);
	}

	@Override
	public List<Operator> getAllOperator() {
		return operatorDAO.getAllOperator();
	}

	@Override
	public void delete(Long id) {
		operatorDAO.delete(id);
	}
}
