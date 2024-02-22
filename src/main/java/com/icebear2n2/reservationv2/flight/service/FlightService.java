package com.icebear2n2.reservationv2.flight.service;

import com.icebear2n2.reservationv2.domain.entity.Flight;
import com.icebear2n2.reservationv2.domain.entity.Seat;
import com.icebear2n2.reservationv2.domain.repository.FlightRepository;
import com.icebear2n2.reservationv2.domain.request.FlightRequest;
import com.icebear2n2.reservationv2.domain.request.UpdateFlightRequest;
import com.icebear2n2.reservationv2.domain.response.FlightResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FlightService {
    private final FlightRepository flightRepository;

    // 비행기 생성
    public FlightResponse createFlight(FlightRequest flightRequest) {
        try {
            Flight flight = flightRequest.toEntity();
            generateSeats(flight, flightRequest.toEntity().getSeatCapacity(), flightRequest.getBusinessClassPrice(), flightRequest.getEconomyClassPrice() ); // 좌석 생성
            Flight savedFlight = flightRepository.save(flight); // 저장
            return new FlightResponse(savedFlight);
        } catch (Exception ex) {
            throw new RuntimeException("Failed to create flight: " + ex.getMessage());
        }
    }


    // 비즈니스 로직: 좌석 생성
    private void generateSeats(Flight flight, int totalSeats, int businessClassPrice, int economyClassPrice) {
        List<Seat> seats = new ArrayList<>();

        // 비즈니스석 생성 (최대 4개)
        int maxBusinessClassSeats = Math.min(totalSeats / 2, 4); // 최대 4개의 비즈니스석 생성
        for (int i = 1; i <= maxBusinessClassSeats; i++) {
            Seat seat = Seat.builder()
                    .seatNumber("B" + i) // 고정된 4자리의 좌석 번호로 설정
                    .seatClass("Business")
                    .price(businessClassPrice) // 비즈니스석 가격 설정
                    .reserved(false)
                    .adultDiscountRate(0)
                    .childDiscountRate(25)
                    .infantDiscountRate(50)
                    .flight(flight) // 해당 좌석이 속한 비행기 설정
                    .build();
            seats.add(seat); // 생성된 좌석을 리스트에 추가
        }

        // 이코노미석 생성
        int economyClassSeats = totalSeats - maxBusinessClassSeats;
        for (int i = 1; i <= economyClassSeats; i++) {
            Seat seat = Seat.builder()
                    .seatNumber("E" + i) // 이코노미석의 좌석 번호 설정
                    .seatClass("Economy")
                    .price(economyClassPrice) // 이코노미석 가격 설정
                    .reserved(false)
                    .adultDiscountRate(0)
                    .childDiscountRate(25)
                    .infantDiscountRate(50)
                    .flight(flight) // 해당 좌석이 속한 비행기 설정
                    .build();
            seats.add(seat); // 생성된 좌석을 리스트에 추가
        }

        flight.setSeats(seats); // 비행기에 생성된 좌석 설정
    }


    // 항공편 조회
    public FlightResponse getFlightById(Long flightId) {
        Flight flight = flightRepository.findById(flightId)
                .orElseThrow(() -> new EntityNotFoundException("Flight not found with id: " + flightId));
        return new FlightResponse(flight);
    }

    // 출발 시간 빠른 순으로 항공편 조회
    public Page<FlightResponse> getFlightsByEarliestDepartureTime(PageRequest pageRequest) {
        try {
            Page<Flight> flights = flightRepository.findAllByOrderByDepartureTimeAsc(pageRequest);
            return flights.map(FlightResponse::new);
        } catch (Exception ex) {
            throw new RuntimeException("Failed to get flights by earliest departure time: " + ex.getMessage());
        }
    }

    // 출발 시간 늦은 순으로 항공편 조회
    public Page<FlightResponse> getFlightsByLatestDepartureTime(PageRequest pageRequest) {
        try {
            Page<Flight> flights = flightRepository.findAllByOrderByDepartureTimeDesc(pageRequest);
            return flights.map(FlightResponse::new);
        } catch (Exception ex) {
            throw new RuntimeException("Failed to get flights by latest departure time: " + ex.getMessage());
        }
    }

    // 날짜 별로 항공편 조회
    public Page<FlightResponse> getFlightsByDate(LocalDate date, PageRequest pageRequest) {
        try {
            Page<Flight> flights = flightRepository.findByDepartureDate(date, pageRequest);
            return flights.map(FlightResponse::new);
        } catch (Exception ex) {
            throw new RuntimeException("Failed to get flights by date: " + ex.getMessage());
        }
    }

    // 모든 항공편 조회
    public Page<FlightResponse> getAllFlights(PageRequest pageRequest) {
        try {
            Page<Flight> allFlights = flightRepository.findAll(pageRequest);
            return allFlights.map(FlightResponse::new);
        } catch (Exception ex) {
            throw new RuntimeException("Failed to get all flights: " + ex.getMessage());
        }
    }

    // 항공편 출발 및 도착 시간 업데이트
    public FlightResponse updateFlightTime(Long flightId, UpdateFlightRequest updateFlightRequest) {
        try {
            Flight flight = flightRepository.findById(flightId)
                    .orElseThrow(() -> new EntityNotFoundException("Flight not found with id: " + flightId));

            if (updateFlightRequest.getDepartureTime() != null) {
                flight.setDepartureTime(updateFlightRequest.getDepartureTime());
            }
            if (updateFlightRequest.getArrivalTime() != null) {
                flight.setArrivalTime(updateFlightRequest.getArrivalTime());
            }

            Flight updatedFlight = flightRepository.save(flight);
            return new FlightResponse(updatedFlight);
        } catch (Exception ex) {
            throw new RuntimeException("Failed to update flight time: " + ex.getMessage());
        }
    }

    // 항공편 삭제
    public void deleteFlight(Long flightId) {
        try {
            if (!flightRepository.existsById(flightId)) {
                throw new EntityNotFoundException("Flight not found with id: " + flightId);
            }
            flightRepository.deleteById(flightId);
        } catch (Exception ex) {
            throw new RuntimeException("Failed to delete flight: " + ex.getMessage());
        }
    }
}
