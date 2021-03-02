package com.newgen.worksteps;

import com.newgen.iforms.EControl;
import com.newgen.iforms.FormDef;
import com.newgen.iforms.custom.IFormReference;
import com.newgen.iforms.custom.IFormServerEventHandler;
import com.newgen.reusableObject.Commons;
import com.newgen.utils.LogGen;
import com.newgen.utils.MailSetup;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TreasuryOfficerInitiator extends Commons implements IFormServerEventHandler {
    private Logger logger = LogGen.getLoggerInstance(TreasuryOfficerInitiator.class);
    @Override
    public void beforeFormLoad(FormDef formDef, IFormReference ifr) {
        try {
            cpFormLoadActivity(ifr);
        }
        catch (Exception e){
            logger.error("Exception-- "+ e.getMessage());
        }
    }

    @Override
    public String setMaskedValue(String s, String s1) {
        return s1;
    }

    @Override
    public JSONArray executeEvent(FormDef formDef, IFormReference iFormReference, String s, String s1) {
        return null;
    }

    @Override
    public String executeServerEvent(IFormReference ifr, String controlName, String eventName, String data) {
        try {
            switch (eventName){
                case formLoad:{}
                break;
                case onLoad:{}
                break;
                case onClick:{}
                break;
                case onChange:{
                    switch (controlName){
                        case onChangeProcess: {
                            selectMarketSheet(ifr);
                            break;
                        }
                    }
                }
                break;
                case custom:{}
                break;
                case onDone:{}
                break;
                case decisionHistory:{
                   if (getProcess(ifr).equalsIgnoreCase(commercialProcess))
                       setCpDecisionHistory(ifr);
                }
                break;
                case sendMail:{
                    if (getProcess(ifr).equalsIgnoreCase(commercialProcess))
                        cpMail(ifr);
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
            logger.info("Exception Occurred-- "+ e.getMessage());
        }
        return null;
    }

    private void cpMail(IFormReference ifr){
        String message = "A window open request for Commercial Paper has been Initiated with ref number "+getWorkItemNumber(ifr)+".";
        new MailSetup(ifr,getWorkItemNumber(ifr),getUsersMailsInGroup(ifr,groupName),empty,mailSubject,message);
    }
    private void cpFormLoadActivity(IFormReference ifr){
        hideProcessMarkets(ifr);
        hideCpSections(ifr);
        hideLandingMessageLabel(ifr);
        String sol = getSol(ifr);
        ifr.setValue(solLocal, sol);
        ifr.setValue(loginUserLocal,ifr.getUserName());
        ifr.setValue(currWsLocal, ifr.getActivityName());
        ifr.setValue(prevWsLocal, na);
        ifr.addItemInCombo(cpDecisionLocal,decSubmit,decSubmit);
        ifr.addItemInCombo(cpDecisionLocal,decDiscard,decDiscard);
        ifr.setStyle(cpLandingMsgSection,visible,True);
        ifr.setStyle(cpDecisionSection,visible,True);
        ifr.setStyle(cpMarketSection,visible,True);
        ifr.setStyle(cpSelectMarket,mandatory,True);
        ifr.setStyle(cpLandMsgLocal,mandatory,True);
    }

    @Override
    public JSONArray validateSubmittedForm(FormDef formDef, IFormReference iFormReference, String s) {
        return null;
    }

    @Override
    public String executeCustomService(FormDef formDef, IFormReference iFormReference, String s, String s1, String s2) {
        return null;
    }

    @Override
    public String getCustomFilterXML(FormDef formDef, IFormReference iFormReference, String s) {
        return null;
    }

    @Override
    public String generateHTML(EControl eControl) {
        return null;
    }

    @Override
    public String introduceWorkItemInWorkFlow(IFormReference iFormReference, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        return null;
    }
}
