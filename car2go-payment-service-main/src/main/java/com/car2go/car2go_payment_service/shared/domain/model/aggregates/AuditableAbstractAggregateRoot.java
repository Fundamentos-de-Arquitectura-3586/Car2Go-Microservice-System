package com.car2go.car2go_payment_service.shared.domain.model.aggregates;

import java.util.Date;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.AbstractAggregateRoot;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

/**
 * Clase base abstracta para entidades agregadas que necesitan auditoría (fecha de creación y modificación).
 * Extiende de AbstractAggregateRoot para habilitar eventos de dominio en DDD (Domain-Driven Design).
 */
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class) // Habilita el auditor automático para createdAt y updatedAt
@MappedSuperclass // Permite que otras entidades hereden sus propiedades mapeadas en la base de datos
public class AuditableAbstractAggregateRoot<T extends AbstractAggregateRoot<T>> extends AbstractAggregateRoot<T> {

    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Genera un ID único automáticamente
    private Long id;

    @Getter
    @CreatedDate // Se establece automáticamente cuando se crea la entidad
    @Column(nullable = false, updatable = false) // No puede ser null ni modificarse luego
    private Date createdAt;

    @Getter
    @LastModifiedDate // Se actualiza automáticamente cada vez que la entidad se modifica
    @Column(nullable = false)
    private Date updatedAt;
}
