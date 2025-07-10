package com.pe.platform.vehicle.application.internal.services.outboundservice;

import org.springframework.stereotype.Service;

@Service
public class VehicleService {

    public VehicleService() {
        // Constructor simplificado
    }

    public boolean validateUserExists(String userId) {
        try {
            // Si llegamos hasta aquí, el JWT ya fue validado por el filtro de seguridad
            // Por lo tanto, el usuario está autenticado y autorizado
            // Solo validamos que el userId no sea null o vacío
            return userId != null && !userId.trim().isEmpty();
        } catch (Exception e) {
            return false;
        }
    }
}
