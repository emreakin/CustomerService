package com.tesodev.customer.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.google.gson.Gson;
import com.tesodev.customer.dto.AddressDTO;
import com.tesodev.customer.dto.CustomerDTO;
import com.tesodev.customer.rest.CustomerController;
import com.tesodev.customer.service.impl.CustomerService;

@RunWith(MockitoJUnitRunner.Silent.class)
@WebMvcTest(CustomerController.class)
@AutoConfigureMockMvc
public class CustomerControllerTest {

	@Mock
    private CustomerService customerService;
	
	@InjectMocks
    private CustomerController customerController;
	
    private MockMvc mockMvc;
	private final Gson gson = new Gson();
	private final UUID GENERATED_CUSTOMER_ID = UUID.fromString("bce7399d-57f6-4e44-8bd6-001d44518db2");
	
	@Before
    public void setup() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }
	
	@Test
    public void createCustomerAndReturnIdWhenPostMethod() throws Exception {
		CustomerDTO customer = createDummyCustomerDTO(null);
		
		when(customerService.create(customer)).thenReturn(GENERATED_CUSTOMER_ID);

        mockMvc.perform(post("/api/customer/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(customer)))
        		.andExpect(status().is(200))
                .andExpect(jsonPath("$.result.id").value(GENERATED_CUSTOMER_ID.toString()));
    }
	
	@Test
    public void updateCustomerAndReturnTrueWhenPutMethod() throws Exception {
		CustomerDTO customer = createDummyCustomerDTO(GENERATED_CUSTOMER_ID);
		
		when(customerService.update(customer)).thenReturn(true);

        mockMvc.perform(put("/api/customer/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(customer)))
        		.andExpect(status().is(200))
                .andExpect(jsonPath("$.result.status").value(true));
    }
	
	@Test
    public void deleteCustomerAndReturnTrueWhenDeleteMethod() throws Exception {
		
		when(customerService.delete(GENERATED_CUSTOMER_ID)).thenReturn(true);

        mockMvc.perform(delete("/api/customer/delete/" + GENERATED_CUSTOMER_ID)
                .contentType(MediaType.APPLICATION_JSON))
        		.andExpect(status().is(200))
                .andExpect(jsonPath("$.result.status").value(true));
    }
	
	@Test
    public void listCustomersWhenGetMethod() throws Exception {
		List<CustomerDTO> customers = Collections.singletonList(createDummyCustomerDTO(GENERATED_CUSTOMER_ID));
		
		when(customerService.getAll()).thenReturn(customers);

        mockMvc.perform(get("/api/customer/list")
                .contentType(MediaType.APPLICATION_JSON))
        		.andExpect(status().is(200))
                .andExpect(jsonPath("$.result.customers[0].id").value(GENERATED_CUSTOMER_ID.toString()));
    }
	
	@Test
    public void getCustomerByIdWhenGetMethod() throws Exception {
		CustomerDTO customer = createDummyCustomerDTO(GENERATED_CUSTOMER_ID);
		
		when(customerService.get(GENERATED_CUSTOMER_ID)).thenReturn(customer);

        mockMvc.perform(get("/api/customer/get/" + GENERATED_CUSTOMER_ID)
                .contentType(MediaType.APPLICATION_JSON))
        		.andExpect(status().is(200))
                .andExpect(jsonPath("$.result.id").value(GENERATED_CUSTOMER_ID.toString()));
    }
	
	@Test
    public void validateCustomerAndReturnTrueWhenGetMethod() throws Exception {
		
		when(customerService.validate(GENERATED_CUSTOMER_ID)).thenReturn(true);

        mockMvc.perform(get("/api/customer/validate/" + GENERATED_CUSTOMER_ID)
                .contentType(MediaType.APPLICATION_JSON))
        		.andExpect(status().is(200))
                .andExpect(jsonPath("$.result.status").value(true));
    }
	
	private CustomerDTO createDummyCustomerDTO(UUID uuid) {
		AddressDTO addressDTO = new AddressDTO(null, "Test Address", "Antalya", "Turkey", 7);
		return new CustomerDTO(uuid, "Emre Akın", "emre@test.com", addressDTO);
	}
}
