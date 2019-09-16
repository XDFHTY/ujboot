package com.cj.common.utils.hx.api.impl;


import com.cj.common.utils.hx.api.ChatMessageAPI;
import com.cj.common.utils.hx.comm.EasemobAPI;
import com.cj.common.utils.hx.comm.OrgInfo;
import com.cj.common.utils.hx.comm.ResponseHandler;
import com.cj.common.utils.hx.comm.TokenUtil;
import io.swagger.client.ApiException;
import io.swagger.client.api.ChatHistoryApi;


public class EasemobChatMessage  implements ChatMessageAPI {

    private ResponseHandler responseHandler = new ResponseHandler();
    private ChatHistoryApi api = new ChatHistoryApi();

    @Override
    public Object exportChatMessages(final Long limit,final String cursor,final String query) {
        return responseHandler.handle(new EasemobAPI() {
            @Override
            public Object invokeEasemobAPI() throws ApiException {
                return api.orgNameAppNameChatmessagesGet(OrgInfo.ORG_NAME,OrgInfo.APP_NAME, TokenUtil.getAccessToken(),query,limit+"",cursor);
            }
        });
    }

    @Override
    public Object exportChatMessages(final String timeStr) {
        return responseHandler.handle(new EasemobAPI() {
            @Override
            public Object invokeEasemobAPI() throws ApiException {
                return api.orgNameAppNameChatmessagesTimeGet(OrgInfo.ORG_NAME,OrgInfo.APP_NAME,TokenUtil.getAccessToken(),timeStr);
            }
        });
    }
}
