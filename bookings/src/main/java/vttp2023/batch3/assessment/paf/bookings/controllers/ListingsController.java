package vttp2023.batch3.assessment.paf.bookings.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import vttp2023.batch3.assessment.paf.bookings.models.Listing;
import vttp2023.batch3.assessment.paf.bookings.models.Reservation;
import vttp2023.batch3.assessment.paf.bookings.services.ListingsService;
import vttp2023.batch3.assessment.paf.bookings.services.ReservationsService;
import vttp2023.batch3.assessment.paf.bookings.utils.Utilities;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping
public class ListingsController {
    @Autowired
    private ListingsService listingsSvc;
    @Autowired
    private ReservationsService reservationsSvc;

    @GetMapping
    public ModelAndView getLanding() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("view1");
        mav.setStatus(HttpStatus.OK);
        mav.addObject("countries", listingsSvc.getCountries());
        return mav;
    }

	//TODO: Task 2

    @GetMapping("/search")
    public ModelAndView getSearchResults(@RequestParam String country,
                                         @RequestParam Integer pax,
                                         @RequestParam Double priceMin,
                                         @RequestParam Double priceMax) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("country", country);
        List<String> errorMessages = Utilities.validateSearchInputs(country, pax, priceMin, priceMax);
        if (errorMessages.size() > 0) {
            mav.setViewName("view1");
            mav.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            mav.addObject("countries", listingsSvc.getCountries());
            mav.addObject("errorMessages", errorMessages);
            return mav;
        }
        mav.setViewName("view2");
        mav.setStatus(HttpStatus.OK);
        mav.addObject("listings", listingsSvc.getListingsByCountry(country, pax, priceMin, priceMax));
        return mav;
    }
	
	//TODO: Task 3
    @GetMapping("/listing/{id}")
    public ModelAndView getListingById(@PathVariable String id) {
        Optional<Listing> listing = listingsSvc.getListingById(id);
        ModelAndView mav = new ModelAndView();
        if (listing.isEmpty()) {
            mav.setViewName("view3");
            mav.setStatus(HttpStatus.NOT_FOUND);
        }
        mav.setViewName("view3");
        mav.setStatus(HttpStatus.OK);
        mav.addObject("listing", listing.get());
        mav.addObject("reservation", new Reservation());
        return mav;
    }

	//TODO: Task 4
    @PostMapping("/listing/{id}/booking")
    public ModelAndView createBooking(@PathVariable String id, @ModelAttribute Reservation reservation) {
        ModelAndView mav = new ModelAndView();
        reservation.setAccId(id);
        try {
            mav.setViewName("view4");
            mav.setStatus(HttpStatus.CREATED);
            mav.addObject("reservation", reservationsSvc.createReservation(reservation, id));
            return mav;
        } catch (SQLException e) {
            Optional<Listing> listing = listingsSvc.getListingById(id);
            mav.setViewName("view3");
            mav.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            mav.addObject("listing", listing.get());
            mav.addObject("errorMessage", e.getMessage());
            return mav;
        }
    }
	

	//TODO: Task 5


}
