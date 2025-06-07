package com.car2go.car2go_iam_service.iam.domain.services;

import java.util.List;
import java.util.Optional;

import com.car2go.car2go_iam_service.iam.domain.model.entities.Role;
import com.car2go.car2go_iam_service.iam.domain.model.queries.GetAllRolesQuery;
import com.car2go.car2go_iam_service.iam.domain.model.queries.GetRoleByNameQuery;

public interface RoleQueryService {
    List<Role> handle(GetAllRolesQuery query);
    Optional<Role> handle(GetRoleByNameQuery query);
}