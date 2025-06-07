package com.car2go.car2go_iam_service.iam.domain.model.commands;

import java.util.List;

import com.car2go.car2go_iam_service.iam.domain.model.entities.Role;

public record SignUpCommand(String username, String password, List<Role> roles) {
}