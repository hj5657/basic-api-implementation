package com.thoughtworks.rslist.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Create by 木水 on 2020/9/15.
 */
public class RsEvent {
    @NotNull
    private String eventName;
    @NotNull
    private String keyWord;
    private int userId;

    public RsEvent() {
    }

    public RsEvent(String eventName, String keyWord ,int userId) {
        this.eventName = eventName;
        this.keyWord = keyWord;
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }
}
