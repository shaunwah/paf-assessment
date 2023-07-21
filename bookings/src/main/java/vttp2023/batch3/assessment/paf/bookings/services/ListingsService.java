package vttp2023.batch3.assessment.paf.bookings.services;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vttp2023.batch3.assessment.paf.bookings.models.Listing;
import vttp2023.batch3.assessment.paf.bookings.repositories.ListingsRepository;
import vttp2023.batch3.assessment.paf.bookings.utils.Utilities;

import java.util.List;
import java.util.Optional;

@Service
public class ListingsService {
    @Autowired
    private ListingsRepository repo;

    public List<String> getCountries() {
        return repo.getCountries();
    }
	
	//TODO: Task 2
    public List<Listing> getListingsByCountry(String country, Integer accommodates, Double priceMin, Double priceMax) {
        return repo.getListingsByCountry(country, accommodates, priceMin, priceMax)
                .stream()
                .map(Utilities::toListingObj)
                .toList();
    }
	
	//TODO: Task 3
    public Optional<Listing> getListingById(String id) {
        Document doc = repo.getListingById(id);
        if (doc == null) {
            return Optional.empty();
        }
        return Optional.of(Utilities.toListingObj(doc));
    }

	//TODO: Task 4
	

	//TODO: Task 5


}
