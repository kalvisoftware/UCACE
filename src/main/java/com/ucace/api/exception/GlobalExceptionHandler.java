package com.ucace.api.exception;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ControllerAdvice // hanlde exception globally
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class) // Handles ResourceNotFoundException thrown from any controller

    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex) { // Passing
                                                                                                         // ResourceNotFoundException
                                                                                                         // object in
                                                                                                         // method and
                                                                                                         // return
                                                                                                         // ResponseEntity<ErrorResponse>

        ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage()); // creating
                                                                                                // ErrorResponse object

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error); // return HttpStatus and error with
                                                                        // ResponseEntity type

    }

    @ExceptionHandler(MethodArgumentNotValidException.class) // Handles MethodArgumentNotValidException thrown from any
                                                             // controller)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {

        String errorMessage = ex.getBindingResult().getFieldError().getDefaultMessage();

        ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), errorMessage);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> resourceAlreadyExistsException(ResourceAlreadyExistsException ex) {
        ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<ErrorResponse> invalidPasswordException(InvalidPasswordException ex) {
        ErrorResponse error = new ErrorResponse(HttpStatus.NO_CONTENT.value(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(error);
    }

}
