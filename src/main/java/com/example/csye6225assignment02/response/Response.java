package com.example.csye6225assignment02.response;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class Response<T> implements Serializable {
    @Serial
    private static final long serialVersionUID = -7318100325049342203L;

    // status code
    private Integer code;
    // the message of the response
    private String message;
    //data
    private T data;

    private static final String SUCCESS_MESSAGE = "OK";
    private static final String CREATED_MESSAGE = "Created";
    private static final String NO_CONTENT_MESSAGE = "No Content";
    private static final String BAD_REQUEST_MESSAGE = "Bad Request";
    private static final String FORBIDDEN_MESSAGE = "Forbidden";
    private static final String NOT_FOUND_MESSAGE = "Not Found";
    private static final String METHOD_NOT_ALLOWED_MESSAGE = "Method Not Allowed";
    private static final String SERVICE_UNAVAILABLE_MESSAGE = "Service Unavailable";
    private static final String UNAUTHORIZED_MESSAGE = "Unauthorized";

    /**
     * success response 200
     * @param data response data optional
     * @return response
     * @param <T> data type
     */
    public static <T> Response<T> success(T data) {
        Response<T> response = new Response<>();
        response.setCode(ResponseCode.SUCCESS.getCode());
        response.setMessage(SUCCESS_MESSAGE);
        response.setData(data);
        return response;
    }

    /**
     * 201 created
     * @param data response data optional
     * @param <T> data type
     */
    public static <T> Response<T> created(T data) {
        Response<T> response = new Response<>();
        response.setCode(ResponseCode.CREATED.getCode());
        response.setMessage(CREATED_MESSAGE);
        response.setData(data);
        return response;
    }

    /**
     * no content response 204
     * @return response
     * @param <T> data type
     */
    public static <T> Response<T> noContent() {
        Response<T> response = new Response<>();
        response.setCode(ResponseCode.NO_CONTENT.getCode());
        response.setMessage(NO_CONTENT_MESSAGE);
        return response;
    }

    /**
     * bad request response 400
     * @return response
     * @param <T> data type
     */
    public static <T> Response<T> badRequest() {
        Response<T> response = new Response<>();
        response.setCode(ResponseCode.BAD_REQUEST.getCode());
        response.setMessage(BAD_REQUEST_MESSAGE);
        return response;
    }

    /**
     * unauthorized response 401
     * @return response
     * @param <T> data type
     */
    public static <T> Response<T> unauthorized(String message) {
        Response<T> response = new Response<>();
        response.setCode(ResponseCode.UNAUTHORIZED.getCode());
        response.setMessage(UNAUTHORIZED_MESSAGE + ": " + message);
        return response;
    }

    /**
     * forbidden response 403
     * @return response
     * @param <T> data type
     */
    public static <T> Response<T> forbidden(String message) {
        Response<T> response = new Response<>();
        response.setCode(ResponseCode.Forbidden.getCode());
        response.setMessage(FORBIDDEN_MESSAGE+ ": " + message);
        return response;
    }

    /**
     * not found response 404
     * @return response
     *
     */
    public static <T> Response<T> notFound(String message) {
        Response<T> response = new Response<>();
        response.setCode(ResponseCode.NOT_FOUND.getCode());
        response.setMessage(NOT_FOUND_MESSAGE + ": " + message);
        return response;
    }

    /**
     * method not allowed response 405
     * @return response
     * @param <T> data type
     */
    public static <T> Response<T> methodNotAllowed() {
        Response<T> response = new Response<>();
        response.setCode(ResponseCode.METHOD_NOT_ALLOWED.getCode());
        response.setMessage(METHOD_NOT_ALLOWED_MESSAGE);
        return response;
    }

    /**
     * service unavailable response 503
     * @return response
     * @param <T> data type
     */
    public static <T> Response<T> serviceUnavailable() {
        Response<T> response = new Response<>();
        response.setCode(ResponseCode.SERVICE_UNAVAILABLE.getCode());
        response.setMessage(SERVICE_UNAVAILABLE_MESSAGE);
        return response;
    }

}
