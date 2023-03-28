package com.example.hotelantique.components;

import com.example.hotelantique.service.ReservationService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ReservationScheduler {

    private final ReservationService reservationService;

    public ReservationScheduler(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @Scheduled(cron = "0 0 1 * * ?")
    public void deactivatePastReservations(){
        this.reservationService.deactivatePastReservations();
    }


}
