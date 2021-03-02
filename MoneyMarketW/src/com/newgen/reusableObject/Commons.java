package com.newgen.reusableObject;

import com.newgen.iforms.custom.IFormReference;
import com.newgen.utils.Constants;
import com.newgen.utils.DbConnect;
import com.newgen.utils.LogGen;
import com.newgen.utils.Query;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.text.ParseException;
import java.util.Date;

import java.text.SimpleDateFormat;

public class Commons implements Constants {
    private Logger logger = LogGen.getLoggerInstance(Commons.class);
    private String getTat (String entryDate, String exitDate){
            SimpleDateFormat sdf = new SimpleDateFormat(dateTimeFormat);
            try {
                Date d1 = sdf.parse(entryDate);
                Date d2 = sdf.parse(exitDate);

                long difference_In_Time = d2.getTime() - d1.getTime();
                long difference_In_Seconds = (difference_In_Time / 1000) % 60;
                long difference_In_Minutes = (difference_In_Time / (1000 * 60)) % 60;
                long difference_In_Hours = (difference_In_Time / (1000 * 60 * 60)) % 24;
                long difference_In_Years = (difference_In_Time / (1000l * 60 * 60 * 24 * 365));
                long difference_In_Days = (difference_In_Time / (1000 * 60 * 60 * 24)) % 365;
                logger.info("Tat-- "+ difference_In_Days + " days, " + difference_In_Hours + " hours, " + difference_In_Minutes + " minutes, " + difference_In_Seconds + " seconds");

                return  difference_In_Days + " days, " + difference_In_Hours + " hours, " + difference_In_Minutes + " minutes, " + difference_In_Seconds + " seconds";
            }
            catch (ParseException e) {
                e.printStackTrace();
                logger.error("Exception-- "+ e.getMessage());
            }
            return null;
    }
    public void setCpDecisionHistory (IFormReference ifr){
        String marketType = (String)ifr.getValue(selectCpMarket);
        String remarks = (String)ifr.getValue(cpRemarksLocal);
        String entryDate = (String)ifr.getValue(entryDateLocal);
        String exitDate = getCurrentDateTime();

        setDecisionHistory(ifr,getLoginUser(ifr),cpProcessName,marketType,getCpDecision(ifr),remarks,getActivityName(ifr),entryDate,exitDate,getTat(entryDate,exitDate));
        ifr.setValue(decHisFlagLocal,flag);
    }
    public void setDecisionHistory(IFormReference ifr, String staffId, String process, String marketType, String decision,
                                     String remarks, String prevWs, String entryDate, String exitDate, String tat){
        JSONArray jsRowArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
            jsonObject.put(dhRowStaffId,staffId);
            jsonObject.put(dhRowProcess,process);
            jsonObject.put(dhRowMarketType,marketType);
            jsonObject.put(dhRowDecision,decision);
            jsonObject.put(dhRowRemarks,remarks);
            jsonObject.put(dhRowPrevWs,prevWs);
            jsonObject.put(dhRowEntryDate,entryDate);
            jsonObject.put(dhRowExitDate,exitDate);
            jsonObject.put(dhRowTat,tat);
            jsRowArray.add(jsonObject);

        ifr.addDataToGrid(decisionHisTable, jsRowArray);
    }

