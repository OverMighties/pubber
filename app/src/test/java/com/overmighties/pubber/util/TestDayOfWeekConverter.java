package com.overmighties.pubber.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.util.Optional;

public class TestDayOfWeekConverter {

    @Test
    public void testNone(){
        DayOfWeekConverter none = DayOfWeekConverter.NONE;
        assertEquals(none.getNumeric(), null);
        assertEquals(none.getUpperCase(), null);
        assertEquals(none.getNormal(), null);
        assertEquals(none, DayOfWeekConverter.getByNumber(-1));
        assertEquals(none, DayOfWeekConverter.getByCamelCase("NONE"));
        assertEquals(none, DayOfWeekConverter.getByUpperCase("None"));
    }

    @Test
    public void testMonday(){
        DayOfWeekConverter monday = DayOfWeekConverter.MONDAY;
        assertEquals(Optional.ofNullable(monday.getNumeric()), Optional.ofNullable(1));
        assertEquals(monday.getUpperCase(), "MONDAY");
        assertEquals(monday.getNormal(), "Monday");
        assertEquals(monday, DayOfWeekConverter.getByNumber(1));
        assertEquals(monday, DayOfWeekConverter.getByUpperCase("MONDAY"));
        assertEquals(monday, DayOfWeekConverter.getByCamelCase("Monday"));
    }

    @Test
    public void testTuesday(){
        DayOfWeekConverter tuesday = DayOfWeekConverter.TUESDAY;
        assertEquals(Optional.ofNullable(tuesday.getNumeric()), Optional.ofNullable(2));
        assertEquals(tuesday.getUpperCase(), "TUESDAY");
        assertEquals(tuesday.getNormal(), "Tuesday");
        assertEquals(tuesday, DayOfWeekConverter.getByNumber(2));
        assertEquals(tuesday, DayOfWeekConverter.getByUpperCase("TUESDAY"));
        assertEquals(tuesday, DayOfWeekConverter.getByCamelCase("Tuesday"));
    }

    @Test
    public void testWednesday(){
        DayOfWeekConverter wednesday = DayOfWeekConverter.WEDNESDAY;
        assertEquals(Optional.ofNullable(wednesday.getNumeric()), Optional.ofNullable(3));
        assertEquals(wednesday.getUpperCase(), "WEDNESDAY");
        assertEquals(wednesday.getNormal(), "Wednesday");
        assertEquals(wednesday, DayOfWeekConverter.getByNumber(3));
        assertEquals(wednesday, DayOfWeekConverter.getByUpperCase("WEDNESDAY"));
        assertEquals(wednesday, DayOfWeekConverter.getByCamelCase("Wednesday"));
    }

    @Test
    public void testThurday(){
        DayOfWeekConverter thursday = DayOfWeekConverter.THURSDAY;
        assertEquals(Optional.ofNullable(thursday.getNumeric()), Optional.ofNullable(4));
        assertEquals(thursday.getUpperCase(), "THURSDAY");
        assertEquals(thursday.getNormal(), "Thursday");
        assertEquals(thursday, DayOfWeekConverter.getByNumber(4));
        assertEquals(thursday, DayOfWeekConverter.getByUpperCase("THURSDAY"));
        assertEquals(thursday, DayOfWeekConverter.getByCamelCase("Thursday"));
    }

    @Test
    public void testFriday(){
        DayOfWeekConverter friday = DayOfWeekConverter.FRIDAY;
        assertEquals(Optional.ofNullable(friday.getNumeric()), Optional.ofNullable(5));
        assertEquals(friday.getUpperCase(), "FRIDAY");
        assertEquals(friday.getNormal(), "Friday");
        assertEquals(friday, DayOfWeekConverter.getByNumber(5));
        assertEquals(friday, DayOfWeekConverter.getByUpperCase("FRIDAY"));
        assertEquals(friday, DayOfWeekConverter.getByCamelCase("Friday"));
    }

    @Test
    public void testSaturday(){
        DayOfWeekConverter saturday = DayOfWeekConverter.SATURDAY;
        assertEquals(Optional.ofNullable(saturday.getNumeric()), Optional.ofNullable(6));
        assertEquals(saturday.getUpperCase(), "SATURDAY");
        assertEquals(saturday.getNormal(), "Saturday");
        assertEquals(saturday, DayOfWeekConverter.getByNumber(6));
        assertEquals(saturday, DayOfWeekConverter.getByUpperCase("SATURDAY"));
        assertEquals(saturday, DayOfWeekConverter.getByCamelCase("Saturday"));
    }

