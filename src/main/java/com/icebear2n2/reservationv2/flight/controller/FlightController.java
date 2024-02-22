package com.icebear2n2.reservationv2.flight.controller;

import com.icebear2n2.reservationv2.domain.request.FlightRequest;
import com.icebear2n2.reservationv2.domain.request.UpdateFlightRequest;
import com.icebear2n2.reservationv2.domain.response.FlightResponse;
import com.icebear2n2.reservationv2.flight.service.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    public ResponseEntity<FlightResponse> createFlight(@RequestBody FlightRequest flightRequest) {
        FlightResponse createdFlight = flightService.createFlight(flightRequest);
        return new ResponseEntity<>(createdFlight, HttpStatus.CREATED);
    }

    @GetMapping("/{flightId}")
    public ResponseEntity<FlightResponse> getFlightById(@PathVariable Long flightId) {
        FlightResponse flight = flightService.getFlightById(flightId);
        return ResponseEntity.ok(flight);
    }

    @GetMapping("/earliest-departure")
    public ResponseEntity<Page<FlightResponse>> getFlightsByEarliestDepartureTime(@RequestParam(defaultValue = "0") int page,
                                                                                  @RequestParam(defaultValue = "10") int size) {
        Page<FlightResponse> flights = flightService.getFlightsByEarliestDepartureTime(PageRequest.of(page, size));
        return ResponseEntity.ok(flights);
    }

    @GetMapping("/latest-departure")
    public ResponseEntity<Page<FlightResponse>> getFlightsByLatestDepartureTime(@RequestParam(defaultValue = "0") int page,
                                                                                @RequestParam(defaultValue = "10") int size) {
        Page<FlightResponse> flights = flightService.getFlightsByLatestDepartureTime(PageRequest.of(page, size));
        return ResponseEntity.ok(flights);
    }

    @GetMapping("/by-date")
    public ResponseEntity<Page<FlightResponse>> getFlightsByDate(@RequestParam LocalDate date,
                                                                 @RequestParam(defaultValue = "0") int page,
                                                                 @RequestParam(defaultValue = "10") int size) {
        Page<FlightResponse> flights = flightService.getFlightsByDate(date, PageRequest.of(page, size));
        return ResponseEntity.ok(flights);
    }

    @GetMapping("/all")
    public ResponseEntity<Page<FlightResponse>> getAllFlights(@RequestParam(defaultValue = "0") int page,
                                                              @RequestParam(defaultValue = "10") int size) {
        Page<FlightResponse> flights = flightService.getAllFlights(PageRequest.of(page, size));
        return ResponseEntity.ok(flights);
    }

    @PutMapping("/{flightId}/update-time")
    public ResponseEntity<FlightResponse> updateFlightTime(@PathVariable Long flightId,
                                                           @RequestBody UpdateFlightRequest updateFlightRequest) {
        FlightResponse updatedFlight = flightService.updateFlightTime(flightId, updateFlightRequest);
        return ResponseEntity.ok(updatedFlight);
    }

    @DeleteMapping("/{flightId}")
    public ResponseEntity<String> deleteFlight(@PathVariable Long flightId) {
        flightService.deleteFlight(flightId);
        return ResponseEntity.ok("Flight with ID " + flightId + " deleted successfully.");
    }
}
