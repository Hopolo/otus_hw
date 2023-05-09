package ru.otus.atm;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.atm.bills.Bill;
import ru.otus.atm.bills.Nominal;

class ATMTest {

    List<Bill> billList;
    ATM atm;

    @BeforeEach
    void init() {
        atm = new ATM();
        billList = new ArrayList<>();
        billList.add(new Bill(Nominal.FIFTYTH));
        billList.add(new Bill(Nominal.ONE_HUNDREDTH));
        billList.add(new Bill(Nominal.FIVE_HUNDREDTH));
        billList.add(new Bill(Nominal.ONE_THOUSANDTH));
        billList.add(new Bill(Nominal.FIVE_THOUSANDTH));
    }

    @Test
    void getBills() {
        atm.depositBills(billList);
        assertEquals(6650, atm.balanceATM());
        List<Bill> issued = atm.getBills(5050);
        assertEquals(1600, atm.balanceATM());
    }

    @Test
    void balance() {
        atm.depositBills(billList);
        var result = atm.balanceATM();
        assertEquals(6650, result);
    }

}