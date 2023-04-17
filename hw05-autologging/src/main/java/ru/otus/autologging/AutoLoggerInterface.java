package ru.otus.autologging;

public interface AutoLoggerInterface {

    void pushUp(int count);

    void pushUp(
        int count,
        boolean slowly
    );

    void squat(int count);

}
