package com.thoughtworks.rslist.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.rslist.domain.User;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Create by 木水 on 2020/9/16.
 */
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Order(1)
    @Test
    public void should_register_user() throws Exception {
        User user = new User("hejie", 22, "male", "hj@c", "13599999999");
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(user);
        mockMvc.perform(post("/user").content(jsonString).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/user"))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].userName", is("hejie")))
                .andExpect(jsonPath("$[0].age", is(22)))
                .andExpect(jsonPath("$[0].gender", is("male")))
                .andExpect(jsonPath("$[0].email", is("hj@c")))
                .andExpect(jsonPath("$[0].phone", is("13599999999")))
                .andExpect(status().isOk());
    }

    @Order(2)
    @Test
    public void name_should_not_more_than_8_length() throws Exception {
        User user = new User("hejiehhhh", 22, "male", "hj@c", "13599999999");
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(user);
        mockMvc.perform(post("/user").content(jsonString).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Order(3)
    @Test
    public void gender_should_not_null() throws Exception {
        User user = new User("hejie", 22, null, "hj@c", "13599999999");
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(user);
        mockMvc.perform(post("/user").content(jsonString).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Order(4)
    @Test
    public void age_should_between_18_to_100() throws Exception {
        User user = new User("hejie", 122, "male", "hj@c", "13599999999");
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(user);
        mockMvc.perform(post("/user").content(jsonString).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Order(5)
    @Test
    public void email_should_be_format() throws Exception {
        User user = new User("hejie", 22, "male", "hehehe", "13599999999");
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(user);
        mockMvc.perform(post("/user").content(jsonString).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Order(6)
    @Test
    public void phone_should_start_1_total_11_number() throws Exception {
        User user = new User("hejie", 22, "male", "hj@c", "135999999999");
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(user);
        mockMvc.perform(post("/user").content(jsonString).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
