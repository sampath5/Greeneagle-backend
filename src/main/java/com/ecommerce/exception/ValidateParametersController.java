//package com.ecommerce.exception;
//
//import javax.validation.ConstraintViolationException;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseStatus;
//
//class ValidateParametersController {
//
//  @ExceptionHandler(ConstraintViolationException.class)
//  @ResponseStatus(HttpStatus.BAD_REQUEST)
//  ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException e) {
//    return new ResponseEntity<>("not valid due to validation error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
//  }
//
//}