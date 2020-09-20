package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.domain.RsEvent;
import com.thoughtworks.rslist.domain.Vote;
import com.thoughtworks.rslist.exception.InvalidIndexException;
import com.thoughtworks.rslist.exception.InvalidParamException;
import com.thoughtworks.rslist.po.RsEventPo;
import com.thoughtworks.rslist.po.UserPo;
import com.thoughtworks.rslist.po.VotePo;
import com.thoughtworks.rslist.repository.RsEventRepository;
import com.thoughtworks.rslist.repository.UserRepository;
import com.thoughtworks.rslist.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
public class RsController {
    @Autowired
    RsEventRepository rsEventRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    VoteRepository voteRepository;

    @GetMapping("/rs/{id}")
    public ResponseEntity getOneRs(@PathVariable Integer id) {
        Optional<RsEventPo> rsEventPo = rsEventRepository.findById(id);
        if (!rsEventPo.isPresent()) {
            throw new InvalidIndexException("invalid index");
        }
        return ResponseEntity.ok(rsEventPo.get());
    }

    @GetMapping("/rs/list")
    public ResponseEntity getRsList() {
        List<RsEventPo> rsEvents = rsEventRepository.findAll();
        return ResponseEntity.ok(rsEvents);
    }

    @PostMapping("/rs/event")
    public ResponseEntity addRsEvent(@RequestBody @Valid RsEvent rsEvent) {
        Optional<UserPo> userPo = userRepository.findById(rsEvent.getUserId());
        if (!userPo.isPresent()) {
            return ResponseEntity.badRequest().build();
        }
        RsEventPo rsEventPo = RsEventPo.builder().eventName(rsEvent.getEventName()).keyWord(rsEvent.getKeyWord())
                .userPo(userPo.get()).voteNum(0).build();
        rsEventRepository.save(rsEventPo);
        return ResponseEntity.created(null).build();
    }

    @PatchMapping("/rs/{rsEventId}")
    public ResponseEntity updateRsEvent(@PathVariable int rsEventId, @RequestBody RsEvent rsEvent) {
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

    @PostMapping("/rs/vote/{rsEventId}")
    public ResponseEntity vote(@PathVariable int rsEventId, @RequestBody Vote vote) {
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
        rsEventRepository.updateVoteById(rsEventId, totalVoteNum - voteNum);
        return ResponseEntity.ok().build();
    }
}
