package com.cj.skidney.service.impl;

import com.cj.core.exception.MyException;
import com.cj.core.domain.ApiResult;
import com.cj.core.util.rest.RestUtil;
import com.cj.skidney.service.KidneyCallImService;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author： 刘磊
 * @Description: 调用推送实现
 * @date： 2019/4/10 16:31
 **/
@Component
public class KidneyCallImServiceImpl implements KidneyCallImService {
    RestUtil restUtil = new RestUtil();
    /**
     * 推送异常信息给医生
     * @param map
     * @return
     */
    @Override
    public ApiResult buildPushObjectAllAliasAlert(Map map) {
        return restUtil.postJson("/s-im/api/v1/jPush/buildPushObjectAllAliasAlert",map);
        //throw new MyException("s-skidney --> s-im");
    }

    @Override
    public ApiResult buildPushObjectAllAllAlert(Map map) {
        return restUtil.postJson("/s-im/api/v1/jPush/buildPushObjectAllAllAlert",map);
       // throw new MyException("s-skidney --> s-im");
    }
}
