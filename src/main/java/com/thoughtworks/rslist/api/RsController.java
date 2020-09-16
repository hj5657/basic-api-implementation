package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.domain.RsEvent;
import com.thoughtworks.rslist.domain.User;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RsController {
    private List<RsEvent> rsList = initAddRsEvent();

    private List<RsEvent> initAddRsEvent() {
        List<RsEvent> list = new ArrayList<>();
        User user = new User("hejie", 22, "male", "hj@c", "13599999999");
        list.add(new RsEvent("第一条事件", "无标签", user));
        list.add(new RsEvent("第二条事件", "无标签", user));
        list.add(new RsEvent("第三条事件", "无标签", user));
        return list;
    }

    @GetMapping("/init")
    public void init() {
        initAddRsEvent();
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

}
