package com.elgoooog.predictor.feature;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Nicholas Hauschild
 *         Date: 8/28/12
 *         Time: 10:35 PM
 */
public class FeatureVector {
    private Map<String, Double> featureValues;

    public FeatureVector(final Map<String, Double> featureScores) {
        featureValues = Collections.unmodifiableMap(featureScores);
    }

    public static class Builder {
        private Map<String, Double> featureVals = new HashMap<String, Double>();

        public Builder addFeature(final String featureName, final double value) {
            featureVals.put(featureName, value);
            return this;
        }

        public FeatureVector build() {
            return new FeatureVector(featureVals);
        }
    }
}
