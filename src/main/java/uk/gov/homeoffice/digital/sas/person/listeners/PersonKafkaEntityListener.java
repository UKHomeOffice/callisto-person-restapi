package uk.gov.homeoffice.digital.sas.person.listeners;

import jakarta.persistence.PostPersist;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import uk.gov.homeoffice.digital.sas.person.model.Person;
import uk.gov.homeoffice.digital.sas.kafka.producer.KafkaProducerService;
import uk.gov.homeoffice.digital.sas.kafka.listener.KafkaEntityListener;

@Component
public class PersonKafkaEntityListener extends KafkaEntityListener<Person> {


  @Autowired
  public PersonKafkaEntityListener(KafkaProducerService<Person> kafkaProducerService) {
    super(kafkaProducerService);
  }

  @Override
  public String resolveMessageKey(Person person) {
    return "123";
  }

  @PostPersist
  void sendMessageOnCreate(Person resource) {
    System.out.println("HELLOOOOOOO");
//    if (isLocalHost()) {
//      super.sendKafkaMessageOnCreate(resource);
//    }
  }
//
//  @PostUpdate
//  void sendMessageOnUpdate(Person resource) {
//    if (isLocalHost()) {
//      super.sendKafkaMessageOnUpdate(resource);
//    }
//  }
//
//  @PostRemove
//  void sendMessageOnDelete(Person resource) {
//    if (isLocalHost()) {
//      super.sendKafkaMessageOnDelete(resource);
//    }
//  }


//  // temporary check to prevent Kafka in dev environment
//  private boolean isLocalHost() {
//    return Arrays.stream(this.environment.getActiveProfiles()).toList()
//        .contains("localhost");
//  }
}
