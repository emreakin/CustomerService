package com.tesodev.customer.service;

import java.util.UUID;

import com.tesodev.customer.dto.AddressDTO;
import com.tesodev.customer.exception.ServiceException;

public interface IAddressService {

	UUID create(AddressDTO addressDTO) throws ServiceException;
	
	boolean delete(UUID addressId) throws ServiceException;
	
	boolean validate(UUID addressId);
}
