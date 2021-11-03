package com.tesodev.customer.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.tesodev.customer.dto.CustomerDTO;
import com.tesodev.customer.entity.Customer;

@Mapper(componentModel = "spring", uses = { AddressMapper.class })
public interface CustomerMapper extends EntityMapper<CustomerDTO, Customer> {
	
	@Mapping(target = "address", source = "address", qualifiedByName = "id")
	CustomerDTO toDto(Customer s);
}
