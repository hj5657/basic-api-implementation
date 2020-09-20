package com.thoughtworks.rslist.service;

import com.thoughtworks.rslist.po.RsEventPo;
import com.thoughtworks.rslist.repository.RsEventRepository;
import com.thoughtworks.rslist.repository.UserRepository;
import com.thoughtworks.rslist.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Create by 木水 on 2020/9/20.
 */
@Service
public class RsService {
    @Autowired
    RsEventRepository rsEventRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    VoteRepository voteRepository;

    public Optional<RsEventPo> findById(Integer id) {
        return rsEventRepository.findById(id);
    }

    public List<RsEventPo> findAll() {
        return rsEventRepository.findAll();
    }

    public RsEventPo save(RsEventPo rsEventPo) {
        return rsEventRepository.save(rsEventPo);
    }

    public void updateEventNameAndKeyWordById(int rsEventId, String eventName, String keyWord) {
        rsEventRepository.updateEventNameAndKeyWordById(rsEventId,eventName,keyWord);
    }

    public void updateVoteById(int rsEventId, int voteNum) {
        rsEventRepository.updateVoteById(rsEventId,voteNum);
    }

    public void deleteAll() {
        rsEventRepository.deleteAll();
    }
}
