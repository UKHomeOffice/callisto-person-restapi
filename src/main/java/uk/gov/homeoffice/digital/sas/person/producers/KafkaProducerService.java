package uk.gov.homeoffice.digital.sas.person.producers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import uk.gov.homeoffice.digital.sas.kafka.message.KafkaAction;
import uk.gov.homeoffice.digital.sas.kafka.message.KafkaEventMessage;

@Component
@EnableAutoConfiguration
public class KafkaProducerService<T> {
  private final KafkaTemplate<String, KafkaEventMessage<T>> kafkaTemplate;
  private final String topicName;
  private final String projectVersion;

  public KafkaProducerService(KafkaTemplate<String, KafkaEventMessage<T>> kafkaTemplate, @Value("${spring.kafka.template.default-topic}") String topicName, @Value("${projectVersion}") String projectVersion) {
    this.kafkaTemplate = kafkaTemplate;
    this.topicName = topicName;
    this.projectVersion = projectVersion;
  }

  public void sendMessage(String messageKey, Class<T> resourceType, T resource, KafkaAction action) {
    KafkaEventMessage<T> kafkaEventMessage = new KafkaEventMessage(this.projectVersion, resourceType, resource, action);
    this.kafkaTemplate.send(this.topicName, messageKey, kafkaEventMessage);
  }
}
