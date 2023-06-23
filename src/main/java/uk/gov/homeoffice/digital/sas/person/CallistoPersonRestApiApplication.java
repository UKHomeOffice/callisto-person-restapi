package uk.gov.homeoffice.digital.sas.person;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"uk.gov.homeoffice.digital.sas.kafka.producer",
                "uk.gov.homeoffice.digital.sas.kafka.transactionsync"})
public class CallistoPersonRestApiApplication {
  public static void main(String[] args) {
    SpringApplication.run(CallistoPersonRestApiApplication.class, args);
  }
}
