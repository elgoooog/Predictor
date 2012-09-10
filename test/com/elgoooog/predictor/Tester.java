package com.elgoooog.predictor;

import com.elgoooog.predictor.data.H2Interface;
import com.elgoooog.predictor.data.SqlReader;
import com.elgoooog.predictor.feature.FromJoinToQuestionTime;
import com.elgoooog.predictor.feature.OwnerRepAtPostCreation;
import com.elgoooog.predictor.feature.TitleNoiseWordPercentage;
import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;
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
    public void testScoreAndRank() throws Exception {
        List<TrainData> trainDatas = new TrainerReader().read ("S:\\competition\\stackoverflow\\train-sample.csv");

        Map<TrainData, Map<OpenStatus, Double>> out = new ScoreAndRank().scoreFeatures(trainDatas,
                new TitleNoiseWordPercentage(), new FromJoinToQuestionTime(), new OwnerRepAtPostCreation());

        Map<OpenStatus, Integer> wrongCounts = new HashMap<OpenStatus, Integer>();

        int correct = 0;
        int wrong = 0;
        for(Map.Entry<TrainData, Map<OpenStatus, Double>> e : out.entrySet()) {
            Map<OpenStatus, Double> scores = e.getValue();
            TrainData data = e.getKey();
            double sum = 0d;
            for(Double d : scores.values()) {
                sum += d;
            }

            Map<OpenStatus, Double> percents = new HashMap<OpenStatus, Double>();
            OpenStatus largest = OpenStatus.OPEN;
            double largestScore = Double.MIN_VALUE;
            for(Map.Entry<OpenStatus, Double> ee : scores.entrySet()) {
                double weightOfStatus = 1d;
//                if( ee.getKey() == OpenStatus.TOO_LOCALIZED) {
//                    weightOfStatus = 0.5d;
//                }
//                if( ee.getKey() == OpenStatus.NOT_CONSTRUCTIVE) {
//                                    weightOfStatus = 0.5d;
//                                }

                double s = ee.getValue()/sum * ( weightOfStatus);
                if(largestScore < s) {
                    largestScore = s;
                    largest = ee.getKey();
                }
                percents.put(ee.getKey(), s);
            }

            if( largest == data.getOpenStatus()) {
                correct ++;
            }
            else {
                wrong++;
                Integer c = wrongCounts.get(largest);
                if( c == null ) c = new Integer(0);
                wrongCounts.put(largest, c+1);
            }
        }

        System.out.println("We were correct: " + correct  + " and wrong: " + wrong);
        System.out.println("Percentage: " + correct * 100 / (correct + wrong));
        for( Map.Entry<OpenStatus, Integer> entry : wrongCounts.entrySet()) {
            System.out.println(entry.getKey()+" "+entry.getValue()+ " "+entry.getValue()*100d/wrong);
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

    @Test
    public void testEl() {
        H2Interface h2 = new H2Interface("");
        h2.executeQuery("hola sucka! ${hi there} some more here...", Collections.singletonMap("hi there", "hola again!"));
    }

    @Test
    public void testSqlReader() {
        System.out.println(SqlReader.readFromFile("sql/deleteTrainData.sql"));
        System.out.println(SqlReader.readFromFile("sql/createTrainData.sql"));
    }
}
