package com.elgoooog.predictor;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Nicholas Hauschild
 *         Date: 8/27/12
 *         Time: 8:47 PM
 */
public class TrainerReader {
    public List<TrainData> read(final String file) throws Exception
    {
        return read(new File(file));
    }

    public List<TrainData> read(final File file) throws Exception
    {
        return read(new FileReader(file));
    }

    public List<TrainData> read(final Reader inReader) throws Exception
    {
        final List<TrainData> data = new ArrayList<TrainData>();

        final BufferedReader reader = new BufferedReader(inReader);
        final DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

        //consume first line
        reader.readLine();

        String line;
        while((line = reader.readLine()) != null)
        {
            String[] parts = line.split(",");

            //System.out.println(parts[0]);

            final long postId = Long.parseLong(parts[0]);
            final Date postCreationDate = dateFormat.parse(parts[1]);
            final long ownerId = Long.parseLong(parts[2]);
            final Date ownerCreationDate = dateFormat.parse(parts[3]);
            final int repAtPostCreation = Integer.parseInt(parts[4]);
            final int ownerUndeletedAnswerCountAtPostTime = Integer.parseInt(parts[5]);

            String title;
            int partsIndex = 6;
            if(parts[partsIndex].startsWith("\"")) {
                final StringBuilder titleBuilder = new StringBuilder();
                String temp = parts[partsIndex].substring(1);
                int from = 0;
                while(true) {
                    int index = temp.indexOf("\"",from);
                    if(index == -1) {
                        titleBuilder.append(temp.substring(from));
                        partsIndex++;
                        if(partsIndex == parts.length) {
                            line = reader.readLine();
                            parts = line.split(",");
                            partsIndex = 0;
                        }
                        temp = parts[partsIndex];
                        from = 0;
                        continue;
                    }

                    titleBuilder.append(temp.substring(from, index));

                    int count = 0;
                    while(temp.charAt(index) == '\"') {
                        count++;
                        index++;
                    }

                    for(int i = 0; i < count / 2; ++i) {
                        titleBuilder.append("\"");
                    }

                    if(count % 2 == 0) {
                        from = index;
                    } else {
                        break;
                    }
                }
                title = titleBuilder.toString();
            } else {
                title = parts[partsIndex];
            }

            partsIndex++;

            String markdown;
            if(parts[partsIndex].startsWith("\"")) {
                final StringBuilder markdownBuilder = new StringBuilder();
                String temp = parts[partsIndex].substring(1);
                int from = 0;
                while(true) {
                    int index = temp.indexOf("\"",from);
                    if(index == -1) {
                        markdownBuilder.append(temp.substring(from));
                        partsIndex++;
                        if(partsIndex == parts.length) {
                            line = reader.readLine();
                            parts = line.split(",");
                            partsIndex = 0;
                            markdownBuilder.append("\n");
                        } else {
                            markdownBuilder.append(",");
                        }
                        temp = parts[partsIndex];
                        from = 0;
                        continue;
                    }

                    markdownBuilder.append(temp.substring(from, index));

                    int count = 0;
                    while(index < temp.length() && temp.charAt(index) == '\"') {
                        count++;
                        index++;
                    }

                    for(int i = 0; i < count / 2; ++i) {
                        markdownBuilder.append("\"");
                    }

                    if(count % 2 == 0) {
                        from = index;
                    } else {
                        break;
                    }
                }
                markdown = markdownBuilder.toString();
            } else {
                markdown = parts[partsIndex];
            }

            partsIndex++;

            final List<String> tags = new ArrayList<String>();
            if(parts[partsIndex] != null && !"".equals(parts[partsIndex])) {
                tags.add(parts[partsIndex]);
            }
            partsIndex++;
            if(parts[partsIndex] != null && !"".equals(parts[partsIndex])) {
                tags.add(parts[partsIndex]);
            }
            partsIndex++;
            if(parts[partsIndex] != null && !"".equals(parts[partsIndex])) {
                tags.add(parts[partsIndex]);
            }
            partsIndex++;
            if(parts[partsIndex] != null && !"".equals(parts[partsIndex])) {
                tags.add(parts[partsIndex]);
            }
            partsIndex++;
            if(parts[partsIndex] != null && !"".equals(parts[partsIndex])) {
                tags.add(parts[partsIndex]);
            }
            partsIndex++;

            Date postClosedDate = null;
            if(parts[partsIndex] != null && !"".equals(parts[partsIndex])) {
                postClosedDate = dateFormat.parse(parts[partsIndex]);
            }
            partsIndex++;

            final OpenStatus openStatus = OpenStatus.valueOf(parts[partsIndex].toUpperCase().replaceAll(" ", "_"));

            data.add(new TrainData(postId, postCreationDate, ownerId, ownerCreationDate, repAtPostCreation, ownerUndeletedAnswerCountAtPostTime, title, markdown, tags, postClosedDate, openStatus));
        }

        reader.close();

        return data;
    }
}
