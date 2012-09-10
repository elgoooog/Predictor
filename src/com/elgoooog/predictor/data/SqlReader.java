package com.elgoooog.predictor.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author Nicholas Hauschild
 *         Date: 9/9/12
 *         Time: 10:28 PM
 */
public class SqlReader {
    public static String readFromFile(String file) {
        try {
            File f = new File(file);
            BufferedReader reader = new BufferedReader(new FileReader(f));

            String line;

            StringBuilder builder = new StringBuilder();
            while((line = reader.readLine()) != null) {
                builder.append(line);
            }
            return builder.toString();
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }
}
