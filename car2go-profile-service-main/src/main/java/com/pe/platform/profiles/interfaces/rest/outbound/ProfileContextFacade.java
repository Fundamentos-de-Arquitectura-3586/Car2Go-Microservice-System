package com.pe.platform.profiles.interfaces.rest.outbound;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.pe.platform.profiles.domain.model.aggregates.Profile;
import com.pe.platform.profiles.domain.model.queries.GetProfileByIdQuery;
import com.pe.platform.profiles.domain.services.ProfileQueryService;

@Service
public class ProfileContextFacade {

    private final ProfileQueryService profileQueryService;

    public ProfileContextFacade( ProfileQueryService profileQueryService) {
        this.profileQueryService = profileQueryService;
    }

    public Optional<Profile> getProfileById(Long id) {
        var query = new GetProfileByIdQuery(id);
        return profileQueryService.handle(query);
    }

}