package com.bp.var.loaders.fileloaders;

import com.bp.var.loaders.PriceLoader;
import com.bp.var.model.PositionKey;
import com.bp.var.model.PriceObservation;
import com.bp.var.model.Trade;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by rothb1 on 26/05/2016.
 */
public class PriceCSVParser extends FileParser implements PriceLoader {

    private enum PriceType { OPTION, FUTURE }
    private Date todaysDate;

    public List<PriceObservation> load() throws Exception {
        return parseFile(getFilePath());
    }

    public PriceCSVParser() {
        Calendar.getInstance().set(2016, 4, 26, 0, 0);

        // Today's date is actually the date in which I took the market data snapshot
        todaysDate = Calendar.getInstance().getTime();
    }

    public List<PriceObservation> parseFile(String filePath) throws Exception {
        InputStream inputStream = getClass().getResourceAsStream(filePath);
        return parse(new Scanner(inputStream));
    }

    private List<PriceObservation> parse(Scanner inputStream) throws Exception {
        int lineCount = 1;
        List<PriceObservation> priceList = new ArrayList<>();
        PriceType priceType;

        // Skips line 1 as that's the header
        if (inputStream.hasNextLine()) inputStream.nextLine();

        while(inputStream.hasNextLine()){
            String line = inputStream.nextLine();
            lineCount++;
            if (lineCount % 50 == 0) {
                System.out.println("Loading prices..." + lineCount + " so far");
            }
            String[] tokens = line.split(",");

            // option prices have 6 tokens, futures prices have 4 tokens
            if(tokens.length == 4) priceType = PriceType.FUTURE;
            else if(tokens.length == 6) priceType = PriceType.OPTION;
            else throw new Exception("Unexpected: line " + lineCount + " has " + tokens.length + " tokens.");

            PositionKey key = new PositionKey();
            PriceObservation p = new PriceObservation();
            p.setKey(key);

            key.setProduct(tokens[0]);
            key.setDeliveryPeriod(tokens[1]);

            if(!tokens[2].equals("")) {
                SimpleDateFormat s = new SimpleDateFormat("dd-MMM-yy");
                Date date = s.parse(tokens[2]);
                p.setObservationDate(date);
            }

            Date dateForMonthsAheadCalc;
            if (p.getObservationDate() == null) {
                dateForMonthsAheadCalc = todaysDate;
            } else {
                dateForMonthsAheadCalc = p.getObservationDate();
            }

            Integer monthsAhead = calculateMonthsAhead(p.getObservationDate(), key.getDeliveryPeriod());
            key.setMonthsAhead(monthsAhead);

            if(!tokens[3].equals(""))p.setPrice(Double.valueOf(tokens[3]));

            if(priceType== PriceType.OPTION ) {
                if(tokens[4].equalsIgnoreCase("Put")) key.setOptionPutCall(Trade.OptionPutCall.PUT);
                else if(tokens[4].equalsIgnoreCase("Call")) key.setOptionPutCall(Trade.OptionPutCall.CALL);
                else throw new Exception("Unexpected: neither put nor call in line " + lineCount);
            }

            if(priceType== PriceType.OPTION) key.setOptionStrikePrice(Double.valueOf(tokens[5]));

            priceList.add(p);
        }

        inputStream.close();

        return priceList;
    }

    private Integer calculateMonthsAhead(Date observationDate, String deliveryPeriod) throws ParseException {
        SimpleDateFormat s = new SimpleDateFormat("MMM-yy");
        Date deliveryMonth = s.parse(deliveryPeriod);

        Calendar c = Calendar.getInstance();

        c.setTime(observationDate);
        Integer observationYear = c.get(Calendar.YEAR);
        Integer observationMonth = c.get(Calendar.MONTH);

        c.setTime(deliveryMonth);
        Integer contractYear = c.get(Calendar.YEAR);
        Integer contractMonth = c.get(Calendar.MONTH);

        int monthsAhead = (contractYear - observationYear) * 12 + contractMonth - observationMonth;
        return monthsAhead;
    }


}
