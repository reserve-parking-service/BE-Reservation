package com.icebear2n2.reservationv2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ReservationV2Application {

    public static void main(String[] args) {
        SpringApplication.run(ReservationV2Application.class, args);
    }

}
