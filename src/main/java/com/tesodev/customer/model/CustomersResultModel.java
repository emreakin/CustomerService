package com.tesodev.customer.model;

import java.io.Serializable;
import java.util.List;

import com.tesodev.customer.dto.CustomerDTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomersResultModel implements Serializable {

	private static final long serialVersionUID = 5222347255293885347L;

	private List<CustomerDTO> customers;
}
