package com.bp.var.scenario;

import com.bp.var.model.PositionKey;
import com.bp.var.model.PriceObservation;
import com.bp.var.model.Scenario;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

/**
 * Created by rothb1 on 30/06/2016.
 */
public class ResponseFormatter {

    private static final DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yy");
    private static final DecimalFormat priceFormat = new DecimalFormat("###,###,###.00");

    public static String format(Scenario s) {
        if (s==null) return "scenario is null";

        StringBuffer b = new StringBuffer();
        Set<PriceObservation> priceObservations = s.getPriceObservations();
        for (PriceObservation priceObservation : priceObservations) {
            PositionKey key = priceObservation.getKey();
            if (key != null) {
                b.append(key.getProduct());
                b.append(",");
                b.append(key.getDeliveryPeriod());
                b.append(",");
                Date observationDate = priceObservation.getObservationDate();

                if (observationDate != null) {
                    b.append(dateFormat.format(observationDate));
                }

                b.append(",");
                Double price = priceObservation.getPrice();
                if (price != null) {
                    b.append(priceFormat.format(price));
                }

                if (key.getOptionPutCall() == null) {
                    b.append(",,");
                } else {
                    b.append(",");
                    b.append(key.getOptionPutCall());
                    b.append(",");
                    b.append(key.getOptionStrikePrice());
                }

                b.append("\r\n");
            }
        }


        return b.toString();
    }
}
