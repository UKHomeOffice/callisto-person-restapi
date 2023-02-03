package uk.gov.homeoffice.digital.sas.person;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

// TODO: remove exclusion once database is setup
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class CallistoPersonRestApiApplication {
  public static void main(String[] args) {
    SpringApplication.run(CallistoPersonRestApiApplication.class, args);
  }
}
