package com.letscode.starwars.exception;


import com.letscode.starwars.dto.response.AppResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class AppExceptionHandler extends ResponseEntityExceptionHandler {

    static Logger logger = LoggerFactory.getLogger(AppExceptionHandler.class);

    @ExceptionHandler(value = StarwarsException.EntityNotFoundException.class)
    public ResponseEntity entityNotFoundException(StarwarsException.EntityNotFoundException e) {
        logger.error(e.getMessage());
        AppResponse appResponse = AppResponse.notFound();
        appResponse.addErrorMsgToResponse(e.getMessage(), e);
        return new ResponseEntity(appResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = StarwarsException.DuplicateEntityException.class)
    public ResponseEntity duplicateNotFoundException(StarwarsException.DuplicateEntityException e) {
        logger.error(e.getMessage());
        AppResponse appResponse = AppResponse.notFound();
        appResponse.addErrorMsgToResponse(e.getMessage(), e);
        return new ResponseEntity(appResponse, HttpStatus.IM_USED);
    }

    @ExceptionHandler(value = StarwarsException.InternalServerException.class)
    public ResponseEntity internalServerException(StarwarsException.InternalServerException e) {
        logger.error(e.getMessage());
        AppResponse appResponse = AppResponse.exception();
        appResponse.addErrorMsgToResponse(e.getMessage(), e);
        return new ResponseEntity(appResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
