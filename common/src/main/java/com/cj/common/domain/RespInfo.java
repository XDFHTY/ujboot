package com.cj.common.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespInfo {

    //用户ID
    private long userId;

    private String userType;

    private String name;

    private String nickname;

    private String headUrl;

    private List<Long> bindIds;
}
