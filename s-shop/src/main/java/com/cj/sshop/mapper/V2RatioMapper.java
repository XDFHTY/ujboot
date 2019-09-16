package com.cj.sshop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cj.core.v2entity.V2Ratio;

/**
* Created by Mybatis Generator 2019-06-21 16:16:06
*/
public interface V2RatioMapper extends BaseMapper<V2Ratio> {

    V2Ratio findRatioByType(String goodType);

    V2Ratio findRatioByGoodId(long goodId);

    int updateRatio(long ratioId);
}