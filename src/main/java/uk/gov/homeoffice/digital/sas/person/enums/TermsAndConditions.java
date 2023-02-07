package uk.gov.homeoffice.digital.sas.person.enums;


public enum TermsAndConditions {
  MODERNISED("MODERNISED"),
  PRE_MODERNISED("PRE_MODERNISED");
  private final String stringValue;

  TermsAndConditions(final String s) {
    this.stringValue = s;
  }

  @Override
  public String toString() {
    return stringValue;
  }
}
