package com.tesodev.customer.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.tesodev.customer.dto.AddressDTO;
import com.tesodev.customer.dto.CustomerDTO;
import com.tesodev.customer.entity.Address;
import com.tesodev.customer.entity.Customer;
import com.tesodev.customer.exception.ServiceException;
import com.tesodev.customer.mapper.CustomerMapper;
import com.tesodev.customer.repository.CustomerRepository;
import com.tesodev.customer.service.impl.AddressService;
import com.tesodev.customer.service.impl.CustomerService;

@RunWith(MockitoJUnitRunner.Silent.class)
public class CustomerServiceTest {

	@Mock
    private CustomerRepository customerRepository;
	
	@Mock
    private CustomerMapper customerMapper;
	
	@Mock
    private AddressService addressService;
	
	@InjectMocks
    private CustomerService customerService;
	
	private final UUID GENERATED_CUSTOMER_ID = UUID.fromString("bce7399d-57f6-4e44-8bd6-001d44518db2");
	private final UUID GENERATED_ADDRESS_ID = UUID.fromString("3c1b17db-d4bd-46e9-b582-86bfdbdc0723");
	
	@Test
    public void whenCreateCustomerShouldReturnCustomerId() {		
		Customer customer = createDummyCustomer(GENERATED_CUSTOMER_ID);
		
		when(customerMapper.toEntity(ArgumentMatchers.any(CustomerDTO.class))).thenReturn(customer);
		when(customerRepository.save(ArgumentMatchers.any(Customer.class))).thenReturn(customer);
		
		CustomerDTO customerDTO = createDummyCustomerDTO(null);
		UUID customerId = null;
		try {
			customerId = customerService.create(customerDTO);
		} catch (ServiceException e) {
			Assert.fail("Exception " + e);
		}
		
		assertNotNull(customerId);
    }
	
	@Test
    public void whenCreateAdressShouldThrownExceptionIfCustomerIdNotEmpty() {		
		
		CustomerDTO customerDTO = createDummyCustomerDTO(GENERATED_CUSTOMER_ID);
		try {
			customerService.create(customerDTO);
		} catch (ServiceException e) {
			assertEquals(e.getErrorCode(), "E01");
		}

    }
	
	@Test
    public void whenGivenCustomerShouldUpdateCustomerIfFound() {
		Customer customer = createDummyCustomer(GENERATED_CUSTOMER_ID);
        
        CustomerDTO newCustomer = createDummyCustomerDTO(GENERATED_CUSTOMER_ID);
        newCustomer.setName("New name");
        
        when(customerRepository.existsById(customer.getId())).thenReturn(true);
        when(customerMapper.toEntity(ArgumentMatchers.any(CustomerDTO.class))).thenReturn(customer);

        boolean status = false;
		try {
			status = customerService.update(newCustomer);
		} catch (ServiceException e) {
			Assert.fail("Exception " + e);
		}
        
        assertTrue(status);
    }
	
	@Test
    public void whenUpdateAdressShouldThrownExceptionIfCustomerIdEmpty() {		
		
		CustomerDTO customerDTO = createDummyCustomerDTO(null);
		try {
			customerService.update(customerDTO);
		} catch (ServiceException e) {
			assertEquals(e.getErrorCode(), "E02");
		}

    }
	
	@Test
	public void whenShouldDeleteCustomerByGivenCustomerIdIfFound() {
		Customer customer = createDummyCustomer(GENERATED_CUSTOMER_ID);
		customer.getAddress().setId(GENERATED_ADDRESS_ID);
		
		boolean status = false;
		try {
			when(customerRepository.findById(GENERATED_CUSTOMER_ID)).thenReturn(Optional.of(customer));
			when(addressService.delete(GENERATED_ADDRESS_ID)).thenReturn(true);
			
			status = customerService.delete(GENERATED_CUSTOMER_ID);
		} catch (ServiceException e) {
			Assert.fail("Exception " + e);
		}
		
		assertTrue(status);
	}
	
	public void shouldThrowExceptionWhenAdressNotDeleted() {
		Customer customer = createDummyCustomer(GENERATED_CUSTOMER_ID);
		customer.getAddress().setId(GENERATED_ADDRESS_ID);
		
		try {
			when(customerRepository.findById(GENERATED_CUSTOMER_ID)).thenReturn(Optional.of(customer));
			when(addressService.delete(GENERATED_ADDRESS_ID)).thenThrow(new ServiceException("E03", "Address doesn't exist"));
			
			customerService.delete(GENERATED_CUSTOMER_ID);
		} catch (ServiceException e) {
			assertEquals(e.getErrorCode(), "E00");
		}

	}
	
	@Test
    public void shouldReturnAllCustomers() {
		List<CustomerDTO> customerDTOs = Collections.singletonList(createDummyCustomerDTO(null));
		List<Customer> customers = Collections.singletonList(createDummyCustomer(null));
		
		when(customerRepository.findAll()).thenReturn(customers);
		when(customerMapper.toDto(ArgumentMatchers.any(Customer.class))).thenReturn(createDummyCustomerDTO(null));
		
		List<CustomerDTO> returnCustomers = customerService.getAll();
		
		assertEquals(returnCustomers, customerDTOs);
    }
	
	@Test
    public void shouldReturnCustomerById() {
		CustomerDTO customer = createDummyCustomerDTO(GENERATED_CUSTOMER_ID);
		
		when(customerRepository.findById(GENERATED_CUSTOMER_ID)).thenReturn(Optional.of(createDummyCustomer(null)));
		when(customerMapper.toDto(ArgumentMatchers.any(Customer.class))).thenReturn(customer);
		
		CustomerDTO returnCustomer = customerService.get(GENERATED_CUSTOMER_ID);
		
		assertNotNull(returnCustomer);
		assertEquals(returnCustomer, customer);
    }
	
	@Test
	public void whenGivenCustomerIdShouldValidateCustomer() {
		when(customerRepository.existsById(GENERATED_CUSTOMER_ID)).thenReturn(true);
		
		assertTrue(customerService.validate(GENERATED_CUSTOMER_ID));
	}
	
	private CustomerDTO createDummyCustomerDTO(UUID uuid) {
		AddressDTO addressDTO = new AddressDTO(null, "Test Address", "Antalya", "Turkey", 7);
		return new CustomerDTO(uuid, "Emre Akın", "emre@test.com", addressDTO);
	}
	
	private Customer createDummyCustomer(UUID uuid) {
		Address address = new Address(null, "Test Address", "Antalya", "Turkey", 7, false);
		return new Customer(uuid, "Emre Akın", "emre@test.com", address);
	}
}
