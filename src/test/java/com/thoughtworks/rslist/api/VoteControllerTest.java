package com.thoughtworks.rslist.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.rslist.po.RsEventPo;
import com.thoughtworks.rslist.po.UserPo;
import com.thoughtworks.rslist.po.VotePo;
import com.thoughtworks.rslist.service.RsService;
import com.thoughtworks.rslist.service.UserService;
import com.thoughtworks.rslist.service.VoteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class VoteControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    UserService userService;
    @Autowired
    RsService rsService;
    @Autowired
    VoteService voteService;
    private UserPo saveUser;
    private RsEventPo rsEventPo;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        voteService.deleteAll();
        rsService.deleteAll();
        userService.deleteAll();
        saveUser = UserPo.builder().age(22).email("hj@c").gender("male").phone("13599999999")
                .userName("hejie").build();
        saveUser = userService.save(saveUser);
        rsEventPo = RsEventPo.builder().eventName("基金涨了").keyWord("经济").voteNum(0).build();
        rsEventPo = rsService.save(rsEventPo);
    }

    @Test
    void should_get_vote_record() throws Exception {
        for (int i = 0; i < 8; i++) {
            VotePo votePo = VotePo.builder().userPo(saveUser).rsEventPo(rsEventPo)
                    .time(LocalDateTime.now()).num(i + 1).build();
            voteService.save(votePo);
        }

        mockMvc.perform(get("/voteRecord").param("userId", String.valueOf(saveUser.getId()))
                .param("rsEventId", String.valueOf(rsEventPo.getId()))
                .param("pageIndex", String.valueOf(1)))
                .andExpect(jsonPath("$", hasSize(5)))
                .andExpect(jsonPath("$[0].userId", is(saveUser.getId())))
                .andExpect(jsonPath("$[0].rsEventId", is(rsEventPo.getId())))
                .andExpect(jsonPath("$[0].voteNum", is(1)))
                .andExpect(jsonPath("$[1].voteNum", is(2)))
                .andExpect(jsonPath("$[2].voteNum", is(3)))
                .andExpect(jsonPath("$[3].voteNum", is(4)))
                .andExpect(jsonPath("$[4].voteNum", is(5)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/voteRecord").param("userId", String.valueOf(saveUser.getId()))
                .param("rsEventId", String.valueOf(rsEventPo.getId()))
                .param("pageIndex", String.valueOf(2)))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].userId", is(saveUser.getId())))
                .andExpect(jsonPath("$[0].rsEventId", is(rsEventPo.getId())))
                .andExpect(jsonPath("$[0].voteNum", is(6)))
                .andExpect(jsonPath("$[1].voteNum", is(7)))
                .andExpect(jsonPath("$[2].voteNum", is(8)))
                .andExpect(status().isOk());
    }

    @Test
    void should_return_vote_record_of_time() throws Exception {
        String startTime = String.valueOf(LocalDateTime.now().plusHours(1));
        for (int i = 0; i < 8; i++) {
            VotePo votePo = VotePo.builder().userPo(saveUser).rsEventPo(rsEventPo)
                    .time(LocalDateTime.now().plusHours(i)).num(i + 1).build();
            voteService.save(votePo);
        }
        String endTime = String.valueOf(LocalDateTime.now().plusHours(5));
        mockMvc.perform(get("/vote/timeList").param("startTime", startTime)
                .param("endTime", endTime))
                .andExpect(jsonPath("$",hasSize(5)))
                .andExpect(status().isOk());
    }
}