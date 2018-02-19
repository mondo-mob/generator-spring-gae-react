package threewks.framework.controller.advice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.contrib.gae.objectify.repository.EntityNotFoundException;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import threewks.util.NotFoundException;

@ControllerAdvice
public class ExceptionHandlerAdvice extends ResponseEntityExceptionHandler {
    private static final Logger LOG = LoggerFactory.getLogger(ExceptionHandlerAdvice.class);

    @Order(100)
    @ExceptionHandler({IllegalArgumentException.class, HttpMessageConversionException.class})
    public ResponseEntity<ResponseError> handleBadRequestExceptions(Exception e) {
        LOG.debug("Bad request", e);
        return buildResponseError(e, HttpStatus.BAD_REQUEST);
    }

    @Order(100)
    @ExceptionHandler({NotFoundException.class, EntityNotFoundException.class})
    public ResponseEntity<ResponseError> handleNotFound(Exception e) {
        LOG.debug("Not found. {}: {}", e.getClass(), e.getMessage());
        return buildResponseError(e, HttpStatus.NOT_FOUND);
    }

    @Order
    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<ResponseError> handleAllOthers(RuntimeException e) {
        LOG.error("Uncaught exception", e);
        return buildResponseError(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<ResponseError> buildResponseError(Exception e, HttpStatus statusCode) {
        return buildResponseError(e.getClass().getSimpleName(), e.getMessage(), statusCode);
    }

    private ResponseEntity<ResponseError> buildResponseError(String error, String message, HttpStatus statusCode) {
        return new ResponseEntity<>(new ResponseError(error, message), statusCode);
    }

}
