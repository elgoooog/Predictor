package com.elgoooog.predictor;

import com.elgoooog.predictor.feature.Feature;
import com.elgoooog.predictor.feature.TrainingFeatureVector;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    public void train(final String file, final OpenStatus openStatus) {
        try {
            train(new File(file), openStatus);
        } catch(final Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void train(final File file, final OpenStatus openStatus) {
        try {
            train(new FileReader(file), openStatus);
        } catch(final Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void train(final Reader reader, final OpenStatus openStatus) {
        try {
            final List<TrainData> trainDatas = trainerReader.read(reader);

            final List<TrainingFeatureVector> featureVectors = new ArrayList<TrainingFeatureVector>();
            for(final TrainData trainData : trainDatas) {
                final TrainingFeatureVector.Builder featureVectorBuilder = new TrainingFeatureVector.Builder();
                for(final Feature feature : features) {
                    double val = feature.getValue(trainData);

                    featureVectorBuilder.addFeature(feature.getName(), val);
                }
                featureVectorBuilder.setResult(trainData.getOpenStatus() == openStatus ? 1 : -1);
                featureVectors.add(featureVectorBuilder.build());
            }

            writeFile(featureVectors);
        } catch(final Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void writeFile(List<TrainingFeatureVector> featureVectors) throws IOException {
        BufferedWriter out = null;
        BufferedWriter key = null;
        try {
            out = new BufferedWriter(new FileWriter(new File("train.out")));
            key = new BufferedWriter(new FileWriter(new File("train.key")));

            boolean keyNotWritten = true;
            for(TrainingFeatureVector vector : featureVectors) {
                int result = vector.getResult();
                if(result > 0) {
                    out.append("+");
                }

                out.append(String.valueOf(result)).append(' ');

                int index = 1;
                Map<String, Double> features = vector.getFeatures();
                for(Map.Entry<String, Double> e : features.entrySet()) {
                    out.append(String.valueOf(index)).append(':').append(String.valueOf(e.getValue())).append(' ');
                    ++index;
                }
                out.append('\n');
                out.flush();

                if(keyNotWritten) {
                    int keyIndex = 1;
                    for(Map.Entry<String, Double> e : features.entrySet()) {
                        key.append(String.valueOf(keyIndex)).append(':').append(e.getKey()).append(' ');
                        ++keyIndex;
                    }
                    keyNotWritten = false;
                    key.flush();
                }
            }
        } finally {
            if(out != null) {
                out.close();
            }
            if(key != null) {
                key.close();
            }
        }
    }
}
