package com.icebear2n2.reservationv2.domain.repository;

import com.icebear2n2.reservationv2.domain.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatRepository extends JpaRepository<Seat, Long> {
    Seat findBySeatNumber(String SeatNumber);
}
