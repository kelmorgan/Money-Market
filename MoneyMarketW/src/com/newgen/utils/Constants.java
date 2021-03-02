package com.newgen.utils;

public interface Constants {
	String ProcessName = "MoneyMarketW";

	//WorkSteps
	String treasuryOfficerInitiator = "Treasury_Officer_Initiator";
	String treasuryOfficerVerifier = "Treasury_Officer_Verifier";
	String treasuryOfficerMaker = "Treasury_Officer_Maker";
	String treasuryOpsVerifier = "TreasuryOps_Verifier";
	String treasuryOpsMature = "TreasuryOps_Mature";
	String awaitingMaturityUtility = "AwaitingMaturity_Utility";
	String treasuryOpsMatureOnMaturity = "TreasuryOps_Mature_on_maturity";
	String branchInitiator = "Branch_Initiator";
	String branchVerifier = "Branch_Verifier";
	String branchException = "Branch_Exception";
	String rpcVerifier = "RPC_Verifier";
	String treasuryOpsFailed = "TreasuryOps_Failed";
	String awaitingMaturity = "AwaitingMaturity";
	String treasuryOpsSuccessful = "TreasuryOps_Successful";
	String discardWs = "Discard";
	String exit = "Exit";
	String query = "Query";
	// Please input workSteps between comment bracket

	//process Ids
	String processTabName = "tab2";
	String dashboardTab = "0";
	String commercialTab = "1";
	String treasuryTab = "2";
	String omoTab = "3";
	String selectProcess = "g_select_market";
	String commercialProcess = "cp_market";
	String treasuryProcess = "tb_market";
	String omoProcess = "omo_market";
	String moneyMarketSection = "g_moneyMarket_section";
	String solLocal = "g_sol";
	String loginUserLocal ="g_loginUser";
	String currWsLocal = "g_currWs";
	String prevWsLocal = "g_prevWs";
	String cpBranchPriSection = "cp_branchPm_section";
	String cpLandingMsgSection = "cp_landingMsg_section";
	String cpMarketSection = "cp_market_section";
	String cpPrimaryBidSection = "cp_primaryBid_section";
	String cpTerminationSection = "cp_term_section";
	String cpPoiSection = "cp_poi_section";
	String cpDecisionSection = "cp_dec_section";
	String cpTreasuryPriSetupSection = "cp_pmTreasury_section";
	String cpDecisionLocal = "cp_decision";
	String wiNameLocal ="WorkItemName";
	String decisionHisTable = "g_decisionHistory";
	String dhRowStaffId = "Staff ID";
	String dhRowProcess = "Process";
	String dhRowDecision = "Decision";
	String dhRowRemarks = "Remarks";
	String dhRowPrevWs = "Previous Workstep";
	String dhRowEntryDate = "Entry Date";
	String dhRowExitDate = "Exit Date";
	String dhRowTat = "TAT";
	String dhRowMarketType = "Market Type";
	String selectCpMarket = "cp_select_market";
	String cpRemarksLocal = "cp_remarks";
	String entryDateLocal = "EntryDateTime";
	String decHisFlagLocal = "g_decisionHistoryFlag";

	//common variables
	String visible = "visible";
	String disable = "disable";
	String mandatory = "mandatory";
	String True = "true";
	String False = "false";
	String na = "N.A";
	String decDiscard = "Discard";
	String decSubmit = "Submit";
	String decApprove = "Approve";
	String decReject = "Reject";
	String cpProcessName = "Commercial Paper";
	String dateTimeFormat = "dd/MM/yyyy HH:mm:ss";
	String flag = "Y";

	//eventName/controlName
	String formLoad = "formLoad";
	String onClick = "onClick";
	String onChange = "onChange";
	String onDone = "onDone";
	String onLoad = "onLoad";
	String custom = "custom";
	String sendMail = "sendMail";
	String onChangeProcess = "onChangeProcess";
	String decisionHistory = "decisionHistory";

	//config
	String logPath = "nglogs/NGF_Logs/MoneyMarket/";
	String configPath = "/was/IBM/WebSphere/AppServer/profiles/AppSrv01/installedApps/HO-IBPSUTIL01Cell01/MoneyMarketW.ear/MoneyMarketW.war/config/moneymarket.properties";
	String mailFromField ="MAILFROM";
	String processDefIdField = "PROCESSDEFID";
}
