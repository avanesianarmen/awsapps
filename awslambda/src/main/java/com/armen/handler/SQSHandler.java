package com.armen.handler;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

import java.util.Objects;

public class SQSHandler implements RequestHandler<SQSEvent, String> {

  private AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
      .withRegion(Regions.US_EAST_2)
      .build();
  private String bucketName = System.getenv("bucket");

  public String handleRequest(SQSEvent sqsEvent, Context context) {

    try {
      for (SQSEvent.SQSMessage record : sqsEvent.getRecords()) {
        String isbn = record.getAttributes().get("isbn");

        context.getLogger().log(record.getBody());

        if (Objects.nonNull(isbn)) {
          s3Client.putObject(bucketName, isbn, record.getBody());
        }
      }
    } catch (Exception e) {
      context.getLogger().log("Failed to put object " + e.getMessage());
    }

    return null;
  }
}
