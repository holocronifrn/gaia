package com.holocron.gaia.model;

public enum WeekDay {

    SELECT(0), MONDAY(1), TUESDAY(2), WEDNESDAY(3), THURSDAY(4), FRIDAY(5);

    int value;

    WeekDay(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static WeekDay getDay(int position) {
        for (WeekDay day: WeekDay.values()) {
            if (day.value == position) {
                return day;
            }
        }
        return null;
    }



}