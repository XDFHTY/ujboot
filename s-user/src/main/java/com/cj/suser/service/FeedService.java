package com.cj.suser.service;

import com.cj.core.entity.Feedback;
import com.cj.suser.domain.VoFeed;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;

public interface FeedService {

    public int addfeed(VoFeed voFeed, HttpServletRequest request) throws InvocationTargetException, IllegalAccessException;
}
