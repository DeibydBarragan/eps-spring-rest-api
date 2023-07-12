package com.eps.epsspringrestapi.utils;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;

public class ResponseBuilder {
    private final Boolean error;
    private final String field;
    private final String message;
    private final HttpStatusCode status;
    private Object data;

    public ResponseBuilder(Boolean error, String field, String message, HttpStatusCode status) {
        this.error = error;
        this.field = field;
        this.message = message;
        this.status = status;
    }

    public ResponseBuilder(Boolean error, String message, HttpStatusCode status) {
        this.error = error;
        this.field = null;
        this.message = message;
        this.status = status;
    }

    public ResponseBuilder(Object data, String message ,HttpStatusCode status) {
        this.error = false;
        this.field = null;
        this.message = message;
        this.status = status;
        this.data = data;
    }

    public ResponseBuilder(String message ,HttpStatusCode status) {
        this.error = false;
        this.field = null;
        this.message = message;
        this.status = status;
    }

    public ResponseEntity<Object> send(){
        HashMap<String, Object> data = new HashMap<>();
        if (this.error)
            data.put("error", true);
        if (this.field != null)
            data.put("field", this.field);
        if (this.message != null)
            data.put("message", this.message);
        if (this.data != null)
            data.put("data", this.data);
        return new ResponseEntity<>(
            data,
            this.status
        );
    }

    public Boolean getError() {
        return error;
    }

    public String getField() {
        return field;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatusCode getStatus() {
        return status;
    }

    public Object getData() {
        return data;
    }
}
