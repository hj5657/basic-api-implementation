package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.domain.Vote;
import com.thoughtworks.rslist.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

/**
 * Create by 木水 on 2020/9/20.
 */
@RestController
public class VoteController {
    @Autowired
    VoteRepository voteRepository;

    @GetMapping("/voteRecord")
    public ResponseEntity getVoteRecord(@RequestParam int userId, @RequestParam int rsEventId,
                                        @RequestParam int pageIndex) {
        Pageable pageable = PageRequest.of(pageIndex - 1, 5);
        return ResponseEntity.ok(
                voteRepository.findByUserIdAndRsEventId(userId, rsEventId, pageable).stream().map(
                        item -> Vote.builder().userId(item.getUserPo().getId())
                                .rsEventId(item.getRsEventPo().getId())
                                .voteNum(item.getNum()).time(item.getTime()).build()
                ).collect(Collectors.toList())
        );
    }
    @GetMapping("/vote/timeList")
    public ResponseEntity getVoteRecordOfTime(@RequestParam String startTime,
                                              @RequestParam String endTime){

        return ResponseEntity.ok(
          voteRepository.findBetweenTime(startTime, endTime).stream().map(
                  item -> Vote.builder().userId(item.getUserPo().getId())
                          .rsEventId(item.getRsEventPo().getId())
                          .voteNum(item.getNum()).time(item.getTime()).build()
          ).collect(Collectors.toList())
        );
    }
}
