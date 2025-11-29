package com.epam.edp.demo.configs;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;

/**
 * Configuration class for custom metrics
 */
@Configuration
public class CustomMetricsConfig {

    private final MeterRegistry meterRegistry;
    private static final String METRIC_NAME = "app_memory_usage_enc4ehiy";

    public CustomMetricsConfig(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    @PostConstruct
    public void registerCustomMetrics() {
        // Register custom memory usage metric
        Gauge.builder(METRIC_NAME, this, value -> getMemoryUsage())
                .description("Application memory usage in bytes")
                .baseUnit("bytes")
                .register(meterRegistry);
    }

    private double getMemoryUsage() {
        Runtime runtime = Runtime.getRuntime();
        return runtime.totalMemory() - runtime.freeMemory();
    }
}