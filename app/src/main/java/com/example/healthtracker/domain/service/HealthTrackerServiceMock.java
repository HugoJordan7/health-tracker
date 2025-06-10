package com.example.healthtracker.domain.service;

import com.example.healthtracker.App;
import com.example.healthtracker.domain.exception.EmailAlreadyExistException;
import com.example.healthtracker.domain.exception.UserNotFoundException;
import com.example.healthtracker.domain.model.MedicationRoutine;
import com.example.healthtracker.domain.model.Schedule;
import com.example.healthtracker.domain.model.User;
import com.example.healthtracker.model.Calc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HealthTrackerServiceMock implements HealthTrackerService {

    private List<User> users = new ArrayList<>();
    private List<Calc> calcs = new ArrayList<>();

    private List<MedicationRoutine> medications = new ArrayList<>();

    public HealthTrackerServiceMock() {
        for (int i = 1; i<10; i++) {
            users.add(new User("User" + i, "user" + i + "@gmail.com", "1234567" + i));
            calcs.add(new Calc("imc", i, "Good"));
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

    @Override
    public User getUser(String email) {
        for(User fakeUser: users) {
            if (fakeUser.getEmail().equals(email)) {
                return fakeUser;
            }
        }
        throw new UserNotFoundException();
    }

    @Override
    public List<Calc> getAllCalcs(String type) {
        List<Calc> filteredCalcs = new ArrayList<>();
        for(Calc calc: calcs) {
            if (calc.getType().equals(type)) {
                filteredCalcs.add(calc);
            }
        }
        return filteredCalcs;
    }

    @Override
    public boolean clearCalcs(String type) {
        calcs.clear();
        return true;
    }

    @Override
    public void insertCalc(Calc calc) {
        calcs.add(calc);
    }

    @Override
    public MedicationRoutine postMedicationRoutine(String name, String frequency, List<Schedule> schedules) {
        String userEmail = new SharedPreferencesService(App.getContext()).getEmail();
        MedicationRoutine medicationRoutine = new MedicationRoutine(userEmail, name, frequency, schedules);
        medications.add(medicationRoutine);
        return medicationRoutine;
    }

    @Override
    public MedicationRoutine putMedicationRoutine(MedicationRoutine medicationRoutine) {
        for (int i = 0; i < medications.size(); i++) {
            if (medications.get(i).getId().equals(medicationRoutine.getId())) {
                medications.set(i, medicationRoutine);
                return medicationRoutine;
            }
        }
        return null;
    }

    @Override
    public void removeMedicationRoutine(String medicationRoutineId) {
        for (int i = 0; i < medications.size(); i++) {
            if (medications.get(i).getId().equals(medicationRoutineId)) {
                medications.remove(i);
                break;
            }
        }
    }

}
