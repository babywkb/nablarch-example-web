package com.nablarch.example.app.metrics;

import io.micrometer.core.instrument.binder.MeterBinder;
import nablarch.core.log.basic.LogLevel;
import nablarch.integration.micrometer.DefaultMeterBinderListProvider;
import nablarch.integration.micrometer.instrument.binder.logging.LogCountMetrics;

import java.util.Arrays;
import java.util.List;

public class CustomMeterBinderListProvider extends DefaultMeterBinderListProvider {

    @Override
    protected List<MeterBinder> createMeterBinderList() {
        return Arrays.asList(
            new LogCountMetrics(LogLevel.DEBUG)
        );
    }
}
