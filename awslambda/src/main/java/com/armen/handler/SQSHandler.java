package com.armen.handler;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class SQSHandler implements RequestHandler<SQSEvent, String> {

  private AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
      .withRegion(Regions.US_EAST_2)
      .build();
  private String bucketName = System.getenv("bucket");

  public String handleRequest(SQSEvent sqsEvent, Context context) {

    try {
      for (SQSEvent.SQSMessage record : sqsEvent.getRecords()) {
        Map<String, SQSEvent.MessageAttribute> messageAttributes = record.getMessageAttributes();
        SQSEvent.MessageAttribute isbn = messageAttributes.get("isbn");
        SQSEvent.MessageAttribute event = messageAttributes.get("s3Event");

        context.getLogger().log(record.getBody());

        if (Objects.nonNull(isbn) && Objects.nonNull(event)) {
          Optional<S3Event> optionalS3Event = S3Event.getEvent(event.getStringValue());
          if (optionalS3Event.isPresent()) {
            S3Event s3Event = optionalS3Event.get();
            if (S3Event.UPSERT.equals(s3Event)) {
              context.getLogger().log("Upserting object to S3...");
              s3Client.putObject(bucketName, isbn.getStringValue(), record.getBody());
              context.getLogger().log("Completed object upsert");
            } else if (S3Event.DELETE.equals(s3Event)) {
              context.getLogger().log("Deleting object from S3...");
              s3Client.deleteObject(bucketName, isbn.getStringValue());
              context.getLogger().log("Completed object deletion");
            }
          }
        }
      }
    } catch (Exception e) {
      context.getLogger().log("Failed to put/delete object " + e.getMessage());
    }

    return null;
  }
}
