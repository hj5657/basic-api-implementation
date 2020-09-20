package com.thoughtworks.rslist.service;

import com.thoughtworks.rslist.po.UserPo;
import com.thoughtworks.rslist.repository.RsEventRepository;
import com.thoughtworks.rslist.repository.UserRepository;
import com.thoughtworks.rslist.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public void updateVoteById(int id, int voteNum) {
        userRepository.updateVoteById(id,voteNum);
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
}
