package com.example.course.Exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@Data
@EqualsAndHashCode(callSuper = false)
public class ControllerException {
    private String message;
    private HttpStatus status;

    public ControllerException(String message, HttpStatus status) {
        super();
        this.setMessage(message);
        this.setStatus(status);

    }

}
