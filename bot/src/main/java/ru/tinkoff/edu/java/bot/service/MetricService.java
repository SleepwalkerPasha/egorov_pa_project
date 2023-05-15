package ru.tinkoff.edu.java.bot.service;

import io.micrometer.core.instrument.Metrics;


public class MetricService {

    public void incrementHandleMessageCount() {
        Metrics.counter("handle_message_count").increment();
    }
}
