package com.car2go.car2go_iam_service.iam.domain.model.queries ;

import com.car2go.car2go_iam_service.iam.domain.model.valueobjects.Roles;

public record GetRoleByNameQuery(Roles roleName) {
}