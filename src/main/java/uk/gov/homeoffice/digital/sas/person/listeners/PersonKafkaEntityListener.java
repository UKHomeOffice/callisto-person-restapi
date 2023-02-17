package uk.gov.homeoffice.digital.sas.person.listeners;

import org.springframework.stereotype.Component;
import uk.gov.homeoffice.digital.sas.kafka.listener.KafkaEntityListener;
import uk.gov.homeoffice.digital.sas.kafka.producer.KafkaProducerService;
import uk.gov.homeoffice.digital.sas.person.model.Person;

@Component
public class PersonKafkaEntityListener extends KafkaEntityListener<Person> {

  public PersonKafkaEntityListener(KafkaProducerService<Person> kafkaProducerService) {
    super(kafkaProducerService);
  }

  @Override
  public String resolveMessageKey(Person person) {
    return "123";
  }
}
