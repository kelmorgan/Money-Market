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

public class TreasuryOfficerVerifier extends Commons implements IFormServerEventHandler {
    private static Logger logger = LogGen.getLoggerInstance(TreasuryOfficerVerifier.class);
    @Override
    public void beforeFormLoad(FormDef formDef, IFormReference ifr) {
        if (!getProcess(ifr).equalsIgnoreCase(empty))
            showSelectedProcessSheet(ifr);
        if (getProcess(ifr).equalsIgnoreCase(commercialProcess))
            cpFormLoadActivity(ifr);
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
                }
                break;
                case custom:{}
                break;
                case onDone:{}
                break;
                case decisionHistory:{
                    if (getProcess(ifr).equalsIgnoreCase(commercialProcess)) setCpDecisionHistory(ifr);
                }
                break;
                case sendMail:{
                    if (getProcess(ifr).equalsIgnoreCase(commercialProcess)) sendCpMail(ifr);
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
            logger.info("Exception Occurred-- "+ e.getMessage());
        }
        return null;
    }

    private void sendCpMail(IFormReference ifr) {
        String message;
        if (getPrevWs(ifr).equalsIgnoreCase(treasuryOfficerInitiator)){
            if (getCpDecision(ifr).equalsIgnoreCase(decApprove)) {
                message = "Landing Message has been approved by the treasury officer verifier with ref "+getWorkItemNumber(ifr)+". Login to setup market.";
                new MailSetup(ifr, getWorkItemNumber(ifr), getUsersMailsInGroup(ifr, groupName), empty, mailSubject, message);
            }
            else {
                message = "Landing Message has been rejected by the treasury officer verifier with ref "+getWorkItemNumber(ifr)+". Login to make necessary corrections.";
                new MailSetup(ifr, getWorkItemNumber(ifr), getUsersMailsInGroup(ifr, groupName), empty, mailSubject, message);
            }
    }
    }
    private void cpFormLoadActivity(IFormReference ifr){
        hideCpSections(ifr);
        hideShowLandingMessageLabel(ifr,False);
        setGenDetails(ifr);
        disableCpSections(ifr);
        ifr.addItemInCombo(cpDecisionLocal, decApprove, decApprove);
        ifr.addItemInCombo(cpDecisionLocal, decReject, decReject);
        if (getPrevWs(ifr).equalsIgnoreCase(treasuryOfficerInitiator)) {
            ifr.setStyle(cpLandingMsgSection, visible, True);
            ifr.setStyle(cpDecisionSection, visible, True);
            ifr.setStyle(cpDecisionSection, disable, False);
            ifr.setStyle(cpMarketSection, visible, True);
            ifr.setStyle(cpDecisionLocal, mandatory, True);
        }
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
