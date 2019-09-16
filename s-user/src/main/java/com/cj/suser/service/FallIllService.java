package com.cj.suser.service;

import com.cj.core.domain.OldPager;
import com.cj.suser.domain.DtoFallIll;
import com.cj.suser.domain.VoFallIllById;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;

public interface FallIllService {


    public OldPager findFallIll(String json, HttpServletRequest request);


    public VoFallIllById findFallIllById(long fallIllId);

    public int addFallIll(HttpServletRequest request, DtoFallIll dtoFallIll) throws InvocationTargetException, IllegalAccessException;


    public int updateFallIll(HttpServletRequest request, DtoFallIll dtoFallIll) throws InvocationTargetException, IllegalAccessException;

    public int deleteFallIll(HttpServletRequest request,long fallIllId);
}
