package com.thoughtworks.rslist.repository;

import com.thoughtworks.rslist.po.VotePo;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Create by 木水 on 2020/9/20.
 */
public interface VoteRepository extends CrudRepository<VotePo,Integer> {
    @Override
    List<VotePo> findAll();
}
