package com.newgen.worksteps;

import com.newgen.iforms.EControl;
import com.newgen.iforms.FormDef;
import com.newgen.iforms.custom.IFormReference;
import com.newgen.iforms.custom.IFormServerEventHandler;
import com.newgen.reusableObject.Commons;
import com.newgen.reusableObject.CommonsI;
import com.newgen.utils.LogGen;
import com.newgen.utils.MailSetup;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TreasuryOfficerInitiator extends Commons implements IFormServerEventHandler, CommonsI {
    private Logger logger = LogGen.getLoggerInstance(TreasuryOfficerInitiator.class);
    @Override
    public void beforeFormLoad(FormDef formDef, IFormReference ifr) {
        try { cpFormLoadActivity(ifr);}
        catch (Exception e){ logger.error("Exception-- "+ e.getMessage());}
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
                case onClick:{
                    switch (controlName){
                        case goToDashBoard:{
                            backToDashboard(ifr);
                            clearFields(ifr,new String[] {selectProcessLocal});
                            if (getProcess(ifr).equalsIgnoreCase(commercialProcess))
                                cpBackToDashboard(ifr);
                            break;
                        }
                    }
                }
                break;
                case onChange:{
                    switch (controlName){
                        case onChangeProcess: {
                            selectProcessSheet(ifr);
                            if (getProcess(ifr).equalsIgnoreCase(commercialProcess)) cpInitiatorFormLoad(ifr);
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
                        cpSendMail(ifr);
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
            logger.info("Exception Occurred-- "+ e.getMessage());
        }
        return null;
    }

    private void cpInitiatorFormLoad (IFormReference ifr){
        setMandatory(ifr,new String [] {cpSelectMarketLocal,cpLandMsgLocal,cpDecisionLocal});
    }

    @Override
    public void cpSendMail(IFormReference ifr){
        String message = "A window open request for Commercial Paper has been Initiated with ref number "+getWorkItemNumber(ifr)+".";
        new MailSetup(ifr,getWorkItemNumber(ifr),getUsersMailsInGroup(ifr,groupName),empty,mailSubject,message);
    }
    @Override
    public void cpFormLoadActivity(IFormReference ifr){
        hideProcess(ifr);
        hideCpSections(ifr);
        hideShowLandingMessageLabel(ifr,False);
        hideShowBackToDashboard(ifr,False);
        setGenDetails(ifr);
        cpSetDecision(ifr);
        clearFields(ifr,new String [] {selectProcessLocal});
        setMandatory(ifr, new String[]{selectProcessLocal});
        ifr.setValue(currWsLocal, getActivityName(ifr));
        ifr.setValue(prevWsLocal, na);
        setVisible(ifr, new String[]{cpLandingMsgSection, cpDecisionSection, cpMarketSection});
    }

    @Override
    public void cpSetDecision(IFormReference ifr) {
        ifr.addItemInCombo(cpDecisionLocal,decSubmit,decSubmit);
        ifr.addItemInCombo(cpDecisionLocal,decDiscard,decDiscard);
    }


    private void cpBackToDashboard(IFormReference ifr) {
        undoMandatory(ifr,new String [] {cpSelectMarketLocal,cpLandMsgLocal,cpDecisionLocal});
        clearFields(ifr,new String [] {cpSelectMarketLocal,cpLandMsgLocal,cpDecisionLocal});
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
