package com.example.healthtracker.feature.register.data.repository;

import com.example.healthtracker.domain.model.User;

public interface RegisterRepository {
    User registerUser(User user) throws Exception;
}
