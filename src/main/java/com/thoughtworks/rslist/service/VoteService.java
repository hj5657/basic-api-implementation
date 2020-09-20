package com.thoughtworks.rslist.service;

import com.thoughtworks.rslist.po.VotePo;
import com.thoughtworks.rslist.repository.RsEventRepository;
import com.thoughtworks.rslist.repository.UserRepository;
import com.thoughtworks.rslist.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.BitSet;
import java.util.List;

/**
 * Create by 木水 on 2020/9/20.
 */
@Service
public class VoteService {
    @Autowired
    RsEventRepository rsEventRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    VoteRepository voteRepository;
    public void save(VotePo votePo) {
        voteRepository.save(votePo);
    }

    public List<VotePo> findByUserIdAndRsEventId(int userId, int rsEventId, Pageable pageable) {
        return voteRepository.findByUserIdAndRsEventId(userId,rsEventId,pageable);
    }

    public List<VotePo> findBetweenTime(String startTime, String endTime) {
        return voteRepository.findBetweenTime(startTime,endTime);
    }

    public void deleteAll() {
        voteRepository.deleteAll();
    }

    public List<VotePo> findAll() {
        return voteRepository.findAll();
    }
}
