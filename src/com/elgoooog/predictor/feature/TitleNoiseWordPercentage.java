package com.elgoooog.predictor.feature;

import com.elgoooog.predictor.TrainData;

import java.util.Arrays;
import java.util.List;

/**
 * @author Nicholas Hauschild
 *         Date: 8/28/12
 *         Time: 10:50 PM
 */
public class TitleNoiseWordPercentage extends Feature {
    private static final List<String> NOISE_WORDS = Arrays.asList("about","after","all","also","an","and","another","any","are","as","at","be","because","been","before",
            "being","between","both","but","by","came","can","come","could","did","do","each","for","from","get",
            "got","has","had","he","have","her","here","him","himself","his","how","if","in","into","is","it","like",
            "make","many","me","might","more","most","much","must","my","never","now","of","on","only","or","other",
            "our","out","over","said","same","see","should","since","some","still","such","take","than","that",
            "the","their","them","then","there","these","they","this","those","through","to","too","under","up",
            "very","was","way","we","well","were","what","where","which","while","who","with","would","you","your","a",
            "b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z","$","1","2","3","4","5","6","7","8","9","0","_");

    @Override
    public double getValue(TrainData trainData) {
        final String[] parts = trainData.getTitle().split(" |,|-|\\.");

        int noiseWordCount = 0;
        for(final String part : parts) {
            if(NOISE_WORDS.contains(part.trim().toLowerCase())) {
                noiseWordCount++;
            }
        }

        return noiseWordCount * 100 / parts.length;
    }

    public static class Open_Range1Percent
    {
        private final static double a = -7.1543675688983744E+106;
        private final static double b = -1.1840434089499926E+105;
        private final static double c = 6.1855260529429316E+01;

        public static double calc(double x)
        {
            return a/(b+Math.pow(x,c));
        }
    }

    public static class NotRealQuestion_Range1Percent
    {
        private static final double a = 9.8827133967159098E+03;
        private static final double b = 3.3773414200765295E-02;
        private static final double offset = -1.1996131104381167E+04;

        public static double calc(double x)
        {
            return a * Math.pow(x, b) + offset;
        }
    }

    public static class NotConstructive_Range1Percent
    {
        private static final double a = 1.4309486347094109E+04;
        private static final double b = 2.7064417146576154E-02;
        private static final double offset = -1.6928286429403786E+04;

        public static double calc(double x)
        {
            return a * Math.pow(x, b) + offset;
        }
    }

    public static class OffTopic_Range1Percent
    {
        private static final double a = 1.3600568585746476E+04;
        private static final double b = 2.6922396509417336E-02;
        private static final double offset = -1.6102799234055510E+04;

        public static double calc(double x)
        {
            return a * Math.pow(x, b) + offset;
        }
    }

    public static class TooLocalized_Range1Percent
    {
        private static final double a = 1.6280490102882759E+04;
        private static final double b = 2.5851621696235935E-02;
        private static final double offset = -1.9233153618412318E+04;

        public static double calc(double x)
        {
            return a * Math.pow(x, b) + offset;
        }
    }
}
