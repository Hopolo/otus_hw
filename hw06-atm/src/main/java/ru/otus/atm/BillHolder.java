package ru.otus.atm;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.EnumMap;
import java.util.List;
import ru.otus.atm.bills.Bill;
import ru.otus.atm.bills.Nominal;

public class BillHolder {

    EnumMap<Nominal, Cell> billContainer;

    BillHolder() {
        billContainer = new EnumMap<>(Nominal.class);
        for (Nominal nominal : Nominal.values()) {
            billContainer.put(nominal, new Cell());
        }
    }

    public void addBill(Bill bill) {
        billContainer.get(bill.nominal()).add(bill);
    }

    public List<Bill> getBills(int sum) {
        List<Bill> issuedBills = new ArrayList<>();
        int curSum = sum;
        for (Nominal nominal : billContainer.keySet()) {
            Cell cell = billContainer.get(nominal);
            if (curSum < nominal.getValue() ||
                cell.bills.isEmpty()) {
                continue;
            }
            while (!cell.bills.isEmpty() && curSum > 0) {
                issuedBills.add(cell.getBill());
                curSum -= nominal.getValue();
            }
        }
        return issuedBills;
    }

    public int balance() {
        return billContainer.values().stream().mapToInt(Cell::balance).sum();
    }

    private static class Cell {
        Deque<Bill> bills;

        Cell() {
            bills = new ArrayDeque<>();
        }

        int balance() {
            return bills.stream().mapToInt(bill -> bill.nominal().getValue()).sum();
        }

        void add(Bill bill) {
            bills.add(bill);
        }

        Bill getBill() {
            return bills.remove();
        }
    }
}
