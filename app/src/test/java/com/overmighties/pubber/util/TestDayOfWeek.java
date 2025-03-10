package com.overmighties.pubber.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import java.util.Optional;

public class TestDayOfWeek {

    @Test
    public void testNone(){
        DayOfWeek none = DayOfWeek.NONE;
        assertNull(none.getNumeric());
        assertNull(none.getUpperCase());
        assertNull(none.getNormal());
        assertEquals(none, DayOfWeek.getByNumber(-1));
        assertEquals(none, DayOfWeek.getByCamelCase("NONE"));
        assertEquals(none, DayOfWeek.getByUpperCase("None"));
    }

    @Test
    public void testMonday(){
        DayOfWeek monday = DayOfWeek.MONDAY;
        assertEquals(Optional.ofNullable(monday.getNumeric()), Optional.ofNullable(1));
        assertEquals(monday.getUpperCase(), "MONDAY");
        assertEquals(monday.getNormal(), "Monday");
        assertEquals(monday, DayOfWeek.getByNumber(1));
        assertEquals(monday, DayOfWeek.getByUpperCase("MONDAY"));
        assertEquals(monday, DayOfWeek.getByCamelCase("Monday"));
    }

    @Test
    public void testTuesday(){
        DayOfWeek tuesday = DayOfWeek.TUESDAY;
        assertEquals(Optional.ofNullable(tuesday.getNumeric()), Optional.ofNullable(2));
        assertEquals(tuesday.getUpperCase(), "TUESDAY");
        assertEquals(tuesday.getNormal(), "Tuesday");
        assertEquals(tuesday, DayOfWeek.getByNumber(2));
        assertEquals(tuesday, DayOfWeek.getByUpperCase("TUESDAY"));
        assertEquals(tuesday, DayOfWeek.getByCamelCase("Tuesday"));
    }

    @Test
    public void testWednesday(){
        DayOfWeek wednesday = DayOfWeek.WEDNESDAY;
        assertEquals(Optional.ofNullable(wednesday.getNumeric()), Optional.ofNullable(3));
        assertEquals(wednesday.getUpperCase(), "WEDNESDAY");
        assertEquals(wednesday.getNormal(), "Wednesday");
        assertEquals(wednesday, DayOfWeek.getByNumber(3));
        assertEquals(wednesday, DayOfWeek.getByUpperCase("WEDNESDAY"));
        assertEquals(wednesday, DayOfWeek.getByCamelCase("Wednesday"));
    }

    @Test
    public void testThurday(){
        DayOfWeek thursday = DayOfWeek.THURSDAY;
        assertEquals(Optional.ofNullable(thursday.getNumeric()), Optional.ofNullable(4));
        assertEquals(thursday.getUpperCase(), "THURSDAY");
        assertEquals(thursday.getNormal(), "Thursday");
        assertEquals(thursday, DayOfWeek.getByNumber(4));
        assertEquals(thursday, DayOfWeek.getByUpperCase("THURSDAY"));
        assertEquals(thursday, DayOfWeek.getByCamelCase("Thursday"));
    }

    @Test
    public void testFriday(){
        DayOfWeek friday = DayOfWeek.FRIDAY;
        assertEquals(Optional.ofNullable(friday.getNumeric()), Optional.ofNullable(5));
        assertEquals(friday.getUpperCase(), "FRIDAY");
        assertEquals(friday.getNormal(), "Friday");
        assertEquals(friday, DayOfWeek.getByNumber(5));
        assertEquals(friday, DayOfWeek.getByUpperCase("FRIDAY"));
        assertEquals(friday, DayOfWeek.getByCamelCase("Friday"));
    }

    @Test
    public void testSaturday(){
        DayOfWeek saturday = DayOfWeek.SATURDAY;
        assertEquals(Optional.ofNullable(saturday.getNumeric()), Optional.ofNullable(6));
        assertEquals(saturday.getUpperCase(), "SATURDAY");
        assertEquals(saturday.getNormal(), "Saturday");
        assertEquals(saturday, DayOfWeek.getByNumber(6));
        assertEquals(saturday, DayOfWeek.getByUpperCase("SATURDAY"));
        assertEquals(saturday, DayOfWeek.getByCamelCase("Saturday"));
    }

