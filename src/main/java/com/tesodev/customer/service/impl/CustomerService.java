package com.tesodev.customer.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.tesodev.customer.dto.AddressDTO;
import com.tesodev.customer.dto.CustomerDTO;
import com.tesodev.customer.entity.Customer;
import com.tesodev.customer.exception.ErrorCodes;
import com.tesodev.customer.exception.ServiceException;
import com.tesodev.customer.mapper.CustomerMapper;
import com.tesodev.customer.repository.CustomerRepository;
import com.tesodev.customer.service.IAddressService;
import com.tesodev.customer.service.ICustomerService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CustomerService implements ICustomerService {
	
	private final Logger log = LoggerFactory.getLogger(CustomerService.class);

	private final CustomerRepository customerRepository;
	private final CustomerMapper customerMapper;
	
	private final IAddressService addressService;
	
	@Override
	public UUID create(CustomerDTO customerDTO) throws ServiceException {
		log.debug("Request to save Customer : {}", customerDTO);
		
		if(customerDTO.getId() != null)
			throw new ServiceException(ErrorCodes.ID_SHOULD_EMPTY, "Customer Id should be empty");
		
		AddressDTO address = customerDTO.getAddress();
		if(address.getId() == null || !addressService.validate(address.getId())) {
			address.setId(null);
			UUID newAddressId = addressService.create(address);
			customerDTO.getAddress().setId(newAddressId);
		}
		
		Customer customer = customerMapper.toEntity(customerDTO);
		customer = customerRepository.save(customer);
		return customer.getId();
	}
	
	@Override
	public boolean update(CustomerDTO customerDTO) throws ServiceException {
		log.debug("Request to update Customer : {}", customerDTO);
		
		if(customerDTO.getId() == null)
			throw new ServiceException(ErrorCodes.ID_SHOULD_NOT_EMPTY, "Customer Id shouldn't be empty");
		
		if(!customerRepository.existsById(customerDTO.getId()))
			throw new ServiceException(ErrorCodes.RECORD_DOES_NOT_EXIST, "Customer doesn't exist");
		
		customerRepository.save(customerMapper.toEntity(customerDTO));
		
		return true;
	}
	
	@Override
	public boolean delete(UUID customerId) throws ServiceException {
		log.debug("Request to delete Customer Id : {}", customerId);
		
		Optional<Customer> customer = customerRepository.findById(customerId);
		if(!customer.isPresent())
			throw new ServiceException(ErrorCodes.RECORD_DOES_NOT_EXIST, "Customer doesn't exist");
		
		try {
			addressService.delete(customer.get().getAddress().getId());
		} catch (ServiceException e) {
			throw new ServiceException(ErrorCodes.GENERIC_ERROR, "Address didn't delete beacuse of : " + e.getErrorMessage());
		}
		
		customer.get().setClosed(true);
		customerRepository.save(customer.get());
		
		return true;
	}
	
	@Override
	public List<CustomerDTO> getAll() {
		log.debug("Request to get all Customers");
		return customerRepository.findAll().stream().map(customerMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
	}
	
	@Override
	public List<CustomerDTO> getCustomersByIds(List<UUID> customerIds) {
		log.debug("Request to get Customers by id list");
		return customerRepository.findByIdIn(customerIds).stream().map(customerMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
	}
	
	@Override
	public CustomerDTO get(UUID customerId) {
		log.debug("Request to get Customer Id : {}", customerId);
		Optional<CustomerDTO> customerDTO = customerRepository.findById(customerId).map(customerMapper::toDto);
		
		if(customerDTO.isPresent())
			return customerDTO.get();
		
		return null;
	}
	
	@Override
	public boolean validate(UUID customerId) {
		log.debug("Request to validate Customer Id : {}", customerId);
		return customerRepository.existsById(customerId);
	}

}
