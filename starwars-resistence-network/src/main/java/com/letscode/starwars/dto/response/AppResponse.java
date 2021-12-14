package com.letscode.starwars.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Date;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AppResponse<T> {
    private Status status;
    private T payload;
    private Object errors;
    private Object metadata;
    private T response;

    public static <T> AppResponse<T> badRequest() {
        AppResponse<T> appResponse = new AppResponse<>();
        appResponse.setStatus(Status.BAD_REQUEST);
        return appResponse;
    }

    public static <T> AppResponse<T> ok() {
        AppResponse<T> appResponse = new AppResponse<>();
        appResponse.setStatus(Status.OK);
        return appResponse;
    }

    public static <T> AppResponse<T> unauthorized() {
        AppResponse<T> appResponse = new AppResponse<>();
        appResponse.setStatus(Status.UNAUTHORIZED);
        return appResponse;
    }

    public static <T> AppResponse<T> validationException() {
        AppResponse<T> appResponse = new AppResponse<>();
        appResponse.setStatus(Status.VALIDATION_EXCEPTION);
        return appResponse;
    }

    public static <T> AppResponse<T> wrongCredentials() {
        AppResponse<T> appResponse = new AppResponse<>();
        appResponse.setStatus(Status.WRONG_CREDENTIALS);
        return appResponse;
    }

    public static <T> AppResponse<T> accessDenied() {
        AppResponse<T> appResponse = new AppResponse<>();
        appResponse.setStatus(Status.ACCESS_DENIED);
        return appResponse;
    }

    public static <T> AppResponse<T> exception() {
        AppResponse<T> appResponse = new AppResponse<>();
        appResponse.setStatus(Status.EXCEPTION);
        return appResponse;
    }

    public static <T> AppResponse<T> notFound() {
        AppResponse<T> appResponse = new AppResponse<>();
        appResponse.setStatus(Status.NOT_FOUND);
        return appResponse;
    }

    public static <T> AppResponse<T> duplicateEntity() {
        AppResponse<T> appResponse = new AppResponse<>();
        appResponse.setStatus(Status.DUPLICATE_ENTITY);
        return appResponse;
    }

    public void addErrorMsgToResponse(String details, Exception ex) {
        AppErrorResponse error = new AppErrorResponse()
                .setDetails(details)
                .setMessage(ex.getMessage())
                .setTimestamp(new Date());
        setErrors(error);
    }

    public void addErrorMsgToResponse(String details, String errorMsg) {
        AppErrorResponse error = new AppErrorResponse()
                .setDetails(details)
                .setMessage(errorMsg)
                .setTimestamp(new Date());
        setErrors(error);
    }

    public enum Status {
        OK, BAD_REQUEST,
        UNAUTHORIZED,
        VALIDATION_EXCEPTION,
        EXCEPTION,
        WRONG_CREDENTIALS,
        ACCESS_DENIED,
        NOT_FOUND,
        DUPLICATE_ENTITY
    }


}
