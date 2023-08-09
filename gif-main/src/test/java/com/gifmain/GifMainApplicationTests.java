package com.gifmain;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gifmain.models.Gif;
import com.gifmain.repositories.GifRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@ContextConfiguration(initializers = {GifMainApplicationTests.Initializer.class})
@AutoConfigureMockMvc
class GifMainApplicationTests {
    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer("postgres:11.1").withDatabaseName("gifmain")
            .withUsername("postgres")
            .withPassword("root");
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    GifRepository gifRepository;

    static class Initializer
            implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.url=" + postgreSQLContainer.getJdbcUrl(),
                    "spring.datasource.username=" + postgreSQLContainer.getUsername(),
                    "spring.datasource.password=" + postgreSQLContainer.getPassword()
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }
    @Test
    @Transactional
    public void shouldCreateGif() throws Exception {
        Gif gif=new Gif();
        gif.setName("test");
        String request=objectMapper.writeValueAsString(gif);
        mockMvc.perform(MockMvcRequestBuilders.post("/gif").contentType(MediaType.APPLICATION_JSON).content(request)).andExpect(status().isCreated());
        Assertions.assertEquals(gifRepository.findAll().size(),1);
    }

    @Test
    @Transactional
    public void shouldDeleteGif() throws Exception {
        Gif gif=new Gif();
        gif.setName("test");
        String request=objectMapper.writeValueAsString(gif);
        mockMvc.perform(MockMvcRequestBuilders.post("/gif").contentType(MediaType.APPLICATION_JSON).content(request)).andExpect(status().isCreated());
        mockMvc.perform(MockMvcRequestBuilders.delete("/gif/delete/{id}",1).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

    }
    @Test
    void contextLoads() {
    }

}
