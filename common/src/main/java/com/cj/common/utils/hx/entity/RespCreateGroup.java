package com.cj.common.utils.hx.entity;

import lombok.Data;

import java.util.Map;

/**
 * 创建群组 返回实体类
 * Created by JuLei on 2019/6/20.
 */
@Data
public class RespCreateGroup {
    private String action;

    private String application;

    private String uri;

    private Map<String,String> data;

    private String timestamp;

    private Integer duration;

    private String organization;

    private String applicationName;
}
