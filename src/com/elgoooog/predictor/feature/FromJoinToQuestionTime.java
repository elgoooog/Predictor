package com.elgoooog.predictor.feature;

import com.elgoooog.predictor.TrainData;

/**
 * @author Nicholas Hauschild
 *         Date: 8/28/12
 *         Time: 10:15 PM
 */

@FeatureName("JoinToQuestionTime")
public class FromJoinToQuestionTime extends Feature {
    public double getValue(TrainData trainData) {
        return trainData.getPostCreationDate().getTime() - trainData.getOwnerCreationDate().getTime();
    }
}
