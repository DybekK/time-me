package com.dybek.timeme.exception.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class RestErrorHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<?> handleStatusException(ResponseStatusException ex, WebRequest request) {
        return handleResponseStatusException(ex, request);
    }

//    @Override
//    @SuppressWarnings("unchecked")
//    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
//        ResponseEntity<?> responseEntity;
//
//        if (!status.isError()) {
//            responseEntity = handleStatusException(ex, status, request);
//        } else if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
//            request.setAttribute("javax.servlet.error.exception", ex, 0);
//            responseEntity = handleEveryException(ex, request);
//        } else {
//            responseEntity = handleEveryException(ex, request);
//        }
//
//        return (ResponseEntity<Object>) responseEntity;
//    }

    protected ResponseEntity<RestResponse> handleResponseStatusException(ResponseStatusException ex, WebRequest request) {
        return RestResponse.builder()
                .exception(ex)
                .path(getPath(request))
                .entity();
    }
//
//    protected ResponseEntity<RestResponse> handleStatusException(Exception ex, HttpStatus status, WebRequest request) {
//        return RestResponse.builder()
//                .status(status)
//                .message("Execution halted")
//                .path(getPath(request))
//                .entity();
//    }
//
//    protected ResponseEntity<RestResponse> handleEveryException(Exception ex, WebRequest request) {
//        return RestResponse.builder()
//                .status(HttpStatus.INTERNAL_SERVER_ERROR)
//                .message(ex.getMessage())
//                .path(getPath(request))
//                .entity();
//    }
//
    private String getPath(WebRequest request) {
        return request.getDescription(false).substring(4);
    }
}