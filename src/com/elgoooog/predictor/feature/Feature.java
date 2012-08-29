package com.elgoooog.predictor.feature;

import com.elgoooog.predictor.TrainData;

/**
 * @author Nicholas Hauschild
 *         Date: 8/28/12
 *         Time: 10:09 PM
 */
public abstract class Feature {
    public abstract double getValue(TrainData trainData);

    public final String getName() {
        Class<? extends Feature> clazz = getClass();
        FeatureName featureName = clazz.getAnnotation(FeatureName.class);

        if(featureName == null) {
            return clazz.getSimpleName();
        }

        return featureName.value();
    }
}
