package com.tesodev.customer.service;

import java.util.UUID;

import com.tesodev.customer.dto.AddressDTO;

public interface IAddressService {

	UUID create(AddressDTO addressDTO);
	
	boolean delete(UUID addressId);
	
	boolean validate(UUID addressId);
}
