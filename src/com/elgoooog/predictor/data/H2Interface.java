package com.elgoooog.predictor.data;

import java.sql.*;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Bait
 * Date: 9/8/12
 * Time: 1:22 PM
 * To change this template use File | Settings | File Templates.
 */
public class H2Interface {
    private Connection conn = null;

    public H2Interface(String dbLocation)
    {
        try {
            Class.forName("org.h2.Driver");

            try {
                conn = DriverManager.getConnection("jdbc:h2:" + dbLocation);

            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void executeQuery(String query)
    {
        Statement statement = null;
        try {
            statement = conn.createStatement();
            statement.execute(query);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(statement != null){
                    statement.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void executeQuery(String query, Map<String, String> params) {
        int index = query.indexOf("${");
        while(index != -1) {

            int endIndex = query.indexOf('}', index);
            String param = query.substring(index + 2, endIndex);
            String replacement = params.get(param);
            query = query.replaceFirst("\\$\\{" + param + "\\}", replacement);
            index = query.indexOf("${", endIndex);
        }

        executeQuery(query);
    }

    public ResultSet runSelectQuery(String query)
    {
        ResultSet resultSet = null;
        Statement statement = null;
        try {
            statement = conn.createStatement();
            resultSet = statement.executeQuery(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public void printResults(ResultSet rs)
    {
        try {
            for(int i=1; i<=rs.getMetaData().getColumnCount(); i++)
            {
                System.out.print(rs.getMetaData().getColumnName(i));
                if(i<rs.getMetaData().getColumnCount())
                {
                    System.out.print("|");
                }
            }
            System.out.println();
            while(rs.next())
            {
                for(int i=1; i<=rs.getMetaData().getColumnCount(); i++)
                {
                    System.out.print(rs.getString(i));
                    if(i<rs.getMetaData().getColumnCount())
                    {
                        System.out.print("|");
                    }
                }
                System.out.println();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createTables(String csvLocation)
    {
        executeQuery("CREATE TABLE TRAINDATA(POSTID LONG PRIMARY KEY, POSTCREATIONDATE VARCHAR(30), OWNERUSERID LONG, " +
        "OWNERCREATIONDATE  VARCHAR(30), REPUTATIONATPOSTCREATION INT, OWNERUNDELETEDANSWERCOUNTATPOSTTIME INT, TITLE VARCHAR(MAX), " +
        "BODYMARKDOWN VARCHAR(MAX), TAG1 VARCHAR(50), TAG2 VARCHAR(50), TAG3 VARCHAR(50), TAG4 VARCHAR(50), TAG5 VARCHAR(50), " +
        "POSTCLOSEDDATE  VARCHAR(30), OPENSTATUS VARCHAR(20)) AS SELECT * FROM CSVREAD('" + csvLocation + "') ");
    }

    public void clearDatabase()
    {
        executeQuery("DELETE FROM TRAINDATA");
    }

    public void closeConnection()
    {
        try
        {
            conn.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
