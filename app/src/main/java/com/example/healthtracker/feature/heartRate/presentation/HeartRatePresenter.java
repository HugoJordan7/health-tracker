package com.example.healthtracker.feature.heartRate.presentation;

import com.example.healthtracker.R;
import com.example.healthtracker.common.util.Constants;
import com.example.healthtracker.feature.heartRate.HeartRate;
import com.example.healthtracker.model.Calc;
import com.example.healthtracker.model.CalcDao;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import kotlin.Pair;

public class HeartRatePresenter implements HeartRate.Presenter {

    private HeartRate.View view;
    private final ExecutorService executorService;

    public HeartRatePresenter(HeartRate.View view) {
        this.view = view;
        this.executorService = Executors.newSingleThreadExecutor();
    }

    @Override
    public void registerHeartRateValue(double bpm, String hrClassification, CalcDao dao) {
        executorService.execute(() -> {
            try {
                dao.insert(new Calc(Constants.BPM, bpm, hrClassification));
                if (view != null) {
                    view.onHeartRateRegister();
                }
            } catch (Exception e) {
                if (view != null) {
                    view.displayFailure(e.getMessage() != null ? e.getMessage() : "Unknown error");
                }
            }
        });
    }

    @Override
    public boolean validate(String age, String bpm) {
        return (!bpm.isEmpty() && !bpm.startsWith("0") && !age.isEmpty() && !age.startsWith("0"));
    }

    @Override
    public Pair<Integer, Pair<Integer, Integer>> getClassificationHeartRate(boolean isMan, int bpm, int age) {
        if (isMan) {
            return getMaleHeartRateClassification(age, bpm);
        }
        return getFemaleHeartRateClassification(age, bpm);
    }

    private Pair<Integer, Pair<Integer, Integer>> getMaleHeartRateClassification(int age, int bpm) {
        if (age >= 18 && age <= 25) {
            return classifyHeartRate(bpm, new int[]{55, 61, 65, 69, 73, 81});
        } else if (age >= 26 && age <= 35) {
            return classifyHeartRate(bpm, new int[]{54, 61, 65, 70, 74, 81});
        } else if (age >= 36 && age <= 45) {
            return classifyHeartRate(bpm, new int[]{56, 62, 66, 70, 75, 82});
        } else if (age >= 46 && age <= 55) {
            return classifyHeartRate(bpm, new int[]{57, 63, 67, 71, 76, 83});
        } else if (age >= 56 && age <= 65) {
            return classifyHeartRate(bpm, new int[]{56, 61, 67, 71, 75, 81});
        } else {
            return classifyHeartRate(bpm, new int[]{55, 61, 65, 69, 73, 79});
        }
    }

    private Pair<Integer, Pair<Integer, Integer>> getFemaleHeartRateClassification(int age, int bpm) {
        if (age >= 18 && age <= 25) {
            return classifyHeartRate(bpm, new int[]{60, 65, 69, 73, 78, 84});
        } else if (age >= 26 && age <= 35) {
            return classifyHeartRate(bpm, new int[]{59, 64, 68, 72, 76, 82});
        } else if (age >= 36 && age <= 45) {
            return classifyHeartRate(bpm, new int[]{59, 64, 69, 73, 78, 84});
        } else if (age >= 46 && age <= 55) {
            return classifyHeartRate(bpm, new int[]{60, 65, 69, 73, 77, 83});
        } else if (age >= 56 && age <= 65) {
            return classifyHeartRate(bpm, new int[]{59, 64, 68, 73, 77, 83});
        } else {
            return classifyHeartRate(bpm, new int[]{59, 64, 68, 72, 76, 84});
        }
    }

    private Pair<Integer, Pair<Integer, Integer>> classifyHeartRate(int bpm, int[] ranges) {
        int[] classifications = new int[]{
                R.string.bellow_normal_heart_rate,
                R.string.excellent_heart_rate,
                R.string.optimum_heart_rate,
                R.string.good_heart_rate,
                R.string.normal_heart_rate,
                R.string.less_good_heart_rate,
                R.string.bad_heart_rate
        };
        for (int i = 0; i < ranges.length; i++) {
            if (bpm <= ranges[i]) {
                int lower = (i == 0) ? 0 : ranges[i - 1] + 1;
                return new Pair<>(classifications[i], new Pair<>(lower, ranges[i]));
            }
        }
        return new Pair<>(classifications[classifications.length - 1], new Pair<>(ranges[ranges.length - 1] + 1, 999));
    }

    @Override
    public void onDestroy() {
        executorService.shutdownNow();
        view = null;
    }
}
