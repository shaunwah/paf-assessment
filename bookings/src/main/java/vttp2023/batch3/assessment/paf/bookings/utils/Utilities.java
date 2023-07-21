package vttp2023.batch3.assessment.paf.bookings.utils;

import org.bson.Document;
import vttp2023.batch3.assessment.paf.bookings.models.Listing;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class Utilities {
    public static String generateId(Integer length) {
        return UUID
                .randomUUID()
                .toString()
                .substring(0, length);
    }

    public static List<String> validateSearchInputs(String country, Integer pax, Double priceMin, Double priceMax) {
        List<String> errorMessages = new ArrayList<>();
        if (country == null || country.isEmpty()) {
            errorMessages.add("Country must not be null or empty!");
        }

        if (pax < 1 || pax > 10) {
            errorMessages.add("Number of people has to be between 1 and 10!");
        }

        if (priceMin > priceMax) {
            errorMessages.add("Minimum price has to be lower than maximum price!");
        }

        if (priceMin < 1 || priceMin > 10_000) {
            errorMessages.add("Minimum price has to be between 1 and 10,000!");
        }

        if (priceMax < 1 || priceMax > 10_000) {
            errorMessages.add("Maximum price has to be between 1 and 10,000!");
        }
        return errorMessages;
    }

    public static Listing toListingObj(Document doc) {
        Document address = doc.get(Constants.A_ADDRESS, Document.class);
        return new Listing(
                doc.getString(Constants.A_ID),
                doc.getString(Constants.A_NAME),
                doc.getString(Constants.A_DESCRIPTION),
                doc.getInteger(Constants.A_ACCOMMODATES),
                address.getString(Constants.A_STREET),
                address.getString(Constants.A_ADDRESS_SUBURB),
                address.getString(Constants.A_COUNTRY),
                doc.get(Constants.A_IMAGES, Document.class)
                        .getString(Constants.A_PICTURE_URL)
                        .replace("?aki_policy=large", ""), // quick fix for access denied error
                doc.getDouble(Constants.A_PRICE),
                doc.getList(Constants.A_AMENITIES, String.class)
        );
    }
}
