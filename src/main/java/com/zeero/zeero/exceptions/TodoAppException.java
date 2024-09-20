package com.zeero.zeero.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TodoAppException extends RuntimeException{
    private ErrorStatus errorStatus;
    private String message;
    private int errorCode;
    public TodoAppException(ErrorStatus errorStatus, String message){
        this.errorStatus=errorStatus;
        if(message == null){
            this.message=errorStatus.getErrorMessage();
        }else{
            this.message=message;
        }
        this.errorCode = errorStatus.getErrorCode();
    }
}
