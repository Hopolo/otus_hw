package ru.otus.annotations.runners;

import ru.otus.test.SmthTest;

public class TestApplication {
    public static void main(String[] args) {
        AnnotationRunner annotationRunner = new AnnotationRunner();
        annotationRunner.startTesting(SmthTest.class);
    }
}
