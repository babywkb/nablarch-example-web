package com.nablarch.example.app.web.action;

import io.micrometer.core.instrument.Measurement;
import io.micrometer.core.instrument.Meter;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import nablarch.core.log.Logger;
import nablarch.core.log.LoggerManager;
import nablarch.core.repository.SystemRepository;
import nablarch.fw.ExecutionContext;
import nablarch.fw.web.HttpRequest;
import nablarch.fw.web.HttpResponse;

public class MetricsAction {
    private static final Logger LOGGER = LoggerManager.get(MetricsAction.class);

    public HttpResponse index(HttpRequest request, ExecutionContext context) {
        String[] errors = request.getParam("error");
        if (errors != null && 0 < errors.length) {
            stackoverflow();
        }

        String[] loggings = request.getParam("logging");
        if (loggings !=null && 0 < loggings.length) {
            switch (loggings[0]) {
                case "trace":
                    LOGGER.logTrace("trace log");
                    break;
                case "debug":
                    LOGGER.logDebug("debug log");
                    break;
                case "info":
                    LOGGER.logInfo("info log");
                    break;
                case "warn":
                    LOGGER.logWarn("warn log");
                    break;
                case "error":
                    LOGGER.logError("error log");
                    break;
                case "fatal":
                    LOGGER.logFatal("fatal log");
                    break;
                default:
                    LOGGER.logError("unknown loggings=" + loggings[0]);
            }
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
