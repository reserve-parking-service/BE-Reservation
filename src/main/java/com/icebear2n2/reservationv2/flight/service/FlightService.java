package com.icebear2n2.reservationv2.flight.service;

import com.icebear2n2.reservationv2.domain.entity.Flight;
import com.icebear2n2.reservationv2.domain.entity.FlightReservation;
import com.icebear2n2.reservationv2.domain.entity.Seat;
import com.icebear2n2.reservationv2.domain.repository.FlightRepository;
import com.icebear2n2.reservationv2.domain.repository.FlightReservationRepository;
import com.icebear2n2.reservationv2.domain.request.FlightRequest;
import com.icebear2n2.reservationv2.domain.request.UpdateFlightRequest;
import com.icebear2n2.reservationv2.domain.response.FlightResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FlightService {
    private final FlightRepository flightRepository;
    private static final Logger logger = LoggerFactory.getLogger(FlightService.class);

    @Transactional
    public FlightResponse createFlight(FlightRequest flightRequest) {
            Flight flight = flightRequest.toEntity();
            generateSeats(flight, flightRequest.toEntity().getSeatCapacity(), flightRequest.getBusinessClassPrice(), flightRequest.getEconomyClassPrice() ); // 좌석 생성
            Flight savedFlight = flightRepository.save(flight);
            return new FlightResponse(savedFlight);
    }

    @Transactional
    public FlightResponse updateFlightTime(Long flightId, UpdateFlightRequest updateFlightRequest) {
        Flight flight = getFlight(flightId);
        updateFlightRequest.departureTime().ifPresent(flight::setDepartureTime);
        updateFlightRequest.arrivalTime().ifPresent(flight::setArrivalTime);
        return new FlightResponse(flight);
    }

    @Transactional
    public void deleteFlight(Long flightId) {
        Flight flight = getFlight(flightId);
        flight.setDeletedAt(new Timestamp(System.currentTimeMillis()));

    }

    public FlightResponse getFlightById(Long flightId) {
        return new FlightResponse(getFlight(flightId));
    }

    public Page<FlightResponse> getFlightsByEarliestDepartureTime(Pageable pageable) {
            Page<Flight> flights = flightRepository.findAllByOrderByDepartureTimeAsc(pageable);
            return flights.map(FlightResponse::new);
    }

    public Page<FlightResponse> getFlightsByLatestDepartureTime(Pageable pageable) {
            Page<Flight> flights = flightRepository.findAllByOrderByDepartureTimeDesc(pageable);
            return flights.map(FlightResponse::new);

    }

    public Page<FlightResponse> getFlightsByDate(LocalDate date, Pageable pageable) {
            Page<Flight> flights = flightRepository.findByDepartureDate(date, pageable);
            return flights.map(FlightResponse::new);
    }

    public Page<FlightResponse> getAllFlights(Pageable pageable) {
            Page<Flight> allFlights = flightRepository.findAll(pageable);
            return allFlights.map(FlightResponse::new);
    }

    public Page<FlightResponse> getAllByDeletedFlight(Pageable pageable) {
            Page<Flight> allFlights = flightRepository.findAllByDeletedAtNotNull(pageable);
            return allFlights.map(FlightResponse::new);

    }

    public Page<FlightResponse> getAllByNotDeletedFlight(Pageable pageable) {
            Page<Flight> allFlights = flightRepository.findAllByDeletedAtNull(pageable);
            return allFlights.map(FlightResponse::new);
    }

    @Scheduled(cron = "0 * * * * ?")
    @Transactional
    public void deletePastFlights() {
        LocalDateTime now = LocalDateTime.now();
        List<Flight> flights = flightRepository.findAll();
        int deletedCount = 0;

        for (Flight flight : flights) {
            if (isPastFlight(flight, now)) {
                flight.setDeletedAt(new Timestamp(System.currentTimeMillis()));
                deletedCount++;
            }
        }

        logger.info("Deleting past flights: {} flights found", deletedCount);
        flightRepository.saveAll(flights);
        logger.info("Deleted {} past flights.", deletedCount);
    }


    private boolean isPastFlight(Flight flight, LocalDateTime now) {
        LocalDateTime departureDateTime = LocalDateTime.of(flight.getDepartureDate(), flight.getDepartureTime());
        return departureDateTime.isBefore(now);
    }


    private void generateSeats(Flight flight, int totalSeats, int businessClassPrice, int economyClassPrice) {
        List<Seat> seats = new ArrayList<>();


        int maxBusinessClassSeats = Math.min(totalSeats / 2, 4);
        for (int i = 1; i <= maxBusinessClassSeats; i++) {
            seats.add(Seat.generateBusinessSeats(flight, i, businessClassPrice));
        }


        int economyClassSeats = totalSeats - maxBusinessClassSeats;
        for (int i = 1; i <= economyClassSeats; i++) {
            seats.add(Seat.generateEconomyClassSeats(flight, i, economyClassPrice));
        }

        flight.setSeats(seats);
    }

    private Flight getFlight(Long flightId) {
        return flightRepository.findById(flightId)
                .orElseThrow(() -> new EntityNotFoundException("Flight not found with id: %s".formatted(flightId)));
    }

}
