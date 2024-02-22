package com.icebear2n2.reservationv2.flight.controller;

import com.icebear2n2.reservationv2.domain.entity.Flight;
import com.icebear2n2.reservationv2.domain.request.FlightRequest;
import com.icebear2n2.reservationv2.domain.request.UpdateFlightRequest;
import com.icebear2n2.reservationv2.domain.response.FlightResponse;
import com.icebear2n2.reservationv2.domain.response.SeatResponse;
import com.icebear2n2.reservationv2.flight.service.FlightService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class FlightControllerTest {

    private MockMvc mockMvc;

    @Mock
    private FlightService flightService;

    @InjectMocks
    private FlightController flightController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(flightController).build();

        // 권한 부여
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
                "username", "password", Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"))));
    }

    @Test
    void testCreateFlight() throws Exception {
        FlightRequest request = new FlightRequest();
        request.setAirplaneName("Test Airplane");
        request.setDeparture("Departure");
        request.setDestination("Destination");
        request.setDepartureDate(LocalDate.of(2024, 3, 1));
        request.setDepartureTime(LocalTime.of(10, 30));
        request.setArrivalDate(LocalDate.of(2024, 3, 1));
        request.setArrivalTime(LocalTime.of(13, 0));
        request.setSeatCapacity(100);
        request.setBusinessClassPrice(200);
        request.setEconomyClassPrice(100);

        FlightResponse response = new FlightResponse();
        response.setId(1L);
        response.setAirplaneName(request.getAirplaneName());
        response.setDeparture(request.getDeparture());
        response.setDestination(request.getDestination());
        response.setDepartureDate(request.getDepartureDate());
        response.setDepartureTime(request.getDepartureTime());
        response.setArrivalDate(request.getArrivalDate());
        response.setArrivalTime(request.getArrivalTime());
        response.setSeatCapacity(request.getSeatCapacity());

        List<SeatResponse> seats = new ArrayList<>();
        for (int i = 1; i <= request.getSeatCapacity(); i++) {
            SeatResponse seat = new SeatResponse();
            seat.setId((long) i);
            seat.setSeatNumber("E" + i);
            seat.setSeatClass("Economy");
            seat.setPrice(request.getEconomyClassPrice());
            seat.setFlightId(response.getId());
            seat.setReserved(false);
            seats.add(seat);
        }
        response.setSeats(seats);

        when(flightService.createFlight(any(FlightRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/v1/flights/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"airplaneName\": \"Test Airplane\", \"departure\": \"Departure\", \"destination\": \"Destination\", " +
                                "\"departureDate\": \"2024-03-01\", \"departureTime\": \"10:30\", \"arrivalDate\": \"2024-03-01\", " +
                                "\"arrivalTime\": \"13:00\", \"seatCapacity\": 100, \"businessClassPrice\": 200, \"economyClassPrice\": 100 }"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }



}
