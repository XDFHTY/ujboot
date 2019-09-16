package com.cj.common.utils.hx.api.impl;



import com.cj.common.utils.hx.api.SendMessageAPI;
import com.cj.common.utils.hx.comm.EasemobAPI;
import com.cj.common.utils.hx.comm.OrgInfo;
import com.cj.common.utils.hx.comm.ResponseHandler;
import com.cj.common.utils.hx.comm.TokenUtil;
import io.swagger.client.ApiException;
import io.swagger.client.api.MessagesApi;
import io.swagger.client.model.Msg;

public class EasemobSendMessage implements SendMessageAPI {
    private ResponseHandler responseHandler = new ResponseHandler();
    private MessagesApi api = new MessagesApi();
    @Override
    public Object sendMessage(final Object payload) {
        return responseHandler.handle(new EasemobAPI() {
            @Override
            public Object invokeEasemobAPI() throws ApiException {
                return api.orgNameAppNameMessagesPost(OrgInfo.ORG_NAME,OrgInfo.APP_NAME, TokenUtil.getAccessToken(), (Msg) payload);
            }
        });
    }
}
