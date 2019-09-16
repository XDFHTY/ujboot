package com.cj.suser.service.impl;

import com.cj.core.entity.FallIll;
import com.cj.common.service.PowerService;
import com.cj.core.domain.OldPager;
import com.cj.core.util.JsonUtil;
import com.cj.suser.domain.DtoFallIll;
import com.cj.suser.domain.VoFallIllById;
import com.cj.suser.mapper.FallIllImgMapper;
import com.cj.suser.mapper.FallIllMapper;
import com.cj.suser.service.FallIllService;
import com.google.gson.Gson;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.converters.SqlDateConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class FallIllServiceImpl implements FallIllService {

    Gson gson = JsonUtil.gson;

    @Resource
    private FallIllMapper fallIllMapper;

    @Resource
    private FallIllImgMapper fallIllImgMapper;

    @Resource
    private PowerService powerService;

    @Override
    public OldPager findFallIll(String json, HttpServletRequest request) {
        long userId = Long.parseLong(request.getHeader("id"));

        OldPager oldPager = gson.fromJson(json,OldPager.class);
        oldPager.setParameter(userId);
        List<List<?>> lists = fallIllMapper.findFallIll(oldPager);

        oldPager.setLists(lists);
        return oldPager;
    }


    @Override
    public VoFallIllById findFallIllById(long fallIllId) {
        return fallIllMapper.findFallIllById(fallIllId);
    }

    @Override
    public int addFallIll(HttpServletRequest request, DtoFallIll dtoFallIll) throws InvocationTargetException, IllegalAccessException {
        long userId = Long.parseLong(request.getHeader("id"));
        if (dtoFallIll.getUserId()!=null)
        powerService.checkIdalike(request,dtoFallIll.getUserId());

        FallIll fallIll = new FallIll();
        dtoFallIll.setAddTime(new Date());

        BeanUtils.copyProperties(fallIll,dtoFallIll);
        fallIll.setUserId(userId);
        int i = fallIllMapper.insertSelective(fallIll);
        dtoFallIll
                .getFallIllImgs()
                .forEach(fallIllImg -> {
            fallIllImg.setFallIllId(fallIll.getFallIllId());
            fallIllImgMapper.insertSelective(fallIllImg);
        });
        return i;
    }

    @Override
    public int updateFallIll(HttpServletRequest request, DtoFallIll dtoFallIll) throws InvocationTargetException, IllegalAccessException {
        long userId = Long.parseLong(request.getHeader("id"));
        powerService.checkIdalike(request,dtoFallIll.getUserId());

        FallIll fallIll = new FallIll();
        BeanUtils.copyProperties(fallIll,dtoFallIll);
        int i = fallIllMapper.updateByPrimaryKeySelective(fallIll);
        int j = fallIllImgMapper.deleteFallIllImgs(dtoFallIll.getFallIllId());
        int k = fallIllImgMapper.addFallIllImgs(dtoFallIll.getFallIllImgs());
        if ((i>0  && k>0) ||  k>0) return 1;
        return 0;
    }

    @Override
    public int deleteFallIll(HttpServletRequest request, long fallIllId) {
        long userId = Long.parseLong(request.getHeader("id"));

        return fallIllMapper.deleteFallIll(userId,fallIllId);
    }
}
