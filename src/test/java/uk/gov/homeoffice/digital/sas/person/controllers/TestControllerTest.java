package uk.gov.homeoffice.digital.sas.person.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class TestControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  void shouldReturnDefaultMessage() throws Exception {
    this.mockMvc.perform(get("/test")).andDo(print()).andExpect(status().isOk())
        .andExpect(content().string(is("Welcome to Person REST API")));
  }
}
