package com.car2go.car2go_payment_service.shared.Infrastructure.security;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String SECRET_KEY = "WriteHereYourSecretStringForTokenSigningCredentials";

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        System.out.println("🔥 Filtro JWT interceptó la petición");

        final String authHeader = request.getHeader("Authorization");
        System.out.println("Authorization header: " + authHeader);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String jwt = authHeader.substring(7);
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8)))
                    .build()
                    .parseSignedClaims(jwt)
                    .getPayload();

            Object idClaim = claims.get("userId");
            if (idClaim != null) {
                request.setAttribute("JWT_TOKEN", jwt);
                System.out.println("Token válido para userId: " + idClaim);
            }
        } catch (Exception e) {
            System.err.println("Token JWT inválido: " + e.getMessage());
        }

        filterChain.doFilter(request, response);
    }
}