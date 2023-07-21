package vttp2023.batch3.assessment.paf.bookings.repositories;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import vttp2023.batch3.assessment.paf.bookings.utils.Constants;

import java.util.List;

@Repository
public class ListingsRepository {
    @Autowired
    private MongoTemplate mongoTemplate;

    // db.listings.distinct("address.country");
    public List<String> getCountries() {
        return mongoTemplate.findDistinct(new Query(), Constants.A_ADDRESS_COUNTRY, Constants.C_LISTINGS, String.class);
    }

	//TODO: Task 2


    // db.listings.find(
    // {
    //    "address.country": { $regex: <country>, $options: "i" },
    //    accommodates: <accommodates>,
    //    price: { $gte: <priceMin>, $lte: <priceMax>> }
    // }
    // ).sort(
    //    { "price" : -1 }
    // )
    public List<Document> getListingsByCountry(String country, Integer accommodates, Double priceMin, Double priceMax) {
        return mongoTemplate.find(
                Query.query(
                        Criteria.where(Constants.A_ADDRESS_COUNTRY)
                                .regex(country, "i")
                                .and(Constants.A_ACCOMMODATES)
                                .is(accommodates)
                                .and(Constants.A_PRICE)
                                .gte(priceMin)
                                .lte(priceMax)
                        )
                        .with(Sort.by(Sort.Direction.DESC, Constants.A_PRICE)),
                Document.class,
                Constants.C_LISTINGS
        );
    }
	
	//TODO: Task 3
    // db.listings.findOne(
    //    { _id: <id> }
    // )
    public Document getListingById(String id) {
        return mongoTemplate.findOne(
                Query.query(Criteria.where(Constants.A_ID).is(id)),
                Document.class,
                Constants.C_LISTINGS
        );
    }


	//TODO: Task 4
	// done in ReservationsRepository

	//TODO: Task 5
    // done in ReservationsRepository

}
