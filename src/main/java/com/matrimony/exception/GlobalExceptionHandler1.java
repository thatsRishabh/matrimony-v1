//package com.matrimony.exception;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.context.request.WebRequest;
//import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
//
//@ControllerAdvice
//public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
//
//    @ExceptionHandler(Exception.class)
//    protected ResponseEntity<Object> handleHttpMessageNotReadable(Exception ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
//        String errorDetails = "Malformed JSON request or invalid request body.";
//        return handleExceptionInternal(ex, errorDetails, headers, HttpStatus.BAD_REQUEST, request);
//    }
//}
