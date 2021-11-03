package com.tesodev.customer.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tesodev.customer.entity.Address;

/**
 * Spring Data SQL repository for the Address entity.
 */
@Repository
public interface AddressRepository extends JpaRepository<Address, UUID> {
	
}
