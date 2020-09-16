package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.domain.RsEvent;
import com.thoughtworks.rslist.domain.User;
import com.thoughtworks.rslist.exception.InvalidParamException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
public class RsController {
    private List<RsEvent> rsList = initAddRsEvent();
    private List<User> userList = initAddUser();

    private List<User> initAddUser() {
        List<User> list = new ArrayList<>();
        list.add(new User("hejie", 22, "male", "hj@c", "13599999999"));
        return list;
    }

    private List<RsEvent> initAddRsEvent() {
        List<RsEvent> list = new ArrayList<>();
        User user = new User("hejie", 22, "male", "hj@c", "13599999999");
        list.add(new RsEvent("第一条事件", "无标签", user));
        list.add(new RsEvent("第二条事件", "无标签", user));
        list.add(new RsEvent("第三条事件", "无标签", user));
        return list;
    }

    @GetMapping("/rs/{index}")
    public ResponseEntity getOneRs(@PathVariable Integer index) {
        return ResponseEntity.ok(rsList.get(index - 1));
    }

    @GetMapping("/rs/list")
    public ResponseEntity getRsBetween(@RequestParam(required = false) Integer start, @RequestParam(required = false) Integer end) {
        if (start == null && end == null) {
            return ResponseEntity.ok(rsList);
        }
        if (start <= 0 || end > rsList.size()) {
            throw new InvalidParamException("invalid request param");
        }
        return ResponseEntity.ok(rsList.subList(start - 1, end));
    }

    @PostMapping("/rs/event")
    public ResponseEntity addRsEvent(@RequestBody @Valid RsEvent rsEvent) {
        String headerValue = "";
        rsList.add(rsEvent);
        headerValue = String.valueOf(rsList.size() - 1);
        return ResponseEntity.created(null).header("index", headerValue).build();
    }

}
