package com.dsr.linker.handler;

import com.dsr.linker.dto.ErrorDto;
import com.dsr.linker.exception.ResourceAlreadyExistsException;
import com.dsr.linker.exception.ResourceNotAllowedException;
import com.dsr.linker.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Calendar;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public @ResponseBody
    ResponseEntity<ErrorDto> handleResourceNotFoundException(Exception e) {
        Calendar timestamp = Calendar.getInstance();
        return new ResponseEntity<>(
                new ErrorDto(e.getMessage(), timestamp.getTime()),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public @ResponseBody ResponseEntity<ErrorDto> handleResourceAlreadyExistsException(Exception e) {
        Calendar timestamp = Calendar.getInstance();
        return new ResponseEntity<>(
                new ErrorDto(e.getMessage(), timestamp.getTime()),
                HttpStatus.CONFLICT
        );
    }

    @ExceptionHandler(ResourceNotAllowedException.class)
    public @ResponseBody ResponseEntity<ErrorDto> handleResourceNotAllowedException(Exception e) {
        Calendar timestamp = Calendar.getInstance();
        return new ResponseEntity<>(
                new ErrorDto(e.getMessage(), timestamp.getTime()),
                HttpStatus.METHOD_NOT_ALLOWED
        );
    }
}
