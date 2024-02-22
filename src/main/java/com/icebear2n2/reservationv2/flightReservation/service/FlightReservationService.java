package com.icebear2n2.reservationv2.flightReservation.service;

import com.icebear2n2.reservationv2.domain.entity.Flight;
import com.icebear2n2.reservationv2.domain.entity.FlightReservation;
import com.icebear2n2.reservationv2.domain.entity.Seat;
import com.icebear2n2.reservationv2.domain.entity.User;
import com.icebear2n2.reservationv2.domain.repository.FlightRepository;
import com.icebear2n2.reservationv2.domain.repository.FlightReservationRepository;
import com.icebear2n2.reservationv2.domain.repository.SeatRepository;
import com.icebear2n2.reservationv2.domain.repository.UserRepository;
import com.icebear2n2.reservationv2.domain.request.FlightReservationRequest;
import com.icebear2n2.reservationv2.domain.response.FlightReservationResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FlightReservationService {
    private final FlightReservationRepository flightReservationRepository;
    private final UserRepository userRepository;
    private final FlightRepository flightRepository;
    private final SeatRepository seatRepository;


    public FlightReservationResponse createReservation(FlightReservationRequest reservationRequest) {
        try {
            User user = userRepository.findById(reservationRequest.getUserId())
                    .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + reservationRequest.getUserId()));
            Flight flight = flightRepository.findById(reservationRequest.getFlightId())
                    .orElseThrow(() -> new EntityNotFoundException("Flight not found with id: " + reservationRequest.getFlightId()));
            Seat seat = seatRepository.findBySeatNumber(reservationRequest.getSeatNumber());

            if (seat.isReserved()) {
                throw new RuntimeException("Seat " + seat.getSeatNumber() + " is already reserved.");
            }

            seat.setReserved(true);
            seatRepository.save(seat);


            double discountRate = 0.0;
            double discountedPrice = seat.getPrice();

            switch (reservationRequest.getPassengerType()) {
                case CHILD:
                    discountRate = seat.getChildDiscountRate() / 100.0;
                    discountedPrice = seat.getPrice() * (1 - discountRate);
                    break;
                case INFANT:
                    discountRate = seat.getInfantDiscountRate() / 100.0;
                    discountedPrice = seat.getPrice() * (1 - discountRate);
                    System.out.println("2 discountedPrice = " + discountedPrice);
                    break;
                case ADULT:
                    break;
            }

            FlightReservation reservation = FlightReservation.builder()
                    .user(user)
                    .flight(flight)
                    .seat(seat)
                    .paymentStatus(reservationRequest.getPaymentStatus())
                    .passengerType(reservationRequest.getPassengerType())
                    .discountedPrice((int) Math.round(discountedPrice))
                    .build();

            FlightReservation savedReservation = flightReservationRepository.save(reservation);

            return new FlightReservationResponse(savedReservation);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Failed to create reservation: " + ex.getMessage());
        }
    }


    // 예약 조회
    public FlightReservationResponse getReservationById(Long reservationId) {
        try {
            FlightReservation reservation = flightReservationRepository.findById(reservationId)
                    .orElseThrow(() -> new EntityNotFoundException("Reservation not found with id: " + reservationId));
            return new FlightReservationResponse(reservation);
        } catch (Exception ex) {

            ex.printStackTrace();
            throw new RuntimeException("Failed to get reservation: " + ex.getMessage());
        }
    }

    // 모든 예약 조회
    public Page<FlightReservationResponse> getAllReservations(PageRequest pageRequest) {
        try {
            Page<FlightReservation> reservations = flightReservationRepository.findAll(pageRequest);
            return reservations.map(FlightReservationResponse::new);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Failed to get all reservations: " + ex.getMessage());
        }
    }

    // 업데이트 로직은 일단 제외. 예약 취소 로직만 진행하자

    public void deleteReservation(Long reservationId) {
        try {
            if (!flightReservationRepository.existsById(reservationId)) {
                throw new EntityNotFoundException("Reservation not found with id: " + reservationId);
            }
            flightReservationRepository.deleteById(reservationId);
        } catch (Exception ex) {

            ex.printStackTrace();
            throw new RuntimeException("Failed to delete reservation: " + ex.getMessage());
        }
    }
}