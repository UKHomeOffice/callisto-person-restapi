package uk.gov.homeoffice.digital.sas.person.listeners;

import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
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
    return person.getId().toString();
  }

  @PostPersist
  void sendMessageOnCreate(Person resource) {
    super.sendKafkaMessageOnCreate(resource);
  }

  @PostUpdate
  void sendMessageOnUpdate(Person resource) {
    super.sendKafkaMessageOnUpdate(resource);
  }

  @PostRemove
  void sendMessageOnDelete(Person resource) {
    super.sendKafkaMessageOnDelete(resource);
  }
}
