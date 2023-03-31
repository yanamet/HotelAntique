package com.example.hotelantique.shedule;

import com.example.hotelantique.service.ReservationService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ReservationsCheckSchedule {

    private final ReservationService reservationService;

    public ReservationsCheckSchedule(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @Scheduled(cron = "0 0 1 * * *")
    public void checkForReservationPast(){
        this.reservationService.deactivatePastReservations();
    }

}
