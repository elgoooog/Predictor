package com.elgoooog.predictor;

import com.elgoooog.predictor.feature.Feature;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Nicholas Hauschild
 *         Date: 8/30/12
 *         Time: 11:09 PM
 */
public class DataPointGatherer {
    public Map<OpenStatus, Map<Integer, Integer>> gatherDataPoints(Feature feature, int range) throws Exception {
        List<TrainData> trainDatas = new TrainerReader().read ("S:\\competition\\stackoverflow\\train-sample.csv");

        range = range < 1 ? 1 : range;

        Map<OpenStatus, Map<Integer, Integer>> out = new HashMap<OpenStatus, Map<Integer, Integer>>();
        for(OpenStatus currentStatus : OpenStatus.values()) {
            Map<Integer, Integer> countMap = new TreeMap<Integer, Integer>();
            out.put(currentStatus, countMap);

            for(TrainData data : trainDatas) {
                int val = (int)feature.getValue(data) / range;
                int incDec = data.getOpenStatus() == currentStatus ? 1 : -1;

                Integer count = countMap.get(val);
                if(count == null) {
                    countMap.put(val, incDec);
                }
                else {
                    countMap.put(val, count + incDec);
                }
            }
        }

        return out;
    }
}
