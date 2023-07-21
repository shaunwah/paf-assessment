package vttp2023.batch3.assessment.paf.bookings.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vttp2023.batch3.assessment.paf.bookings.models.Occupancy;
import vttp2023.batch3.assessment.paf.bookings.models.Reservation;
import vttp2023.batch3.assessment.paf.bookings.repositories.OccupanciesRepository;
import vttp2023.batch3.assessment.paf.bookings.repositories.ReservationsRepository;
import vttp2023.batch3.assessment.paf.bookings.utils.Utilities;

import java.sql.SQLException;
import java.util.Optional;

@Service
public class ReservationsService {
    @Autowired
    private ReservationsRepository reservationsRepo;
    @Autowired
    private OccupanciesRepository occupanciesRepo;

    @Transactional
    public Reservation createReservation(Reservation reservation, String accId) throws SQLException {
        Optional<Occupancy> occupancy = getOccupancyById(accId);
        if (occupancy.isEmpty()) {
            throw new SQLException("The occupancy information for this accommodation is not found.");
        }

        if (reservation.getDuration() > occupancy.get().getVacancy()) {
            throw new SQLException("This accommodation is only available for booking for up to %d day(s).".formatted(occupancy.get().getVacancy()));
        }

        reservation.setResvId(Utilities.generateId(8));
        if (!insertReservation(reservation)) {
            throw new SQLException("An issue occurred while creating a new booking.");
        }

        if (!decrementOccupancyVacancyById(accId, reservation.getDuration())) {
            throw new SQLException("An issue occurred while adjusting the available vacancy days.");
        }
        return reservation;
    }

    public Boolean insertReservation(Reservation reservation) {
        reservation.setResvId(Utilities.generateId(8));
        return reservationsRepo.insertReservation(reservation) > 0;
    }

    public Optional<Occupancy> getOccupancyById(String id) {
        Occupancy occupancy = occupanciesRepo.getOccupancyById(id);
        if (occupancy == null) {
            return Optional.empty();
        }
        return Optional.of(occupancy);
    }

    public Boolean decrementOccupancyVacancyById(String id, Integer amount) {
        return occupanciesRepo.decrementOccupancyVacancyById(id, amount) > 0;
    }
}
