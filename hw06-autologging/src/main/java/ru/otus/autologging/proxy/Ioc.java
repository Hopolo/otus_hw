package ru.otus.autologging.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import ru.otus.autologging.AutoLogger;
import ru.otus.autologging.AutoLoggerInterface;
import ru.otus.autologging.annotations.Log;

public class Ioc {
    private Ioc() {
    }

    public static AutoLoggerInterface newInstance() {
        InvocationHandler handler = new AutologgingInvocationHandler(new AutoLogger());
        return (AutoLoggerInterface) Proxy.newProxyInstance(
            Ioc.class.getClassLoader(),
            new Class<?>[] {AutoLoggerInterface.class},
            handler
        );
    }

    static class AutologgingInvocationHandler implements InvocationHandler {

        private final AutoLoggerInterface autoLogger;

        AutologgingInvocationHandler(AutoLoggerInterface autoLogger) {
            this.autoLogger = autoLogger;
        }

        @Override
        public Object invoke(
            Object proxy,
            Method method,
            Object[] args
        ) throws Throwable {
            Method clazMethod = AutoLogger.class.getDeclaredMethod(method.getName(), method.getParameterTypes());
            if (clazMethod.getAnnotationsByType(Log.class).length > 0) {
                System.out.println("method: " + clazMethod.getName() + "(), params: " + Arrays.toString(args));
            }
            return method.invoke(autoLogger, args);
        }
    }
}
