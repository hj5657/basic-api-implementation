package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.domain.RsEvent;
import com.thoughtworks.rslist.domain.User;
import com.thoughtworks.rslist.exception.InvalidIndexException;
import com.thoughtworks.rslist.exception.InvalidParamException;
import com.thoughtworks.rslist.po.RsEventPo;
import com.thoughtworks.rslist.po.UserPo;
import com.thoughtworks.rslist.repository.RsEventRepository;
import com.thoughtworks.rslist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class RsController {
    @Autowired
    RsEventRepository rsEventRepository;
    @Autowired
    UserRepository userRepository;

    @GetMapping("/rs/{index}")
    public ResponseEntity getOneRs(@PathVariable Integer index) {
        List<RsEventPo> rsEvents = rsEventRepository.findAll();
        if (index <= 0 || index > rsEvents.size()) {
            throw new InvalidIndexException("invalid index");
        }
        return ResponseEntity.ok(rsEvents.get(index - 1));
    }

    @GetMapping("/rs/list")
    public ResponseEntity getRsBetween(@RequestParam(required = false) Integer start, @RequestParam(required = false) Integer end) {
        List<RsEventPo> rsEvents = rsEventRepository.findAll();
        if (start == null && end == null) {
            return ResponseEntity.ok(rsEvents);
        }
        if (start <= 0 || end > rsEvents.size()) {
            throw new InvalidParamException("invalid request param");
        }
        return ResponseEntity.ok(rsEvents.subList(start - 1, end));
    }

    @PostMapping("/rs/event")
    public ResponseEntity addRsEvent(@RequestBody @Valid RsEvent rsEvent) {
        Optional<UserPo> userPo = userRepository.findById(rsEvent.getUserId());
        if (!userPo.isPresent()) {
            return ResponseEntity.badRequest().build();
        }
        RsEventPo rsEventPo = RsEventPo.builder().eventName(rsEvent.getEventName()).keyWord(rsEvent.getKeyWord())
                .userPo(userPo.get()).build();
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
}
