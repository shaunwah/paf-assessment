package vttp2023.batch3.assessment.paf.bookings.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Listing {
    private String id;
    private String name;
    private String description;
    private Integer accommodates;
    private String addressStreet;
    private String addressSuburb;
    private String addressCountry;
    private String image;
    private Double price;
    private List<String> amenities;
}
