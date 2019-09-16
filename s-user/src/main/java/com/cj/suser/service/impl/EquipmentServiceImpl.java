package com.cj.suser.service.impl;

import com.cj.core.domain.ApiResult;
import com.cj.core.entity.UserEquipment;
import com.cj.core.exception.MyException;
import com.cj.suser.domain.VoUserEquipment;
import com.cj.suser.mapper.UserEquipmentMapper;
import com.cj.suser.service.EquipmentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Service
public class EquipmentServiceImpl implements EquipmentService {

    @Resource
    private UserEquipmentMapper userEquipmentMapper;


    @Override
    public List<VoUserEquipment> findAllEquipmentByUserId(HttpServletRequest request) {
        long userId = Long.parseLong(request.getHeader("id"));

        return userEquipmentMapper.findAllEquipmentByUserId(userId);
    }

    @Override
    public int addEquipmentByUserId(UserEquipment userEquipment,HttpServletRequest request) {
        long userId = Long.parseLong(request.getHeader("id"));

        userEquipment.setUserId(userId);
        userEquipment.setStartTime(new Date());
        //查询用户是否已绑定此类设备
        int num = userEquipmentMapper.findEquipmentNumByIdAndType(userId,userEquipment.getEquipmentType());
        if (num>0){
            throw new MyException("此设备已绑定，不可重复绑定");
        }

        return userEquipmentMapper.insertSelective(userEquipment);
    }

    @Override
    public int updateEquipmentByUserId(HttpServletRequest request, UserEquipment userEquipment) {
        long userId = Long.parseLong(request.getHeader("id"));
        return userEquipmentMapper.updateEquipmentByUserId(userId,userEquipment.getUserBindEquipmentId());
    }
}
