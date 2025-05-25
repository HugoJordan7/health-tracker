package com.example.healthtracker.domain.service;

import com.example.healthtracker.domain.exception.EmailAlreadyExistException;
import com.example.healthtracker.domain.model.User;

import java.util.ArrayList;
import java.util.List;

public class HealthTrackerServiceMock implements HealthTrackerService {

    private List<User> users = new ArrayList<>();

    public HealthTrackerServiceMock() {
        for (int i = 1; i<10; i++) {
            users.add(new User("User" + i, "user" + i + "@gmail.com", "1234567" + i));
        }
    }

    @Override
    public User postUser(User user) {
        for(User fakeUser: users) {
            if (fakeUser.getEmail().equals(user.getEmail())) {
                throw new EmailAlreadyExistException();
            }
        }
        users.add(user);
        return user;
    }

}
