package com.armen.awsspringboot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HealthCheckController {

  @GetMapping("/{name}")
  public String greeting(@PathVariable String name) {
    return String.format("<b><h1>Hello, %s </h1></b> <p><i>Good luck, have a nice day ;)</i><p/>", name);
  }

}
