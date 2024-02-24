package com.icebear2n2.reservationv2.domain.response;

import com.icebear2n2.reservationv2.domain.entity.Seat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SeatResponse {

    @Schema(description = "좌석 ID")
    private Long id;

    @Schema(description = "좌석 번호")
    private String seatNumber;

    @Schema(description = "좌석 등급")
    private String seatClass;

    @Schema(description = "가격")
    private int price;

    @Schema(description = "항공편 ID")
    private Long flightId;

    @Schema(description = "예약 여부")
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
