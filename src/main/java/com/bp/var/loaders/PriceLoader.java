package com.bp.var.loaders;

import com.bp.var.model.PriceObservation;

import java.util.List;

/**
 * Created by rothb1 on 08/06/2016.
 */
public interface PriceLoader {
    List<PriceObservation> load() throws Exception;
}
