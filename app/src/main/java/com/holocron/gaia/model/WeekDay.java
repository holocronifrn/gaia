package com.holocron.gaia.model;

public enum WeekDay {

    MONDAY(0), TUESDAY(1), WEDNESDAY(2), THURSDAY(3), FRIDAY(4);

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