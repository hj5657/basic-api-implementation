package com.thoughtworks.rslist.service;

import com.thoughtworks.rslist.domain.RsEvent;
import com.thoughtworks.rslist.domain.Vote;
import com.thoughtworks.rslist.po.RsEventPo;
import com.thoughtworks.rslist.po.UserPo;
import com.thoughtworks.rslist.po.VotePo;
import com.thoughtworks.rslist.repository.RsEventRepository;
import com.thoughtworks.rslist.repository.UserRepository;
import com.thoughtworks.rslist.repository.VoteRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Create by 木水 on 2020/9/20.
 */
@Configuration
public class RsService {

    private RsEventRepository rsEventRepository;
    private UserRepository userRepository;
    private VoteRepository voteRepository;

    public RsService(RsEventRepository rsEventRepository, UserRepository userRepository, VoteRepository voteRepository) {
        this.rsEventRepository = rsEventRepository;
        this.userRepository = userRepository;
        this.voteRepository = voteRepository;
    }

    @Bean
    public RsService init(){
        return new RsService(rsEventRepository,userRepository,voteRepository);
    }
    public Optional<RsEventPo> findById(Integer id) {
        return rsEventRepository.findById(id);
    }

    public List<RsEventPo> findAll() {
        return rsEventRepository.findAll();
    }

    public RsEventPo save(RsEventPo rsEventPo) {
        return rsEventRepository.save(rsEventPo);
    }

    public void deleteAll() {
        rsEventRepository.deleteAll();
    }

    public ResponseEntity vote(int rsEventId, Vote vote) {
        Optional<RsEventPo> rsEventPo = rsEventRepository.findById(rsEventId);
        Optional<UserPo> userPo = userRepository.findById(vote.getUserId());
        int voteNum = vote.getVoteNum();
        int totalVoteNum = userPo.get().getVoteNum();
        if (!rsEventPo.isPresent() || !userPo.isPresent() || voteNum > totalVoteNum) {
            return ResponseEntity.badRequest().build();
        }
        VotePo votePo = VotePo.builder().num(voteNum).userPo(userPo.get())
                .rsEventPo(rsEventPo.get()).time(LocalDateTime.now()).build();
        voteRepository.save(votePo);
        userRepository.updateVoteById(userPo.get().getId(), totalVoteNum - voteNum);
        rsEventRepository.updateVoteById(rsEventId, rsEventPo.get().getVoteNum() + voteNum);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity updateRsEvent(int rsEventId, RsEvent rsEvent) {
        Optional<RsEventPo> rsEventPo = rsEventRepository.findById(rsEventId);
        if (!rsEventPo.isPresent() || rsEvent.getUserId() != rsEventPo.get().getUserPo().getId()) {
            return ResponseEntity.badRequest().build();
        }
        if (rsEvent.getEventName() == null) {
            rsEvent.setEventName(rsEventPo.get().getEventName());
        }
        if (rsEvent.getKeyWord() == null) {
            rsEvent.setKeyWord(rsEventPo.get().getKeyWord());
        }
        rsEventRepository.updateEventNameAndKeyWordById(rsEventId, rsEvent.getEventName(), rsEvent.getKeyWord());
        return ResponseEntity.ok().build();
    }

    public void addRsEvent(RsEvent rsEvent) {
        Optional<UserPo> userPo = userRepository.findById(rsEvent.getUserId());
        if (!userPo.isPresent()) {
            throw new RuntimeException();
        }
        RsEventPo rsEventPo = RsEventPo.builder().eventName(rsEvent.getEventName()).keyWord(rsEvent.getKeyWord())
                .userPo(userPo.get()).voteNum(0).build();
        rsEventRepository.save(rsEventPo);
    }
}
