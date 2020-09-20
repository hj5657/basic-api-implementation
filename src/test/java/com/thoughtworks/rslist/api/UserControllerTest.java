package com.thoughtworks.rslist.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.rslist.domain.User;
import com.thoughtworks.rslist.po.RsEventPo;
import com.thoughtworks.rslist.po.UserPo;
import com.thoughtworks.rslist.repository.RsEventRepository;
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
    RsEventRepository rsEventRepository;
    @Autowired
    ObjectMapper objectMapper;

    User user;

    @BeforeEach
    void setUp() {
        user = new User("hj", 23, "male", "tx@c", "15599999999");
        rsEventRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void should_register_user() throws Exception {
        String jsonString = objectMapper.writeValueAsString(user);
        mockMvc.perform(post("/user").content(jsonString).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
        List<UserPo> users = userRepository.findAll();
        Assertions.assertEquals(1, users.size());
        Assertions.assertEquals("hj", users.get(0).getUserName());
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
        String jsonString = objectMapper.writeValueAsString(user);
        mockMvc.perform(post("/user").content(jsonString).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
        int id=userRepository.findAll().get(0).getId();
        mockMvc.perform(get("/user/"+id)).andExpect(status().isOk())
        .andExpect(jsonPath("$.userName",is("hj")))
        .andExpect(jsonPath("$.age",is(23)));
    }

    @Test
    public void should_delete_user_by_id() throws Exception {
        String jsonString = objectMapper.writeValueAsString(user);
        mockMvc.perform(post("/user").content(jsonString).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
        int id=userRepository.findAll().get(0).getId();
        mockMvc.perform(delete("/user/{id}",id)).andExpect(status().isOk());

        mockMvc.perform(get("/user/{id}",id)).andExpect(status().isBadRequest());

    }

    @Test
    public void should_delete_user_both_delete_rs() throws Exception {
        UserPo userPo = UserPo.builder().userName("ceshi").phone("18888888888").gender("male").email("a@b")
                .age(19).build();
        userRepository.save(userPo);
        RsEventPo rsEventPo = RsEventPo.builder().eventName("股票账了").keyWord("财经").userPo(userPo).build();
        rsEventRepository.save(rsEventPo);
        mockMvc.perform(delete("/user/{id}", 1))
                .andExpect(status().isOk());
        Assertions.assertEquals(0,rsEventRepository.findAll().size());
        Assertions.assertEquals(0,userRepository.findAll().size());
    }
}
