package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.domain.RsEvent;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RsController {
    private List<RsEvent> rsList = initAddRsEvent();

    private List<RsEvent> initAddRsEvent() {
        List<RsEvent> list = new ArrayList<>();
        list.add(new RsEvent("第一条事件", "无标签"));
        list.add(new RsEvent("第二条事件", "无标签"));
        list.add(new RsEvent("第三条事件", "无标签"));
        return list;
    }

    @GetMapping("/rs/{index}")
    public RsEvent getOneRs(@PathVariable Integer index) {
        return rsList.get(index - 1);
    }

    @GetMapping("/rs/list")
    public List<RsEvent> getRsBetween(@RequestParam(required = false) Integer start, @RequestParam(required = false) Integer end) {
        if (start == null && end == null) {
            return rsList;
        }
        return rsList.subList(start - 1, end);
    }

    @PostMapping("/rs/event")
    public void addRsEvent(@RequestBody RsEvent rsEvent) {
        rsList.add(rsEvent);
    }

    @PostMapping("/rs/update/{index}")
    public void updateRsEvent(@RequestBody RsEvent rsEvent, @PathVariable int index) {
        if (rsEvent.getEventName() == null) {
            rsEvent.setEventName(rsList.get(index - 1).getEventName());
        }
        if (rsEvent.getKeyWord() == null) {
            rsEvent.setKeyWord(rsList.get(index - 1).getKeyWord());
        }
        rsList.set(index - 1, rsEvent);
    }

    @GetMapping("/rs/delete/{index}")
    public void deleteRsEvent(@PathVariable int index) {
        rsList.remove(index - 1);
    }
}
