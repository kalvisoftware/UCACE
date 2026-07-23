package com.ucace.api.exception;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.ucace.api.response.ApiResponseDTO;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.slf4j.*;

@ControllerAdvice // hanlde exception globally
public class GlobalExceptionHandler {
        private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

        @ExceptionHandler(ResourceNotFoundException.class) // Handles ResourceNotFoundException thrown from any
                                                           // controller

        public ResponseEntity<ApiResponseDTO<ErrorResponse>> handleResourceNotFoundException(
                        ResourceNotFoundException ex) { // Passing
                // ResourceNotFoundException
                // object in
                // method and
                // return
                // ResponseEntity<ErrorResponse>

                ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage()); // creating
                ApiResponseDTO<ErrorResponse> response = new ApiResponseDTO<>(
                                false,
                                "Resource Not Found Exception",
                                error,
                                LocalDateTime.now()); // ErrorResponse object
                logger.error("Resource Not Found : {}", ex.getMessage());
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response); // return HttpStatus and error with
                                                                                   // ResponseEntity type

        }

        @ExceptionHandler(MethodArgumentNotValidException.class) // Handles MethodArgumentNotValidException thrown from
                                                                 // any
                                                                 // controller)
        public ResponseEntity<ApiResponseDTO<ErrorResponse>> handleMethodArgumentNotValidException(
                        MethodArgumentNotValidException ex) {

                String errorMessage = ex.getBindingResult().getFieldError().getDefaultMessage();

                ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), errorMessage);
                ApiResponseDTO<ErrorResponse> response = new ApiResponseDTO<>(
                                false,
                                "Method Argument Not Valid Exception",
                                error,
                                LocalDateTime.now());
                logger.error("Method Argument Not Valid Exception : {}", errorMessage);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        @ExceptionHandler(ResourceAlreadyExistsException.class)
        public ResponseEntity<ApiResponseDTO<ErrorResponse>> resourceAlreadyExistsException(
                        ResourceAlreadyExistsException ex) {
                ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
                ApiResponseDTO<ErrorResponse> response = new ApiResponseDTO<>(
                                false,
                                "Resource Already exists",
                                error,
                                LocalDateTime.now());
                logger.error("Resource Already Exists Exception : {}", ex.getMessage());
                return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }

        @ExceptionHandler(InvalidPasswordException.class)
        public ResponseEntity<ApiResponseDTO<ErrorResponse>> invalidPasswordException(InvalidPasswordException ex) {
                ErrorResponse error = new ErrorResponse(HttpStatus.NO_CONTENT.value(), ex.getMessage());
                ApiResponseDTO<ErrorResponse> response = new ApiResponseDTO<>(
                                false,
                                "Invalid password",
                                error,
                                LocalDateTime.now());
                logger.error("Invalid Password : {}", ex.getMessage());
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

}
