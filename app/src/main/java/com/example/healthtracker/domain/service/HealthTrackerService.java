package com.example.healthtracker.domain.service;

import com.example.healthtracker.common.base.RequestCallback;
import com.example.healthtracker.domain.model.User;
import com.example.healthtracker.model.Calc;
import com.example.healthtracker.model.CalcDao;

import java.util.List;

public interface HealthTrackerService {
    User postUser(User user);

    User getUser(String email);

    List<Calc> getAllCalcs(String type);

    boolean clearCalcs(String type);
}
