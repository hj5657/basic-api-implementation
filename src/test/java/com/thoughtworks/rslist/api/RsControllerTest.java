package com.thoughtworks.rslist.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.rslist.domain.RsEvent;
import com.thoughtworks.rslist.domain.User;
import com.thoughtworks.rslist.po.RsEventPo;
import com.thoughtworks.rslist.po.UserPo;
import com.thoughtworks.rslist.repository.RsEventRepository;
import com.thoughtworks.rslist.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Create by 木水 on 2020/9/15.
 */
@SpringBootTest
@AutoConfigureMockMvc
public class RsControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RsEventRepository rsEventRepository;
    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    public void should_get_rs_list() throws Exception {
        mockMvc.perform(get("/rs/list"))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].eventName", is("第一条事件")))
                .andExpect(jsonPath("$[0].keyWord", is("无标签")))
                .andExpect(jsonPath("$[0]", not(hasKey("user"))))
                .andExpect(jsonPath("$[1].eventName", is("第二条事件")))
                .andExpect(jsonPath("$[1].keyWord", is("无标签")))
                .andExpect(jsonPath("$[1]", not(hasKey("user"))))
                .andExpect(jsonPath("$[2].eventName", is("第三条事件")))
                .andExpect(jsonPath("$[2].keyWord", is("无标签")))
                .andExpect(jsonPath("$[2]", not(hasKey("user"))))
                .andExpect(status().isOk());
    }

    @Test
    public void should_get_one_rs() throws Exception {
        mockMvc.perform(get("/rs/1"))
                .andExpect(jsonPath("$.eventName", is("第一条事件")))
                .andExpect(jsonPath("$.keyWord", is("无标签")))
                .andExpect(status().isOk());
        mockMvc.perform(get("/rs/2"))
                .andExpect(jsonPath("$.eventName", is("第二条事件")))
                .andExpect(jsonPath("$.keyWord", is("无标签")))
                .andExpect(status().isOk());
        mockMvc.perform(get("/rs/3"))
                .andExpect(jsonPath("$.eventName", is("第三条事件")))
                .andExpect(jsonPath("$.keyWord", is("无标签")))
                .andExpect(status().isOk());
    }

    @Test
    public void should_get_rs_event_between() throws Exception {
        mockMvc.perform(get("/rs/list?start=1&end=2"))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].eventName", is("第一条事件")))
                .andExpect(jsonPath("$[0].keyWord", is("无标签")))
                .andExpect(jsonPath("$[1].eventName", is("第二条事件")))
                .andExpect(jsonPath("$[1].keyWord", is("无标签")))
                .andExpect(status().isOk());

    }

    @Test
    public void should_add_rs_event_when_user_exist() throws Exception {
        UserPo saveUser = userRepository.save(UserPo.builder().age(22).email("hj@c").gender("male").phone("13599999999")
                .userName("hejie").build());
        RsEvent rsEvent = new RsEvent("猪肉涨价了", "经济", saveUser.getId());
        String jsonString = objectMapper.writeValueAsString(rsEvent);
        mockMvc.perform(post("/rs/event").content(jsonString)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        List<RsEventPo> rsEvents = rsEventRepository.findAll();
        Assertions.assertNotNull(rsEvents);
        Assertions.assertEquals(1,rsEvents.size());
        Assertions.assertEquals("猪肉涨价了",rsEvents.get(0).getEventName());
        Assertions.assertEquals("经济",rsEvents.get(0).getKeyWord());
        Assertions.assertEquals(saveUser.getId(),rsEvents.get(0).getUserId());
    }

    @Test
    public void should_not_add_rs_event_when_user_not_exist() throws Exception {
        RsEvent rsEvent = new RsEvent("猪肉涨价了", "经济", 100);
        String jsonString = objectMapper.writeValueAsString(rsEvent);
        mockMvc.perform(post("/rs/event").content(jsonString)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }



    @Test
    public void should_return_invalid_param_exception() throws Exception {
        mockMvc.perform(get("/rs/list?start=1&end=5"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", is("invalid request param")));
    }

    @Test
    public void should_return_invalid_index_exception() throws Exception {
        mockMvc.perform(get("/rs/0"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", is("invalid index")));
    }

    @Test
    public void should_return_method_argument_not_valid_exception() throws Exception {
        RsEvent rsEvent = new RsEvent(null, "经济", 2);
        String jsonString = objectMapper.writeValueAsString(rsEvent);
        mockMvc.perform(post("/rs/event").content(jsonString)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", is("invalid param")));
    }
}
