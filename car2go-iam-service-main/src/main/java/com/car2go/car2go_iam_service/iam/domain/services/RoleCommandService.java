package com.car2go.car2go_iam_service.iam.domain.services;

import com.car2go.car2go_iam_service.iam.domain.model.commands.SeedRolesCommand;

public interface RoleCommandService {
    void handle(SeedRolesCommand command);
}