package com.bluetroy.crawler91.vo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: heyixin
 * Date: 2018-08-16
 * Time: 下午4:33
 */
@Data
@Component
@Scope("request")
public class JsonResponse<T> implements Serializable {
    private String method;
    private T data;
    private String message;

    public JsonResponse() {

    }

    public JsonResponse(String method, T t) {
        this.method = method;
        this.data = t;
    }

    public JsonResponse(String message) {
        this.message = message;
    }

    public String get() throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(this);
    }
}
