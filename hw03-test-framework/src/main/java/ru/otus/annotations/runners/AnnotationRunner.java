package ru.otus.annotations.runners;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;
import ru.otus.test.SmthTest;

public class AnnotationRunner {

    public void startTesting(Class<?> clazz){
        Map<String, List<Method>> mappedMethods = collectMethods(clazz);
        Map<Method, Boolean> results = run(mappedMethods);
        showResults(results);
    }

    public Map<String, List<Method>> collectMethods(Class<?> clazz) {
        Map<String, List<Method>> mappedMethods = new HashMap<>();
        mappedMethods.put("before", new ArrayList<>());
        mappedMethods.put("test", new ArrayList<>());
        mappedMethods.put("after", new ArrayList<>());

        for (Method method : clazz.getDeclaredMethods()) {
            int beforeCount = method.getAnnotationsByType(Before.class).length;
            int testCount = method.getAnnotationsByType(Test.class).length;
            int afterCount = method.getAnnotationsByType(After.class).length;
            //collect before methods
            if (beforeCount == 1) {
                mappedMethods.get("before").add(method);
            } else if (testCount > 0 && afterCount > 0) {
                throw new AnnotationMatchException("Wrong annotations matching for ", method);
            }
            //collect test methods
            if (testCount == 1) {
                mappedMethods.get("test").add(method);
            } else if (beforeCount > 0 && afterCount > 0) {
                throw new AnnotationMatchException("Wrong annotations matching for ", method);
            }
            //collect after
            if (afterCount == 1) {
                mappedMethods.get("after").add(method);
            } else if (beforeCount > 0 && testCount > 0) {
                throw new AnnotationMatchException("Wrong annotations matching for ", method);
            }
        }
        return mappedMethods;
    }

    private Map<Method, Boolean> run(
        Map<String, List<Method>> mappedMethods
    ) {
        Map<Method, Boolean> results = new HashMap<>();

        System.out.println("----------------------------------------------------------------------------------------------");
        for (Method testMethod : mappedMethods.get("test")) {
            SmthTest smthTest = new SmthTest();

            try {
                for (Method beforeMethod : mappedMethods.get("before")) {
                    beforeMethod.invoke(smthTest);
                }

                testMethod.invoke(smthTest);
                results.put(testMethod, false);
            } catch (InvocationTargetException e) {
                System.out.println("ERROR during test execution, method: " + testMethod);
                results.put(testMethod, true);
            } catch (IllegalAccessException e) {
                System.out.println("ERROR during test execution, method: " + testMethod);
                results.put(testMethod, true);
            } catch (Exception e) {
                System.out.println("ERROR during test execution, method: " + testMethod);
                results.put(testMethod, true);
            }
            try {
                for (Method afterMethod : mappedMethods.get("after")) {
                    afterMethod.invoke(smthTest);
                }
            } catch (InvocationTargetException e) {
                System.out.println("ERROR during test ending, method: " + testMethod);
            } catch (IllegalAccessException e) {
                System.out.println("ERROR during test ending, method: " + testMethod);
            }
            System.out.println("----------------------------------------------------------------------------------------------");
        }
        return results;
    }

    private void showResults(Map<Method, Boolean> results) {
        int methodsCount = results.size();
        long success = results.entrySet().stream().filter(entry -> entry.getValue().booleanValue() == false).count();
        System.out.println("Testing completed. Results: " + success + "/" + methodsCount + " are passed.");
    }

}
