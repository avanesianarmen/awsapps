package com.armen.awsspringboot.model;

import java.util.Optional;
import java.util.stream.Stream;

public enum S3Event {
  UPSERT,
  DELETE;

  public static Optional<S3Event> getEvent(String event) {
    return Stream.of(values())
        .filter(e -> e.name().equalsIgnoreCase(event))
        .findFirst();
  }

}
