package uk.gov.homeoffice.digital.sas.person.listeners;

import uk.gov.homeoffice.digital.sas.kafka.message.KafkaAction;
import uk.gov.homeoffice.digital.sas.kafka.producer.KafkaProducerService;

public abstract class KafkaEntityListener<T> {
  protected KafkaProducerService<T> kafkaProducerService;

  public KafkaEntityListener() {

  }

  protected KafkaEntityListener(KafkaProducerService<T> kafkaProducerService) {
    this.kafkaProducerService = kafkaProducerService;
  }

  public abstract String resolveMessageKey(T var1);

  protected void sendKafkaMessageOnCreate(T resource) {
    this.sendMessage(resource, KafkaAction.CREATE);
  }

  protected void sendKafkaMessageOnUpdate(T resource) {
    this.sendMessage(resource, KafkaAction.UPDATE);
  }

  protected void sendKafkaMessageOnDelete(T resource) {
    this.sendMessage(resource, KafkaAction.DELETE);
  }

  private void sendMessage(T resource, KafkaAction action) {
    this.kafkaProducerService.sendMessage(this.resolveMessageKey(resource),
        (Class<T>) resource.getClass(), resource, action);
  }
}
