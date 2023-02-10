package uk.gov.homeoffice.digital.sas.person;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import uk.gov.homeoffice.digital.sas.jparest.config.ApplicationConfig;

@SpringBootApplication
@Import(ApplicationConfig.class)
public class CallistoPersonRestApiApplication {
  public static void main(String[] args) {
    SpringApplication.run(CallistoPersonRestApiApplication.class, args);
  }
}
