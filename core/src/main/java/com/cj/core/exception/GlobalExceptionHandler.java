package com.cj.core.exception;

import com.cj.core.domain.ApiResult;
import com.cj.core.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;

/**
 * @description 全局异常处理: 使用 @RestControllerAdvice + @ExceptionHandler 注解方式实现全局异常处理
 */
@RestControllerAdvice
@RestController
@ApiIgnore
@Slf4j
public class GlobalExceptionHandler implements ErrorController {



    /**
     * 系统异常处理
     *
     * @param e 异常
     * @return
     */
    @ExceptionHandler({Exception.class})    //申明捕获那个异常类
    public ResponseEntity globalExceptionHandler(Exception e, HttpServletRequest request) {
        log.error(e.getMessage(), e);

        ApiResult apiResult = ApiResult.CODE_500();


        if (e instanceof org.springframework.web.servlet.NoHandlerFoundException) {
            apiResult = ApiResult.CODE_404();

        } else if (e instanceof java.net.SocketTimeoutException){
            apiResult = ApiResult.CODE_404();
            apiResult.setMsg("连接超时");

        }
        else if (e instanceof org.springframework.web.HttpRequestMethodNotSupportedException) {
            apiResult = ApiResult.CODE_405();

        } else if (e instanceof com.google.gson.JsonSyntaxException
                || e instanceof com.fasterxml.jackson.databind.JsonMappingException
                || e instanceof org.springframework.http.converter.HttpMessageNotReadableException) {
            apiResult = ApiResult.CODE_400();

        } else if (e instanceof java.sql.SQLIntegrityConstraintViolationException){
            apiResult.setMsg("重复:   "+e.getMessage());
        }else if (e instanceof org.springframework.dao.DuplicateKeyException ) {


            apiResult.setMsg("数据库操作异常: " + e.getMessage());

        } else if (e instanceof org.springframework.transaction.NoTransactionException) {

            apiResult.setMsg("事务回滚异常: " + e.getMessage());
        }else if (e instanceof org.springframework.dao.QueryTimeoutException){

            apiResult.setMsg("Redis连接超时: " + e.getMessage());
        }

        return ResponseEntity.status(apiResult.getCode()).body(apiResult);
    }


    /**
     * 捕获业务异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler({MyException.class})
    public ResponseEntity BusinessExceptionHandler(MyException e) {

        log.error(String.valueOf(e));
        ApiResult apiResult = e.getApiResult();
        System.out.println(JsonUtil.gson.toJson(apiResult));
        if (apiResult.getCode() > 999) {

            return ResponseEntity.status(200).body(apiResult);
        } else {
            return ResponseEntity.status(apiResult.getCode()).body(apiResult);

        }

    }

    /**
     * 捕获404
     */

    private static final String ERROR_PATH = "/error";

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }


    @RequestMapping(value = ERROR_PATH)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ApiResult handleError(HttpServletRequest request) {


        ApiResult apiResult = ApiResult.CODE_404();
        return apiResult;
    }

}
