package com.example.course.Exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@Data
@EqualsAndHashCode(callSuper = false)
public class RecordNotExistsException extends RuntimeException {
    private String message;
    private HttpStatus status;

    public RecordNotExistsException(String message) {
        super();
        this.setMessage(message);
        this.setStatus(HttpStatus.NOT_FOUND);
    }

    public RecordNotExistsException(String message, HttpStatus status) {
        super();
        this.setMessage(message);
        this.setStatus(status);

    }

}
