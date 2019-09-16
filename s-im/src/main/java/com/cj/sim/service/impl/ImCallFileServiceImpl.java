package com.cj.sim.service.impl;

import com.cj.core.domain.ApiResult;
import com.cj.core.util.rest.Parameter;
import com.cj.core.util.rest.RestUtil;
import com.cj.sim.service.ImCallFileService;
import com.cj.sim.service.ImCallUserService;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by XD on 2019/3/14.
 */
@Component
public class ImCallFileServiceImpl implements ImCallFileService {

    RestUtil restUtil = new RestUtil();


    @Override
    public ApiResult downloadFile(Map map) {
        // throw new MyException("s-im --> s-user");
        return restUtil.postJson("/s-file/api/file/hxUploads",map);
    }
}
