package com.thoughtworks.rslist.service;

import com.thoughtworks.rslist.domain.Vote;
import com.thoughtworks.rslist.po.VotePo;
import com.thoughtworks.rslist.repository.VoteRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Create by 木水 on 2020/9/20.
 */
@Service
public class VoteService {
    private VoteRepository voteRepository;

    public VoteService(VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    public void save(VotePo votePo) {
        voteRepository.save(votePo);
    }

    public void deleteAll() {
        voteRepository.deleteAll();
    }

    public List<VotePo> findAll() {
        return voteRepository.findAll();
    }

    public ResponseEntity<List<Vote>> getVoteRecord(int userId, int rsEventId, int pageIndex) {
        Pageable pageable = PageRequest.of(pageIndex - 1, 5);
        return ResponseEntity.ok(
                voteRepository.findByUserIdAndRsEventId(userId, rsEventId, pageable).stream().map(
                        item -> Vote.builder().userId(item.getUserPo().getId())
                                .rsEventId(item.getRsEventPo().getId())
                                .voteNum(item.getNum()).time(item.getTime()).build()
                ).collect(Collectors.toList())
        );
    }

    public ResponseEntity getVoteRecordOfTime(String startTime, String endTime) {
        return ResponseEntity.ok(
                voteRepository.findBetweenTime(startTime, endTime).stream().map(
                        item -> Vote.builder().userId(item.getUserPo().getId())
                                .rsEventId(item.getRsEventPo().getId())
                                .voteNum(item.getNum()).time(item.getTime()).build()
                ).collect(Collectors.toList())
        );
    }
}
