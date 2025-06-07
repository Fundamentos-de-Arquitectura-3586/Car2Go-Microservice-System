package com.car2go.car2go_iam_service.iam.infrastructure.hashing.bcrypt;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.car2go.car2go_iam_service.iam.application.internal.outboundservices.hashing.HashingService;

/**
 * This interface is a marker interface for the BCrypt hashing service.
 * It extends the {@link HashingService} and {@link PasswordEncoder} interfaces.
 * This interface is used to inject the BCrypt hashing service in the {@link com.car2go.car2go_iam_service.iam.application.internal.outboundservices.hashing.HashingService} class.
 */
public interface BCryptHashingService extends HashingService, PasswordEncoder {

}