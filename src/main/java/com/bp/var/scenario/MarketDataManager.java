package com.bp.var.scenario;

import com.bp.var.loaders.fileloaders.PriceCSVParser;
import com.bp.var.model.PriceObservation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by rothb1 on 06/06/2016.
 */
public class MarketDataManager {
    private List<Date> orderedDateList;
    private Map<Date,Set<PriceObservation>> pricesByDate;

    private static MarketDataManager instance;

    private MarketDataManager() throws Exception {
        PriceCSVParser priceParser = new PriceCSVParser();
        priceParser.setFilePath("/all_prices.csv");

        System.out.println("Loading historic prices... ");
        Collection<PriceObservation> priceList = priceParser.load();
        System.out.println("Loaded " + priceList.size() + " prices.");

        pricesByDate = groupPricesByBusinessDate(priceList);
        orderedDateList = new ArrayList<>();
        for(Date date : pricesByDate.keySet()) orderedDateList.add(date);
        Collections.sort(orderedDateList);
    }

    public static MarketDataManager getInstance() throws Exception {
        if (instance == null) {
            instance = new MarketDataManager();
        }
        return instance;
    }


    public Set<PriceObservation> getPrices(int i) {
        return pricesByDate.get(orderedDateList.get(i));
    }

    public Set<PriceObservation> getPrices(Date date) {
        Set<PriceObservation> priceObservations = pricesByDate.get(date);
        return priceObservations;
    }

    public Set<PriceObservation> getLastBusinessDayPrices() {
        // todo: un-hardcode the date
        try {
            Date d = new SimpleDateFormat("dd-MMM-yy").parse("14-Apr-16");
            return getPrices(d);
        } catch (ParseException e) {
            return null;
        }
    }

    public int getNumberOfBusinessDays() {
        return pricesByDate.size();
    }

    private  Map<Date,Set<PriceObservation>> groupPricesByBusinessDate(Collection<PriceObservation> priceList) {
        Map<Date,Set<PriceObservation>> pricesByDate = new HashMap<>();

        for (PriceObservation price : priceList) {
            Date date = price.getObservationDate();
            Set<PriceObservation> priceObservations;

            if (pricesByDate.containsKey(date)) priceObservations = pricesByDate.get(date);
            else priceObservations = new HashSet<>();
            priceObservations.add(price);

            pricesByDate.put(date, priceObservations);
        }

        return pricesByDate;
    }
}
