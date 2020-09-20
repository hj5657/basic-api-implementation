package com.thoughtworks.rslist.repository;

import com.thoughtworks.rslist.po.RsEventPo;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Create by 木水 on 2020/9/17.
 */
public interface RsEventRepository extends CrudRepository<RsEventPo, Integer> {
    @Override
    List<RsEventPo> findAll();


    @Transactional(rollbackOn = Exception.class)
    @Modifying
    @Query(value = "update rs_event set event_name =:eventName, key_word =:keyWord where id =:id",nativeQuery = true)
    void updateEventNameAndKeyWordById(int id, String eventName, String keyWord);
}
