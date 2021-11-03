package com.tesodev.customer.service.impl;

import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.tesodev.customer.dto.AddressDTO;
import com.tesodev.customer.entity.Address;
import com.tesodev.customer.mapper.AddressMapper;
import com.tesodev.customer.repository.AddressRepository;
import com.tesodev.customer.service.IAddressService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AddressService implements IAddressService {

	private final Logger log = LoggerFactory.getLogger(AddressService.class);

	private final AddressRepository addressRepository;
	private final AddressMapper addressMapper;
	
	@Override
	public UUID create(AddressDTO addressDTO) {
		log.debug("Request to save Address : {}", addressDTO);
		Address address = addressMapper.toEntity(addressDTO);
		address = addressRepository.save(address);
		return address.getId();
	}
	
	@Override
	public boolean delete(UUID addressId) {
		log.debug("Request to delete Address Id : {}", addressId);
		
		if(!addressRepository.existsById(addressId))
			return false;
		
		Optional<Address> address = addressRepository.findById(addressId);
		if(!address.isPresent())
			return false;
		
		address.get().setClosed(true);
		addressRepository.save(address.get());
		
		return true;
	}

	@Override
	public boolean validate(UUID addressId) {
		log.debug("Request to validate Address Id : {}", addressId);
		return addressRepository.existsById(addressId);
	}

}
