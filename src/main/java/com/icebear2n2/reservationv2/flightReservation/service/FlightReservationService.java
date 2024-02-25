package com.icebear2n2.reservationv2.flightReservation.service;

import com.icebear2n2.reservationv2.domain.entity.*;
import com.icebear2n2.reservationv2.domain.repository.FlightRepository;
import com.icebear2n2.reservationv2.domain.repository.FlightReservationRepository;
import com.icebear2n2.reservationv2.domain.repository.SeatRepository;
import com.icebear2n2.reservationv2.domain.repository.UserRepository;
import com.icebear2n2.reservationv2.domain.request.FlightReservationRequest;
import com.icebear2n2.reservationv2.domain.request.UpdatePaymentStatusRequest;
import com.icebear2n2.reservationv2.domain.response.FlightReservationResponse;
import com.icebear2n2.reservationv2.exception.SeatReservedException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FlightReservationService {
    private final FlightReservationRepository flightReservationRepository;
    private final UserRepository userRepository;
    private final FlightRepository flightRepository;
    private final SeatRepository seatRepository;


    @Transactional
    public FlightReservationResponse createReservation(FlightReservationRequest reservationRequest) {
            User user = getUser(reservationRequest.getUserId());
            Flight flight = getFlight(reservationRequest.getFlightId());
            Seat seat = seatRepository.findBySeatNumber(reservationRequest.getSeatNumber());

            if (seat.isReserved()) {
                throw new SeatReservedException("Seat %s is already reserved.".formatted(seat.getSeatNumber()));
            }
            seat.reserved();
            return new FlightReservationResponse(createFlightReservation(reservationRequest, user, flight, seat));
    }

    public FlightReservationResponse getReservationById(Long reservationId) {
            FlightReservation reservation = getReservation(reservationId);
            return new FlightReservationResponse(reservation);
    }

    public Page<FlightReservationResponse> getAllReservations(Pageable pageable) {
            Page<FlightReservation> reservations = flightReservationRepository.findAll(pageable);
            return reservations.map(FlightReservationResponse::new);
    }

    @Transactional
    public FlightReservationResponse updatePaymentStatus(Long reservationId, UpdatePaymentStatusRequest updatePaymentStatusRequest) {
            FlightReservation reservation = getReservation(reservationId);

            updatePaymentStatusRequest.paymentStatus().ifPresent(reservation::setPaymentStatus);

            if (updatePaymentStatusRequest.isCanceled()) {
                reservation.cancel();
            }

            return new FlightReservationResponse(reservation);
    }

    @Transactional
    public void deleteReservation(Long reservationId) {
        FlightReservation flightReservation = getReservation(reservationId);
        Seat seat = seatRepository.findBySeatNumber(flightReservation.getSeat().get().getSeatNumber());
        seat.setReserved(false);
    }

    private FlightReservation getReservation(Long reservationId) {
        return flightReservationRepository.findById(reservationId).orElseThrow(() -> new EntityNotFoundException("Reservation not found with id: " + reservationId));
    }



    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
    }

    private Flight getFlight(Long flightId) {
        return flightRepository.findById(flightId)
                .orElseThrow(() -> new EntityNotFoundException("Flight not found with id: " + flightId));
    }

    private FlightReservation createFlightReservation(FlightReservationRequest reservationRequest, User user, Flight flight, Seat seat) {
        return flightReservationRepository.save(FlightReservation.create(user, flight, seat, reservationRequest.getPaymentStatus(), reservationRequest.getPassengerType()));
    }

}