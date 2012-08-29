package com.elgoooog.predictor.feature;

import com.elgoooog.predictor.TrainData;

import java.util.Arrays;
import java.util.List;

/**
 * @author Nicholas Hauschild
 *         Date: 8/28/12
 *         Time: 10:50 PM
 */
public class TitleNoiseWordPercentage extends Feature {
    private static final List<String> NOISE_WORDS = Arrays.asList("the", "a", "is");

    @Override
    public double getValue(TrainData trainData) {
        final String[] parts = trainData.getTitle().split(" ,-\\.");

        int noiseWordCount = 0;
        for(final String part : parts) {
            if(NOISE_WORDS.contains(part.trim().toLowerCase())) {
                noiseWordCount++;
            }
        }

        return noiseWordCount / parts.length;
    }
}
