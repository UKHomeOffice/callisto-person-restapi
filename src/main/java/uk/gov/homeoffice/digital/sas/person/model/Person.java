package uk.gov.homeoffice.digital.sas.person.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uk.gov.homeoffice.digital.sas.jparest.annotation.Resource;
import uk.gov.homeoffice.digital.sas.jparest.models.BaseEntity;
import uk.gov.homeoffice.digital.sas.person.enums.TermsAndConditions;
import java.math.BigDecimal;

@Resource(path = "persons")
@Entity(name = "person")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@NoArgsConstructor
@Getter
@Setter
public class Person extends BaseEntity {

  @Column(name = "version")
  private Integer version;

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "last_name")
  private String lastName;

  @Column(name = "fte_value")
  private BigDecimal fteValue;

  @Enumerated(EnumType.STRING)
  @Column(name = "terms_and_conditions")
  private TermsAndConditions termsAndConditions;

}