    @Test
    public void testSunday(){
        DayOfWeek sunday = DayOfWeek.SUNDAY;
        assertEquals(Optional.ofNullable(sunday.getNumeric()), Optional.ofNullable(7));
        assertEquals(sunday.getUpperCase(), "SUNDAY");
        assertEquals(sunday.getNormal(), "Sunday");
        assertEquals(sunday, DayOfWeek.getByNumber(7));
        assertEquals(sunday, DayOfWeek.getByUpperCase("SUNDAY"));
        assertEquals(sunday, DayOfWeek.getByCamelCase("Sunday"));
    }

    @Test
    public void testPoniedzialek(){
        DayOfWeek poniedzialek = DayOfWeek.PONIEDZIALEK;
        assertEquals(Optional.ofNullable(poniedzialek.getNumeric()), Optional.ofNullable(1));
        assertEquals(poniedzialek.getUpperCase(), "PONIEDZIAŁEK");
        assertEquals(poniedzialek.getNormal(), "Poniedziałek");
        assertEquals(poniedzialek, DayOfWeek.polishDayOfWeekConverter(1));
        assertEquals(DayOfWeek.MONDAY, DayOfWeek.getByCamelCase("Poniedziałek"));
    }

    @Test
    public void testWtorek(){
        DayOfWeek wtorek = DayOfWeek.WTOREK;
        assertEquals(Optional.ofNullable(wtorek.getNumeric()), Optional.ofNullable(2));
        assertEquals(wtorek.getUpperCase(), "WTOREK");
        assertEquals(wtorek.getNormal(), "Wtorek");
        assertEquals(wtorek, DayOfWeek.polishDayOfWeekConverter(2));
        assertEquals(DayOfWeek.TUESDAY, DayOfWeek.getByCamelCase("Wtorek"));
    }

    @Test
    public void testSroda(){
        DayOfWeek sroda = DayOfWeek.SRODA;
        assertEquals(Optional.ofNullable(sroda.getNumeric()), Optional.ofNullable(3));
        assertEquals(sroda.getUpperCase(), "ŚRODA");
        assertEquals(sroda.getNormal(), "Środa");
        assertEquals(sroda, DayOfWeek.polishDayOfWeekConverter(3));
        assertEquals(DayOfWeek.WEDNESDAY, DayOfWeek.getByCamelCase("Środa"));
    }

    @Test
    public void testCzwartek(){
        DayOfWeek czwartek = DayOfWeek.CZWARTEK;
        assertEquals(Optional.ofNullable(czwartek.getNumeric()), Optional.ofNullable(4));
        assertEquals(czwartek.getUpperCase(), "CZWARTEK");
        assertEquals(czwartek.getNormal(), "Czwartek");
        assertEquals(czwartek, DayOfWeek.polishDayOfWeekConverter(4));
        assertEquals(DayOfWeek.THURSDAY, DayOfWeek.getByCamelCase("Czwartek"));
    }

    @Test
    public void testPiatek(){
        DayOfWeek piatek = DayOfWeek.PIATEK;
        assertEquals(Optional.ofNullable(piatek.getNumeric()), Optional.ofNullable(5));
        assertEquals(piatek.getUpperCase(), "PIĄTEK");
        assertEquals(piatek.getNormal(), "Piątek");
        assertEquals(piatek, DayOfWeek.polishDayOfWeekConverter(5));
        assertEquals(DayOfWeek.FRIDAY, DayOfWeek.getByCamelCase("Piątek"));
    }

    @Test
    public void testSobota(){
        DayOfWeek sobota = DayOfWeek.SOBOTA;
        assertEquals(Optional.ofNullable(sobota.getNumeric()), Optional.ofNullable(6));
        assertEquals(sobota.getUpperCase(), "SOBOTA");
        assertEquals(sobota.getNormal(), "Sobota");
        assertEquals(sobota, DayOfWeek.polishDayOfWeekConverter(6));
        assertEquals(DayOfWeek.SATURDAY, DayOfWeek.getByCamelCase("Sobota"));
    }

    @Test
    public void testNiedziela(){
        DayOfWeek niedziela = DayOfWeek.NIEDZIELA;
        assertEquals(Optional.ofNullable(niedziela.getNumeric()), Optional.ofNullable(7));
        assertEquals(niedziela.getUpperCase(), "NIEDZIELA");
        assertEquals(niedziela.getNormal(), "Niedziela");
        assertEquals(niedziela, DayOfWeek.polishDayOfWeekConverter(7));
        assertEquals(DayOfWeek.SUNDAY, DayOfWeek.getByCamelCase("Niedziela"));
    }

}
