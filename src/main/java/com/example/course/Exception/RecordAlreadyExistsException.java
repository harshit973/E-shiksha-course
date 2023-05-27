package com.example.course.Exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@Data
@EqualsAndHashCode(callSuper = false)
public class RecordAlreadyExistsException extends RuntimeException {
    private String message;
    private HttpStatus status;

    public RecordAlreadyExistsException(String message) {
        super();
        this.setMessage(message);
        this.setStatus(HttpStatus.BAD_REQUEST);
    }

    public RecordAlreadyExistsException(String message, HttpStatus status) {
        super();
        this.setMessage(message);
        this.setStatus(status);

    }

}
