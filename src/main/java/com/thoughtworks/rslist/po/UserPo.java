package com.thoughtworks.rslist.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

/**
 * Create by 木水 on 2020/9/17.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class UserPo {
    @Id
    @GeneratedValue
    private int id;
    private String userName;
    private int age;
    private String gender;
    private String email;
    private String phone;
    @Builder.Default
    private int voteNum = 10;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "userPo")
    private List<RsEventPo> rsEventPos;

}
