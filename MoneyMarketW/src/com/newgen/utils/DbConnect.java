package com.newgen.utils;

import com.newgen.iforms.custom.IFormReference;
import org.apache.log4j.Logger;

import java.util.List;

public class DbConnect {
    private static Logger logger = LogGen.getLoggerInstance(DbConnect.class);
    private IFormReference ifr;
    private String query;
    public DbConnect(IFormReference ifr, String query){
        this.ifr = ifr;
        this.query = query;
    }
    public List<List<String>> getData (){
        try {
            return data();
        }
        catch (Exception e){
            e.printStackTrace();
            logger.error("Exception Occurred in fetching Data-- "+ e.getMessage());
            return null;
        }
    }
    public int saveQuery(){
        try {
            return saveData();
        }catch(Exception e){
            e.printStackTrace();
            logger.error("Exception Occurred in saving Data-- "+ e.getMessage());
            return -1;
        }
    }
    private List<List<String>> data (){
         return  ifr.getDataFromDB(query);
    }
    private int saveData (){
        return ifr.saveDataInDB(query);
    }
}
