package ru.otus.autologging;

import ru.otus.autologging.annotations.Log;

public class AutoLogger implements AutoLoggerInterface {

    @Override
    public void pushUp(int count) {
        for (int i = 0; i < count; i++) {
            System.out.println("push-up");
        }
    }

    @Log
    @Override
    public void pushUp(
        int count,
        boolean slowly
    ) {
        if (!slowly) {
            pushUp(count);
            return;
        }
        for (int i = 0; i < count; i++) {
            System.out.println("puuuuuush-uuuuuup");
        }
    }

    @Log
    @Override
    public void squat(int count) {
        for (int i = 0; i < count; i++) {
            System.out.println("squat");
        }
    }

}
