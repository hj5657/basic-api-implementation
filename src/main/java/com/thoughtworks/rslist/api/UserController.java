package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.domain.User;
import com.thoughtworks.rslist.po.UserPo;
import com.thoughtworks.rslist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * Create by 木水 on 2020/9/16.
 */
@RestController
public class UserController {
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
        List<UserPo> users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/user/{index}")
    public ResponseEntity getUserById(@PathVariable int index){
        Optional<UserPo> user = userRepository.findById(index);
        if (!user.isPresent()){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(user.get());
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity deleteUserById(@PathVariable int id){
        userRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
