package com.tesodev.customer.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class BaseDTO implements Serializable{

	private static final long serialVersionUID = -8435941702207243260L;

	private LocalDateTime creationDate;
	private LocalDateTime updateDate;

}
