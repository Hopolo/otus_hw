package ru.otus.atm;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.atm.bills.Bill;
import ru.otus.atm.bills.Fiftyth;
import ru.otus.atm.bills.FiveHundredth;
import ru.otus.atm.bills.FiveThousandth;
import ru.otus.atm.bills.OneHundredth;
import ru.otus.atm.bills.OneThousandth;

class ATMTest {

    List<Bill> billList;
    ATM atm;

    @BeforeEach
    void init() {
        atm = new ATM();
        billList = new ArrayList<>();
        billList.add(new Fiftyth());
        billList.add(new OneHundredth());
        billList.add(new OneThousandth());
        billList.add(new FiveHundredth());
        billList.add(new FiveThousandth());
    }

    @Test
    void checkSum() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> new ATM().getBills(40));
        assertEquals("Can't give out that sum", exception.getMessage());
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