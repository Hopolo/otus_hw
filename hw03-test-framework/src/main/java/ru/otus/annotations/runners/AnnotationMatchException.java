package ru.otus.annotations.runners;

import java.lang.reflect.Method;

public class AnnotationMatchException extends RuntimeException{

    public AnnotationMatchException(String message, Method method) {
        super(message + method.getName());
    }
}
