package com.example.csye6225assignment02.response;

import lombok.Getter;

@Getter
public enum ResponseCode {

    SUCCESS(200),
    CREATED(201),
    NO_CONTENT(204),
    BAD_REQUEST(400),
    UNAUTHORIZED(401),
    NOT_FOUND(404),
    METHOD_NOT_ALLOWED(405),
    Forbidden(403),
    SERVICE_UNAVAILABLE(503);


    private final int code;

    ResponseCode(int code) {
        this.code = code;
    }
}
