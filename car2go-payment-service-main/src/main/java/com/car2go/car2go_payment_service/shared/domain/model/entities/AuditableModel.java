package com.car2go.car2go_payment_service.shared.domain.model.entities;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;


@EntityListeners(AuditingEntityListener.class) // Activa el llenado automático de fechas
@MappedSuperclass // Permite que otras entidades hereden estos campos sin ser una tabla propia
public class AuditableModel {

    @Getter
    @CreatedDate // Se asigna automáticamente cuando se crea la entidad
    @Column(nullable = false, updatable = false) // No puede ser null ni cambiarse luego
    private Date createdAt;

    @Getter
    @LastModifiedDate // Se actualiza automáticamente cada vez que se modifica la entidad
    @Column(nullable = false)
    private Date updatedAt;
}
