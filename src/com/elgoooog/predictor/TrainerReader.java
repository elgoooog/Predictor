package com.elgoooog.predictor;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Nicholas Hauschild
 *         Date: 8/27/12
 *         Time: 8:47 PM
 */
public class TrainerReader {
    public List<TrainData> read(final String file) throws IOException
    {
        return read(new File(file));
    }

    public List<TrainData> read(final File file) throws IOException
    {
        return read(new FileReader(file));
    }

    public List<TrainData> read(final Reader inReader) throws IOException
    {
        final List<TrainData> data = new ArrayList<TrainData>();

        final BufferedReader reader = new BufferedReader(inReader);

        //consume first line
        reader.readLine();

        String line;
        while((line = reader.readLine()) != null)
        {
            String[] parts = line.split(",");
        }

        return data;
    }
}
