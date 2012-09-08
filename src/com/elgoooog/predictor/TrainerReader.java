package com.elgoooog.predictor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
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
        final DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");

        //consume first line
        reader.readLine();

        String line;
        while((line = reader.readLine()) != null)
        {
            String[] parts = line.split(",", -1);

            //System.out.println(parts[0]);

            final long postId = Long.parseLong(parts[0]);
            final Date postCreationDate = parseDate(parts[1], dateFormat, dateFormat2);
            final long ownerId = Long.parseLong(parts[2]);
            final Date ownerCreationDate = parseDate(parts[3], dateFormat, dateFormat2);
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
                            parts = line.split(",", -1);
                            partsIndex = 0;
                            titleBuilder.append("\n");
                        } else {
                            titleBuilder.append(",");
                        }
                        temp = parts[partsIndex];
                        from = 0;
                        continue;
                    }

                    titleBuilder.append(temp.substring(from, index));

                    int count = 0;
                    while(index < temp.length() && temp.charAt(index) == '\"') {
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
                            parts = line.split(",", -1);
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
                postClosedDate = parseDate(parts[partsIndex], dateFormat, dateFormat2);
            }
            partsIndex++;

            final OpenStatus openStatus = OpenStatus.valueOf(parts[partsIndex].toUpperCase().replaceAll(" ", "_"));

            data.add(new TrainData(postId, postCreationDate, ownerId, ownerCreationDate, repAtPostCreation, ownerUndeletedAnswerCountAtPostTime, title, markdown, tags, postClosedDate, openStatus));
        }

        reader.close();

        return data;
    }

    public List<TrainData> read(ResultSet rs)
    {
        final DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        final DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
        final List<TrainData> data = new ArrayList<TrainData>();
        try {
            while(rs.next())
            {
                final long postId = rs.getLong("POSTID");
                final Date postCreationDate = parseDate(rs.getString("POSTCREATIONDATE"), dateFormat, dateFormat2);
                final long ownerId = rs.getLong("OWNERUSERID");
                final Date ownerCreationDate = parseDate(rs.getString("OWNERCREATIONDATE"), dateFormat, dateFormat2);
                final int repAtPostCreation = rs.getInt("REPUTATIONATPOSTCREATION");
                final int ownerUndeletedAnswerCountAtPostTime = rs.getInt("OWNERUNDELETEDANSWERCOUNTATPOSTTIME");
                final String title = rs.getString("TITLE");
                final String markdown = rs.getString("BODYMARKDOWN");
                List<String> tags = new ArrayList<String>();
                for(int i=1; i<6; i++)
                {
                    if(rs.getString("TAG" + i) != null && !rs.getString("TAG" + i).equals(""))
                    {
                        tags.add(rs.getString("TAG" + i));
                    }
                }
                Date postClosedDate = null;
                if(rs.getString("POSTCLOSEDDATE") != null)
                {
                    postClosedDate = parseDate(rs.getString("POSTCLOSEDDATE"), dateFormat, dateFormat2);
                }
                final OpenStatus openStatus = OpenStatus.valueOf(rs.getString("OPENSTATUS").toUpperCase().replaceAll(" ", "_"));

                data.add(new TrainData(postId, postCreationDate, ownerId, ownerCreationDate, repAtPostCreation, ownerUndeletedAnswerCountAtPostTime, title, markdown, tags, postClosedDate, openStatus));
            }
        } catch (SQLException e) {
            System.out.println(e.getStackTrace());
        }
        return data;
    }

    private Date parseDate(final String dateString, final DateFormat... dateFormats) {
        for(DateFormat dateFormat : dateFormats) {
            try {
                return dateFormat.parse(dateString);
            } catch(ParseException e) {
                //do nothing
            }
        }
        throw new RuntimeException("no date formatted");
    }
}
