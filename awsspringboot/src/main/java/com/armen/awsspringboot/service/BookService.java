package com.armen.awsspringboot.service;

import com.armen.awsspringboot.model.Book;
import com.armen.awsspringboot.model.S3Event;
import com.armen.awsspringboot.repository.DynamoDbBookRepository;
import com.armen.awsspringboot.repository.SqsBookRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

@Service
public class BookService {

  private SqsBookRepository sqsRepository;
  private DynamoDbBookRepository dbRepository;
  private ObjectMapper objectMapper;

  public BookService(SqsBookRepository sqsBookRepository, DynamoDbBookRepository dbRepository, ObjectMapper objectMapper) {
    this.sqsRepository = sqsBookRepository;
    this.dbRepository = dbRepository;
    this.objectMapper = objectMapper;
  }

  public Book getBook(String isbn) {
    return dbRepository.read(isbn);
  }

  public void upsertBook(Book book) {
    dbRepository.create(book);
    sqsRepository.sendUsertEvent(book);
  }

  public void deleteBook(String isbn) {
    dbRepository.delete(isbn);
    sqsRepository.sendDeleteEvent(isbn);
  }
}
