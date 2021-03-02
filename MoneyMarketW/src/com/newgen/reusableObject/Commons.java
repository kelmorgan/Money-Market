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
        SimpleDateFormat sdf = new SimpleDateFormat(dbDateTimeFormat);
        try {
            Date d1 = sdf.parse(entryDate);
            Date d2 = sdf.parse(exitDate);

            long difference_In_Time = d2.getTime() - d1.getTime();
            long difference_In_Seconds = (difference_In_Time / 1000) % 60;
            long difference_In_Minutes = (difference_In_Time / (1000 * 60)) % 60;
            long difference_In_Hours = (difference_In_Time / (1000 * 60 * 60)) % 24;
            long difference_In_Days = (difference_In_Time / (1000 * 60 * 60 * 24)) % 365;
            logger.info("getTat method -- tat-- "+ difference_In_Days + " days, " + difference_In_Hours + " hours, " + difference_In_Minutes + " minutes, " + difference_In_Seconds + " seconds");
            // long difference_In_Years = (difference_In_Time / (1000l * 60 * 60 * 24 * 365));

            return  difference_In_Days + " days, " + difference_In_Hours + " hours, " + difference_In_Minutes + " minutes, " + difference_In_Seconds + " seconds";
        }
        catch (ParseException e) {
            e.printStackTrace();
            logger.error("Exception occurred in getTat method-- "+ e.getMessage());
        }
        return null;
    }
    private static void setDecisionHistory(IFormReference ifr, String staffId, String process, String marketType, String decision,
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
    public String getUsersMailsInGroup(IFormReference ifr, String groupName){
        String groupMail= "";
        try {
            DbConnect dbConnect = new DbConnect(ifr, new Query().getUsersInGroup(groupName));
            int count = dbConnect.getData().size();
            for (int i = 0; i < count; i++){
                String mail = dbConnect.getData().get(i).get(0)+endMail;
                groupMail = mail+","+groupMail;
            }
        } catch (Exception e){
            logger.error("Exception occurred in getUsersMailInGroup-- "+ e.getMessage());
            return null;
        }
        logger.info("getUsersMailsGroup method --mail of users-- "+groupMail.trim());
        return groupMail.trim();
    }
    public void setCpDecisionHistory (IFormReference ifr){
        String marketType = (String)ifr.getValue(cpSelectMarket);
        String remarks = (String)ifr.getValue(cpRemarksLocal);
        String entryDate = (String)ifr.getValue(entryDateLocal);
        String exitDate = getCurrentDateTime();

        setDecisionHistory(ifr,getLoginUser(ifr),cpProcessName,marketType,getCpDecision(ifr),remarks,getActivityName(ifr),entryDate,exitDate,getTat(entryDate,exitDate));
        ifr.setValue(decHisFlagLocal,flag);
    }
    public String getProcess(IFormReference ifr){
        return (String)ifr.getValue(selectProcess);
    }
    public String getCurrentDateTime (String format){
        return new SimpleDateFormat(format).format(new Date());
    }
    public String getCurrentDateTime (){
        return new SimpleDateFormat(dbDateTimeFormat).format(new Date());
    }
    public String getCpDecision (IFormReference ifr){
        return (String) ifr.getValue(cpDecisionLocal);
    }
    public String getWorkItemNumber (IFormReference ifr){
        return (String)ifr.getControlValue(wiNameLocal);
    }
    public String getLoginUser(IFormReference ifr){
        return ifr.getUserName();
    }
    public String getActivityName (IFormReference ifr){
        return ifr.getActivityName();
    }
    public String getPrevWs (IFormReference ifr){return (String) ifr.getValue(prevWsLocal);}
    public void setGenDetails(IFormReference ifr){
        ifr.setValue(solLocal,getSol(ifr));
        ifr.setValue(loginUserLocal,getLoginUser(ifr));
    }
    public void hideProcess(IFormReference ifr){
        ifr.setTabStyle(processTabName,commercialTab,visible,False);
        ifr.setTabStyle(processTabName,treasuryTab,visible,False);
        ifr.setTabStyle(processTabName,omoTab,visible,False);
    }
    public void hideDashBoardTab (IFormReference ifr){
        ifr.setTabStyle(processTabName,dashboardTab,visible,False);
    }
    public void selectProcessSheet(IFormReference ifr){
        logger.info("selectProcessSheet Method: "+getProcess(ifr));
        if(getProcess(ifr).equalsIgnoreCase(commercialProcess)) {
            ifr.setTabStyle(processTabName, commercialTab, visible, True);
            ifr.setTabStyle(processTabName, treasuryTab, visible, False);
            ifr.setTabStyle(processTabName, omoTab, visible, False);
        }
        else if (getProcess(ifr).equalsIgnoreCase(treasuryProcess)) {
            ifr.setTabStyle(processTabName, treasuryTab, visible, True);
            ifr.setTabStyle(processTabName, commercialTab, visible, False);
            ifr.setTabStyle(processTabName, omoTab, visible, False);
        }
        else if (getProcess(ifr).equalsIgnoreCase(omoProcess)){
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
    public void showSelectedProcessSheet(IFormReference ifr){
        logger.info("showSelectedProcessMethod -- selected process -- "+getProcess(ifr));
        if(getProcess(ifr).equalsIgnoreCase(commercialProcess)) {
            ifr.setTabStyle(processTabName, commercialTab, visible, True);
            ifr.setTabStyle(processTabName, treasuryTab, visible, False);
            ifr.setTabStyle(processTabName, omoTab, visible, False);
            ifr.setTabStyle(processTabName,dashboardTab,visible,False);
        }
        else if (getProcess(ifr).equalsIgnoreCase(treasuryProcess)) {
            ifr.setTabStyle(processTabName, treasuryTab, visible, True);
            ifr.setTabStyle(processTabName, commercialTab, visible, False);
            ifr.setTabStyle(processTabName, omoTab, visible, False);
            ifr.setTabStyle(processTabName,dashboardTab,visible,False);
        }
        else if (getProcess(ifr).equalsIgnoreCase(omoProcess)){
            ifr.setTabStyle(processTabName,omoTab,visible,True);
            ifr.setTabStyle(processTabName,commercialTab,visible,False);
            ifr.setTabStyle(processTabName,treasuryTab,visible,False);
            ifr.setTabStyle(processTabName,dashboardTab,visible,False);
        }
    }
    public String getSol (IFormReference ifr){
        try { return new DbConnect(ifr, new Query(getLoginUser(ifr)).getSolQuery()).getData().get(0).get(0); }
        catch (Exception e){ logger.error("Exception occurred in getSol Method-- "+e.getMessage());return  null;}
    }
    public void hideCpSections (IFormReference ifr){
      ifr.setStyle(cpBranchPriSection,visible,False);
      ifr.setStyle(cpBranchSecSection,visible,False);
      ifr.setStyle(cpLandingMsgSection,visible,False);
      ifr.setStyle(cpMarketSection,visible,False);
      ifr.setStyle(cpPrimaryBidSection,visible,False);
      ifr.setStyle(cpProofOfInvestSection,visible,False);
      ifr.setStyle(cpTerminationSection,visible,False);
      ifr.setStyle(cpDecisionSection,visible,False);
      ifr.setStyle(cpTreasuryPriSection,visible,False);
      ifr.setStyle(cpTreasurySecSection,visible,False);
      ifr.setStyle(cpTreasuryOpsPriSection,visible,False);
      ifr.setStyle(cpTreasuryOpsSecSection,visible,False);
      ifr.setStyle(cpPostSection,visible,False);
    }
    public void disableCpSections (IFormReference ifr){
        ifr.setStyle(cpBranchPriSection,disable,True);
        ifr.setStyle(cpBranchSecSection,disable,True);
        ifr.setStyle(cpLandingMsgSection,disable,True);
        ifr.setStyle(cpMarketSection,disable,True);
        ifr.setStyle(cpPrimaryBidSection,disable,True);
        ifr.setStyle(cpProofOfInvestSection,disable,True);
        ifr.setStyle(cpTerminationSection,disable,True);
        ifr.setStyle(cpDecisionSection,disable,True);
        ifr.setStyle(cpTreasuryPriSection,disable,True);
        ifr.setStyle(cpTreasurySecSection,disable,True);
        ifr.setStyle(cpTreasuryOpsPriSection,disable,True);
        ifr.setStyle(cpTreasuryOpsSecSection,disable,True);
        ifr.setStyle(cpPostSection,disable,True);
    }
    public void hideShowLandingMessageLabel(IFormReference ifr,String state){ifr.setStyle(landMsgLabelLocal,visible,state);}
}
