package com.cj.core.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @param <T>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OldApiResult<T> {

    /**
     * 返回码
     */
    private int code;

    /**
     * 返回信息描述
     */
    private String msg;

    /**
     * 返回数据
     */
    private T data;


    /**
     * 其他
     */
    protected Object params;

}