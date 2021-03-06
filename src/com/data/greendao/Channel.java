package com.data.greendao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table CHANNEL.
 */
public class Channel {

    private Long id;
    private String name;
    private Long user_id;
    private String privacy;

    public Channel() {
    }

    public Channel(Long id) {
        this.id = id;
    }

    public Channel(Long id, String name, Long user_id, String privacy) {
        this.id = id;
        this.name = name;
        this.user_id = user_id;
        this.privacy = privacy;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getPrivacy() {
        return privacy;
    }

    public void setPrivacy(String privacy) {
        this.privacy = privacy;
    }

}