    public String getProcess(IFormReference ifr){
        return (String)ifr.getValue(selectProcess);
    }
    public String getCurrentDateTime (){
        return new SimpleDateFormat(dateTimeFormat).format(new Date());
    }
    public String getCpDecision (IFormReference ifr){
        return (String) ifr.getValue(cpDecisionLocal);
    }
    public String getWorkItemNumber (IFormReference ifr){
        return (String)ifr.getControlValue(wiNameLocal);
    }
    public String getLoginUser(IFormReference ifr){
        return (String)ifr.getUserName();
    }
    public String getActivityName (IFormReference ifr){
        return (String) ifr.getActivityName();
    }
    public void hideProcessMarkets (IFormReference ifr){
        logger.info("hideProcessMarkets-- method start");
        ifr.setTabStyle(processTabName,commercialTab,visible,False);
        ifr.setTabStyle(processTabName,treasuryTab,visible,False);
        ifr.setTabStyle(processTabName,omoTab,visible,False);
        logger.info("hideProcessMarkets-- method end");
    }
    public void hideDashBoardTab (IFormReference ifr){
        ifr.setTabStyle(processTabName,dashboardTab,visible,False);
    }
    public void selectMarketSheet (IFormReference ifr){
        logger.info("selectMarketSheet- method start");
        String market = (String)ifr.getValue(selectProcess);
        logger.info("market: "+market);
        if(market.equalsIgnoreCase(commercialProcess)) {
            ifr.setTabStyle(processTabName, commercialTab, visible, True);
            ifr.setTabStyle(processTabName, treasuryTab, visible, False);
            ifr.setTabStyle(processTabName, omoTab, visible, False);
        }
        else if (market.equalsIgnoreCase(treasuryProcess)) {
            ifr.setTabStyle(processTabName, treasuryTab, visible, True);
            ifr.setTabStyle(processTabName, commercialTab, visible, False);
            ifr.setTabStyle(processTabName, omoTab, visible, False);
        }
        else if (market.equalsIgnoreCase(omoProcess)){
            ifr.setTabStyle(processTabName,omoTab,visible,True);
            ifr.setTabStyle(processTabName,commercialTab,visible,False);
            ifr.setTabStyle(processTabName,treasuryTab,visible,False);
        }
        else {
            ifr.setTabStyle(processTabName,omoTab,visible,False);
            ifr.setTabStyle(processTabName,commercialTab,visible,False);
            ifr.setTabStyle(processTabName,treasuryTab,visible,False);
        }
        logger.info("selectMarketSheet- method end");
    }
    public void showSelectedMarketSheet(IFormReference ifr){
        logger.info("showSelectedMarketSheet- method start");
        String market = (String)ifr.getValue(selectProcess);
        logger.info("market: "+market);
        if(market.equalsIgnoreCase(commercialProcess)) {
            ifr.setTabStyle(processTabName, commercialTab, visible, True);
            ifr.setTabStyle(processTabName, treasuryTab, visible, False);
            ifr.setTabStyle(processTabName, omoTab, visible, False);
            ifr.setTabStyle(processTabName,dashboardTab,visible,False);
        }
        else if (market.equalsIgnoreCase(treasuryProcess)) {
            ifr.setTabStyle(processTabName, treasuryTab, visible, True);
            ifr.setTabStyle(processTabName, commercialTab, visible, False);
            ifr.setTabStyle(processTabName, omoTab, visible, False);
            ifr.setTabStyle(processTabName,dashboardTab,visible,False);
        }
        else if (market.equalsIgnoreCase(omoProcess)){
            ifr.setTabStyle(processTabName,omoTab,visible,True);
            ifr.setTabStyle(processTabName,commercialTab,visible,False);
            ifr.setTabStyle(processTabName,treasuryTab,visible,False);
            ifr.setTabStyle(processTabName,dashboardTab,visible,False);
        }
        logger.info("showSelectedMarketSheet- method end");
    }
    public String getSol (IFormReference ifr){
        logger.info("getSol- method start");
        Query query = new Query(getLoginUser(ifr));
        logger.info("query-- "+ query.getSolQuery());
        DbConnect dbConnect = new DbConnect(ifr,query.getSolQuery());
        logger.info("sol-- "+ dbConnect.getData().get(0).get(0));
        logger.info("getSol- method end");
        return dbConnect.getData().get(0).get(0);
    }
    public void hideCpSections (IFormReference ifr){
       ifr.setStyle(cpLandingMsgSection,visible,False);
       ifr.setStyle(cpMarketSection,visible,False);
       ifr.setStyle(cpDecisionSection,visible,False);
       ifr.setStyle(cpPoiSection,visible,False);
       ifr.setStyle(cpPrimaryBidSection,visible,False);
       ifr.setStyle(cpTerminationSection,visible,False);
    }
}
