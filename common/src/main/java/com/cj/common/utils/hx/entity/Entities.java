package com.cj.common.utils.hx.entity;

/**
 * 注册时返回对象
 * Created by XD on 2019/2/27.
 */
public class Entities {

    //用户的UUID，标识字段
    private String uuid;

    //“user”用户类型
    private String type;

    //用户名，也就是环信 ID
    private String username;

    //用户是否已激活，“true”已激活，“false“封禁，封禁需要通过解禁接口进行解禁，才能正常登录
    private Boolean activated;

    private Long created;

    private Long modified;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean getActivated() {
        return activated;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    public Long getCreated() {
        return created;
    }

    public void setCreated(Long created) {
        this.created = created;
    }

    public Long getModified() {
        return modified;
    }

    public void setModified(Long modified) {
        this.modified = modified;
    }
}
