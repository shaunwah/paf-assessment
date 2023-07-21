package vttp2023.batch3.assessment.paf.bookings.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {
    private String resvId;
    private String name;
    private String email;
    private String accId;
    private Date arrivalDate;
    private Integer duration;
}
