package com.thoughtworks.rslist.service;

import com.thoughtworks.rslist.domain.User;
import com.thoughtworks.rslist.po.UserPo;
import com.thoughtworks.rslist.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Create by 木水 on 2020/9/20.
 */
@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<UserPo> findById(int userId) {
        return userRepository.findById(userId);
    }

    public UserPo save(UserPo userPo) {
       return userRepository.save(userPo);
    }

    public List<UserPo> findAll() {
        return userRepository.findAll();
    }

    public void deleteById(int id) {
        userRepository.deleteById(id);
    }

    public void deleteAll() {
        userRepository.deleteAll();
    }

    public String registerUser(User user) {
        UserPo userPo = UserPo.builder().age(user.getAge()).email(user.getEmail()).gender(user.getGender())
                .phone(user.getPhone()).userName(user.getUserName()).build();
        userRepository.save(userPo);
        String headerValue = String.valueOf(userPo.getId());
        return headerValue;
    }
}
