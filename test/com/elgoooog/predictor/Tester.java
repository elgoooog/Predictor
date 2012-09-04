package com.elgoooog.predictor;

import com.elgoooog.predictor.feature.FromJoinToQuestionTime;
import com.elgoooog.predictor.feature.OwnerRepAtPostCreation;
import com.elgoooog.predictor.feature.TitleNoiseWordPercentage;
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
        List<TrainData> trainDatas = new TrainerReader().read ("S:\\competition\\stackoverflow\\train-sample.csv");

        Map<OpenStatus, Map<Long, Integer>> out = new DataPointGatherer().gatherDataPoints(trainDatas, new TitleNoiseWordPercentage(), 1);

        for(Map.Entry<Long, Integer> e : out.get(OpenStatus.TOO_LOCALIZED).entrySet()) {
            System.out.println(e.getKey() + "   " + e.getValue());
        }
    }

    @Test
    public void testEquation() throws Exception {
        System.out.println(OwnerRepAtPostCreation.Open_Range50Rep.calc(3));
        System.out.println(OwnerRepAtPostCreation.NotConstructive_Range50Rep.calc(3));
        System.out.println(OwnerRepAtPostCreation.NotRealQuestion_Range50Rep.calc(3));
        System.out.println(OwnerRepAtPostCreation.OffTopic_Range50Rep.calc(3));
        System.out.println(OwnerRepAtPostCreation.TooLocalized_Range50Rep.calc(3));
    }

    @Test
    public void testAnotherEquation() throws Exception {
        System.out.println(FromJoinToQuestionTime.Open_Range1Day.calc(0));
        System.out.println(FromJoinToQuestionTime.NotConstructive_Range1Day.calc(0));
        System.out.println(FromJoinToQuestionTime.NotRealQuestion_Range1Day.calc(0));
        System.out.println(FromJoinToQuestionTime.OffTopic_Range1Day.calc(0));
        System.out.println(FromJoinToQuestionTime.TooLocalized_Range1Day.calc(0));
    }

    @Test
    public void testYetAnotherEquation() throws Exception {
        System.out.println(TitleNoiseWordPercentage.Open_Range1Percent.calc(10));
        System.out.println(TitleNoiseWordPercentage.NotConstructive_Range1Percent.calc(10));
        System.out.println(TitleNoiseWordPercentage.NotRealQuestion_Range1Percent.calc(10));
        System.out.println(TitleNoiseWordPercentage.OffTopic_Range1Percent.calc(10));
        System.out.println(TitleNoiseWordPercentage.TooLocalized_Range1Percent.calc(10));
    }
}
