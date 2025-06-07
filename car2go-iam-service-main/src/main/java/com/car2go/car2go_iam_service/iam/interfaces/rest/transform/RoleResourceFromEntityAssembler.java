package com.car2go.car2go_iam_service.iam.interfaces.rest.transform;

import com.car2go.car2go_iam_service.iam.domain.model.entities.Role;
import com.car2go.car2go_iam_service.iam.interfaces.rest.resources.RoleResource;

public class RoleResourceFromEntityAssembler {
    public static RoleResource toResourceFromEntity(Role role) {
        return new RoleResource(role.getId(), role.getStringName());
    }
}