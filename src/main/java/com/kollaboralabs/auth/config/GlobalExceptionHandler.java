package com.kollaboralabs.auth.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.google.common.collect.ImmutableMap;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ResponseMessage> handleUnknownException(Exception ex, WebRequest req) {
        return this.generateResponseAsMap(HttpStatus.INTERNAL_SERVER_ERROR, "error.unknown", "error", req.getDescription(false));
    }

    @ExceptionHandler(value = IllegalStateException.class)
    public ResponseEntity<ResponseMessage> handleIllegalStateException(IllegalStateException ex, WebRequest req) {
        return this.generateResponseAsMap(HttpStatus.BAD_REQUEST, "error.badState", "error", req.getDescription(false));
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<ResponseMessage> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest req) {
        return this.generateResponseAsList(ex, HttpStatus.BAD_REQUEST, "error.badRequest", req.getDescription(false));
    }

    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ResponseMessage> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex, WebRequest req) {
        return this.generateResponseAsMap(ex, HttpStatus.BAD_REQUEST, req.getDescription(false));
    }

    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    public ResponseEntity<ResponseMessage> handleMissingServletRequestParameterException(MissingServletRequestParameterException ex, WebRequest req) {
        return this.generateResponseAsMap(ex, HttpStatus.BAD_REQUEST, req.getDescription(false));
    }

    @ExceptionHandler(value = ServletRequestBindingException.class)
    public ResponseEntity<ResponseMessage> handleServletRequestBindingException(ServletRequestBindingException ex, WebRequest req) {
        return this.generateResponseAsMap(ex, HttpStatus.BAD_REQUEST, req.getDescription(false));
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ResponseEntity<ResponseMessage> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex, WebRequest req) {
        return this.generateResponseAsMap(ex, HttpStatus.BAD_REQUEST, req.getDescription(false));
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseMessage> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest req) {
        return this.generateResponseAsMap(
           HttpStatus.BAD_REQUEST,
           "error.notNull",
           ex.getBindingResult().getFieldError().getField(),
           req.getDescription(false),
           ex.getBindingResult().getFieldError().getField()
        );
    }


    private ResponseEntity<ResponseMessage> generateResponseAsMap(HttpStatus httpStatus, String messageKey, String columnKey,
            String path, String... args) {
        String message = this.messageSource.getMessage(messageKey, args, LocaleContextHolder.getLocale());
        ResponseMessage responseMessage = new ResponseMessage(
                httpStatus,
                ImmutableMap.of(columnKey, Arrays.asList(new String[]{message})),
                path.replaceAll("uri=", ""));
        return new ResponseEntity<ResponseMessage>(responseMessage, httpStatus);
    }

    private ResponseEntity<ResponseMessage> generateResponseAsMap(Exception ex, HttpStatus httpStatus, String path) {
        ResponseMessage responseMessage = new ResponseMessage(httpStatus, ex.getLocalizedMessage(), path.replaceAll("uri=", ""));
        return new ResponseEntity<ResponseMessage>(responseMessage, httpStatus);
    }

    private ResponseEntity<ResponseMessage> generateResponseAsList(Exception ex, HttpStatus httpStatus, String messageKey, String path) {
        String message = this.messageSource.getMessage(messageKey, new String[]{}, LocaleContextHolder.getLocale());
        String exceptionMessage = ex.getLocalizedMessage() != null ? ex.getLocalizedMessage() : "";
        ResponseMessage responseMessage = new ResponseMessage(
            httpStatus,
            message + " " + exceptionMessage,
            path.replaceAll("uri=", ""));

        return new ResponseEntity<ResponseMessage>(responseMessage, httpStatus);
    }

    private ResponseEntity<ResponseMessage> generateResponseAsList(Exception ex, HttpStatus httpStatus, String path) {
        ResponseMessage responseMessage = new ResponseMessage(
            httpStatus,
            ex.getLocalizedMessage() != null ? ex.getLocalizedMessage() : "",
            path.replaceAll("uri=", ""));

        return new ResponseEntity<ResponseMessage>(responseMessage, httpStatus);
    }
}
