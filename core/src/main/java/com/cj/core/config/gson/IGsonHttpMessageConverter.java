package com.cj.core.config.gson;

import com.cj.core.util.timeUtil.DateUtil;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import springfox.documentation.spring.web.json.Json;


@Configuration
public class IGsonHttpMessageConverter extends GsonHttpMessageConverter {


    public IGsonHttpMessageConverter() {
        super();
        this.setGson(gson());
    }

    private Gson gson() {
        final GsonBuilder builder = new GsonBuilder();

        builder
                .setDateFormat(DateUtil.YYYY_MM_DDHHMMSS)
                .registerTypeAdapter(Json.class, new SpringfoxJsonToGsonSerialer())
                .setExclusionStrategies(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes fieldAttributes) {
                        return fieldAttributes.getName().contains("handler");
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> aClass) {
                        return false;
                    }
                });
        return builder.create();
    }


}
