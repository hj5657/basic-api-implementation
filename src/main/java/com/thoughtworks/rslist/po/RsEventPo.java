package com.thoughtworks.rslist.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Create by 木水 on 2020/9/17.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "rsEvent")
public class RsEventPo {
    @Id
    @GeneratedValue
    private int id;
    private String eventName;
    private String keyWord;
    private int userId;
}
