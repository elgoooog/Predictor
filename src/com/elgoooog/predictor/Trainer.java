package com.elgoooog.predictor;

import com.elgoooog.predictor.feature.Feature;
import com.elgoooog.predictor.feature.FeatureVector;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Nicholas Hauschild
 *         Date: 8/28/12
 *         Time: 10:21 PM
 */
public class Trainer {
    private TrainerReader trainerReader;
    private Feature[] features;

    public Trainer(final TrainerReader reader, final Feature... theFeatures) {
        trainerReader = reader;
        features = theFeatures;
    }

    public void train(final String file) {
        try {
            train(new File(file));
        } catch(final Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void train(final File file) {
        try {
            train(new FileReader(file));
        } catch(final Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void train(final Reader reader) {
        try {
            final List<TrainData> trainDatas = trainerReader.read(reader);

            final List<FeatureVector> featureVectors = new ArrayList<FeatureVector>();
            for(final TrainData trainData : trainDatas) {
                final FeatureVector.Builder featureVectorBuilder = new FeatureVector.Builder();
                for(final Feature feature : features) {
                    double val = feature.getValue(trainData);

                    featureVectorBuilder.addFeature(feature.getName(), val);
                }
                featureVectors.add(featureVectorBuilder.build());
            }

            //do something with featureVectors
        } catch(final Exception e) {
            throw new RuntimeException(e);
        }
    }
}
