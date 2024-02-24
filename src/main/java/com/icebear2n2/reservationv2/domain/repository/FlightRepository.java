package com.icebear2n2.reservationv2.domain.repository;

import com.icebear2n2.reservationv2.domain.entity.Flight;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;

public interface FlightRepository extends JpaRepository<Flight, Long> {

    // 삭제된 항공편 조회
    @Query("select f from Flight f where f.deletedAt is not null")
    Page<Flight> findAllByDeletedAtNotNull(PageRequest pageRequest);

    // 삭제되지 않은 항공편 조회
    @Query("select f from Flight f where f.deletedAt is null")
    Page<Flight> findAllByDeletedAtNull(PageRequest pageRequest);

    // 출발 시간이 빠른 순으로 항공편 조회
    @Query("select f from Flight f where f.deletedAt is null order by f.departureTime asc")
    Page<Flight> findAllByOrderByDepartureTimeAsc(PageRequest pageRequest);

    // 출발 시간이 느린 순으로 항공편 조회
    @Query("select f from Flight f where f.deletedAt is null order by f.departureTime desc")
    Page<Flight> findAllByOrderByDepartureTimeDesc(PageRequest pageRequest);

    // 특정 날짜에 출발하는 항공편 조회
    @Query("select f from Flight f where f.deletedAt is null and f.departureDate = :date order by f.departureDate asc")
    Page<Flight> findByDepartureDate(LocalDate date, PageRequest pageRequest);
}
