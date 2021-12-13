package com.letscode.starwars.exception;

import org.springframework.stereotype.Component;

@Component
public class StarwarsException {
    public static class EntityNotFoundException extends RuntimeException {
        public EntityNotFoundException(String message) {
            super(message);
        }
    }

    public static class DuplicateEntityException extends RuntimeException {
        public DuplicateEntityException(String message) {
            super(message);
        }
    }

    public static class InternalServerException extends RuntimeException {
        public InternalServerException(String message) {
            super(message);
        }
    }

}
