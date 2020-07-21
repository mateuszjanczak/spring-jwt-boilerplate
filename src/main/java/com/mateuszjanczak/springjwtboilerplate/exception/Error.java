package com.mateuszjanczak.springjwtboilerplate.exception;

import org.springframework.http.HttpStatus;

public class Error {

    private int errorCode;
    private String errorName;
    private String errorMessage;

    public Error(HttpStatus status, String message) {
        this.errorCode = status.value();
        this.errorName = status.name();
        this.errorMessage = message;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorName() {
        return errorName;
    }

    public void setErrorName(String errorName) {
        this.errorName = errorName;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String toJson() {
        return "{ \"errorCode\": " + "\"" + errorCode+"\", \"errorName\": " + "\"" + errorName + "\", \"errorMessage\": " + "\"" + errorMessage.replace("\"", "'") + "\" }";
    }

}
