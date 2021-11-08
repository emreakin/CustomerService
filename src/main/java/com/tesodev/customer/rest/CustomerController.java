package com.tesodev.customer.rest;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tesodev.customer.dto.CustomerDTO;
import com.tesodev.customer.exception.ServiceException;
import com.tesodev.customer.model.BaseResponseModel;
import com.tesodev.customer.model.CustomersResultModel;
import com.tesodev.customer.model.IdResultModel;
import com.tesodev.customer.model.StatusResultModel;
import com.tesodev.customer.service.impl.CustomerService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/customer")
public class CustomerController {

	private final CustomerService customerService;

	@PostMapping("/create")
	public BaseResponseModel<IdResultModel> createCustomer(@RequestBody CustomerDTO customer) {
		BaseResponseModel<IdResultModel> response = new BaseResponseModel<>();
		
		try {
			UUID customerId = customerService.create(customer);
			response.setResult(new IdResultModel(customerId));
		} catch (ServiceException e) {
			response.setError(e.getErrorMessage());
		}
		
		return response;
	}
	
	@PutMapping("/update")
	public BaseResponseModel<StatusResultModel> updateCustomer(@RequestBody CustomerDTO customer) {
		BaseResponseModel<StatusResultModel> response = new BaseResponseModel<>();
		
		try {
			boolean resultStatus = customerService.update(customer);
			response.setResult(new StatusResultModel(resultStatus));
		} catch (ServiceException e) {
			response.setError(e.getErrorMessage());
		}
		
		return response;
	}
	
	@DeleteMapping("/delete/{id}")
	public BaseResponseModel<StatusResultModel> deleteCustomer(@PathVariable(value = "id", required = true) UUID customerId) {
		BaseResponseModel<StatusResultModel> response = new BaseResponseModel<>();
		
		try {
			boolean resultStatus = customerService.delete(customerId);
			response.setResult(new StatusResultModel(resultStatus));
		} catch (ServiceException e) {
			response.setError(e.getErrorMessage());
		}
		
		return response;
	}
	
	@GetMapping("/list")
    public BaseResponseModel<CustomersResultModel> getAllCustomers() {
		BaseResponseModel<CustomersResultModel> response = new BaseResponseModel<>();
		
		List<CustomerDTO> customers = customerService.getAll();
		response.setResult(new CustomersResultModel(customers));
		
        return response;
    }
	
	@GetMapping("/get/{id}")
	public BaseResponseModel<CustomerDTO> getCustomer(@PathVariable(value = "id", required = true) UUID customerId) {
		BaseResponseModel<CustomerDTO> response = new BaseResponseModel<>();
		
		CustomerDTO customer = customerService.get(customerId);
		response.setResult(customer);
		
        return response;
    }
	
	@GetMapping("/validate/{id}")
	public BaseResponseModel<StatusResultModel> validateCustomer(@PathVariable(value = "id", required = true) UUID customerId) {
		BaseResponseModel<StatusResultModel> response = new BaseResponseModel<>();
		
		boolean resultStatus = customerService.validate(customerId);
		response.setResult(new StatusResultModel(resultStatus));
		
		return response;
	}
}
