package com.elgoooog.predictor.feature;

import com.elgoooog.predictor.OpenStatus;
import com.elgoooog.predictor.TrainData;

/**
 * @author Nicholas Hauschild
 *         Date: 8/28/12
 *         Time: 10:12 PM
 */
public class OwnerRepAtPostCreation extends Feature {
    public double getValue(TrainData trainData) {
        return trainData.getReputationAtPostCreation();
    }

    @Override
    public double getNormalizedValue(OpenStatus status, TrainData trainData) {
        double val = getValue(trainData)/50d;
        double result;
        switch(status) {
            case TOO_LOCALIZED:
                result = TooLocalized_Range50Rep.calc(val);
                break;
            case NOT_CONSTRUCTIVE:
                result = NotConstructive_Range50Rep.calc(val);
                break;
            case OFF_TOPIC:
                result = OffTopic_Range50Rep.calc(val);
                break;
            case NOT_A_REAL_QUESTION:
                result = NotRealQuestion_Range50Rep.calc(val);
                break;
            default:
                result = Open_Range50Rep.calc(val);
        }
        return result*getWeight(status);
    }

    @Override
    public double getWeight(OpenStatus openStatus) {
        return 1.0;
    }

    public static class Open_Range50Rep
    {
        private final static double a = -4.5624955366567554E-01;
        private final static double b = 4.2261013079793942E-05;
        private final static double c = 4.2352108401279059E-04;

    	public static double calc(double x)
    	{
    		return (a+x)/(b+c*Math.pow(x,2.0));
    	}
    }

    public static class NotRealQuestion_Range50Rep
    {
        private static final double a = -1.4335592033613648E+04;
        private static final double b = -5.1517113591752561E-01;
        private static final double c = -1.1382722934031637E+00;

        public static double calc(double x)
        {
            return a * Math.pow((x-b), c);
        }
    }

    public static class NotConstructive_Range50Rep
    {
        private static final double a = -6.2375410246371670E+04;
        private static final double b = 1.5057052800958093E+00;
        private static final double c = 6.7225433107299737E-01;

        public static double calc(double x)
        {
            return a/Math.pow(1.0+b*Math.pow(x,c),2.0);
        }
    }

    public static class OffTopic_Range50Rep
    {
        private static final double a = -5.6460930242813680E+04;
        private static final double b = 1.4241302598671595E+00;
        private static final double c = 6.5760700925669358E-01;

        public static double calc(double x)
        {
            return a/Math.pow(1.0+b*Math.pow(x,c),2.0);
        }
    }

    public static class TooLocalized_Range50Rep
    {
        private static final double a = -6.7888828165347135E+04;
        private static final double b = 1.3782913819733238E+00;
        private static final double c = 6.6354291818556976E-01;

        public static double calc(double x)
        {
            return a/Math.pow(1.0+b*Math.pow(x,c),2.0);
        }
    }
}
