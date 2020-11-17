package com.armen.handler;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

import java.util.Map;
import java.util.Objects;

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

        context.getLogger().log(record.getBody());

        if (Objects.nonNull(isbn)) {
          context.getLogger().log("Saving object to S3...");
          s3Client.putObject(bucketName, isbn.getStringValue(), record.getBody());
          context.getLogger().log("Completed object saving");
        }
      }
    } catch (Exception e) {
      context.getLogger().log("Failed to put object " + e.getMessage());
    }

    return null;
  }
}
