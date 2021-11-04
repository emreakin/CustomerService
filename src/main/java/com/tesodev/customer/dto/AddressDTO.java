package com.tesodev.customer.dto;

import java.io.Serializable;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class AddressDTO implements Serializable {

	private static final long serialVersionUID = -5304352763631736509L;

	private UUID id;
	private String addressLine;
    private String city;
    private String country;
    private int cityCode;
    
}
