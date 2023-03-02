package uk.gov.homeoffice.digital.sas.person.model;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class PersonTest {

  @Test
  void resolveMessageKey_personEntity_tenantIdAndOwnerIdReturnedInMessageKey() {
    Person person = new Person();
    UUID tenantId = UUID.randomUUID();
    UUID id = UUID.randomUUID();

    person.setTenantId(tenantId);
    person.setId(id);

    String expectedMessageKey = tenantId + ":" + id;
    String actualMessageKey = person.resolveMessageKey();

    assertThat(actualMessageKey).isEqualTo(expectedMessageKey);
  }
}
