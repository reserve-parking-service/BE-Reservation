package com.icebear2n2.reservationv2.flight.controller;

import com.icebear2n2.reservationv2.domain.request.FlightRequest;
import com.icebear2n2.reservationv2.domain.request.UpdateFlightRequest;
import com.icebear2n2.reservationv2.domain.response.FlightResponse;
import com.icebear2n2.reservationv2.flight.service.FlightService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Flight", description = "항공편 API")
public class FlightController {
    private final FlightService flightService;

    @PostMapping("/create")
    @Operation(summary = "항공편 생성", description = "새로운 항공편 생성")
    public ResponseEntity<FlightResponse> createFlight(@RequestBody FlightRequest flightRequest) {
        FlightResponse createdFlight = flightService.createFlight(flightRequest);
        return new ResponseEntity<>(createdFlight, HttpStatus.CREATED);
    }

    @GetMapping("/{flightId}")
    @Operation(summary = "항공편 고유 식별자 별 조회", description = "항공편 고유 식별자 별 조회")
    public ResponseEntity<FlightResponse> getFlightById(@PathVariable Long flightId) {
        FlightResponse flight = flightService.getFlightById(flightId);
        return ResponseEntity.ok(flight);
    }

    @GetMapping("/earliest-departure")
    @Operation(summary = "항공편 출발 시간 빠른 순으로 조회 (삭제된 항공편 제외)", description = "항공편 출발 시간 빠른 순으로 조회 (삭제된 항공편 제외)")
    public ResponseEntity<Page<FlightResponse>> getFlightsByEarliestDepartureTime(@RequestParam(defaultValue = "0") int page,
                                                                                  @RequestParam(defaultValue = "10") int size) {
        Page<FlightResponse> flights = flightService.getFlightsByEarliestDepartureTime(PageRequest.of(page, size));
        return ResponseEntity.ok(flights);
    }

    @GetMapping("/latest-departure")
    @Operation(summary = "항공편 출발 시간 느린 순으로 조회 (삭제된 항공편 제외)", description = "항공편 출발 시간 느린 순으로 조회 (삭제된 항공편 제외)")
    public ResponseEntity<Page<FlightResponse>> getFlightsByLatestDepartureTime(@RequestParam(defaultValue = "0") int page,
                                                                                @RequestParam(defaultValue = "10") int size) {
        Page<FlightResponse> flights = flightService.getFlightsByLatestDepartureTime(PageRequest.of(page, size));
        return ResponseEntity.ok(flights);
    }

    @GetMapping("/by-date")
    @Operation(summary = "항공편 날짜 순으로 조회 (삭제된 항공편 제외)", description = "항공편 날짜 순으로 조회 (삭제된 항공편 제외)")
    public ResponseEntity<Page<FlightResponse>> getFlightsByDate(@RequestParam LocalDate date,
                                                                 @RequestParam(defaultValue = "0") int page,
                                                                 @RequestParam(defaultValue = "10") int size) {
        Page<FlightResponse> flights = flightService.getFlightsByDate(date, PageRequest.of(page, size));
        return ResponseEntity.ok(flights);
    }

    @GetMapping("/all/deleted")
    @Operation(summary = "삭제된 항공편 조회", description = "삭제된 항공편 조회")
    public ResponseEntity<Page<FlightResponse>> getAllFlightsByDeleted(@RequestParam(defaultValue = "0") int page,
                                                              @RequestParam(defaultValue = "10") int size) {
        Page<FlightResponse> flights = flightService.getAllByDeletedFlight(PageRequest.of(page, size));
        return ResponseEntity.ok(flights);
    }

    @GetMapping("/flights/not-deleted")
    @Operation(summary = "삭제되지 않은 항공편 조회", description = "삭제되지 않은 항공편 조회")
    public ResponseEntity<Page<FlightResponse>> getAllFlightsByNotDeleted(@RequestParam(defaultValue = "0") int page,
                                                                       @RequestParam(defaultValue = "10") int size) {
        Page<FlightResponse> flights = flightService.getAllByNotDeletedFlight(PageRequest.of(page, size));
        return ResponseEntity.ok(flights);
    }

    @GetMapping("/all")
    @Operation(summary = "전체 항공편 조회", description = "전체 항공편 조회")
    public ResponseEntity<Page<FlightResponse>> getAllFlights(@RequestParam(defaultValue = "0") int page,
                                                              @RequestParam(defaultValue = "10") int size) {
        Page<FlightResponse> flights = flightService.getAllFlights(PageRequest.of(page, size));
        return ResponseEntity.ok(flights);
    }

    @PutMapping("/{flightId}/update-time")
    @Operation(summary = "항공편 출발 시간 또는 도착 시간 업데이트", description = "항공편 출발 시간 또는 도착 시간 업데이트")
    public ResponseEntity<FlightResponse> updateFlightTime(@PathVariable Long flightId,
                                                           @RequestBody UpdateFlightRequest updateFlightRequest) {
        FlightResponse updatedFlight = flightService.updateFlightTime(flightId, updateFlightRequest);
        return ResponseEntity.ok(updatedFlight);
    }

    @PutMapping("/{flightId}")
    @Operation(summary = "항공편 삭제 시간 설정", description = "항공편 삭제 시간 설정")
    public ResponseEntity<String> deleteFlight(@PathVariable Long flightId) {
        flightService.deleteFlight(flightId);
        return ResponseEntity.ok("Flight with ID " + flightId + " deleted successfully.");
    }
}
