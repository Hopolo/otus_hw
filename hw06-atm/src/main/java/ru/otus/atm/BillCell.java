package ru.otus.atm;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import ru.otus.atm.bills.Bill;

public class BillCell<T extends Bill> {
    private final Class<T> billType;
    private List<Bill> bills;

    public BillCell(Class<T> billType) {
        this.billType = billType;
        bills = new ArrayList<>();
    }

    public Class<T> getBillType() {
        return this.billType;
    }

    public void deposit(Bill bill) {
        bills.add(bill);
    }

    public int showBalance() {
        return bills.stream().mapToInt(Bill::value).sum();
    }

    public int getBills(
        int sum,
        List<Bill> issuedBills
    ) {
        int value = Optional.ofNullable(bills.get(0)).get().value();
        int count = sum / value;
        int result = sum - count * value;
        Iterator<Bill> iterator = bills.iterator();
        while (iterator.hasNext() && count > 0) {
            issuedBills.add(iterator.next());
            iterator.remove();
            count--;
        }
        return result;
    }

}
