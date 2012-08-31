package com.elgoooog.predictor;

import com.elgoooog.predictor.feature.OwnerRepAtPostCreation;
import org.junit.Test;

import java.util.List;
import java.util.Map;

/**
 * @author Nicholas Hauschild
 *         Date: 8/27/12
 *         Time: 10:05 PM
 */
public class Tester {
    @Test
    public void testTrainerReader() throws Exception {
        final List<TrainData> trainDatas = new TrainerReader().read("S:\\competition\\stackoverflow\\train-sample.csv");

        System.out.println("Train data size: " + trainDatas.size());
    }

    @Test
    public void testDataPointGatherer() throws Exception {
        Map<OpenStatus, Map<Integer, Integer>> out = new DataPointGatherer().gatherDataPoints(new OwnerRepAtPostCreation(), 50);

        for(Map.Entry<Integer, Integer> e : out.get(OpenStatus.TOO_LOCALIZED).entrySet()) {
            System.out.println(e.getKey() + "   " + e.getValue());
        }
    }

    @Test
    public void testEquation() throws Exception {
        OwnerRepAtPostCreation.Open_Range50.calc(3);
        System.out.println(OwnerRepAtPostCreation.Open_Range50.calc(3));
        System.out.println(OwnerRepAtPostCreation.NotConstructive_Range50.calc(3));
        System.out.println(OwnerRepAtPostCreation.NotRealQuestion_Range50.calc(3));
        System.out.println(OwnerRepAtPostCreation.OffTopic_Range50.calc(3));
        System.out.println(OwnerRepAtPostCreation.TooLocalized_Range50.calc(3));
    }
}
