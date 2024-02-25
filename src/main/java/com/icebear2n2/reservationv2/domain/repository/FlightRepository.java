package com.icebear2n2.reservationv2.domain.repository;

import com.icebear2n2.reservationv2.domain.entity.Flight;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;

public interface FlightRepository extends JpaRepository<Flight, Long> {

    Page<Flight> findAllByDeletedAtNotNull(Pageable pageable
);

    Page<Flight> findAllByDeletedAtNull(Pageable pageable
);

    // 출발 시간이 빠른 순으로 항공편 조회
    @Query("select f from Flight f where f.deletedAt is null order by f.departureTime asc")
    Page<Flight> findAllByOrderByDepartureTimeAsc(Pageable pageable
);

    // 출발 시간이 느린 순으로 항공편 조회
    @Query("select f from Flight f where f.deletedAt is null order by f.departureTime desc")
    Page<Flight> findAllByOrderByDepartureTimeDesc(Pageable pageable);

    // 특정 날짜에 출발하는 항공편 조회
    @Query("select f from Flight f where f.deletedAt is null and f.departureDate = :date order by f.departureDate asc")
    Page<Flight> findByDepartureDate(LocalDate date, Pageable pageable);
}
