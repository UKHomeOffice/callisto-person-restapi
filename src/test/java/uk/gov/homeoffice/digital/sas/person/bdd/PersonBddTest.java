package uk.gov.homeoffice.digital.sas.person.bdd;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = {"classpath:uk.gov.homeoffice.digital.sas.person.bdd"},
    glue = {"uk.gov.homeoffice.digital.sas.cucumberjparest",
        "uk.gov.homeoffice.digital.sas.person.bdd"})
public class PersonBddTest {
}
