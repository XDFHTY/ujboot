package com.cj.sshop.service;

import com.cj.core.v2entity.V2Ratio;

import java.util.List;

public interface RatioService {


    List<V2Ratio> findAllRatios();


    int updateRatio(V2Ratio ratio);
}
