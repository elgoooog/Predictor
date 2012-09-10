package com.elgoooog.predictor;

import com.elgoooog.predictor.feature.Feature;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Nicholas Hauschild
 *         Date: 9/9/12
 *         Time: 9:49 PM
 */
public class ScoreAndRank {
    public Map<TrainData, Map<OpenStatus,Double>> scoreFeatures(List<TrainData> datas, Feature... features ) throws Exception {
        Map<TrainData, Map<OpenStatus,Double>> out = new HashMap<TrainData, Map<OpenStatus,Double>>();
        for(TrainData data : datas) {
            Map<OpenStatus, Double> scores = new HashMap<OpenStatus, Double>();
            out.put(data, scores);
            for(OpenStatus currentStatus : OpenStatus.values()) {
                double score = 0d;
                for(Feature feature : features) {
                    score += feature.getNormalizedValue(currentStatus, data);
                }
                scores.put(currentStatus, score);
            }
        }

        return out;
    }
}
