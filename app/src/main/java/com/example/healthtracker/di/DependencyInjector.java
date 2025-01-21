package com.example.healthtracker.di;

import com.example.healthtracker.feature.imc.data.data_source.ImcDataSource;
import com.example.healthtracker.feature.imc.data.data_source.ImcDataSourceImpl;
import com.example.healthtracker.feature.imc.data.repository.ImcRepository;
import com.example.healthtracker.feature.imc.data.repository.ImcRepositoryImpl;

public class DependencyInjector {

    private static final ImcDataSource imcDataSource = new ImcDataSourceImpl();
    private static final ImcRepository imcRepository = new ImcRepositoryImpl(imcDataSource);

    public static ImcRepository getImcRepository() {
        return imcRepository;
    }
}
