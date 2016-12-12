package cgt.dop.service.perf;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cgt.dop.bean.InfluxUserProfileBean;
import cgt.dop.bean.ObservationsBean;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.export.Exporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

//import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
//import net.sf.jasperreports.export.Exporter;
//import net.sf.jasperreports.export.SimpleExporterInput;
//import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

/**
 * @Github: https://github.com/javaquery/Examples
 */
public class JasperReport {
	public static void main(String[] args) {
		try {

			String str = "Hello Jasper Reports";
			/* User home directory location */
			String userHomeDirectory = System.getProperty("user.home");
			/* Output file location */
			String outputFile = userHomeDirectory + File.separatorChar + "TableExample_today.pdf";
			
			
//			InfluxUserProfileBean cList = new InfluxUserProfileBean();
//			cList.setScenario_name("sceeeee");
//			cList.setMaxThreads("2");
//			cList.setTeststarttime("start");
//			cList.setTestendtime("testend");
//			cList.setTestduration("30");
//			cList.setRampup("20");
//			cList.setSteadyperiod("100");
//			cList.setRampdown("10");
			
			/*ObservationsBean kList=new ObservationsBean();
			kList.setError(0);
			kList.setResponseTime(10);
			kList.setTps(0);
			kList.getTps();
			
			

			
			ArrayList<ObservationsBean> al1=new ArrayList<ObservationsBean>();
			al1.add(kList);*/
			
			
			List<ChartData> cList = new ArrayList<ChartData>();
			cList.add(new ChartData("hoursNormal","month1", 12.3)); //The use of resources or static text is beyond this example
			cList.add(new ChartData("hoursTravel","month1", 3.2));
			cList.add(new ChartData("hoursOvertime","month1", 1.3));
			cList.add(new ChartData("hoursNormal","month2", 16.4));
			cList.add(new ChartData("hoursTravel","month2", 5.2));
			cList.add(new ChartData("hoursOvertime","month2", 4.1));
			
			ArrayList<ChartData> al=new ArrayList<ChartData>();
			al.addAll(al);
			
			//Map<String, Object> paramMap1 = new HashMap<String, Object>();
			
		    Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("Test_Summary", new JRBeanCollectionDataSource(al));
//			paramMap.put("Test_Summary", new JRBeanCollectionDataSource(al1));
			//paramMap1.put("Test_Summary", new JRBeanCollectionDataSource(al1));
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					"C:\\cgt-dop-admin\\src\\main\\resources\\Blank_A4_test.jasper",paramMap, new JREmptyDataSource());
			
//			JasperPrint jasperPrint1 = JasperFillManager.fillReport(
//					"C:\\cgt-dop-admin\\src\\main\\resources\\Lennox_JasperReportDoc.jasper",paramMap1, new JREmptyDataSource());

			/* outputStream to create PDF */
			OutputStream outputStream = new FileOutputStream(new File(outputFile));
			/* Write content to PDF file */
			JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
			System.out.println("File Generated");

			// Write content to Word Document
			Exporter exporter = new JRDocxExporter();
			exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
			File exportReportFile = new File("C:\\Users\\E001102\\Reportdocu12.docx");
			exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(exportReportFile));
			exporter.exportReport();
			//System.out.println("Document got generated");

		} catch (JRException ex) {
			ex.printStackTrace();
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		}

	}


}