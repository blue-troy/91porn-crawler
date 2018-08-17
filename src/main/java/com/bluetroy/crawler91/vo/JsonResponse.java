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
public class JsonResponse implements Serializable {
    String method;
    Object data;

    public JsonResponse(String method, Object o) {
        this.method = method;
        this.data = o;
    }

    public String get() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(this);
    }
}
