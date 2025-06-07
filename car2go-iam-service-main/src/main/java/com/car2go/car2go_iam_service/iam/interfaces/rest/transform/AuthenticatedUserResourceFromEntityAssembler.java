package com.car2go.car2go_iam_service.iam.interfaces.rest.transform;

import com.car2go.car2go_iam_service.iam.domain.model.aggregates.User;
import com.car2go.car2go_iam_service.iam.interfaces.rest.resources.AuthenticatedUserResource;

public class AuthenticatedUserResourceFromEntityAssembler {
    public static AuthenticatedUserResource toResourceFromEntity(User user, String token) {
        return new AuthenticatedUserResource(user.getId(), user.getUsername(), token);
    }
}