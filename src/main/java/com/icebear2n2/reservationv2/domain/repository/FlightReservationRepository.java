package com.icebear2n2.reservationv2.domain.repository;

import com.icebear2n2.reservationv2.domain.entity.FlightReservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlightReservationRepository extends JpaRepository<FlightReservation, Long> {
}
