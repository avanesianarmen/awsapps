package com.armen.awsspringboot.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SQSConfig {

  @Value("${aws.access.key}")
  private String awsAccessKey;

  @Value("${aws.access.secret-key}")
  private String awsSecretAccessKey;

  @Value("${aws.region}")
  private String awsRegion;

  @Value("${aws.sqs.endpoint-url}")
  private String awsSqsEndpointUrl;

  @Bean
  public AmazonSQS amazonSQS() {
    return AmazonSQSClientBuilder.standard()
        .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(awsSqsEndpointUrl, awsRegion))
        .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(awsAccessKey, awsSecretAccessKey)))
        .build();
  }
}
