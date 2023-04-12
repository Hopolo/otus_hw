package ru.otus.autologging.proxy;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import ru.otus.autologging.AutoLoggerInterface;
import ru.otus.autologging.annotations.Log;

public class Ioc {
    private Ioc() {
    }

    public static AutoLoggerInterface newInstance(
        Class<?> clazz,
        Class<?>[] argsTypes,
        Object[] args
    ) {
        Object obj;
        try {
            Constructor<?> constructor = clazz.getConstructor(argsTypes);
            obj = constructor.newInstance(args);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("Can't create object", e);
        }
        InvocationHandler handler = new AutologgingInvocationHandler((AutoLoggerInterface) obj, clazz);
        return (AutoLoggerInterface) Proxy.newProxyInstance(
            Ioc.class.getClassLoader(),
            new Class<?>[] {AutoLoggerInterface.class},
            handler
        );
    }

    static class AutologgingInvocationHandler implements InvocationHandler {

        private final AutoLoggerInterface autoLogger;
        private final List<Method> methodsForAutoLogger = new ArrayList<>();

        AutologgingInvocationHandler(
            AutoLoggerInterface autoLogger,
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
