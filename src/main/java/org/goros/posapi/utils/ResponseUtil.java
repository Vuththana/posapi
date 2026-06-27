package org.goros.posapi.utils;

import org.goros.posapi.model.response.ApiResponse;
import org.goros.posapi.model.response.ApiResponseVoid;
import org.springframework.http.HttpStatus;

import java.time.Instant;

public class ResponseUtil {
    public static<T> ApiResponse<T> success(String message, T payload) {
        return ApiResponse.<T>builder().status(HttpStatus.OK).success(true).message(message).payload(payload).timestamp(Instant.now()).build();
    }
    public static ApiResponseVoid successVoid(String message) {
        return ApiResponseVoid.builder().success(true).message(message).status(HttpStatus.OK).timestamp(Instant.now()).build();
    }

    public static ApiResponseVoid errorVoid(String message) {
        return ApiResponseVoid.builder().success(false).message(message).status(HttpStatus.BAD_REQUEST).timestamp(Instant.now()).build();
    }
}
