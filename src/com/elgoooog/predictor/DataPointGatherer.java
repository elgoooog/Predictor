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
    public Map<OpenStatus, Map<Long, Integer>> gatherDataPoints(List<TrainData> datas, Feature feature, long range) throws Exception {
        range = range < 1 ? 1 : range;

        Map<OpenStatus, Map<Long, Integer>> out = new HashMap<OpenStatus, Map<Long, Integer>>();
        for(OpenStatus currentStatus : OpenStatus.values()) {
            Map<Long, Integer> countMap = new TreeMap<Long, Integer>();
            out.put(currentStatus, countMap);

            for(TrainData data : datas) {
                long val = (long)feature.getValue(data) / range;
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
