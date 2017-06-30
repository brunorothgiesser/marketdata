package com.bp.var.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Set;

/**
 * Created by rothb1 on 30/06/2016.
 */
@XmlRootElement
public class Scenario {
    private Set<PriceObservation> priceObservations;

    public Scenario() {}

    public Scenario(Set<PriceObservation> priceObservations) {
        this.priceObservations = priceObservations;
    }

    public Set<PriceObservation> getPriceObservations() {
        return priceObservations;
    }
}
