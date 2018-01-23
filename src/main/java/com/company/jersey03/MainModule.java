package com.company.jersey03;

import com.google.inject.servlet.ServletModule;
import com.company.jersey03.services.CharityService;
import com.company.jersey03.services.DonorService;

public class MainModule extends ServletModule {

    @Override
    protected void configureServlets() {
        bind(CharityService.class);
        bind(DonorService.class);
    }
}