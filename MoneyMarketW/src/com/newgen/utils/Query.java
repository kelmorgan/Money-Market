package com.newgen.utils;

public class Query {
    private String wiName;
    private String sendMail;
    private String copyMail;
    private String mailSubject;
    private String mailMessage;
    private String userName;

    public Query (){}
    public Query (String userName){
        this.userName = userName;
    }
    public Query (String wiName, String sendMail, String copyMail, String mailSubject, String mailMessage){
        this.wiName = wiName;
        this.sendMail = sendMail;
        this.copyMail = copyMail;
        this.mailSubject = mailSubject;
        this.mailMessage = mailMessage;
    }
    public String getSolQuery(){
        return "select sole_id from usr_0_fbn_usr_branch_mapping where upper(user_id) = upper('"+userName+"')";
    }
    public String getUsersInGroup (String groupName){
        return "select username from pdbuser where userindex in (select userindex from pdbgroupmember where groupindex = (select groupindex from PDBGroup where GroupName='"+groupName+"'))";
    }
    public String mailQuery (){
        return "insert into wfmailqueuetable (" +
                "mailfrom," +
                "mailto," +
                "mailcc," +
                "mailsubject," +
                "mailmessage," +
                "mailcontenttype," +
                "mailpriority," +
                "insertedby," +
                "mailactiontype," +
                "insertedtime," +
                "processdefid," +
                "processinstanceid," +
                "workitemid," +
                "activityid," +
                "mailstatus) " +
                "values (" +
                "'"+LoadProp.mailFrom+"'," +
                "'"+sendMail+"'," +
                "'"+copyMail+"'," +
                "'"+mailSubject+"'," +
                "'"+mailMessage+"'," +
                "'text/html;charset=UTF-8'," +
                "1," +
                "'System'," +
                "'TRIGGER'," +
                "SYSDATE," +
                ""+LoadProp.processDefId+"," +
                "'"+wiName+"'," +
                "1," +
                "1," +
                "'N')";
    }
}
