package com.tesodev.customer.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractAuditingEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@CreatedDate
	@Column(name = "created_at", updatable = false)
	private LocalDateTime creationDate;

	@LastModifiedDate
	@Column(name = "updated_at")
	private LocalDateTime updateDate;

	@Column(name = "closed")
	private boolean closed;

}
