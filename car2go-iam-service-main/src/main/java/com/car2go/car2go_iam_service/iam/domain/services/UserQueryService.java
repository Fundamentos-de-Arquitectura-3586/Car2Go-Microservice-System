package com.car2go.car2go_iam_service.iam.domain.services;

import java.util.List;
import java.util.Optional;

import com.car2go.car2go_iam_service.iam.domain.model.aggregates.User;
import com.car2go.car2go_iam_service.iam.domain.model.queries.GetAllUsersQuery;
import com.car2go.car2go_iam_service.iam.domain.model.queries.GetUserByIdQuery;
import com.car2go.car2go_iam_service.iam.domain.model.queries.GetUserByUsernameQuery;

public interface UserQueryService {
    List<User> handle(GetAllUsersQuery query);
    Optional<User> handle(GetUserByIdQuery query);
    Optional<User> handle(GetUserByUsernameQuery query);
}