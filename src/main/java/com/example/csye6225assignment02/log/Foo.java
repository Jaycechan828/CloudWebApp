package com.example.csye6225assignment02.log;

import com.timgroup.statsd.StatsDClient;
import com.timgroup.statsd.NonBlockingStatsDClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Foo {
    private final StatsDClient statsd;

    @Autowired
    public Foo(StatsDClient statsd) {
        this.statsd = statsd;
    }

    public void recordMetrics() {
        statsd.incrementCounter("bar");
        statsd.recordGaugeValue("baz", 100);
        statsd.recordExecutionTime("bag", 25);
        statsd.recordSetEvent("qux", "one");
    }
}
