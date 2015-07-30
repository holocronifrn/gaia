package com.holocron.gaia.model;

import com.holocron.gaia.R;

/**
 * Created by jhoanes on 30/07/15.
 */
public enum WeekDay {

    MONDAY(R.string.segunda), TUESDAY(R.string.terca);

    int value;

    WeekDay(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }


}
