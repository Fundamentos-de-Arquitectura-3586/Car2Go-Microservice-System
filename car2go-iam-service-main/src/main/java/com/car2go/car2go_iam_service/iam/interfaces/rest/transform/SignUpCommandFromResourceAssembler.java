package com.car2go.car2go_iam_service.iam.interfaces.rest.transform;



import java.util.ArrayList;

import com.car2go.car2go_iam_service.iam.domain.model.commands.SignUpCommand;
import com.car2go.car2go_iam_service.iam.domain.model.entities.Role;
import com.car2go.car2go_iam_service.iam.interfaces.rest.resources.SignUpResource;

public class SignUpCommandFromResourceAssembler {
    public static SignUpCommand toCommandFromResource(SignUpResource resource) {
        var roles = resource.roles() != null ? resource.roles().stream().map(name -> Role.toRoleFromName(name)).toList() : new ArrayList<Role>();
        return new SignUpCommand(resource.username(), resource.password(), roles);
    }
}