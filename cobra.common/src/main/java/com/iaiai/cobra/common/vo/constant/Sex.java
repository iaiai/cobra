package com.iaiai.cobra.common.vo.constant;

public enum Sex {

    UNKNOW(0), //未知
    MALE(1),   //男
    FAMALE(2);  //女

    private int value;

    Sex(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
