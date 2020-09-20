package com.thoughtworks.rslist.repository;

import com.thoughtworks.rslist.po.VotePo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDateTime;
import java.util.BitSet;
import java.util.List;

/**
 * Create by 木水 on 2020/9/20.
 */
public interface VoteRepository extends PagingAndSortingRepository<VotePo,Integer> {
    @Override
    List<VotePo> findAll();

    @Query(value = "select * from vote where user_id =:userId and rs_event_id =:rsEventId",nativeQuery = true)
    List<VotePo> findByUserIdAndRsEventId(int userId, int rsEventId, Pageable pageable);

    @Query(value = "select * from vote where time between :startTime and :endTime",nativeQuery = true)
    List<VotePo> findBetweenTime(String startTime, String endTime);
}
