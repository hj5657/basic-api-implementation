package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * Create by 木水 on 2020/9/20.
 */
@RestController
public class VoteController {
    @Autowired
    VoteRepository voteRepository;
}
