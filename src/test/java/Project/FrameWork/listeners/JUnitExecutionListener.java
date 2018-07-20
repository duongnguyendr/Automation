package Project.FrameWork.listeners;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.RunListener;

import Project.FrameWork.base.BaseTest;
import Project.FrameWork.common.Generic;
import Project.FrameWork.common.report.EmailReport;
import Project.FrameWork.common.report.GenerateReport;
import Project.FrameWork.common.report.PDFReport;
import net.masterthought.cucumber.ReportResult;
import net.masterthought.cucumber.Reportable;

public class JUnitExecutionListener extends RunListener{
	private static Logger logger = Logger.getLogger(JUnitExecutionListener.class.getSimpleName());
	public static File sHtmlReports;
    public static File sPdfReports;
    public static PDFReport pdf;
    public static Map<Integer, List<String>> excelDatas;
    public static int failCount;
	@Override
    public void testStarted(Description description) {
		// Do something before start test suite
		try {
			initReportsFolder();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void initReportsFolder() throws IOException {
        try {
            System.out.printf("JUNIT");
            logger.info("***** Start initialize Report folders *****\n");
            sHtmlReports = new File(Generic.sDirPath + "/Reports/HTMLReports");
            sPdfReports = new File(Generic.sDirPath + "/Reports/PDFReports");
            excelDatas = new HashMap<Integer, List<String>>();
            if (!sHtmlReports.exists()) {
                FileUtils.forceMkdir(sHtmlReports);
            }
            if (!sPdfReports.exists()) {
                FileUtils.forceMkdir(sPdfReports);
            }
        } catch (Exception e) {
            logger.info("***** Unable to create reportLB folders: " + e.getMessage());
        }
    }
	@Override
	public void testRunFinished(Result result) {
		// Do something after run test suite
	
	logger.info("***** Generating the Master thought Report *****");
    // Generate HTML Report
    String timeStamp = Generic.GetTimeStampValue();
    Reportable results = GenerateReport.GenerateMasterthoughtReport(timeStamp);
    File sDateReports = new File(Generic.sDirPath+"/Reports/ImageReports/" + timeStamp);
    try {
        logger.info("JUNIT");
        if (!sDateReports.exists()) {
            FileUtils.forceMkdir(sDateReports);
        }
        sPdfReports = new File(sPdfReports+"/PDFReports_"+ timeStamp +".pdf");
        // Create Bar char
        Generic.getFeaturesChart(results.getPassedFeatures(), results.getFailedFeatures(),0, timeStamp);
        // Create Pie char
        Generic.getScenariosChart(results.getPassedScenarios(), results.getFailedScenarios(),0, timeStamp);
        // Create report result
        ReportResult reportResult= GenerateReport.createReportDetail(timeStamp);
        
        // Generate PDF File
        pdf = new PDFReport();
        pdf.toExecute(sPdfReports, timeStamp, reportResult);
        //Send mail
        Generic.writeToExcelfile(excelDatas, Generic.sDirPath+"/Reports/FailCase" + timeStamp + ".xlsx");
        logger.info("Send to email = " + BaseTest.sToEmail);
        logger.info("CC to email = " + BaseTest.sCcEmail);
        System.out.println("Send to email = " + BaseTest.sToEmail);
        System.out.println("CC to email = " + BaseTest.sCcEmail);
        if((null != BaseTest.sToEmail) || (null != BaseTest.sCcEmail)){
            logger.info("Send Report Mail.");
            EmailReport.sendMail(sPdfReports,timeStamp, reportResult);
        }
    }catch (Exception ex){
        ex.printStackTrace();
    }

    //Send Email

    logger.info("***** Junit has finished, the execution *****");
	}
}
