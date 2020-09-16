package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.domain.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * Create by 木水 on 2020/9/16.
 */
@RestController
public class UserController {
    List<User> userList = new ArrayList<>();
    @PostMapping("/user")
    public ResponseEntity addUser(@RequestBody @Valid User user){
        userList.add(user);
        return ResponseEntity.created(null).build();
    }

    @GetMapping("/user")
    public ResponseEntity getUserList(){
        return ResponseEntity.ok(userList);
    }
}
