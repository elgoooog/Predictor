package com.elgoooog.predictor.feature;

import java.util.Map;

/**
 * @author Nicholas Hauschild
 *         Date: 9/12/12
 *         Time: 8:41 PM
 */
public class TrainingFeatureVector extends FeatureVector {
    private int result;

    public TrainingFeatureVector(final Map<String, Double> featureScores, int result) {
        super(featureScores);
        this.result = result;
    }

    public int getResult() {
        return result;
    }

    public static class Builder extends FeatureVector.Builder {
        protected int result;

        public Builder setResult(final int result) {
            this.result = result;
            return this;
        }

        public TrainingFeatureVector build() {
            return new TrainingFeatureVector(featureVals, result);
        }
    }
}
