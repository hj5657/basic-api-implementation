package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.domain.RsEvent;
import com.thoughtworks.rslist.domain.Vote;
import com.thoughtworks.rslist.exception.InvalidIndexException;
import com.thoughtworks.rslist.po.RsEventPo;
import com.thoughtworks.rslist.service.RsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class RsController {

    private RsService rsService;

    public RsController(RsService rsService) {
        this.rsService = rsService;
    }

    @GetMapping("/rs/{id}")
    public ResponseEntity getOneRs(@PathVariable Integer id) {
        Optional<RsEventPo> rsEventPo = rsService.findById(id);
        if (!rsEventPo.isPresent()) {
            throw new InvalidIndexException("invalid index");
        }
        return ResponseEntity.ok(rsEventPo.get());
    }

    @GetMapping("/rs/list")
    public ResponseEntity getRsList() {
        List<RsEventPo> rsEvents = rsService.findAll();
        return ResponseEntity.ok(rsEvents);
    }

    @PostMapping("/rs/event")
    public ResponseEntity addRsEvent(@RequestBody @Valid RsEvent rsEvent) {
        rsService.addRsEvent(rsEvent);
        return ResponseEntity.created(null).build();
    }

    @PatchMapping("/rs/{rsEventId}")
    public ResponseEntity updateRsEvent(@PathVariable int rsEventId, @RequestBody RsEvent rsEvent) {
        return rsService.updateRsEvent(rsEventId,rsEvent);
    }

    @PostMapping("/rs/vote/{rsEventId}")
    public ResponseEntity vote(@PathVariable int rsEventId, @RequestBody Vote vote) {
        return rsService.vote(rsEventId,vote);
    }
}
