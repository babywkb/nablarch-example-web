package com.nablarch.example.app.metrics;

import io.micrometer.core.instrument.binder.MeterBinder;
import nablarch.integration.micrometer.DefaultMeterBinderListProvider;

import java.util.Arrays;
import java.util.List;

public class CustomMeterBinderListProvider extends DefaultMeterBinderListProvider {

    @Override
    protected List<MeterBinder> createMeterBinderList() {
        return Arrays.asList(
//            new TomcatMetrics(null, null)
        );
    }
}
