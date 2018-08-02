package com.gvi.project.framework.common;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

public class Generic {
	private static Logger logger = Logger.getLogger(Generic.class.getSimpleName());
	public static String sDirPath = System.getProperty("user.dir");
	public final static String PROPERTIES_FILE = sDirPath + "/TestBDD.properties";
	public static String sBrowser = "Chrome";
	private static String OS = System.getProperty("os.name");
	public static String excelFile = "";
	private static XSSFWorkbook workbook = new XSSFWorkbook();

	public static String GetTimeStampValue() {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd HH_mm_ss_SSS");
		String systime = sdf.format(new Date());
		systime = systime.replace(":", "");
		systime = systime.replace("-", "");
		return systime;
	}

	public static void getFeaturesChart(int iPassCount, int iFailCount, int iSkippedCount, String timeStamp) {
		getPieChart(iPassCount, iFailCount, iSkippedCount, timeStamp, "Features");
	}
	
	public static void getScenariosChart(int iPassCount, int iFailCount, int iSkippedCount, String timeStamp) {
        getPieChart(iPassCount, iFailCount, iSkippedCount, timeStamp, "Scenarios");
    }

	private static void getPieChart(int iPassCount, int iFailCount, int iSkippedCount, String timeStamp,
			String nameChart) {
		DefaultPieDataset pieDataset = new DefaultPieDataset();
		pieDataset.setValue("FAIL", new Integer(iFailCount));
		// pieDataset.setValue("SKIP", new Integer(iSkippedCount));
		pieDataset.setValue("PASS", new Integer(iPassCount));

		JFreeChart piechart = ChartFactory.createPieChart(nameChart, pieDataset, true, true, false);
		PiePlot plot = (PiePlot) piechart.getPlot();

		plot.setSectionPaint("FAIL", Color.RED);
		// plot.setSectionPaint("SKIP", Color.ORANGE);
		plot.setSectionPaint("PASS", new Color(192 * 85 + 192 * 104 + 192 * 47));
		plot.setBackgroundPaint(new Color(192 * 65536 + 192 * 256 + 192));
		plot.setExplodePercent("FAIL", 0.05);
		plot.setSimpleLabels(true);
		plot.setSectionOutlinesVisible(true);

		PieSectionLabelGenerator gen = new StandardPieSectionLabelGenerator("{0}: {1} ({2})", new DecimalFormat("0"),
				new DecimalFormat("0%"));
		plot.setLabelGenerator(gen);
		plot.setLabelFont(new Font("SansSerif", Font.BOLD, 12));
		try {
			ChartUtilities.saveChartAsJPEG(new File(System.getProperty("user.dir") + "/Reports/ImageReports/"
					+ timeStamp + "/" + nameChart + "Chart" + "_" + timeStamp + ".png"), piechart, 400, 400);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static String  convertDirectory(String directory){
        if (OS.contains("Windows")){
            logger.info("***** We are working on Windows environment: " + OS);
            directory = directory.replace("/","\\");
        }else {
            logger.info("***** We are working on Linux / Mac environment: " + OS);
        }
        return directory;
    }
	
	/**
     * Description: Method to read the configuration from Config file.
     *
     * @param sFile FILE Destination
     * @param sKey  KeyWord to read value.
     * @return
     */
    public static String getConfigValue(String sFile, String sKey) {
        logger.info("**** Read Configuration file ****");
        Properties prop = new Properties();
        String sValue = null;
        try {
            InputStream input = new FileInputStream(sFile);
            prop.load(input);
            sValue = prop.getProperty(sKey);
            logger.info("***** Value from Properties file of Parameter: " + sKey + ": " + sValue);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            logger.info("***** Can not find the properties file ****" + sValue);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sValue;
    }
    
    /*
     * Use initializeExcelFile(hashmap,path) to initialize a Xlsx file in given path
     * After that, write the  content of hashmap into Xlsx file
     */
    public static void writeToExcelfile(Map<Integer, List<String>> hashmap, String path) throws IOException {
        Set<Integer> keyset = hashmap.keySet();
        if (!keyset.isEmpty()) {
            logger.info("Create file: " + path);
            excelFile = path;
            FileOutputStream out = new FileOutputStream(new File(path));
            int rownum = 1;
            int cellnum = 0;
            initializeExcelFile(hashmap, path);
            workbook = new XSSFWorkbook(path);
            XSSFSheet sheet = workbook.getSheetAt(0);
            for (Integer key : keyset) {
                List<String> nameList = hashmap.get(key);
                for (String s : nameList) {
                    XSSFRow row = sheet.getRow(rownum);
                    Cell cell = row.getCell(cellnum++);
                    if (null != cell) {
                        cell.setCellValue(s);
                    }
                }
                cellnum = 0;
                rownum++;
            }

            workbook.write(out);
            out.close();
        }
    }
    
    public static void initializeExcelFile(Map<Integer, List<String>> hashmap, String path) throws IOException {
        FileOutputStream out = new FileOutputStream(new File(path));
        Set<Integer> keyset = hashmap.keySet();
        XSSFSheet sheet = workbook.createSheet();
        XSSFRow row = null;
        List<String> nameList = hashmap.get(keyset.toArray()[0]);

        row = sheet.createRow((short) 0);
        row.createCell(0).setCellValue("Feature Name");
        row.createCell(1).setCellValue("Scenario Name");

        for (int j = 1; j < keyset.size() + 1; j++) {
            row = sheet.createRow(j);
            if (null != row) {
                for (int i = 0; i < nameList.size() + 1; i++) {
                    row.createCell(i);
                }
            }
        }

        workbook.write(out);
        out.close();
    }
	
}
