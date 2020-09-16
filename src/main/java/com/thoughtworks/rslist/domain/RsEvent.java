package com.thoughtworks.rslist.domain;

/**
 * Create by 木水 on 2020/9/15.
 */
public class RsEvent {
    private String eventName;
    private String keyWord;
    private User user;

    public RsEvent() {
    }

    public RsEvent(String eventName, String keyWord ,User user) {
        this.eventName = eventName;
        this.keyWord = keyWord;
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
