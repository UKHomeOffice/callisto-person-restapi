package uk.gov.homeoffice.digital.sas.person.listeners;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.math.BigDecimal;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.homeoffice.digital.sas.kafka.producer.KafkaProducerService;
import uk.gov.homeoffice.digital.sas.person.enums.TermsAndConditions;
import uk.gov.homeoffice.digital.sas.person.model.Person;

@ExtendWith(MockitoExtension.class)
class PersonKafkaEntityListenerTest {

  private final static String TENANT_ID = "b7e813a2-bb28-11ec-8422-0242ac120002";
  private final static String PERSON_ID = "7f000001-867e-1c1f-8186-7edc2eb90000";

  private Person person;

  @Mock
  private KafkaProducerService<Person> kafkaProducerService;

  private final PersonKafkaEntityListener personKafkaEntityListener =
      new PersonKafkaEntityListener(kafkaProducerService);

  @BeforeEach
  void setup() {
    person = Person.builder()
        .firstName("John")
        .lastName("Smith")
        .version(1)
        .fteValue(new BigDecimal("1.0000"))
        .termsAndConditions(TermsAndConditions.MODERNISED)
        .build();

    person.setId(UUID.fromString(PERSON_ID));
    person.setTenantId(UUID.fromString(TENANT_ID));
  }

  @Test
  void resolveMessageKey_personEntity_tenantIdAndOwnerIdReturnedInMessageKey() {
    String expectedMessageKey = TENANT_ID + ":" + PERSON_ID;
    String actualMessageKey = personKafkaEntityListener.resolveMessageKey(person);

    assertThat(actualMessageKey).isEqualTo(expectedMessageKey);
  }
}