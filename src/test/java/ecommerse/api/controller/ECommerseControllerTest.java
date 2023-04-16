package ecommerse.api.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import com.fasterxml.jackson.databind.ObjectMapper;
import ecommerse.api.ECommerseApplication;
import ecommerse.api.model.Product;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(classes = ECommerseApplication.class)
@AutoConfigureMockMvc
public class ECommerseControllerTest {

  private static final String PATH = "/api/v1/ecommerse";

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void createECardTest() throws Exception {
    this.mockMvc.perform(post(PATH)).andExpect(status().isCreated());
  }

  @Test
  public void updateECardTest() throws Exception {
    ObjectMapper objectMapper = new ObjectMapper();

    this.mockMvc.perform(put(PATH+ "/1000/update").contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(Arrays.asList(new Product(1L, 2L, "Desc")))))
        .andExpect(status().isNotFound());
  }

  @Test
  public void getECardTest() throws Exception {
    this.mockMvc.perform(get(PATH+ "/1000")).andExpect(status().isNotFound());
  }

  @Test
  public void deleteECardTest() throws Exception {
    this.mockMvc.perform(delete(PATH+ "/1000")).andExpect(status().isNotFound());
  }
}
