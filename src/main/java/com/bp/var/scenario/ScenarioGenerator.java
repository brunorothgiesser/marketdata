package com.bp.var.scenario;

import com.bp.var.model.PositionKey;
import com.bp.var.model.PriceObservation;
import com.bp.var.model.Scenario;

import java.util.HashSet;
import java.util.Set;

/**
 * Generates a "random" scenario,
 * i.e. a scenario made of price shifts randomly chosen from historic data points.
 */
public class ScenarioGenerator {

    public Scenario generateScenario(int numberOfDaysInTimeHorizon) throws Exception {

        MarketDataManager mdm = MarketDataManager.getInstance();

        Set<PriceObservation> lastBusinessDayPrices = mdm.getLastBusinessDayPrices();
        Set<PriceObservation> scenario = new HashSet<>();

        int hitCount = 0;
        int missCount = 0;
        for (PriceObservation priceObservation : lastBusinessDayPrices) {
            Double priceShift = getPriceShift(numberOfDaysInTimeHorizon, mdm, priceObservation);

            if (priceShift == null)  {
                scenario.add(priceObservation);
                missCount++;
            }
            else {
                // to multiply by -1 in 50% of cases
                if(Math.random()>0.5) priceShift = priceShift * -1;

                PriceObservation priceForScenario = new PriceObservation();
                priceForScenario.setKey(priceObservation.getKey());
                priceForScenario.setPrice(priceObservation.getPrice() + priceShift);

                scenario.add(priceForScenario);
                hitCount++;
            }
        }

        System.out.println("Hits = " + hitCount + " / Misses=" + missCount);

        return new Scenario(scenario);
    }

    private Double getPriceShift(int numberOfDaysInTimeHorizon, MarketDataManager mdm, PriceObservation priceObservation) {
        int numberOfBusinessDays = mdm.getNumberOfBusinessDays();
        int retries = 0;

        while (retries < 50) {
            int indexForPriceSet1 = (int) Math.round(numberOfBusinessDays * Math.random());

            int randomDateOffsetWithinTimeHorizon = 1;
            if (numberOfDaysInTimeHorizon > 1) {
                randomDateOffsetWithinTimeHorizon = (int) Math.round(numberOfDaysInTimeHorizon * Math.random());
            }

            // in case the random index got rounded UP to the set size, we need to avoid IndexOutOfBoundsException...
            if (indexForPriceSet1 == numberOfBusinessDays) indexForPriceSet1--;

            int indexForPriceSet2 = indexForPriceSet1 - randomDateOffsetWithinTimeHorizon;
            if (indexForPriceSet2 < 0) {
                indexForPriceSet2 = indexForPriceSet1 + randomDateOffsetWithinTimeHorizon;
            }

            Set<PriceObservation> randomPriceSet1 = mdm.getPrices(indexForPriceSet1);
            Set<PriceObservation> randomPriceSet2 = mdm.getPrices(indexForPriceSet2);

            PriceObservation matchingPriceObservation1 = findMatchingPriceObservation(priceObservation.getKey(), randomPriceSet1);
            PriceObservation matchingPriceObservation2 = findMatchingPriceObservation(priceObservation.getKey(), randomPriceSet2);

            if ((matchingPriceObservation1 != null && matchingPriceObservation2 != null)) {
                double shift = matchingPriceObservation1.getPrice() - matchingPriceObservation2.getPrice();
                return shift;
            } else {
                retries++;
            }
        }

        return null;
    }

    private PriceObservation findMatchingPriceObservation(PositionKey key, Set<PriceObservation> priceObservations) {
        for (PriceObservation priceObservation : priceObservations) {
            if (priceObservation.getKey().matches(key)) {
                return priceObservation;
            }
        }
        return null;
    }
}
