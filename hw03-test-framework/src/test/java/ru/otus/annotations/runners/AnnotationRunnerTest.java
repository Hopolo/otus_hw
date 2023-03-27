package ru.otus.annotations.runners;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.otus.test.SmthTest;

class AnnotationRunnerTest {

    @Test
    void run() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        AnnotationRunner runner = new AnnotationRunner();
        runner.startTesting(SmthTest.class);
        Assertions.assertTrue(true);
    }
}