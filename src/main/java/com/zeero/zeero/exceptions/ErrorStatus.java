package com.zeero.zeero.exceptions;

import lombok.Getter;

@Getter
public enum ErrorStatus {

    USER_NOT_FOUND_ERROR(404, "user not found"),
    GENERAL_ERROR(500, "oops something went wrong"),
    RESOURCE_NOT_FOUND_ERROR(404, "This resource is not available at the moment"),
    VALIDATION_ERROR(400, "Invalid input"),
    UNAUTHORIZED_ERROR(401, "you are not authorized to use this resource"),
    REDIS_CACHE_ERROR(503, "redis cache error"),
    USER_ALREADY_EXIST(501, "User already exist");
    final int errorCode;

    final String errorMessage;
    ErrorStatus(int errorCode, String errorMessage){
        this.errorCode  = errorCode;
        this.errorMessage = errorMessage;
    }
}
