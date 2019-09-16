package com.cj.sim.controller;

import com.cj.core.aop.Log;
import com.cj.core.domain.ApiResult;
import com.cj.common.domain.RespIDCard;
import com.cj.core.exception.MyException;
import com.cj.sim.service.IDCardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 身份证识别 控制器
 * Created by XD on 2019/3/9.
 */
@RestController
@RequestMapping(value = "/s-im/api/v1/idCard", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(tags = "用户端&医生端：身份证识别管理")
public class IDCardController {

    @Autowired
    private IDCardService idCardService;


    /**
     * 身份证识别接口
     * @param map idCardUrl=身份证照片绝对路径
     *            idCardSide= 1-正面照  2-背面照
     * @return
     */
    @PostMapping("/IDCardRecogize")
    @ApiOperation("身份证识别接口")
    @Log(name = "身份证识别管理", value = "身份证识别接口")
    @ApiImplicitParam(name = "map",value = "idCardUrl=身份证照片绝对路径  idCardSide= 1-正面照  2-背面照 {\"idCardUrl\":\"d:/1.png\",\"idCardSide\":\"1\" }",required = true)
    public ApiResult insert(@RequestBody Map<String,String> map) {
        ApiResult apiResult;

        JSONObject obj = idCardService.IDCardRecogize(map.get("idCardUrl"),map.get("idCardSide"));

        if (obj == null){
            apiResult  = ApiResult.FAIL();
            apiResult.setMsg("识别失败");
            return apiResult;
        }else if (obj.toString().indexOf("error_code")>0){
            throw new MyException((String)obj.get("error_code"));
        }

        String status = (String) obj.get("image_status");//识别状态
        if ("normal".equals(status)){ //识别正常
            //获取身份证中的信息
            JSONObject info = (JSONObject) obj.get("words_result");


            //正面
            if ("1".equals(map.get("idCardSide"))){
                RespIDCard respIDCard = new RespIDCard();

                JSONObject xm = (JSONObject)info.get("姓名");
                respIDCard.setName((String) xm.get("words"));

                JSONObject mz = (JSONObject)info.get("民族");
                respIDCard.setNation((String) mz.get("words"));

                JSONObject zz = (JSONObject)info.get("住址");
                respIDCard.setAddress((String) zz.get("words"));

                JSONObject sfz = (JSONObject)info.get("公民身份号码");
                respIDCard.setIdNumber((String) sfz.get("words"));

                JSONObject cs = (JSONObject)info.get("出生");
                respIDCard.setBirth((String) cs.get("words"));

                JSONObject xb = (JSONObject)info.get("性别");
                respIDCard.setSex((String) xb.get("words"));

                apiResult = ApiResult.SUCCESS();
                apiResult.setData(respIDCard);
                return apiResult;
            }
            //背面
            if ("2".equals(map.get("idCardSide"))){

                Map resultMap = new HashMap();

                JSONObject sxrq = (JSONObject)info.get("失效日期");
                resultMap.put("expirationDate",sxrq.get("words"));

                JSONObject qfjg = (JSONObject)info.get("签发机关");
                resultMap.put("issuingAuthority",qfjg.get("words"));

                JSONObject qfrq = (JSONObject)info.get("签发日期");
                resultMap.put("issuingDate",qfrq.get("words"));
            }

            /*apiResult = ApiResult.SUCCESS();
            apiResult.setData(respIDCard);
            return apiResult;*/
        }



        if ("reversed_side".equals(status)){ //未摆正身份证
            apiResult = ApiResult.FAIL();
            apiResult.setMsg("未摆正身份证");
            return apiResult;
        }
        if ("non_idcard".equals(status)){ //上传的图片中不包含身份证
            apiResult = ApiResult.FAIL();
            apiResult.setMsg("上传的图片中不包含身份证");
            return apiResult;
        }
        if ("blurred".equals(status)){ //身份证模糊
            apiResult = ApiResult.FAIL();
            apiResult.setMsg("身份证模糊");
            return apiResult;
        }
        if ("over_exposure".equals(status)){ //身份证关键字段反光或过曝
            apiResult = ApiResult.FAIL();
            apiResult.setMsg("身份证关键字段反光或过曝");
            return apiResult;
        }
        if ("unknown".equals(status)){ //未知状态
            apiResult = ApiResult.FAIL();
            apiResult.setMsg("识别失败");
            return apiResult;
        }
        apiResult = ApiResult.FAIL();
        apiResult.setMsg("识别失败");
        return apiResult;
    }



}
