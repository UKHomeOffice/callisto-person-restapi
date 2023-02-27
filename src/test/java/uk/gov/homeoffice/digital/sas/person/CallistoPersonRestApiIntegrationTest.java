package uk.gov.homeoffice.digital.sas.person;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import com.jayway.jsonpath.JsonPath;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import uk.gov.homeoffice.digital.sas.kafka.message.KafkaAction;
import uk.gov.homeoffice.digital.sas.kafka.message.KafkaEventMessage;
import uk.gov.homeoffice.digital.sas.person.enums.TermsAndConditions;
import uk.gov.homeoffice.digital.sas.person.model.Person;
import uk.gov.homeoffice.digital.sas.person.testutils.KafkaConsumer;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
@ComponentScan("uk.gov.homeoffice.digital.sas.person")
@EmbeddedKafka(
    partitions = 1,
    brokerProperties = {
        "listeners=PLAINTEXT://localhost:3333",
        "port=3333"
    }
)
class CallistoPersonRestApiIntegrationTest {

  private static final int CONSUMER_TIMEOUT = 10;
  private static final String TENANT_ID = "b7e813a2-bb28-11ec-8422-0242ac120002";
  private static final String TENANT_ID_PARAM = "?tenantId="+TENANT_ID;
  private static final String PERSON_URL = "/resources/persons";
  private Person person;

  private final Faker faker = new Faker();

  @Value("${projectVersion}")
  private String version;

  @Autowired
  private MockMvc mvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private KafkaConsumer kafkaConsumer;

  @BeforeEach
  void setup() {
    person = Person.builder()
        .firstName(faker.name().firstName())
        .lastName(faker.name().lastName())
        .version(faker.number().randomDigit())
        .fteValue(randomFteValue())
        .termsAndConditions(randomTermsAndConditions())
        .build();
  }

  @Test
  void shouldReturnEmptyListOfItemsWhenNoPersonDataPresent() throws Exception {

    mvc.perform(get(PERSON_URL+ TENANT_ID_PARAM)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content()
            .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.items", empty()));
  }

  @Test
  void shouldSendCreateMessageToPersonTopicWhenPersonIsCreated() throws Exception {

    MvcResult result = postPerson(person)
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.items", not(empty())))
        .andReturn();

    setPersonIdAndTenantId(result);

    String payload = getPayloadFromConsumer();
    var expectedMessage = new KafkaEventMessage<>(version, Person.class, person, KafkaAction.CREATE);

    assertEqualsEventMessage(payload, expectedMessage);
  }

  @Test
  void shouldSendUpdateMessageToPersonTopicWhenPersonIsUpdated() throws Exception {

    kafkaConsumer.setExpectedNumberOfMessages(2);

    MvcResult result = postPerson(person)
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.items", not(empty())))
        .andReturn();

    setPersonIdAndTenantId(result);

    person.setFteValue(new BigDecimal("0.5000"));
    person.setVersion(2);

    updatePerson(person).andExpect(status().isOk());

    String payload = getPayloadFromConsumer();
    var expectedMessage = new KafkaEventMessage<>(version, Person.class, person, KafkaAction.UPDATE);

    assertEqualsEventMessage(payload, expectedMessage);
  }

  @Test
  void shouldSendDeleteMessageToPersonTopicWhenPersonIsDeleted() throws Exception {

    kafkaConsumer.setExpectedNumberOfMessages(2);

    MvcResult result = postPerson(person)
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.items", not(empty())))
        .andReturn();

    setPersonIdAndTenantId(result);

    mvc.perform(delete(PERSON_URL + "/" + person.getId() + TENANT_ID_PARAM))
        .andExpect(status().isOk());

    String payload = getPayloadFromConsumer();
    var expectedMessage = new KafkaEventMessage<>(version, Person.class, person, KafkaAction.DELETE);

    assertEqualsEventMessage(payload, expectedMessage);
  }

  private ResultActions postPerson(Person person) throws Exception {
    return mvc.perform(post(PERSON_URL + TENANT_ID_PARAM)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(person)));
  }

  private ResultActions updatePerson(Person person) throws Exception {
    return mvc.perform(put(PERSON_URL + "/" + person.getId() + TENANT_ID_PARAM)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(person)));
  }

  private void setPersonIdAndTenantId(MvcResult result) throws UnsupportedEncodingException {
    String personId = JsonPath.read(result.getResponse().getContentAsString(), "$.items[0].id");
    person.setId(UUID.fromString(personId));
    person.setTenantId(UUID.fromString(TENANT_ID));
  }

  private String getPayloadFromConsumer() throws InterruptedException {
    boolean messageConsumed = kafkaConsumer.getLatch().await(CONSUMER_TIMEOUT, TimeUnit.SECONDS);
    assertThat(messageConsumed).isTrue();
    return kafkaConsumer.getPayload();
  }

  private void assertEqualsEventMessage(String actual, KafkaEventMessage<Person> expected) throws JsonProcessingException {
    assertThat(objectMapper.readTree(actual))
        .isEqualTo(objectMapper.readTree(objectMapper.writeValueAsString(expected)));
  }

  private BigDecimal randomFteValue() {
    return new BigDecimal(String.valueOf(faker.number().randomDouble(4, 0, 1)));
  }

  private TermsAndConditions randomTermsAndConditions(){
    return TermsAndConditions.values()[new Random().nextInt(TermsAndConditions.values().length)];
  }
}