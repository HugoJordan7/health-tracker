package com.example.healthtracker.feature.register.data.repository;

import com.example.healthtracker.domain.model.User;
import com.example.healthtracker.domain.service.HealthTrackerService;
import com.example.healthtracker.domain.service.HealthTrackerServiceMock;

public class RegisterRepositoryImpl implements RegisterRepository {

    private HealthTrackerService healthTrackerService;

    public RegisterRepositoryImpl(HealthTrackerService healthTrackerService) {
        this.healthTrackerService = healthTrackerService;
    }

    @Override
    public User registerUser(User user) throws Exception {
        return healthTrackerService.postUser(user);
    }
}
