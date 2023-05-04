package ru.otus.atm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import ru.otus.atm.bills.Bill;
import ru.otus.atm.bills.Fiftyth;
import ru.otus.atm.bills.FiveHundredth;
import ru.otus.atm.bills.FiveThousandth;
import ru.otus.atm.bills.OneHundredth;
import ru.otus.atm.bills.OneThousandth;

public class ATM {

    private final List<BillCell<? extends Bill>> listOfCells;
    private BillCell<Fiftyth> fiftythsCell;
    private BillCell<OneHundredth> oneHundredthsCell;
    private BillCell<FiveHundredth> fiveHundredthsCell;
    private BillCell<OneThousandth> oneThousandthsCell;
    private BillCell<FiveThousandth> fiveThousandthsCell;

    public ATM() {
        fiftythsCell = new BillCell<>(Fiftyth.class);
        oneHundredthsCell = new BillCell<>(OneHundredth.class);
        fiveHundredthsCell = new BillCell<>(FiveHundredth.class);
        oneThousandthsCell = new BillCell<>(OneThousandth.class);
        fiveThousandthsCell = new BillCell<>(FiveThousandth.class);
        listOfCells = Arrays.asList(fiveThousandthsCell, oneThousandthsCell, fiveHundredthsCell, oneHundredthsCell, fiftythsCell);
    }

    public boolean depositBills(List<Bill> bills) {
        bills.forEach(bill -> bill.depositBill(listOfCells));
        return true;
    }

    public List<Bill> getBills(int sum) {
        if (sum % 50 != 0) {
            throw new RuntimeException("Can't give out that sum");
        }
        if (balanceATM() < sum) {
            throw new RuntimeException("Not enough money");
        }
        var issuedBills = new ArrayList<Bill>();
        int currSum = sum;
        for (BillCell<? extends Bill> cell : listOfCells) {
            currSum = cell.getBills(currSum, issuedBills);
        }
        return issuedBills;
    }

    public int balanceATM() {
        return listOfCells.stream().mapToInt(BillCell::showBalance).sum();
    }

}