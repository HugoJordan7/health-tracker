package com.example.healthtracker.di;

import com.example.healthtracker.domain.service.HealthTrackerService;
import com.example.healthtracker.domain.service.HealthTrackerServiceMock;
import com.example.healthtracker.feature.heartRate.data.data_source.HeartRateDataSource;
import com.example.healthtracker.feature.heartRate.data.data_source.HeartRateDataSourceImpl;
import com.example.healthtracker.feature.heartRate.data.repository.HeartRateRepository;
import com.example.healthtracker.feature.heartRate.data.repository.HeartRateRepositoryImpl;
import com.example.healthtracker.feature.imc.data.data_source.ImcDataSource;
import com.example.healthtracker.feature.imc.data.data_source.ImcDataSourceImpl;
import com.example.healthtracker.feature.imc.data.repository.ImcRepository;
import com.example.healthtracker.feature.imc.data.repository.ImcRepositoryImpl;
import com.example.healthtracker.feature.listCalc.data.data_source.ListCalcDataSource;
import com.example.healthtracker.feature.listCalc.data.data_source.ListCalcDataSourceImpl;
import com.example.healthtracker.feature.listCalc.data.repository.ListCalcRepository;
import com.example.healthtracker.feature.listCalc.data.repository.ListCalcRepositoryImpl;
import com.example.healthtracker.feature.login.data.repository.LoginRepository;
import com.example.healthtracker.feature.login.data.repository.LoginRepositoryImpl;
import com.example.healthtracker.feature.register.data.repository.RegisterRepository;
import com.example.healthtracker.feature.register.data.repository.RegisterRepositoryImpl;
import com.example.healthtracker.feature.tmb.data.data_source.TmbDataSource;
import com.example.healthtracker.feature.tmb.data.data_source.TmbDataSourceImpl;
import com.example.healthtracker.feature.tmb.data.repository.TmbRepository;
import com.example.healthtracker.feature.tmb.data.repository.TmbRepositoryImpl;

public class DependencyInjector {

    private static final HealthTrackerService healthTrackerService = new HealthTrackerServiceMock();
    private static final RegisterRepository registerRepository = new RegisterRepositoryImpl(healthTrackerService);
    private static final LoginRepository loginRepository = new LoginRepositoryImpl(healthTrackerService);
    private static final ImcRepository imcRepository = new ImcRepositoryImpl(healthTrackerService);
    private static final HeartRateRepository heartRateRepository = new HeartRateRepositoryImpl(healthTrackerService);

    private static final TmbDataSource tmbDataSource = new TmbDataSourceImpl();
    private static final TmbRepository tmbRepository = new TmbRepositoryImpl(tmbDataSource);

    private static final ListCalcRepository listCalcRepository = new ListCalcRepositoryImpl(healthTrackerService);

    public static ImcRepository getImcRepository() {
        return imcRepository;
    }
    public static HeartRateRepository getHeartRateRepository() {
        return heartRateRepository;
    }
    public static TmbRepository getTmbRepository() {
        return tmbRepository;
    }
    public static ListCalcRepository getListCalcRepository() {
        return listCalcRepository;
    }

    public static RegisterRepository getRegisterRepository() {
        return registerRepository;
    }
    public static LoginRepository getLoginRepository() {
        return loginRepository;
    }

}
