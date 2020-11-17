package com.armen.awsspringboot.repository;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.MessageAttributeValue;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.armen.awsspringboot.model.Book;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class SqsBookRepository {
  @Value("${aws.sqs.book.endpoint-url}")
  private String sqsBookUrl;

  @Value("${aws.sqs.book.group-id}")
  private String sqsBookGroupId;

  private AmazonSQS sqsClient;
  private ObjectMapper objectMapper;

  public SqsBookRepository(AmazonSQS sqsClient, ObjectMapper objectMapper) {
    this.sqsClient = sqsClient;
    this.objectMapper = objectMapper;
  }

  public void sendMessage(Book book) {
    Map<String, MessageAttributeValue> messageAttributes = new HashMap<>();
    messageAttributes.put("isbn", new MessageAttributeValue()
        .withStringValue(book.getIsbn())
        .withDataType("String"));
    SendMessageRequest sqsRequest = null;
    try {
      sqsRequest = new SendMessageRequest()
          .withQueueUrl(sqsBookUrl)
          .withMessageBody(objectMapper.writeValueAsString(book))
          .withMessageGroupId(sqsBookGroupId)
          .withMessageAttributes(messageAttributes);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    sqsClient.sendMessage(sqsRequest);
  }
}
