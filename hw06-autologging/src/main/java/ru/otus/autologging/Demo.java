package ru.otus.autologging;

import ru.otus.autologging.proxy.Ioc;

public class Demo {
    public static void main(String[] args) {
        AutoLoggerInterface autoLogger = Ioc.newInstance(AutoLogger.class, new Class<?>[] {int.class}, new Object[] {1});
        autoLogger.pushUp(3, true);
        autoLogger.pushUp(5);
        autoLogger.squat(7);
    }
}