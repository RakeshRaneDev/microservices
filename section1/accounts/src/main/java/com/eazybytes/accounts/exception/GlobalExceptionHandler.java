package com.eazybytes.accounts.exception;

import com.eazybytes.accounts.dto.ErrorResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
@AllArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, String> validateErrors = new HashMap<>();
        List<ObjectError> validationError = ex.getBindingResult().getAllErrors();

        validationError.forEach((error) -> {
                    String fieldName = ((FieldError) error).getField();
                    String validationMsg = error.getDefaultMessage();
                    validateErrors.put(fieldName, validationMsg);
                }
        );
        return new ResponseEntity<>(validateErrors, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(CustomerAlreadyExistsExceptions.class)
    public ResponseEntity<ErrorResponseDto> handleCustomerAlreadyExistException(CustomerAlreadyExistsExceptions exception,
                                                                                WebRequest request) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(request.getDescription(false),
                HttpStatus.BAD_REQUEST, exception.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(errorResponseDto, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleGlobalException(WebRequest request,
                                                                  Exception exception) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(request.getDescription(false),
                HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(errorResponseDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleResourceNotFoundException(WebRequest request,
                                                                            ResourceNotFoundException exception) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(request.getDescription(false),
                HttpStatus.NOT_FOUND, exception.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(errorResponseDto, HttpStatus.NOT_FOUND);
    }


}
