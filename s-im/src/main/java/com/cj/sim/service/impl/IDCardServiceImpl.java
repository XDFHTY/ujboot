package com.cj.sim.service.impl;

import com.cj.sim.service.IDCardService;
import com.cj.sim.utils.BdIdCard;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 百度身份证识别接口
 * Created by XD on 2019/3/9.
 */
@Service
public class IDCardServiceImpl implements IDCardService {

    @Value("${APP_ID}")
    private String APP_ID;

    @Value("${API_KEY}")
    private String API_KEY;

    @Value("${SECRET_KEY}")
    private String SECRET_KEY;



    /**
     * 身份证识别接口
     * @param  idCardUrl =身份证照片绝对路径
     *            idCardSide= 1-正面照  2-背面照
     * @return
     */
    @Override
    public JSONObject IDCardRecogize(String idCardUrl, String idCardSide) {
        BdIdCard bdIdCard = new BdIdCard();

        if ("1".equals(idCardSide)){
            //识别正面照
            return bdIdCard.IDCardRecogizeFront(APP_ID,API_KEY,SECRET_KEY,idCardUrl);
        }else if ("2".equals(idCardSide)){
            return bdIdCard.IDCardRecogizeBack(APP_ID,API_KEY,SECRET_KEY,idCardUrl);
        }
        return null;
    }
}
