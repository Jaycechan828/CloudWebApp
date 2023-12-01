package com.example.csye6225assignment02.service;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.PublishRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.google.gson.JsonObject;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.PublishRequest;

@Service
public class SnsService {

    public void publishToTopic(String submissionUrl, String userEmail) {
        AmazonSNS snsClient = AmazonSNSClientBuilder.standard()
                .withRegion(Regions.US_WEST_2)
                .build();

        String topicArn = snsClient.listTopics().getTopics().get(0).getTopicArn();

        JsonObject messageJson = new JsonObject();
        messageJson.addProperty("submissionUrl", submissionUrl);
        messageJson.addProperty("userEmail", userEmail);

        PublishRequest publishRequest = new PublishRequest(topicArn, messageJson.toString());

        snsClient.publish(publishRequest);
    }
}

