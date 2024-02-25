package com.icebear2n2.reservationv2.flight.controller;

import com.icebear2n2.reservationv2.domain.request.FlightRequest;
import com.icebear2n2.reservationv2.domain.request.UpdateFlightRequest;
import com.icebear2n2.reservationv2.domain.response.FlightResponse;
import com.icebear2n2.reservationv2.flight.service.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/flights")
@RequiredArgsConstructor
public class FlightController {
    private final FlightService flightService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public FlightResponse createFlight(@RequestBody FlightRequest flightRequest) {
        return flightService.createFlight(flightRequest);
    }

    @GetMapping("/{flightId}")
    public FlightResponse getFlightById(@PathVariable Long flightId) {
        return flightService.getFlightById(flightId);
    }

    @GetMapping("/earliest-departure")
    public Page<FlightResponse> getFlightsByEarliestDepartureTime(Pageable pageable) {
        return flightService.getFlightsByEarliestDepartureTime(pageable);

    }

    @GetMapping("/latest-departure")
    public Page<FlightResponse> getFlightsByLatestDepartureTime(Pageable pageable) {
        return flightService.getFlightsByLatestDepartureTime(pageable);

    }

    @GetMapping("/by-date")
    public Page<FlightResponse> getFlightsByDate(@RequestParam LocalDate date, Pageable pageable) {
        return flightService.getFlightsByDate(date,pageable);

    }

    @GetMapping("/all/deleted")
    public Page<FlightResponse> getAllFlightsByDeleted(Pageable pageable) {
        return flightService.getAllByDeletedFlight(pageable);

    }

    @GetMapping("/flights/not-deleted")
    public Page<FlightResponse> getAllFlightsByNotDeleted(Pageable pageable) {
        return flightService.getAllByNotDeletedFlight(pageable);

    }

    @GetMapping("/all")
    public Page<FlightResponse> getAllFlights(Pageable pageable) {
        return flightService.getAllFlights(pageable);
    }

    @PutMapping("/{flightId}/update-time")
    public FlightResponse updateFlightTime(@PathVariable Long flightId, @RequestBody UpdateFlightRequest updateFlightRequest) {
        return flightService.updateFlightTime(flightId, updateFlightRequest);

    }

    @PutMapping("/{flightId}")
    public String deleteFlight(@PathVariable Long flightId) {
        return "Flight with ID %s deleted successfully.".formatted(flightId);
    }
}
