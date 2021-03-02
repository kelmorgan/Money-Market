package com.newgen.utils;


import com.newgen.iforms.custom.IFormReference;
import org.apache.log4j.Logger;

public class MailSetup {
    private static Logger logger = LogGen.getLoggerInstance(MailSetup.class);

    private String wiName;
    private String sendMail;
    private String copyMail;
    private String mailSubject;
    private String mailMessage;

    public MailSetup (IFormReference ifr, String wiName, String sendMail, String copyMail,String mailSubject, String mailMessage){
    this.wiName = wiName;
    this.sendMail = sendMail;
    this.copyMail = copyMail;
    this.mailSubject = mailSubject;
    setMailMessage(mailMessage);
    sendMail(ifr);
    }
    private void setMailMessage(String mailMessage) {
        this.mailMessage = "<html>" +
                "<body>" +
                "Dear User, <br>" +
                "<br>"+mailMessage+"<br>" +
                "<br> Please do not reply, this is a system generated mail. <br>" +
                "</body>" +
                "</html>";
    }

    private void sendMail (IFormReference ifr){
        DbConnect dbConnect = new DbConnect(ifr,new Query(wiName,sendMail,copyMail,mailSubject,mailMessage).mailQuery());
        int result = dbConnect.saveQuery();
        logger.info("result-- "+ result);
        if (result >= 0)
            logger.info("Mail sent successfully");
    }

}
