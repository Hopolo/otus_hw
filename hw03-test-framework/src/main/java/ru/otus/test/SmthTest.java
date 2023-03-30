package ru.otus.test;

import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;

public class SmthTest {

    @Before
    public void wakeUp(){
        System.out.println("STAND UP!");
    }


    @Before
    public void stretch(){
        System.out.println("...stretching...");
    }

    @Test
    public void trainingGym(){
        throw new RuntimeException("UPS! You hurt your shoulder");
    }

    @Test
    public void trainingPool(){
        System.out.println("..stroke..stroke..");
    }

    @After
    public void sleep(){
        System.out.println("..zzz..");
    }

}