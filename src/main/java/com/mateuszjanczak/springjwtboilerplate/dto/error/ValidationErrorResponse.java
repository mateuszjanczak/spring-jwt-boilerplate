package com.mateuszjanczak.springjwtboilerplate.dto.error;

import org.springframework.http.HttpStatus;

import java.util.List;

public class ValidationErrorResponse extends ErrorResponse {

    private List<String> fieldsErrorList;

    public ValidationErrorResponse(HttpStatus status, String message, List<String> fieldsErrorList) {
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
