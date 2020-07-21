package com.mateuszjanczak.springjwtboilerplate.exception;

import org.springframework.http.HttpStatus;

import java.util.List;

public class ValidationError extends Error {

    private List<String> fieldsErrorList;

    public ValidationError(HttpStatus status, String message, List<String> fieldsErrorList) {
        super(status, message);
        this.fieldsErrorList = fieldsErrorList;
    }

    public List<String> getFieldsErrorList() {
        return fieldsErrorList;
    }

    public void setFieldsErrorList(List<String> fieldsErrorList) {
        this.fieldsErrorList = fieldsErrorList;
    }
}
