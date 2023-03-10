package uk.gov.homeoffice.digital.sas.person.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import uk.gov.homeoffice.digital.sas.jparest.annotation.Resource;
import uk.gov.homeoffice.digital.sas.jparest.models.BaseEntity;
import uk.gov.homeoffice.digital.sas.kafka.listener.KafkaEntityListener;
import uk.gov.homeoffice.digital.sas.kafka.message.Messageable;
import uk.gov.homeoffice.digital.sas.person.enums.TermsAndConditions;

@Resource(path = "persons")
@Entity(name = "person")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@EntityListeners(KafkaEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@SuppressWarnings("squid:S2160") //Suppressed overriding equals until eahw-2652
public class Person extends BaseEntity implements Messageable {

  @NotNull(message = "Version should not be empty")
  private Integer version;

  @NotNull(message = "First Name should not be empty")
  @Length(max = 50)
  private String firstName;

  @NotNull(message = "Last Name should not be empty")
  @Length(max = 50)
  private String lastName;

  @DecimalMin(value = "0.0", inclusive = false)
  @Digits(integer = 1, fraction = 4)
  @DecimalMax(value = "1.0")
  private BigDecimal fteValue;

  @NotNull(message = "Person Terms and Conditions should not be empty")
  @Enumerated(EnumType.STRING)
  private TermsAndConditions termsAndConditions;

  @Override
  public String resolveMessageKey() {
    return getTenantId() + ":" + getId();
  }
}
