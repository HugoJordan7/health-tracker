package com.example.healthtracker.domain.service;

import com.example.healthtracker.App;
import com.example.healthtracker.domain.exception.EmailAlreadyExistException;
import com.example.healthtracker.domain.exception.UserNotFoundException;
import com.example.healthtracker.domain.model.MedicationRoutine;
import com.example.healthtracker.domain.model.Schedule;
import com.example.healthtracker.domain.model.User;
import com.example.healthtracker.model.Calc;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class HealthTrackerServiceMock implements HealthTrackerService {

    private List<User> users = new ArrayList<>();
    private List<Calc> calcs = new ArrayList<>();

    private List<MedicationRoutine> medications = new ArrayList<>();

    public HealthTrackerServiceMock() {
        // Mocking Users
        for (int i = 1; i <= 10; i++) {
            users.add(new User("User" + i, "user" + i + "@gmail.com", "1234567" + i));
        }

        String[] types = {"imc", "tmb", "bpm"};
        for (String type : types) {
            for (int i = 0; i < 10; i++) {
                double res = generateMockResult(type, i);
                String situation = generateMockSituation(type, res);
                Calc calc = new Calc(type, res, situation);

                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DAY_OF_YEAR, -i);
                calc.setCreatedDate(calendar.getTime());
                
                calcs.add(calc);
            }
        }
    }

    private double generateMockResult(String type, int index) {
        switch (type) {
            case "imc":
                return 20.0 + index + (Math.random() * 2);
            case "tmb":
                return 1500.0 + (index * 50) + (Math.random() * 100);
            case "bpm":
                return 60.0 + (index * 2) + (Math.random() * 10);
            default:
                return 0.0;
        }
    }

    private String generateMockSituation(String type, double res) {
        if (type.equals("imc")) {
            if (res < 18.5) return "Abaixo do peso";
            if (res < 25) return "Peso normal";
            if (res < 30) return "Sobrepeso";
            return "Obesidade";
        } else if (type.equals("bpm")) {
            if (res < 60) return "Baixo";
            if (res < 100) return "Normal";
        }
        return "";
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
            if (calc.getType().equalsIgnoreCase(type)) {
                filteredCalcs.add(calc);
            }
        }

        // Sort by date to ensure chart and list show correctly
        filteredCalcs.sort(Comparator.comparing(Calc::getCreatedDate));
        return filteredCalcs;
    }

    @Override
    public boolean clearCalcs(String type) {
        calcs.removeIf(calc -> calc.getType().equalsIgnoreCase(type));
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
