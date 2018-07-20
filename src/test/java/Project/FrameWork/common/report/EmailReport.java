package Project.FrameWork.common.report;

import Project.FrameWork.common.Generic;

import Project.FrameWork.base.BaseTest;
import net.masterthought.cucumber.ReportResult;
import net.masterthought.cucumber.json.Feature;
import org.apache.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

/**
 * Created by tan.pham on 9/8/2017.
 */
public class EmailReport {
    private static Logger logger = Logger.getLogger(EmailReport.class.getSimpleName());
    private static String sExecutionDate;

    private static String createTHTable(int colspan, String nameHeader){
        StringBuilder sb = new StringBuilder();
        if(colspan ==0){
            sb.append("<th style=\"border: 1px solid black;border-collapse: collapse; padding: 5px;text-align: center;\">" + nameHeader +"</th>");
        }else{
            sb.append("<th style=\"border: 1px solid black;border-collapse: collapse; padding: 5px;text-align: center;\" colspan = " + "\""+ colspan + "\""+">" + nameHeader +"</th>");
        }

        return sb.toString();
    }

    private static String createHeaderRow(){
        StringBuilder sb = new StringBuilder();
        sb.append("<tr style=\"border: 1px solid black;border-collapse: collapse; padding: 5px;text-align: center;\">");
        sb.append(createTHTable(0,""));
        sb.append(createTHTable(6,"Steps"));
        sb.append(createTHTable(3,"Scenario"));
        sb.append("</tr>");
        return sb.toString();
    }

    private static String createTDTable(String detailValueColumn){
        StringBuilder sb = new StringBuilder();
        sb.append("<td style=\"border: 1px solid black;border-collapse: collapse;padding: 5px;text-align: left;\">" + detailValueColumn + "</td>");
        return sb.toString();
    }

    private static String createHeaderDetailRow(){
        StringBuilder sb = new StringBuilder();
        sb.append("<tr style=\"border: 1px solid black;border-collapse: collapse; padding: 5px;text-align: center;\">");
        sb.append(createTHTable(0,"Features"));
        sb.append(createTHTable(0,"Passed"));
        sb.append(createTHTable(0,"Failed"));
        sb.append(createTHTable(0,"Skipped"));
        sb.append(createTHTable(0,"Pending"));
        sb.append(createTHTable(0,"Undefined"));
        sb.append(createTHTable(0,"Total"));
        sb.append(createTHTable(0,"Passed"));
        sb.append(createTHTable(0,"Failed"));
        sb.append(createTHTable(0,"Total"));
        sb.append("</tr>");
        return sb.toString();
    }

    private static String createDetailRow(ReportResult reportResult){
        StringBuilder sb = new StringBuilder();
        //Create detail row
        int totalFeatures = reportResult.getAllFeatures().size();
        for(int i=0; i<totalFeatures ;i++) {
            Feature featureItem = reportResult.getAllFeatures().get(i);
            sb.append("<tr>");
            sb.append(createTDTable(featureItem.getName()));
            sb.append(createTDTable(String.valueOf(featureItem.getPassedSteps())));
            sb.append(createTDTable(String.valueOf(featureItem.getFailedSteps())));
            sb.append(createTDTable(String.valueOf(featureItem.getSkippedSteps())));
            sb.append(createTDTable(String.valueOf(featureItem.getPendingSteps())));
            sb.append(createTDTable(String.valueOf(featureItem.getUndefinedSteps())));
            sb.append(createTDTable(String.valueOf(featureItem.getSteps())));
            sb.append(createTDTable(String.valueOf(featureItem.getPassedScenarios())));
            sb.append(createTDTable(String.valueOf(featureItem.getFailedScenarios())));
            sb.append(createTDTable(String.valueOf(featureItem.getScenarios())));
            sb.append("</tr>");
        }
        return sb.toString();
    }

