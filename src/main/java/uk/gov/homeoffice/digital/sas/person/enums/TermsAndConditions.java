package uk.gov.homeoffice.digital.sas.person.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TermsAndConditions {
  MODERNISED("MODERNISED"),
  PRE_MODERNISED("PRE_MODERNISED");

  private final String stringValue;

  @Override
  public String toString() {
    return stringValue;
  }
}
