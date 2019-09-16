package com.cj.common.service;

import com.cj.common.domain.VoRegion;

import java.util.List;

public interface RegionService {

    public List<VoRegion> findAllRegion();
    public List<VoRegion> findAllRegionP();
    public List<VoRegion> findAllRegionC(long pid);
    public List<VoRegion> findAllRegionA(long cid);
}
