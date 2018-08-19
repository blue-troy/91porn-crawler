package com.bluetroy.crawler91.vo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

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
public class JsonResponse<T> implements Serializable {
    String method;
    T data;

    public JsonResponse(String method, T t) {
        this.method = method;
        this.data = t;
    }

    public String get() throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(this);
    }
}