    @Test
    public void testSunday(){
        DayOfWeekConverter sunday = DayOfWeekConverter.SUNDAY;
        assertEquals(Optional.ofNullable(sunday.getNumeric()), Optional.ofNullable(7));
        assertEquals(sunday.getUpperCase(), "SUNDAY");
        assertEquals(sunday.getNormal(), "Sunday");
        assertEquals(sunday, DayOfWeekConverter.getByNumber(7));
        assertEquals(sunday, DayOfWeekConverter.getByUpperCase("SUNDAY"));
        assertEquals(sunday, DayOfWeekConverter.getByCamelCase("Sunday"));
    }

    @Test
    public void testPoniedzialek(){
        DayOfWeekConverter poniedzialek = DayOfWeekConverter.PONIEDZIALEK;
        assertEquals(Optional.ofNullable(poniedzialek.getNumeric()), Optional.ofNullable(1));
        assertEquals(poniedzialek.getUpperCase(), "PONIEDZIAŁEK");
        assertEquals(poniedzialek.getNormal(), "Poniedziałek");
        assertEquals(poniedzialek, DayOfWeekConverter.polishDayOfWeekConverter(1));
        assertEquals(DayOfWeekConverter.MONDAY, DayOfWeekConverter.getByCamelCase("Poniedziałek"));
    }

    @Test
    public void testWtorek(){
        DayOfWeekConverter wtorek = DayOfWeekConverter.WTOREK;
        assertEquals(Optional.ofNullable(wtorek.getNumeric()), Optional.ofNullable(2));
        assertEquals(wtorek.getUpperCase(), "WTOREK");
        assertEquals(wtorek.getNormal(), "Wtorek");
        assertEquals(wtorek, DayOfWeekConverter.polishDayOfWeekConverter(2));
        assertEquals(DayOfWeekConverter.TUESDAY, DayOfWeekConverter.getByCamelCase("Wtorek"));
    }

    @Test
    public void testSroda(){
        DayOfWeekConverter sroda = DayOfWeekConverter.SRODA;
        assertEquals(Optional.ofNullable(sroda.getNumeric()), Optional.ofNullable(3));
        assertEquals(sroda.getUpperCase(), "ŚRODA");
        assertEquals(sroda.getNormal(), "Środa");
        assertEquals(sroda, DayOfWeekConverter.polishDayOfWeekConverter(3));
        assertEquals(DayOfWeekConverter.WEDNESDAY, DayOfWeekConverter.getByCamelCase("Środa"));
    }

    @Test
    public void testCzwartek(){
        DayOfWeekConverter czwartek = DayOfWeekConverter.CZWARTEK;
        assertEquals(Optional.ofNullable(czwartek.getNumeric()), Optional.ofNullable(4));
        assertEquals(czwartek.getUpperCase(), "CZWARTEK");
        assertEquals(czwartek.getNormal(), "Czwartek");
        assertEquals(czwartek, DayOfWeekConverter.polishDayOfWeekConverter(4));
        assertEquals(DayOfWeekConverter.THURSDAY, DayOfWeekConverter.getByCamelCase("Czwartek"));
    }

    @Test
    public void testPiatek(){
        DayOfWeekConverter piatek = DayOfWeekConverter.PIATEK;
        assertEquals(Optional.ofNullable(piatek.getNumeric()), Optional.ofNullable(5));
        assertEquals(piatek.getUpperCase(), "PIĄTEK");
        assertEquals(piatek.getNormal(), "Piątek");
        assertEquals(piatek, DayOfWeekConverter.polishDayOfWeekConverter(5));
        assertEquals(DayOfWeekConverter.FRIDAY, DayOfWeekConverter.getByCamelCase("Piątek"));
    }

    @Test
    public void testSobota(){
        DayOfWeekConverter sobota = DayOfWeekConverter.SOBOTA;
        assertEquals(Optional.ofNullable(sobota.getNumeric()), Optional.ofNullable(6));
        assertEquals(sobota.getUpperCase(), "SOBOTA");
        assertEquals(sobota.getNormal(), "Sobota");
        assertEquals(sobota, DayOfWeekConverter.polishDayOfWeekConverter(6));
        assertEquals(DayOfWeekConverter.SATURDAY, DayOfWeekConverter.getByCamelCase("Sobota"));
    }

    @Test
    public void testNiedziela(){
        DayOfWeekConverter niedziela = DayOfWeekConverter.NIEDZIELA;
        assertEquals(Optional.ofNullable(niedziela.getNumeric()), Optional.ofNullable(7));
        assertEquals(niedziela.getUpperCase(), "NIEDZIELA");
        assertEquals(niedziela.getNormal(), "Niedziela");
        assertEquals(niedziela, DayOfWeekConverter.polishDayOfWeekConverter(7));
        assertEquals(DayOfWeekConverter.SUNDAY, DayOfWeekConverter.getByCamelCase("Niedziela"));
    }

}
