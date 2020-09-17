package com.thoughtworks.rslist.repository;

import com.thoughtworks.rslist.po.UserPo;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Create by 木水 on 2020/9/17.
 */
public interface UserRepository extends CrudRepository<UserPo, Integer> {
    @Override
    List<UserPo> findAll();
}
