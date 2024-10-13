package com.ticketnova.bidService.util;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class Response <T>{
    private int statusCode;
    private String message;
    boolean success = false;
    private T data;
    public Response(int statCode, String statusDesc) {
        statusCode = statCode;
        message = statusDesc;

        if (statusCode == HttpStatus.OK.value()) {
            success = true;
        }

    }

    public Response() {
    }

    public static <T> Response<T> failedResponse(String message) {
        return failedResponse(HttpStatus.BAD_REQUEST.value(), message, null);
    }

    public static <T> Response<T> failedResponse(T data) {
        return failedResponse(HttpStatus.BAD_REQUEST.value(), "Bad request", data);
    }

    public static <T> Response<T> failedResponse(int statusCode, String message) {
        return failedResponse(statusCode, message, null);
    }

    public static  <T> Response<T> failedResponse(int statusCode, String message, T data) {
        Response<T> response = new Response<>(statusCode, message);
        response.setSuccess(false);
        response.setData(data);
        return response;
    }

    public static  <T> Response<T> successfulResponse(String message, T data) {
        return successfulResponse(HttpStatus.OK.value(), message, data);
    }

    public static  <T> Response<T> successfulResponse(String message) {
        return successfulResponse(HttpStatus.OK.value(), message, null);
    }

    public static <T> Response<T> successfulResponse(int statusCode, String message, T data) {
        Response<T> response = new Response<>(statusCode, message);
        response.setSuccess(true);
        response.setData(data);
        return response;
    }
}