    private static String createTotalRow(ReportResult reportResult){
        long featurePassSteps = 0L;
        long featureFailSteps = 0L;
        long featureSkipSteps = 0L;
        long featurePendingSteps = 0L;
        long featureUndefineSteps = 0L;
        long featureTotalSteps = 0L;
        long scenarioPass = 0L;
        long scenarioFail = 0L;
        long scenarioTotal = 0L;

        int totalFeatures = reportResult.getAllFeatures().size();
        for(int i=0; i<totalFeatures ;i++) {
            Feature featureItem = reportResult.getAllFeatures().get(i);
            featurePassSteps+= featureItem.getPassedSteps();
            featureFailSteps += featureItem.getFailedSteps();
            featureSkipSteps += featureItem.getSkippedSteps();
            featurePendingSteps += featureItem.getPendingSteps();
            featureUndefineSteps += featureItem.getUndefinedSteps() ;
            featureTotalSteps += featureItem.getSteps();
            scenarioPass += featureItem.getPassedScenarios();
            scenarioFail += featureItem.getFailedScenarios();
            scenarioTotal += featureItem.getScenarios();
        }
        //Create total row
        StringBuilder sb = new StringBuilder();
        sb.append("<tr>");
        sb.append(createTDTable(""));
        sb.append(createTDTable(String.valueOf(featurePassSteps)));
        sb.append(createTDTable(String.valueOf(featureFailSteps)));
        sb.append(createTDTable(String.valueOf(featureSkipSteps)));
        sb.append(createTDTable(String.valueOf(featurePendingSteps)));
        sb.append(createTDTable(String.valueOf(featureUndefineSteps)));
        sb.append(createTDTable(String.valueOf(featureTotalSteps)));
        sb.append(createTDTable(String.valueOf(scenarioPass)));
        sb.append(createTDTable(String.valueOf(scenarioFail)));
        sb.append(createTDTable(String.valueOf(scenarioTotal)));
        sb.append("</tr>");

        return sb.toString();
    }


