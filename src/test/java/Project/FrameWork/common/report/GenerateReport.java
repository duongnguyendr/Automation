package Project.FrameWork.common.report;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;
import net.masterthought.cucumber.ReportParser;
import net.masterthought.cucumber.ReportResult;
import net.masterthought.cucumber.Reportable;
import net.masterthought.cucumber.json.Feature;

public class GenerateReport {
	private static Logger logger = Logger.getLogger(GenerateReport.class.getSimpleName());
	
	private static Configuration createConfiguration(File reportOutputDirectory, String projectName){
        Configuration configuration = new Configuration(reportOutputDirectory, projectName);
        String buildNumber = "1";
        boolean runWithJenkins = false;
        boolean parallelTesting = false;

        // optional configuration
        configuration.setParallelTesting(parallelTesting);
        configuration.setRunWithJenkins(runWithJenkins);
        configuration.setBuildNumber(buildNumber);

        // addidtional metadata presented on main page
        configuration.addClassifications("Platform", "Windows");
        configuration.addClassifications("Browser", "Chrome");
        configuration.addClassifications("Branch", "release/1.0");
        configuration.addClassifications("Created by","Duong Nguyen");

        return configuration;
    }

    public static Reportable GenerateMasterthoughtReport(String sExecuteTime){
        Reportable result = null;
        try{
            logger.info("START");
            File reportOutputDirectory = new File("Reports/HTMLReports/Report_"+sExecuteTime);
            List<String> jsonFiles = new ArrayList<String>();
            jsonFiles.add("./target/cucumber-report.json");
            String projectName = "BDD Automation Testing";
            Configuration configuration = createConfiguration(reportOutputDirectory, projectName);
            ReportBuilder reportBuilder = new ReportBuilder(jsonFiles, configuration);
            result = reportBuilder.generateReports();
            logger.info("DONE REPORT");
        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }

    public static ReportResult createReportDetail(String sExecuteTime){
        List<String> jsonFiles = new ArrayList<String>();
        jsonFiles.add("./target/cucumber-report.json");
        File reportOutputDirectory = new File("Reports/HTMLReports/Report_"+sExecuteTime);
        String projectName = "BDD Automation Testing";
        Configuration configuration = createConfiguration(reportOutputDirectory, projectName);
        ReportParser reportParser = new ReportParser(configuration);
        List<Feature> features = reportParser.parseJsonFiles(jsonFiles);
        ReportResult reportResult = new ReportResult(features, configuration.getSortingMethod());
        return reportResult;
    }
}
