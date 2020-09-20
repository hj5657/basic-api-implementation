package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.service.VoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Create by 木水 on 2020/9/20.
 */
@RestController
public class VoteController {
    private VoteService voteService;

    public VoteController(VoteService voteService) {
        this.voteService = voteService;
    }

    @GetMapping("/voteRecord")
    public ResponseEntity getVoteRecord(@RequestParam int userId, @RequestParam int rsEventId,
                                        @RequestParam int pageIndex) {
        return voteService.getVoteRecord(userId,rsEventId,pageIndex);
    }
    @GetMapping("/vote/timeList")
    public ResponseEntity getVoteRecordOfTime(@RequestParam String startTime,
                                              @RequestParam String endTime){
        return voteService.getVoteRecordOfTime(startTime,endTime);
    }
}
