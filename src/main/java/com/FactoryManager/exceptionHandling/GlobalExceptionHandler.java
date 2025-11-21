package com.FactoryManager.exceptionHandling;

import com.cloudinary.api.exceptions.AlreadyExists;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(EmailAlreadyExistException.class)
    public ResponseEntity<ApiError> handleEmailAlreadyExistException(EmailAlreadyExistException ex){
        ApiError apiError= new ApiError( ex.getMessage(), HttpStatus.CONFLICT );
        return new ResponseEntity<>(apiError, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(FactoryAlreadyExist.class)
    public ResponseEntity<ApiError> handleAlreadyExistException(FactoryAlreadyExist ex){
        ApiError apiError= new ApiError( ex.getMessage(), HttpStatus.CONFLICT );
        return new ResponseEntity<>(apiError, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ApiError> handleUsernameNotFoundException(UsernameNotFoundException ex){
        ApiError apiError= new ApiError( ex.getMessage(), HttpStatus.NOT_FOUND );
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ElementAlreadyExistException.class)
    public ResponseEntity<ApiError> handleElementAlreadyExistException(ElementAlreadyExistException ex){
        ApiError apiError= new ApiError( ex.getMessage(), HttpStatus.CONFLICT );
        return new ResponseEntity<>(apiError, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ElementNotFoundException.class)
    public ResponseEntity<ApiError> handleElementNotFoundException(ElementNotFoundException ex){
        ApiError apiError= new ApiError( ex.getMessage(), HttpStatus.NOT_FOUND );
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalMoveException.class)
    public ResponseEntity<ApiError> handleIllegalMoveException(IllegalMoveException ex){
        ApiError apiError= new ApiError( ex.getMessage(), HttpStatus.CONFLICT );
        return new ResponseEntity<>(apiError, HttpStatus.CONFLICT);
    }



    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiError> handleIllegalArgumentException(IllegalArgumentException ex){
        ApiError apiError= new ApiError( ex.getMessage(), HttpStatus.BAD_REQUEST );
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(org.springframework.web.method.annotation.HandlerMethodValidationException.class)
    public ResponseEntity<Map<String, String>> handleHandlerMethodValidationException(
            org.springframework.web.method.annotation.HandlerMethodValidationException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getParameterValidationResults().forEach(result -> {
            String paramName = result.getMethodParameter().getParameterName();

            result.getResolvableErrors().forEach(error -> {
                errors.put(paramName, error.getDefaultMessage());
            });
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }







}

