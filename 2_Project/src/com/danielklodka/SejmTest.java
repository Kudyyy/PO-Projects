package com.danielklodka;

import static org.junit.Assert.*;

/**
 * Created by daniel on 13.01.2017.
 */
public class SejmTest {
    @org.junit.Test
    public void spendingsOnTrifleRedacorationsOfDeputy() throws Exception {
        assertEquals(506.77,new Sejm(8).spendingsOnTrifleRedacorationsOfDeputy("Zofia Czernow"),0.001);
    }

    @org.junit.Test
    public void spendingsOfDeputy() throws Exception {
        assertEquals(251773.129999,new Sejm(8).spendingsOfDeputy("Zofia Czernow"),0.001);
    }

}