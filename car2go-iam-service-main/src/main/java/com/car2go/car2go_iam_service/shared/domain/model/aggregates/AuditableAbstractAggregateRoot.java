package com.car2go.car2go_iam_service.shared.domain.model.aggregates;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.AbstractAggregateRoot;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

/**
 * Clase base abstracta para entidades agregadas que necesitan auditoría (fecha de creación y modificación).
 * Extiende de AbstractAggregateRoot para habilitar eventos de dominio en DDD (Domain-Driven Design).
 */
@EntityListeners(AuditingEntityListener.class) // Habilita el auditor automático para createdAt y updatedAt
@MappedSuperclass // Permite que otras entidades hereden sus propiedades mapeadas en la base de datos
public class AuditableAbstractAggregateRoot<T extends AbstractAggregateRoot<T>> extends AbstractAggregateRoot<T> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Genera un ID único automáticamente
    @Column(name = "id")
    @Getter
    private Long id;
    
    public Long getId() {
        return id;
    }

    @Getter
    @CreatedDate // Se establece automáticamente cuando se crea la entidad
    @Column(nullable = false, updatable = false) // No puede ser null ni modificarse luego
    private Date createdAt;

    @Getter
    @LastModifiedDate // Se actualiza automáticamente cada vez que la entidad se modifica
    @Column(nullable = false)
    private Date updatedAt;


}
