package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.domain.User;
import com.thoughtworks.rslist.po.UserPo;
import com.thoughtworks.rslist.repository.UserRepository;
import com.thoughtworks.rslist.service.UserService;
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
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user")
    public ResponseEntity registerUser(@RequestBody @Valid User user) {
        UserPo userPo = UserPo.builder().age(user.getAge()).email(user.getEmail()).gender(user.getGender())
                .phone(user.getPhone()).userName(user.getUserName()).build();
        userService.save(userPo);
        String headerValue = String.valueOf(userPo.getId());
        return ResponseEntity.created(null).header("index", headerValue).build();
    }

    @GetMapping("/users")
    public ResponseEntity getUserList() {
        List<UserPo> users = userService.findAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/user/{index}")
    public ResponseEntity getUserById(@PathVariable int index){
        Optional<UserPo> user = userService.findById(index);
        if (!user.isPresent()){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(user.get());
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity deleteUserById(@PathVariable int id){
        userService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
