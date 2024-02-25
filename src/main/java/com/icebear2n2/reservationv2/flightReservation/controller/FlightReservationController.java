package com.icebear2n2.reservationv2.flightReservation.controller;

import com.icebear2n2.reservationv2.domain.request.FlightReservationRequest;
import com.icebear2n2.reservationv2.domain.request.UpdatePaymentStatusRequest;
import com.icebear2n2.reservationv2.domain.response.FlightReservationResponse;
import com.icebear2n2.reservationv2.flightReservation.service.FlightReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/reservations")
@RequiredArgsConstructor
public class FlightReservationController {
    private final FlightReservationService flightReservationService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public FlightReservationResponse createReservation(@RequestBody FlightReservationRequest reservationRequest) {
        return flightReservationService.createReservation(reservationRequest);
    }

    @GetMapping("/{reservationId}")
    public FlightReservationResponse getReservationById(@PathVariable Long reservationId) {
        return flightReservationService.getReservationById(reservationId);
    }

    @GetMapping("/all")
    public Page<FlightReservationResponse> getAllReservations(Pageable pageable) {
        return flightReservationService.getAllReservations(pageable);
    }

    @PutMapping("/{reservationId}/payment-status")
    public FlightReservationResponse updatePaymentStatus(@PathVariable Long reservationId, @RequestBody UpdatePaymentStatusRequest updatePaymentStatusRequest) {
        return flightReservationService.updatePaymentStatus(reservationId, updatePaymentStatusRequest);
    }

    @DeleteMapping("/{reservationId}")
    public String  deleteReservation(@PathVariable Long reservationId) {
        flightReservationService.deleteReservation(reservationId);
        return "Reservation with ID %s deleted successfully.".formatted(reservationId);
    }
}
