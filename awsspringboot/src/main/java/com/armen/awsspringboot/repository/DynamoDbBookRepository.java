package com.armen.awsspringboot.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.armen.awsspringboot.model.Book;
import org.springframework.stereotype.Repository;

@Repository
public class DynamoDbBookRepository {

  private DynamoDBMapper mapper;

  public DynamoDbBookRepository(DynamoDBMapper mapper) {
    this.mapper = mapper;
  }

  public void create(Book book) {
    mapper.save(book);
  }

  public Book read(String isbn) {
    return mapper.load(Book.class, isbn);
  }

  public void delete(String isbn) {
    Book book = new Book();
    book.setIsbn(isbn);
    mapper.delete(book);
  }

  public void update(Book book) {
    mapper.save(book);
  }
}
