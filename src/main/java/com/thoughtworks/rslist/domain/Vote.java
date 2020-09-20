package com.thoughtworks.rslist.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Create by 木水 on 2020/9/20.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Vote {
    private int voteNum;
    private int userId;
    private int rsEventId;
    private LocalDateTime time;
}
