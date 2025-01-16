package org.tool.common;

import jakarta.servlet.http.HttpServletRequest;

import java.io.BufferedReader;
import java.io.IOException;

public class Util {
    public static boolean isNullOrEmpty(String str) {
        if (str == null || str.isEmpty()) {
            return true;
        }
        return false;
    }

    public static String getDataFromHttp(HttpServletRequest request) throws ServerException {
        // 读取请求体
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            throw new ServerException(Constants.ErrorCode.RUNNING_ERROR, Constants.ErrorMessage.GET_DATA_FROM_HTTP_ERROR);
        }
        return sb.toString();
    }
}
