package com.Jeeva.UserService.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerException {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handle(MethodArgumentNotValidException methodArgumentNotValidException){
        return new ResponseEntity<>(methodArgumentNotValidException.getBindingResult().getFieldError().getDefaultMessage(),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = AgeLimitException.class)
    public ResponseEntity<Object> handleAgeLimitException(Exception e){
        return new ResponseEntity<>(e.getMessage(),  HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = UserAlreadyExistException.class)
    public ResponseEntity<Object> handleUserAlreadyExistException(Exception e){
        return new ResponseEntity<>(e.getMessage(),  HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Object> handle(Exception e){
        return new ResponseEntity<>(e.getMessage(),  HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
