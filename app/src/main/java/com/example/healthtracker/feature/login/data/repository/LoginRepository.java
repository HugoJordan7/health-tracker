package com.example.healthtracker.feature.login.data.repository;

import com.example.healthtracker.domain.model.User;

public interface LoginRepository {
    User loginUser(String email, String password) throws Exception;
}
