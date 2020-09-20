package com.thoughtworks.rslist.repository;

import com.thoughtworks.rslist.po.UserPo;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Create by 木水 on 2020/9/17.
 */
public interface UserRepository extends CrudRepository<UserPo, Integer> {
    @Override
    List<UserPo> findAll();

    @Transactional(rollbackOn = Exception.class)
    @Modifying
    @Query(value = "update user set vote_num =:voteNum where id =:id",nativeQuery = true)
    void updateVoteById(int id, int voteNum);
}
