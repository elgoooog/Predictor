package com.elgoooog.predictor.feature;

import com.elgoooog.predictor.OpenStatus;
import com.elgoooog.predictor.TrainData;

/**
 * @author Nicholas Hauschild
 *         Date: 8/28/12
 *         Time: 10:15 PM
 */

@FeatureName("JoinToQuestionTime")
public class FromJoinToQuestionTime extends Feature {
    @Override
    public double getNormalizedValue(OpenStatus status, TrainData trainData) {
        double val = getValue(trainData)/86400000d;
        double result;
        switch(status) {
            case TOO_LOCALIZED:
                result = TooLocalized_Range1Day.calc(val);
                break;
            case NOT_CONSTRUCTIVE:
                result = NotConstructive_Range1Day.calc(val);
                break;
            case OFF_TOPIC:
                result = OffTopic_Range1Day.calc(val);
                break;
            case NOT_A_REAL_QUESTION:
                result = NotRealQuestion_Range1Day.calc(val);
                break;
            default:
                result = Open_Range1Day.calc(val);
        }
        return result*getWeight(status);
    }

    @Override
    public double getWeight(OpenStatus status) {
        return 1.0;
    }

    public double getValue(TrainData trainData) {
        return trainData.getPostCreationDate().getTime() - trainData.getOwnerCreationDate().getTime();
    }

    public static class Open_Range1Day
    {
        private final static double a = -5.8811076909116004E+01;
        private final static double b = 9.2611648668411220E-03;
        private final static double c = 1.6893650021018281E-01;

        public static double calc(double x)
        {
            return (a+x)/(b+c*x);
        }
    }

    public static class TooLocalized_Range1Day
    {
        private static final double a = 2.1054737426984950E+01;
        private static final double b = -8.9538774046365121E-04;
        private static final double c = -1.4843513482917497E-02;

        public static double calc(double x)
        {
            return (a+x)/(b+c*x);
        }
    }

    public static class NotRealQuestion_Range1Day
    {
        private static final double a = -4.9921450957046190E+01;
        private static final double b = 5.1107939204503712E+00;
        private static final double c = 9.9999999999999911E-01;

        public static double calc(double x)
        {
            return a * Math.exp(b / (x + c));
        }
    }

    public static class OffTopic_Range1Day
    {
        private static final double a = -6.6871199472856290E+01;
        private static final double b = 5.6770583557982270E+00;
        private static final double c = 9.9999999999999978E-01;

        public static double calc(double x)
        {
            return a * Math.exp(b / (x + c));
        }
    }

    public static class NotConstructive_Range1Day
    {
        private static final double a = -4.0802179074313890E+15;
        private static final double b = 1.8583485258969098E+11;
        private static final double c = 3.0920710105802568E+12;

        public static double calc(double x)
        {
            return (a+x)/(b+c*x);
        }
    }
}
