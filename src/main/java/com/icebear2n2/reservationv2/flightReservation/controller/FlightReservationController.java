package com.icebear2n2.reservationv2.flightReservation.controller;

import com.icebear2n2.reservationv2.domain.request.FlightReservationRequest;
import com.icebear2n2.reservationv2.domain.request.UpdatePaymentStatusRequest;
import com.icebear2n2.reservationv2.domain.response.FlightReservationResponse;
import com.icebear2n2.reservationv2.flightReservation.service.FlightReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/reservations")
@RequiredArgsConstructor
@Tag(name = "Flight Reservation", description = "항공 예약 API")
public class FlightReservationController {
    private final FlightReservationService flightReservationService;

    @PostMapping("/create")
    @Operation(summary = "예약 생성", description = "새로운 항공 예약을 생성합니다.")
    public ResponseEntity<FlightReservationResponse> createReservation(
            @Parameter(description = "예약 정보", required = true) @RequestBody FlightReservationRequest reservationRequest
    ) {
        FlightReservationResponse response = flightReservationService.createReservation(reservationRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{reservationId}")
    @Operation(summary = "예약 조회", description = "예약 고유 식별자에 따라 예약을 조회합니다.")
    public ResponseEntity<FlightReservationResponse> getReservationById(
            @Parameter(description = "예약 고유 식별자", required = true) @PathVariable Long reservationId
    ) {
        FlightReservationResponse response = flightReservationService.getReservationById(reservationId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    @Operation(summary = "모든 예약 조회", description = "모든 항공 예약을 조회합니다.")
    public ResponseEntity<Page<FlightReservationResponse>> getAllReservations(
            @Parameter(description = "페이지 번호") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "페이지 크기") @RequestParam(defaultValue = "10") int size
    ) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<FlightReservationResponse> response = flightReservationService.getAllReservations(pageRequest);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{reservationId}/payment-status")
    @Operation(summary = "결제 상태 업데이트", description = "항공 예약의 결제 상태를 업데이트합니다.")
    public ResponseEntity<FlightReservationResponse> updatePaymentStatus(
            @Parameter(description = "예약 고유 식별자", required = true) @PathVariable Long reservationId,
            @Parameter(description = "결제 상태 정보", required = true) @RequestBody UpdatePaymentStatusRequest updatePaymentStatusRequest
    ) {
        FlightReservationResponse response = flightReservationService.updatePaymentStatus(reservationId, updatePaymentStatusRequest);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{reservationId}")
    @Operation(summary = "예약 삭제", description = "예약을 삭제합니다.")
    public ResponseEntity<String> deleteReservation(
            @Parameter(description = "예약 고유 식별자", required = true) @PathVariable Long reservationId
    ) {
        flightReservationService.deleteReservation(reservationId);
        return ResponseEntity.ok("Reservation with ID " + reservationId + " deleted successfully.");
    }
}
