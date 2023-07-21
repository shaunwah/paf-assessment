package vttp2023.batch3.assessment.paf.bookings.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import vttp2023.batch3.assessment.paf.bookings.models.Reservation;

@Repository
public class ReservationsRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Integer insertReservation(Reservation reservation) {
        return jdbcTemplate.update(
                "INSERT INTO reservations VALUES (?, ?, ?, ?, ?, ?)",
                reservation.getResvId(),
                reservation.getName(),
                reservation.getEmail(),
                reservation.getAccId(),
                reservation.getArrivalDate(),
                reservation.getDuration()
        );
    }

}
