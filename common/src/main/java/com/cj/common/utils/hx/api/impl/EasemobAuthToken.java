package com.cj.common.utils.hx.api.impl;



import com.cj.common.utils.hx.api.AuthTokenAPI;
import com.cj.common.utils.hx.comm.TokenUtil;

public class EasemobAuthToken implements AuthTokenAPI {

	@Override
	public Object getAuthToken(){
		return TokenUtil.getAccessToken();
	}
}
