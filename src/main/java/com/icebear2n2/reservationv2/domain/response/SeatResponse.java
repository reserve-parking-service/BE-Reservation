package com.icebear2n2.reservationv2.domain.response;

import com.icebear2n2.reservationv2.domain.entity.Seat;
import com.icebear2n2.reservationv2.domain.entity.SeatClass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SeatResponse {
    private Long id;
    private String seatNumber;
    private SeatClass seatClass;
    private int price;
    private Long flightId;
    private boolean reserved;

    public SeatResponse(Seat seat) {
        this.id = seat.getId();
        this.seatNumber = seat.getSeatNumber();
        this.seatClass = seat.getSeatClass();
        this.price = seat.getPrice();
        this.flightId = seat.getFlight().getId();
        this.reserved = seat.isReserved();
    }
}
