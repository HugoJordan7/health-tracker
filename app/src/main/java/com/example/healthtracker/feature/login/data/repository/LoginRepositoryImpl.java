package com.example.healthtracker.feature.login.data.repository;

import com.example.healthtracker.domain.model.User;
import com.example.healthtracker.domain.service.HealthTrackerService;

public class LoginRepositoryImpl implements LoginRepository{

    private HealthTrackerService healthTrackerService;

    public LoginRepositoryImpl(HealthTrackerService healthTrackerService) {
        this.healthTrackerService = healthTrackerService;
    }

    @Override
    public User loginUser(String email, String password) throws Exception {
        return healthTrackerService.getUser(email);
    }
}
