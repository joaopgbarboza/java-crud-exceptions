package br.com.dio.exception;

public class CustomException extends RuntimeException{

    public CustomException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
