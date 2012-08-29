package com.elgoooog.predictor.feature;

import com.elgoooog.predictor.TrainData;

/**
 * @author Nicholas Hauschild
 *         Date: 8/28/12
 *         Time: 10:12 PM
 */
public class OwnerRepAtPostCreation extends Feature {
    public double getValue(TrainData trainData) {
        return trainData.getReputationAtPostCreation();
    }
}
