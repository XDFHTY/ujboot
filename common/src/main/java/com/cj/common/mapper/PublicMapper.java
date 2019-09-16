package com.cj.common.mapper;

import com.cj.common.domain.VoRegion;
import com.cj.core.entity.DoctorInfo;
import com.cj.core.entity.UserInfo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface PublicMapper {


    public List<VoRegion> findAllRegion();

    public List<VoRegion> findAllRegionP();
    public List<VoRegion> findAllRegionC(long pid);
    public List<VoRegion> findAllRegionA(long cid);



    List<DoctorInfo> findAllByName(String pameter);


    //修改设备最后使用时间
    int putUseTime(@Param("mac") String mac,@Param("time") Date time);
}
