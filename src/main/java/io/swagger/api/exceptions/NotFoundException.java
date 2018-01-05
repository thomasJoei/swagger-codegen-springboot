package io.swagger.api.exceptions;


import io.swagger.api.exceptions.ApiException;

public class NotFoundException extends ApiException {
    private int code;
    public NotFoundException (int code, String msg) {
        super(code, msg);
        this.code = code;
    }
}
