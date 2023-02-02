package uk.gov.homeoffice.digital.sas.person.controllers;

import static org.springframework.http.ResponseEntity.ok;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// TODO: delete this test controller once JAP REST entity(ies) are created under model
@RestController
public class TestController {
  @GetMapping("/test")
  public ResponseEntity<String> welcome() {
    return ok("Welcome to Person REST API");
  }
}
