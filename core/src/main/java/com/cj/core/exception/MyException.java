package com.cj.core.exception;

import com.cj.core.domain.ApiResult;
import lombok.Data;

/**
 *  业务异常父类
 */
@Data
public class MyException extends RuntimeException {


    private ApiResult apiResult = ApiResult.FAIL();


    // 给子类用的方法
    public MyException(ApiResult apiResult) {
        super(apiResult.getMsg());
        this.apiResult = apiResult;
    }

    public MyException(Integer code,String msg){
        super(msg);
        this.apiResult.setCode(code);
        this.apiResult.setMsg(msg);
    }
    public MyException(String msg){
        super(msg);
        this.apiResult.setCode(ApiResult.FAIL_CODE);
        this.apiResult.setMsg(msg);
    }


}