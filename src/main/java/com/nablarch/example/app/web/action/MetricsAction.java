package com.nablarch.example.app.web.action;

import io.micrometer.core.instrument.Measurement;
import io.micrometer.core.instrument.Meter;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import nablarch.core.repository.SystemRepository;
import nablarch.fw.ExecutionContext;
import nablarch.fw.web.HttpRequest;
import nablarch.fw.web.HttpResponse;

public class MetricsAction {
    public HttpResponse index(HttpRequest request, ExecutionContext context) {
        String[] errors = request.getParam("error");
        if (errors != null && 0 < errors.length) {
            stackoverflow();
        }

        String metrics;
        Object registry = SystemRepository.getObject("meterRegistry");
        if (registry instanceof PrometheusMeterRegistry) {
            PrometheusMeterRegistry meterRegistry = (PrometheusMeterRegistry) registry;
            metrics = meterRegistry.scrape();
        } else if (registry instanceof SimpleMeterRegistry) {
            StringBuilder sb = new StringBuilder("@@@@@ SimpleMeterRegistry @@@@@\n");
            SimpleMeterRegistry meterRegistry = (SimpleMeterRegistry) registry;
            for (Meter meter : meterRegistry.getMeters()) {
                Meter.Id id = meter.getId();
                sb.append("[id=").append(id).append("]\n");
                for (Measurement measurement : meter.measure()) {
                    sb.append("  ").append(measurement.getStatistic().name()).append('=').append(measurement.getValue()).append('\n');
                }
            }
            metrics = sb.toString();
        } else {
            metrics = "none";
        }
        context.setRequestScopedVar("metrics", metrics);

        return new HttpResponse("/WEB-INF/view/metrics/index.jsp");
    }

    private void stackoverflow() {
        stackoverflow();
    }
}
