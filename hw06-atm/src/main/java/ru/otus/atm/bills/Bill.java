package ru.otus.atm.bills;

import java.util.Random;

public class Bill {

    private final int id;
    private final Nominal nominal;

    public Bill(Nominal nominal) {
        var random = new Random();
        id = random.nextInt(99999999);
        this.nominal = nominal;
    }

    public Nominal nominal() {
        return nominal;
    }

    public int getId() {
        return id;
    }
}

