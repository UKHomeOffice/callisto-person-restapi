package uk.gov.homeoffice.digital.sas.person.listeners;

import jakarta.persistence.PostPersist;
import org.springframework.stereotype.Component;
import uk.gov.homeoffice.digital.sas.kafka.producer.KafkaProducerService;
import uk.gov.homeoffice.digital.sas.person.model.Person;

@Component
public class PersonKafkaEntityListener extends KafkaEntityListener<Person> {

  private final KafkaProducerService<Person> kafkaProducerService;

  public PersonKafkaEntityListener(KafkaProducerService<Person> kafkaProducerService) {
    this.kafkaProducerService = kafkaProducerService;
  }

  public PersonKafkaEntityListener() {

  }

  @Override
  public String resolveMessageKey(Person person) {
    return "123";
  }

  @PostPersist
  void sendMessageOnCreate(Person resource) {
    System.out.println("HELLOOOOOOO");
    super.sendKafkaMessageOnCreate(resource);
  }
}
