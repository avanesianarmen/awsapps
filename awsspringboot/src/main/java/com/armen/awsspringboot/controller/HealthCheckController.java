package com.armen.awsspringboot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/greeting")
public class HealthCheckController {

  @GetMapping("/{name}")
  public String greeting(@PathVariable String name) {
    return String.format("<b>Hello, %s </b> <p><i>Have a nice day ;)</i><p/>", name);
  }

}
