package ru.otus.atm.bills;

import java.util.List;
import java.util.Random;
import ru.otus.atm.BillCell;

public abstract class Bill {

    protected int id;

    Bill() {
        var random = new Random();
        id = random.nextInt(99999999);
    }

    public void depositBill(List<BillCell<? extends Bill>> cells) {
        cells.stream()
            .filter(billCell -> billCell.getBillType().equals(this.getClass()))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Can't find bill type"))
            .deposit(this);
    }

    public abstract int value();
}
