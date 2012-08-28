package com.elgoooog.predictor;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * @author Nicholas Hauschild
 *         Date: 8/27/12
 *         Time: 10:05 PM
 */
public class TrainerReaderTest {
    private TrainerReader trainerReader;

    @Before
    public void setup() {
        trainerReader = new TrainerReader();
    }

    @Test
    public void testRead() throws Exception {
        final List<TrainData> trainDatas = trainerReader.read ("S:\\competition\\stackoverflow\\train-sample.csv");
    }
}
