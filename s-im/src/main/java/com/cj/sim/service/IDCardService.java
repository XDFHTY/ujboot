package com.cj.sim.service;

import org.json.JSONObject;

/**
 * 百度身份证识别接口
 * Created by XD on 2019/3/9.
 */
public interface IDCardService {

    /**
     * 身份证识别接口
     * @param idCardUrl =身份证照片绝对路径
     *            idCardSide= 1-正面照  2-背面照
     * @return
     */
    JSONObject IDCardRecogize(String idCardUrl, String idCardSide);
}
