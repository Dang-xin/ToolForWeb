package org.tool.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

public class ResponseUtil {
    // 错误代码
    private String errorCode;
    // 错误信息
    private String errorMessage;
    // 数据
    private String data;

    private ResponseUtil(String errorCode, String errorMessage, String data) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.data = data;
    }

    public static String success(String successMessage) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(successMessage);
        } catch (JsonProcessingException e) {
            return error(Constants.ErrorCode.RUNNING_ERROR, Constants.ErrorMessage.JSON_SERIALIZATION_ERROR);
        }
    }

    public static String success(Object data) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            return error(Constants.ErrorCode.RUNNING_ERROR, Constants.ErrorMessage.JSON_SERIALIZATION_ERROR);
        }
    }

    public static String error (String errorCode, String errorMessage) {
        ObjectMapper objectMapper = new ObjectMapper();

        Map<String, String> errorMap= new HashMap<>();
        errorMap.put(Constants.OtherConstants.ERROR_CODE, errorCode);
        errorMap.put(Constants.OtherConstants.ERROR_MESSAGE, errorMessage);
        try {
            return objectMapper.writeValueAsString(errorMap);
        } catch (JsonProcessingException e) {
            return error(Constants.ErrorCode.RUNNING_ERROR, Constants.ErrorMessage.JSON_SERIALIZATION_ERROR);
        }
    }
}
