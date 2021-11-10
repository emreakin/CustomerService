package com.tesodev.customer.model;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerIdListRequestModel implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<UUID> idList;
}
