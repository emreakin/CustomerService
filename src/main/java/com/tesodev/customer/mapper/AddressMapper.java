package com.tesodev.customer.mapper;

import org.mapstruct.*;

import com.tesodev.customer.dto.AddressDTO;
import com.tesodev.customer.entity.Address;

@Mapper(componentModel = "spring", uses = {})
public interface AddressMapper extends EntityMapper<AddressDTO, Address> {
	
	@Named("id")
	AddressDTO toDtoId(Address address);
}
