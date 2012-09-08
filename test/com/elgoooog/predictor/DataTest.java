package com.elgoooog.predictor;

import com.elgoooog.predictor.data.H2Interface;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.ResultSet;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Bait
 * Date: 9/8/12
 * Time: 2:58 PM
 * To change this template use File | Settings | File Templates.
 */
public class DataTest {
    H2Interface h2 = null;
    @Before
    public void createAndPublishData() throws Exception {
        h2 = new H2Interface("D:\\Users\\Bait\\Documents\\Projects\\StackOverflowData\\database");
    }
    @After
    public void cleanUp()
    {
        h2.closeConnection();
    }
    @Test
    public void createTables()
    {
        h2.createTables("D:\\Users\\Bait\\Documents\\Projects\\StackOverflowData\\train.csv");
    }
    @Test
    public void testQueries()
    {
        h2.printResults(h2.runSelectQuery("SELECT TOP 10 * FROM TRAINDATA"));
    }
    @Test
    public void clearDatabase()
    {
        h2.clearDatabase();
    }
    @Test
    public void testTrainData()
    {
       ResultSet rs = h2.runSelectQuery("SELECT TOP 10 * FROM TRAINDATA");
       TrainerReader tr = new TrainerReader();
        List<TrainData> trainDatas = tr.read(rs);
        for(TrainData td : trainDatas)
        {
            System.out.println(td.toString());
        }
    }





}
