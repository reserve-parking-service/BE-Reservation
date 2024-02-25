package com.icebear2n2.reservationv2.domain.entity;


import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum SeatClass {
    BUSINESS("B"),
    ECONOMY("E"),
    SUPER("S");

    private final String prefix;

    public String generateSeatNumber(int number) {
        return prefix + number;
    }
}
