package com.icebear2n2.reservationv2.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

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
    private String seatClass;
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
}
