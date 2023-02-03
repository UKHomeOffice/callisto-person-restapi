package uk.gov.homeoffice.digital.sas.person;

import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

class CallistoPersonRestApiApplicationTest {

  @Test
  void run_applicationClassNameAndArgs_springApplicationRunIsInvoked() {

    ConfigurableApplicationContext mockApplicationContext = mock(ConfigurableApplicationContext.class);

    String[] args = {"arg1", "arg2"};
    try (MockedStatic<SpringApplication> utilities = Mockito.mockStatic(SpringApplication.class)) {
      utilities.when(
              () -> SpringApplication.run(CallistoPersonRestApiApplication.class, args))
          .thenReturn(mockApplicationContext);

      CallistoPersonRestApiApplication.main(args);

      utilities.verify(
          () -> SpringApplication.run(eq(CallistoPersonRestApiApplication.class), eq(args)));
    }
  }
}