package com.tesodev.customer.service;

import java.util.List;
import java.util.UUID;

import com.tesodev.customer.dto.CustomerDTO;
import com.tesodev.customer.exception.ServiceException;

public interface ICustomerService {

	UUID create(CustomerDTO customerDTO) throws ServiceException;
	
	boolean update(CustomerDTO customerDTO) throws ServiceException;
	
	boolean delete(UUID customerId) throws ServiceException;
	
	List<CustomerDTO> getAll();
	
	List<CustomerDTO> getCustomersByIds(List<UUID> customerIds);
	
	CustomerDTO get(UUID customerId);
	
	boolean validate(UUID customerId) throws ServiceException;
}
