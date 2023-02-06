package uk.gov.homeoffice.digital.sas.person;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class CallistoPersonRestApiApplication {
  public static void main(String[] args) {
    SpringApplication.run(CallistoPersonRestApiApplication.class, args);
  }
}
