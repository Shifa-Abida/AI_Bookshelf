package com.Organise.AI_Bookshelf.exception;

import com.Organise.AI_Bookshelf.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleGlobalException(
            Exception ex) {

        ErrorResponseDto error = new ErrorResponseDto();

        error.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        error.setError(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        error.setMessage("Something went wrong. Please try again later.");
        error.setTimestamp(LocalDateTime.now());

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(error);
    }


    @ExceptionHandler(ToolAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDto> handleDuplicateTool(
            ToolAlreadyExistsException ex) {

        ErrorResponseDto error = new ErrorResponseDto();

        error.setMessage(ex.getMessage());
        error.setStatus(HttpStatus.CONFLICT.value());
        error.setTimestamp(LocalDateTime.now());
        error.setError("Conflict");

        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ToolNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponseDto> handleToolNotFound(ToolNotFoundException ex) {

        ErrorResponseDto error = new ErrorResponseDto();

        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setError(HttpStatus.NOT_FOUND.getReasonPhrase());
        error.setMessage(ex.getMessage());
        error.setTimestamp(LocalDateTime.now());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleValidationException(MethodArgumentNotValidException ex) {

        List<String> errorMessages = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getDefaultMessage())
                .toList();

        ErrorResponseDto error = new ErrorResponseDto();

        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setError(HttpStatus.BAD_REQUEST.getReasonPhrase());
        error.setMessage(errorMessages);
        error.setTimestamp(LocalDateTime.now());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(error);
    }

}





