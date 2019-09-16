package com.cj.common.utils.hx.entity;

import java.util.List;
import java.util.Map;

/**
 * 环信 图片上传 返回实体类
 * Created by XD on 2019/2/28.
 */
public class RespUploadImgFile {
    private String action;

    private String application;

    private String path;

    private String uri;

    private List<Map<String,String>> entities;

    private String timestamp;

    private Integer duration;

    private String organization;

    private String applicationName;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public List<Map<String, String>> getEntities() {
        return entities;
    }

    public void setEntities(List<Map<String, String>> entities) {
        this.entities = entities;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }
}
