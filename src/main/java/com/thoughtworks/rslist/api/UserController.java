package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.domain.User;
import com.thoughtworks.rslist.po.UserPo;
import com.thoughtworks.rslist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    UserRepository userRepository;
    @PostMapping("/user")
    public ResponseEntity registerUser(@RequestBody @Valid User user) {
        UserPo userPo = UserPo.builder().age(user.getAge()).email(user.getEmail()).gender(user.getGender())
                .phone(user.getPhone()).userName(user.getUserName()).build();
        userRepository.save(userPo);
        String headerValue = String.valueOf(userRepository.findAll().size()-1);
        return ResponseEntity.created(null).header("index", headerValue).build();
    }

    @GetMapping("/users")
    public ResponseEntity getUserList() {
        return ResponseEntity.ok(userList);
    }

}
