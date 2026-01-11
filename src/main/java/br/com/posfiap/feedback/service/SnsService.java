package br.com.posfiap.feedback.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;

@ApplicationScoped
public class SnsService {

    @Inject
    SnsClient snsClient;

    private static final String TOPIC_ARN =
            System.getenv("SNS_TOPIC_ARN");

    public void publish(String message) {

        snsClient.publish(
                PublishRequest.builder()
                        .topicArn(TOPIC_ARN)
                        .message(message)
                        .build()
        );
    }
}
