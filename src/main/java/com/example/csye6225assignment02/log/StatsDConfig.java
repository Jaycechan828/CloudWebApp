package com.example.csye6225assignment02.log;

import com.timgroup.statsd.NonBlockingStatsDClient;
import com.timgroup.statsd.StatsDClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StatsDConfig {

    @Bean
    public StatsDClient statsDClient() {
        // Configure a NonBlockingStatsDClient
        return new NonBlockingStatsDClient("webapp", "localhost", 8125);
    }

}
