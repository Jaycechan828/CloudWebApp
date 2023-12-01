package com.example.csye6225assignment02.configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AwsSnsConfig {

    @Bean
    public AmazonSNS amazonSNS() {
        BasicAWSCredentials awsCreds = new BasicAWSCredentials("AKIAQWODHYX4K66IA5EY", "W5H1mnKP+d4QdrJhIMjF5gWgRetK6PrYMboXtONs");
        return AmazonSNSClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                .withRegion("Oregon")
                .build();
    }
}