    public static void sendMail(File pdfReports, String timeStamp, ReportResult reportResult) {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        sExecutionDate = simpleDateFormat.format(date);
        Properties properties = new Properties();
        String jenkinsUrl;
        String dirPath = System.getProperty("user.dir").replace("\\", "\\\\");
        String [] data = dirPath.split("\\\\");
        String jobNameJenkins = data[data.length - 1].trim();
        if(BaseTest.baseUrl.equalsIgnoreCase("autoqa.auvenir.com") || BaseTest.baseUrl.equalsIgnoreCase("auto.auvenir.com")){
            jenkinsUrl = "http://jenkins-auvenir-automation.titancorpvn.com:8080/job/" + jobNameJenkins + "/ws/Reports/HTMLReports/Report_" + timeStamp + "/cucumber-html-reports/overview-features.html";
        }else{
            jenkinsUrl = "https://jenkins.auvenir.com/job/" + jobNameJenkins + "/ws/Reports/HTMLReports/Report_" + timeStamp + "/cucumber-html-reports/overview-features.html";
        }

        //Create detail table
        StringBuilder sbDetailTable = new StringBuilder();
        sbDetailTable.append("<table style=\"border: 1px solid black1;border-collapse: collapse; width : 100%\">");
        sbDetailTable.append(createHeaderRow());
        sbDetailTable.append(createHeaderDetailRow());
        sbDetailTable.append(createDetailRow(reportResult));
        sbDetailTable.append(createTotalRow(reportResult));
        sbDetailTable.append("</table>");

        String featureChartImage = System.getProperty("user.dir") + "\\Reports\\ImageReports\\" + timeStamp + "\\FeaturesChart" + "_" + timeStamp + ".png";
        String scenarioChartImage = System.getProperty("user.dir") + "\\Reports\\ImageReports\\" + timeStamp + "\\ScenariosChart" + "_" + timeStamp + ".png";
        // create browser follow pie chat row
        StringBuilder sbBrowserImageRow = new StringBuilder();
        sbBrowserImageRow.append("<tr><td>" + "Feature" + "&nbsp;&nbsp;&nbsp;</td>");
        sbBrowserImageRow.append("&nbsp;&nbsp;&nbsp;");
        sbBrowserImageRow
                .append("<td><img src=\""+ featureChartImage +"\" style=\"height:200px; width: 200px; outline: thin solid;\"></td>");
        sbBrowserImageRow
                .append("<td><img src=\""+ scenarioChartImage +"\" style=\"height:200px; width: 200px; outline: thin solid;\"></td>");
        sbBrowserImageRow.append("</tr>");

        String message =
                "<p>Team,</p><div style=\"font-family:Verdana;\">Find the tests automation execution status as below. For detail information, find the attached pdf file.</div><p></p><p></p><p></p><p></p>" /*+ "<p><div style=\"font-family:Verdana;\"><b> EXECUTION SUMMARY : </b></div></p>" + "<table bgcolor=\"#BDE4F6\" style=\"border-radius: 20px; padding: 25px;\">"*/
                        /*+ sbBrowserImageRow
                        .toString()*/ /*+ "</table><p></p><p></p><p></p><p></p>"*/ + "<p><div style=\"font-family:Verdana;\"><b> EXECUTION SUMMARY FOR BROWSER: " + Generic.sBrowser.toUpperCase() + "</b></div></p>"
                        + sbDetailTable.toString()
                        + "<p></p><div style=\"font-family:Verdana;\">We can review detailed HTML report: </div><p></p>"
                        +"https://"+ BaseTest.baseUrl
                        +"<a href=\""+jenkinsUrl+"\">Automation HTML Report<br/></a>"
//                        +"<a href=\"http://jenkins.auvenir.com\">Automation HTML Report<br/></a>"
                        + "<p></p><div style=\"font-family:Verdana;\">Regards,</div><p></p>" + "<div style=\"font-family:Verdana;\">Automation " +
                        "Team</div>";
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        properties.put("mail.debug", "true");
        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(Generic.getConfigValue(Generic.convertDirectory(Generic.PROPERTIES_FILE),"FROM_EMAILID"),
                                                  Generic.getConfigValue(Generic.convertDirectory(Generic.PROPERTIES_FILE),"FROM_PWD"));
            }
        });
        try {
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress("chr.auditor01.auvenir@gmail.com"));
            if(null != BaseTest.sToEmail) {
                msg.setRecipients(Message.RecipientType.TO, BaseTest.sToEmail);
            }
            if(null != BaseTest.sCcEmail) {
                msg.setRecipients(Message.RecipientType.CC, BaseTest.sCcEmail);
            }
            String prefixProtocol = "";
            if (prefixProtocol == "") {
                prefixProtocol = "https://";
            }

            String baseUrlRun = prefixProtocol + BaseTest.baseUrl;

            msg.setSubject("Auvenir Execution Report on " + baseUrlRun + " " + sExecutionDate);
            msg.setSentDate(new Date());
            Multipart multipart = new MimeMultipart();
            MimeBodyPart textPart = new MimeBodyPart();
            textPart.setContent(message, "text/html");

            multipart.addBodyPart(textPart);
            MimeBodyPart attachementPart = new MimeBodyPart();
            attachementPart.attachFile(pdfReports);

            File featureChartImageFile = new File( System.getProperty("user.dir") + "\\Reports\\ImageReports\\" + timeStamp + "\\FeaturesChart" + "_" + timeStamp + ".png");
            MimeBodyPart featureChartAttachment = new MimeBodyPart();
            featureChartAttachment.attachFile(featureChartImageFile);

            File scenarioChartImageFile =  new File(System.getProperty("user.dir") + "\\Reports\\ImageReports\\" + timeStamp + "\\ScenariosChart" + "_" + timeStamp + ".png");
            MimeBodyPart scenarioChartAttachment = new MimeBodyPart();
            scenarioChartAttachment.attachFile(scenarioChartImageFile);
            if (!Generic.excelFile.equals("")){
                File failCaseExcelFile =  new File(Generic.excelFile);
                MimeBodyPart failCaseExcelAttachment = new MimeBodyPart();
                failCaseExcelAttachment.attachFile(failCaseExcelFile);
                multipart.addBodyPart(failCaseExcelAttachment);
            }
            multipart.addBodyPart(attachementPart);
            multipart.addBodyPart(featureChartAttachment);
            multipart.addBodyPart(scenarioChartAttachment);
            msg.setContent(multipart);
            Transport.send(msg);
            logger.info(" ==== Mail Sent Successfully ====");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}