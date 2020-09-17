package com.thoughtworks.rslist.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.rslist.domain.User;
import com.thoughtworks.rslist.po.UserPo;
import com.thoughtworks.rslist.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Create by 木水 on 2020/9/16.
 */
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserRepository userRepository;
    @Autowired
    ObjectMapper objectMapper;

    User user;

    @BeforeEach
    void setUp() {
        user = new User("hj", 23, "male", "tx@c", "15599999999");
    }

    @Test
    public void should_register_user() throws Exception {
        String jsonString = objectMapper.writeValueAsString(user);
        mockMvc.perform(post("/user").content(jsonString).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(header().stringValues("index", "1"));
        List<UserPo> users = userRepository.findAll();
        Assertions.assertEquals(2, users.size());
        Assertions.assertEquals("tianxin", users.get(0).getUserName());
        Assertions.assertEquals(23, users.get(0).getAge());

    }

    @Test
    public void name_should_not_more_than_8_length() throws Exception {
        user.setUserName("hejiehhhh");
        String jsonString = objectMapper.writeValueAsString(user);
        mockMvc.perform(post("/user").content(jsonString).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void age_should_between_18_to_100() throws Exception {
        user.setAge(122);
        String jsonString = objectMapper.writeValueAsString(user);
        mockMvc.perform(post("/user").content(jsonString).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void phone_should_start_1_total_11_number() throws Exception {
        user.setPhone("135999999999");
        String jsonString = objectMapper.writeValueAsString(user);
        mockMvc.perform(post("/user").content(jsonString).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void should_get_user_by_id() throws Exception {
        mockMvc.perform(get("/user/1")).andExpect(status().isOk())
        .andExpect(jsonPath("$.userName",is("tianxin")))
        .andExpect(jsonPath("$.age",is(23)));
    }

    @Test
    public void should_delete_user_by_id() throws Exception {
        mockMvc.perform(delete("/user/1")).andExpect(status().isOk());

        mockMvc.perform(get("/user/1")).andExpect(status().isBadRequest());

    }
}
