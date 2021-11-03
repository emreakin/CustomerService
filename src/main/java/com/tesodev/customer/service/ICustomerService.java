package com.tesodev.customer.service;

import java.util.List;
import java.util.UUID;

import com.tesodev.customer.dto.CustomerDTO;

public interface ICustomerService {

	UUID create(CustomerDTO customerDTO);
	
	boolean update(CustomerDTO customerDTO);
	
	boolean delete(UUID customerId);
	
	List<CustomerDTO> getAll();
	
	CustomerDTO get(UUID customerId);
	
	boolean validate(UUID customerId);
}
