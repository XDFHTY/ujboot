package com.cj.suser.service;

import com.cj.core.entity.UserEquipment;
import com.cj.suser.domain.VoUserEquipment;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface EquipmentService {

    public List<VoUserEquipment> findAllEquipmentByUserId(HttpServletRequest request);

    public int addEquipmentByUserId(UserEquipment userEquipment,HttpServletRequest request);


    public int updateEquipmentByUserId(HttpServletRequest request,UserEquipment userEquipment);

}
