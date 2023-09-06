package ru.otus.atm;

import java.util.List;
import ru.otus.atm.bills.Bill;

public class ATM {

    private final BillHolder billHolder;

    public ATM() {
        billHolder = new BillHolder();
    }

    public void depositBills(List<Bill> bills) {
        for (Bill bill : bills) {
            billHolder.addBill(bill);
        }
    }

    public List<Bill> getBills(int sum) {
        if (balanceATM() < sum) {
            throw new RuntimeException("Not enough money");
        }
        return billHolder.getBills(sum);
    }

    public int balanceATM() {
        return billHolder.balance();
    }

}