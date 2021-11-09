package com.tesodev.customer.entity;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "t_address")
@Where(clause = "closed=false")
public class Address implements Serializable {

	private static final long serialVersionUID = 1441155949841729665L;

	@Id
	@GeneratedValue(generator = "UUID")
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id")
	private UUID id;
	
	@Column(name = "address_line")
    private String addressLine;
	
	@Column(name = "city")
    private String city;
	
	@Column(name = "country")
    private String country;
	
	@Column(name = "city_code")
    private int cityCode;
	
	@Column(name = "closed")
    private boolean closed;
}
