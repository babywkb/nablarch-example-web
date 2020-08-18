package com.nablarch.example.app.metrics.instrument;

import io.micrometer.prometheus.PrometheusConfig;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import nablarch.core.repository.di.DiContainer;
import nablarch.integration.micrometer.MeterRegistryFactory;
import nablarch.integration.micrometer.MicrometerConfiguration;
import nablarch.integration.micrometer.NablarchMeterRegistryConfig;

public class PrometheusMeterRegistryFactory extends MeterRegistryFactory<PrometheusMeterRegistry> {

    @Override
    protected PrometheusMeterRegistry createMeterRegistry(MicrometerConfiguration micrometerConfiguration) {
        return new PrometheusMeterRegistry(new Config(prefix, micrometerConfiguration));
    }

    @Override
    public PrometheusMeterRegistry createObject() {
        return doCreateObject();
    }

    static class Config extends NablarchMeterRegistryConfig implements PrometheusConfig {

        /**
         * プレフィックスと{@link DiContainer}を指定してインスタンスを生成する。
         *
         * @param prefix      プレフィックス
         * @param diContainer {@link DiContainer}
         */
        public Config(String prefix, DiContainer diContainer) {
            super(prefix, diContainer);
        }

        @Override
        protected String subPrefix() {
            return "prometheus";
        }
    }
}
