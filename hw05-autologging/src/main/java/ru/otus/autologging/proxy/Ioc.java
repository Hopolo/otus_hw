package ru.otus.autologging.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import ru.otus.autologging.annotations.Log;

public class Ioc {
    private Ioc() {
    }

    public static Object newInstance(
        Class<?> clazz,
        Object[] args
    ) {
        Object obj;
        var argsTypes = Arrays.stream(args).map(Object::getClass).toArray(Class<?>[]::new);
        try {
            var constructor = clazz.getConstructor(argsTypes);
            obj = constructor.newInstance(args);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("Can't create object", e);
        }
        var interfazeClass = Optional.ofNullable(clazz.getInterfaces()).filter(interfazes -> interfazes.length > 0).get()[0];
        InvocationHandler handler = new AutologgingInvocationHandler(obj, clazz);
        return Proxy.newProxyInstance(
            Ioc.class.getClassLoader(),
            new Class<?>[] {interfazeClass},
            handler
        );
    }

    static class AutologgingInvocationHandler implements InvocationHandler {

        private final Object autoLogger;
        private final List<Method> methodsForAutoLogger = new ArrayList<>();

        AutologgingInvocationHandler(
            Object autoLogger,
            Class<?> clazz
        ) {
            methodsForAutoLogger.addAll(Arrays.stream(clazz.getDeclaredMethods())
                                            .filter(method -> method.isAnnotationPresent(Log.class))
                                            .toList());
            this.autoLogger = autoLogger;
        }

        @Override
        public Object invoke(
            Object proxy,
            Method method,
            Object[] args
        ) throws Throwable {
            if (methodsForAutoLogger.stream().anyMatch(method1 -> compareMethods(method1, method))) {
                System.out.println("method: " + method.getName() + "(), params: " + (args == null ? "None" : Arrays.toString(args)));
            }
            return method.invoke(autoLogger, args);
        }

        private boolean compareMethods(
            Method a,
            Method b
        ) {
            if (!a.getName().equals(b.getName())) {
                return false;
            }
            return Arrays.equals(a.getParameterTypes(), b.getParameterTypes());
        }
    }
}
