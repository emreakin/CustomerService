package com.tesodev.customer.entity;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Entity
@Table(name = "customer")
@Where(clause = "closed=false")
public class Customer extends AbstractAuditingEntity implements Serializable {

	private static final long serialVersionUID = -999754348870519116L;

	@Id
	@GeneratedValue(generator = "UUID")
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id")
	private UUID id;
	
	@Column(name = "name")
    private String name;
	
	@Column(name = "email")
    private String email;
	
	@OneToOne
    @JoinColumn(name = "address_id")
    private Address address;

}
