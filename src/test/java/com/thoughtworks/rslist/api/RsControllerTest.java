package com.thoughtworks.rslist.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Create by 木水 on 2020/9/15.
 */
@SpringBootTest
@AutoConfigureMockMvc
public class RsControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Test
    public void should_return_rs_list() throws Exception {
        mockMvc.perform(get("/rs/list")).andExpect(content().string("[第一条事件, 第二条事件, 第三条事件]"))
                .andExpect(status().isOk());
    }
    @Test
    public void should_return_one_rs() throws Exception {
        mockMvc.perform(get("/rs/1")).andExpect(content().string("第一条事件"))
                .andExpect(status().isOk());
        mockMvc.perform(get("/rs/2")).andExpect(content().string("第二条事件"))
                .andExpect(status().isOk());
        mockMvc.perform(get("/rs/3")).andExpect(content().string("第三条事件"))
                .andExpect(status().isOk());
    }
    @Test
    public void should_return_rs_between() throws Exception {
        mockMvc.perform(get("/rs/list?start=1&end=2"))
                .andExpect(content().string("[第一条事件, 第二条事件]"))
                .andExpect(status().isOk());
        mockMvc.perform(get("/rs/list?start=2&end=3"))
                .andExpect(content().string("[第二条事件, 第三条事件]"))
                .andExpect(status().isOk());
        mockMvc.perform(get("/rs/list?start=1&end=3"))
                .andExpect(content().string("[第一条事件, 第二条事件, 第三条事件]"))
                .andExpect(status().isOk());
    }
}
