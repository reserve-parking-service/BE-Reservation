package com.icebear2n2.reservationv2.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "seats")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String seatNumber;
    @Enumerated(EnumType.STRING)
    private SeatClass seatClass;
    private int price;
    @ManyToOne
    @JoinColumn(name = "flight_id")
    private Flight flight;
    private boolean reserved;

    // 할인 관련 필드
    @ColumnDefault("0")
    private int adultDiscountRate; // 성인은 할인 없음
    @ColumnDefault("25")
    private int childDiscountRate; // 어린이는 25% 할인
    @ColumnDefault("50")
    private int infantDiscountRate; // 유아는 50% 할인
    public static Seat generateBusinessSeats(Flight flight, int number, int businessClassPrice) {
        var businessClass = SeatClass.BUSINESS;

        return Seat.builder()
                .seatNumber(businessClass.generateSeatNumber(number)) // 고정된 4자리의 좌석 번호로 설정
                .seatClass(businessClass)
                .price(businessClassPrice) // 비즈니스석 가격 설정
                .reserved(false)
                .adultDiscountRate(0)
                .childDiscountRate(25)
                .infantDiscountRate(50)
                .flight(flight) // 해당 좌석이 속한 비행기 설정
                .build();
    }

    public static Seat generateEconomyClassSeats(Flight flight, int number, int economyClassPrice){
        var economyClass = SeatClass.ECONOMY;

        return Seat.builder()
                .seatNumber(economyClass.generateSeatNumber(number)) // 이코노미석의 좌석 번호 설정
                .seatClass(economyClass)
                .price(economyClassPrice) // 이코노미석 가격 설정
                .reserved(false)
                .adultDiscountRate(0)
                .childDiscountRate(25)
                .infantDiscountRate(50)
                .flight(flight) // 해당 좌석이 속한 비행기 설정
                .build();
    }

    public void reserved() {
        this.reserved = true;
    }

    public void cancelReserved() { this.reserved = false; }

    public void setReserved(boolean reserved) {
        this.reserved = reserved;
    }
}
