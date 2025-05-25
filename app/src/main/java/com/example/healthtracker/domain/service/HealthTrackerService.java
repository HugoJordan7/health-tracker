package com.example.healthtracker.domain.service;

import com.example.healthtracker.domain.model.User;

public interface HealthTrackerService {
    User postUser(User user);

    User getUser(String email);
}
