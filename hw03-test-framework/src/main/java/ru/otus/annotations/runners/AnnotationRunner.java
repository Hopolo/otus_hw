package ru.otus.annotations.runners;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;

public class AnnotationRunner {

    public void startTesting(Class<?> clazz) {
        Map<String, List<Method>> mappedMethods = collectMethods(clazz);
        Map<Method, Boolean> results = run(mappedMethods, clazz);
        showResults(results);
    }

    private Map<String, List<Method>> collectMethods(Class<?> clazz) {
        Map<String, List<Method>> mappedMethods = new HashMap<>();
        mappedMethods.put("before", new ArrayList<>());
        mappedMethods.put("test", new ArrayList<>());
        mappedMethods.put("after", new ArrayList<>());

        for (Method method : clazz.getDeclaredMethods()) {
            int beforeCount = method.getAnnotationsByType(Before.class).length;
            int testCount = method.getAnnotationsByType(Test.class).length;
            int afterCount = method.getAnnotationsByType(After.class).length;
            //collect before methods
            if (beforeCount > 0) {
                mappedMethods.get("before").add(method);
            }
            //collect test methods
            if (testCount > 0) {
                mappedMethods.get("test").add(method);
            }
            //collect after
            if (afterCount > 0) {
                mappedMethods.get("after").add(method);
            }
        }
        return mappedMethods;
    }

    private Map<Method, Boolean> run(
        Map<String, List<Method>> mappedMethods,
        Class<?> clazz
    ) {
        Map<Method, Boolean> results = new HashMap<>();

        System.out.println("----------------------------------------------------------------------------------------------");
        for (Method testMethod : mappedMethods.get("test")) {
            Object smthTest = null;
            Constructor<?> constructor;
            try {
                constructor = clazz.getConstructor();
                smthTest = constructor.newInstance();

                for (Method beforeMethod : mappedMethods.get("before")) {
                    beforeMethod.invoke(smthTest);
                }

                testMethod.invoke(smthTest);
                results.put(testMethod, false);
            } catch (Exception e) {
                System.out.println("ERROR during test execution, method: " + testMethod);
                results.put(testMethod, true);
            }
            try {
                for (Method afterMethod : mappedMethods.get("after")) {
                    afterMethod.invoke(smthTest);
                }
            } catch (Exception e) {
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
