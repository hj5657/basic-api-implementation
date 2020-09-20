package com.thoughtworks.rslist.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Create by 木水 on 2020/9/20.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "vote")
public class VotePo {
    @Id
    @GeneratedValue
    private int id;
    private int num;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalDateTime time;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserPo userPo;

    @ManyToOne
    @JoinColumn(name = "rs_event_id")
    private RsEventPo rsEventPo;
}
