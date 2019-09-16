package com.cj.core.util;

import com.cj.core.util.timeUtil.DateUtil;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.security.authentication.AnonymousAuthenticationToken;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JsonUtil {

    public static Gson gson = new GsonBuilder()
            .setDateFormat(DateUtil.YYYY_MM_DDHHMMSS)

//            .setExclusionStrategies(new ExclusionStrategy(){
//                @Override
//                public boolean shouldSkipField(FieldAttributes fieldAttributes) {
//                    return fieldAttributes.getName().contains("handler");
//                }
//
//                @Override
//                public boolean shouldSkipClass(Class<?> aClass) {
//                    return false;
//                }
//            })

            .setLenient()// json宽松
            .enableComplexMapKeySerialization()//支持Map的key为复杂对象的形式
            .serializeNulls() //智能null
//            .setPrettyPrinting()// 调整格式 ，使对齐
//            .disableHtmlEscaping() //默认是GSON把HTML 转义的
            .setExclusionStrategies(new GsonExclusionStrategy()) //自定义排除转json的字段或者类名
//            .excludeFieldsWithoutExposeAnnotation()//启用	@Expose
            .create();
}
class GsonExclusionStrategy implements ExclusionStrategy{

    @Override
    public boolean shouldSkipField(FieldAttributes fieldAttributes) {
        return fieldAttributes.getName().contains("handler");
    }

    @Override
    public boolean shouldSkipClass(Class<?> aClass) {
        if(aClass == HttpServletRequest.class ||
                aClass == HttpServletResponse.class ||
                aClass == AnonymousAuthenticationToken.class

                ){
            return true;
        }
        return false;
    }
}