package com.armen.awsspringboot.controller;

import com.armen.awsspringboot.model.Book;
import com.armen.awsspringboot.service.BookService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dynamodb")
public class DynamoDbController {

  private BookService bookService;

  public DynamoDbController(BookService bookService) {
    this.bookService = bookService;
  }

  @GetMapping
  public Book getBook(@RequestParam String isbn) {
    return bookService.getBook(isbn);
  }

  @PostMapping
  public void insertBook(@RequestBody Book book) {
    bookService.insertBook(book);
  }

  @PutMapping
  public void updateBook(@RequestBody Book book) {
    bookService.updateBook(book);
  }

  @DeleteMapping
  public void deleteBook(@RequestParam String isbn) {
    bookService.deleteBook(isbn);
  }

}
