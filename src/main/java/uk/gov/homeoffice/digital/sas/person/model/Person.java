package uk.gov.homeoffice.digital.sas.person.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uk.gov.homeoffice.digital.sas.jparest.annotation.Resource;
import uk.gov.homeoffice.digital.sas.jparest.models.BaseEntity;
import uk.gov.homeoffice.digital.sas.person.enums.TermsAndConditions;

@Resource(path = "persons")
@Entity(name = "person")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@NoArgsConstructor
@Getter
@Setter
public class Person extends BaseEntity {

  @NotNull(message = "Person Version should not be empty")
  @Column(name = "version")
  private Integer version;

  @NotNull(message = "Person First Name should not be empty")
  @Column(name = "first_name")
  private String firstName;

  @NotNull(message = "Person Last Name should not be empty")
  @Column(name = "last_name")
  private String lastName;

  @Column(name = "fte_value")
  @DecimalMin(value = "0.0", inclusive = false)
  @Digits(integer = 1, fraction = 4)
  @DecimalMax(value = "1.0")
  private BigDecimal fteValue;

  @NotNull(message = "Person Terms and Condition should not be empty")
  @Enumerated(EnumType.STRING)
  @Column(name = "terms_and_conditions")
  private TermsAndConditions termsAndConditions;

}
