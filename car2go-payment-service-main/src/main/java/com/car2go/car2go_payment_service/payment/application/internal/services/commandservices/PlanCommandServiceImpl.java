package com.car2go.car2go_payment_service.payment.application.internal.services.commandservices;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.car2go.car2go_payment_service.payment.domain.model.commands.CreatePlanCommand;
import com.car2go.car2go_payment_service.payment.domain.model.commands.UpdatePlanCommand;
import com.car2go.car2go_payment_service.payment.domain.model.entities.Plan;
import com.car2go.car2go_payment_service.payment.domain.services.PlanCommandService;
import com.car2go.car2go_payment_service.payment.infrastructure.persistence.jpa.PlanRepository;

@Service
public class PlanCommandServiceImpl implements PlanCommandService {

    private final PlanRepository planRepository;
    
    // Mismo secret que usa el filtro JWT
    private static final String SECRET_KEY = "WriteHereYourSecretStringForTokenSigningCredentials";

    public PlanCommandServiceImpl(PlanRepository planRepository) {
        this.planRepository = planRepository;
    }

    @Override
    public Optional<Plan> handle(CreatePlanCommand command) {
        // TODO: Admin role verification disabled temporarily
        // Uncomment the following lines when JWT contains role information:
        /*
        if (!isCurrentUserAdmin()) {
            throw new SecurityException("Only admins can create a plan");
        }
        */

        Optional<Plan> existingPlan = planRepository.findByName(command.name().toLowerCase());
        if (existingPlan.isPresent()) {
            throw new IllegalStateException("A plan with that name already exists");
        }
        
        var plan = new Plan(command.name(), command.price());
        planRepository.save(plan);
        return Optional.of(plan);
    }

    @Override
    public Optional<Plan> handle(UpdatePlanCommand command) {
        // TODO: Admin role verification disabled temporarily
        // Uncomment the following lines when JWT contains role information:
        /*
        if (!isCurrentUserAdmin()) {
            throw new SecurityException("Only admins can update a plan");
        }
        */

        var plan = planRepository.findById(command.planId());
        if (!plan.isPresent()) {
            throw new IllegalArgumentException("Plan doesn't exist");
        }
        plan.get().setPrice(command.price());
        plan.get().setName(command.name());
        var updatedPlan = planRepository.save(plan.get());
        return Optional.of(updatedPlan);
    }
    
    // TODO: Methods below are for admin role verification when JWT contains role information
    // Uncomment these methods when you want to enable admin-only access
    
    /*
    /**
     * Verifica si el usuario actual es admin basándose en el JWT token
     */
    /*
    private boolean isCurrentUserAdmin() {
        try {
            // Obtener la request actual
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpServletRequest request = attributes.getRequest();
            
            // Obtener el JWT token del request
            String jwt = (String) request.getAttribute("JWT_TOKEN");
            if (jwt == null) {
                return false;
            }
            
            // Parsear el token para extraer los claims
            Claims claims = Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8)))
                    .build()
                    .parseSignedClaims(jwt)
                    .getPayload();
            
            // Extraer roles/authorities del token
            List<String> authorities = extractAuthorities(claims);
            
            // Verificar si tiene rol de admin
            return authorities.contains("ROLE_ADMIN") || authorities.contains("ADMIN");
            
        } catch (Exception e) {
            // En caso de error, asumir que no es admin
            System.err.println("Error checking admin role: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Extrae las autoridades del JWT claims
     */
    /*
    @SuppressWarnings("unchecked")
    private List<String> extractAuthorities(Claims claims) {
        try {
            // Intentar extraer roles de diferentes posibles claim names
            Object rolesClaim = claims.get("roles");
            if (rolesClaim == null) {
                rolesClaim = claims.get("authorities");
            }
            if (rolesClaim == null) {
                rolesClaim = claims.get("scope");
            }
            
            if (rolesClaim instanceof List<?> list) {
                return (List<String>) list;
            } else if (rolesClaim instanceof String stringClaim) {
                return List.of(stringClaim.split(","));
            }
        } catch (Exception e) {
            System.err.println("Error extracting authorities from JWT: " + e.getMessage());
        }
        return Collections.emptyList();
    }
    */
}
