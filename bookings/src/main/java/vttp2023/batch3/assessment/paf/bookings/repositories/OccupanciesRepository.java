package vttp2023.batch3.assessment.paf.bookings.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import vttp2023.batch3.assessment.paf.bookings.models.Occupancy;

@Repository
public class OccupanciesRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Occupancy getOccupancyById(String id) {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM acc_occupancy WHERE acc_id = ?",
                BeanPropertyRowMapper.newInstance(Occupancy.class),
                id
        );
    }

    public Integer decrementOccupancyVacancyById(String id, Integer amount) {
        return jdbcTemplate.update(
                "UPDATE acc_occupancy SET vacancy = vacancy - ? WHERE acc_id = ?",
                amount,
                id
        );
    }
}
