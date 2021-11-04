package com.tesodev.customer.dto;

import java.io.Serializable;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class CustomerDTO extends BaseDTO implements Serializable {

	private static final long serialVersionUID = -3810556907125061061L;

	private UUID id;
    private String name;
    private String email;
    private AddressDTO address;

}
