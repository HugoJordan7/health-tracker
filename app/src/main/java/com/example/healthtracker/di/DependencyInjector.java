package com.example.healthtracker.di;

import com.example.healthtracker.feature.heartRate.data.data_source.HeartRateDataSource;
import com.example.healthtracker.feature.heartRate.data.data_source.HeartRateDataSourceImpl;
import com.example.healthtracker.feature.heartRate.data.repository.HeartRateRepository;
import com.example.healthtracker.feature.heartRate.data.repository.HeartRateRepositoryImpl;
import com.example.healthtracker.feature.imc.data.data_source.ImcDataSource;
import com.example.healthtracker.feature.imc.data.data_source.ImcDataSourceImpl;
import com.example.healthtracker.feature.imc.data.repository.ImcRepository;
import com.example.healthtracker.feature.imc.data.repository.ImcRepositoryImpl;

public class DependencyInjector {

    private static final ImcDataSource imcDataSource = new ImcDataSourceImpl();
    private static final ImcRepository imcRepository = new ImcRepositoryImpl(imcDataSource);

    private static final HeartRateDataSource heartRateDataSource = new HeartRateDataSourceImpl();
    private static final HeartRateRepository heartRateRepository = new HeartRateRepositoryImpl(heartRateDataSource);

    public static ImcRepository getImcRepository() {
        return imcRepository;
    }
    public static HeartRateRepository getHeartRateRepository() {
        return heartRateRepository;
    }

}
