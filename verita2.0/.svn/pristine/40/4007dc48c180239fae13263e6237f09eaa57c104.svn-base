package cgt.dop.excel.impl;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeSet;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;

import cgt.dop.excel.DefectsData;
import cgt.dop.excel.ExcelEngineeringKPI;
import cgt.dop.excel.ExecutionTestCasesData;
import cgt.dop.excel.RequirementTestCasesData;




/**
 * Calculates EngineeringKPIImpl for the project.
 * 
 */
@Service("excelEngineeringKPI")
public class ExcelEngineeringKPIImpl  implements ExcelEngineeringKPI  {
	
	double dsiCitrixRel1_1 = 0;
	double dsiSAPBillRel1_1 = 0;
	double dsiSAPFICORel1_1 = 0;
	double dsiTXWebRel1_1 = 0;
	
	double testCovCitrix = 0;
	double testCovSAPBilling = 0;
	double testCovSAPFICO = 0;
	double testCovTXWeb = 0;

	private Object getCellValue(Cell cell) {
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_STRING:
			return cell.getStringCellValue();

		case Cell.CELL_TYPE_NUMERIC:
			return cell.getNumericCellValue();

		case Cell.CELL_TYPE_BOOLEAN:
			return cell.getBooleanCellValue();
		}

		return null;
	}

	public HashMap<String, Double> getDefectSeverity(String excelFilePath) throws IOException{
		FileInputStream inputStream = new FileInputStream(new File(excelFilePath));

		String defectSeverity = null;
		String defectApplicationName = null;
		String defectReleaseName = null;

		double ds = 0;
		double countSevCitrixRel1_1_1 = 0;
		double countSevCitrixRel1_1_2 = 0;
		double countSevCitrixRel1_1_3 = 0;
		double countSevCitrixRel1_1_4 = 0;

		double countSevSAPBillRel1_1_1 = 0;
		double countSevSAPBillRel1_1_2 = 0;
		double countSevSAPBillRel1_1_3 = 0;
		double countSevSAPBillRel1_1_4 = 0;

		double countSevSAPFICORel1_1_1 = 0;
		double countSevSAPFICORel1_1_2 = 0;
		double countSevSAPFICORel1_1_3 = 0;
		double countSevSAPFICORel1_1_4 = 0;

		double countSevTXWebRel1_1_1 = 0;
		double countSevTXWebRel1_1_2 = 0;
		double countSevTXWebRel1_1_3 = 0;
		double countSevTXWebRel1_1_4 = 0;

		Workbook workbook = new HSSFWorkbook(inputStream);
		Sheet lastSheet = workbook.getSheetAt(5);
		System.out.println("Rows : " + lastSheet.getPhysicalNumberOfRows());

		Iterator<Row> iterator = lastSheet.iterator();
		iterator.next();
		while (iterator.hasNext()) {
			Row nextRow = iterator.next();
			Iterator<Cell> cellIterator = nextRow.cellIterator();
			DefectsData ddObj = new DefectsData();
			cellIterator.hasNext();

			while (cellIterator.hasNext()) {
				Cell nextCell = cellIterator.next();
				int columnIndex = nextCell.getColumnIndex();

				switch (columnIndex) {
				case 1:
					// System.out.println("case 0
					// "+getCellValue(nextCell).toString());
					ddObj.setDefect_ID(getCellValue(nextCell).toString());
					break;
				case 3:
					// System.out.println("case 1
					// "+getCellValue(nextCell).toString());
					defectSeverity = ddObj.setDefectSeverity(getCellValue(nextCell).toString());
					break;

				case 5:
					// System.out.println("case
					// 2"+getCellValue(nextCell).toString());
					ddObj.setDefectStatus(getCellValue(nextCell).toString());
					break;

				case 13:
					// System.out.println("case
					// 2"+getCellValue(nextCell).toString());
					defectApplicationName = ddObj.setDefectApplicationName(getCellValue(nextCell).toString());
					break;

				case 15:
					// System.out.println("case
					// 2"+getCellValue(nextCell).toString());
					defectReleaseName = ddObj.setDefectReleaseName(getCellValue(nextCell).toString());
					break;
				}

			}

			// System.out.println("status: "+defectReleaseName);

			if (defectApplicationName.equals("Citrix") && defectReleaseName.equals("Rel 1.1")) {
				// System.out.println("defect severity: "+defectSeverity);

				ds = Double.parseDouble(defectSeverity);
				// System.out.println("check---- "+ds);

				if (ds == 1)
					countSevCitrixRel1_1_1++;
				else if (ds == 2)
					countSevCitrixRel1_1_2++;
				else if (ds == 3)
					countSevCitrixRel1_1_3++;
				else if (ds == 4)
					countSevCitrixRel1_1_4++;
			}

			else if (defectApplicationName.equals("SAP - Billing") && defectReleaseName.equals("Rel 1.1")) {
				// System.out.println("defect severity: "+defectSeverity);

				ds = Double.parseDouble(defectSeverity);
				// System.out.println("check---- "+ds);

				if (ds == 1)
					countSevSAPBillRel1_1_1++;
				else if (ds == 2)
					countSevSAPBillRel1_1_2++;
				else if (ds == 3)
					countSevSAPBillRel1_1_3++;
				else if (ds == 4)
					countSevSAPBillRel1_1_4++;
			}


			else if (defectApplicationName.equals("SAP - FICO") && defectReleaseName.equals("Rel 1.1")) {
				// System.out.println("defect severity: "+defectSeverity);

				ds = Double.parseDouble(defectSeverity);
				// System.out.println("check---- "+ds);

				if (ds == 1)
					countSevSAPFICORel1_1_1++;
				else if (ds == 2)
					countSevSAPFICORel1_1_2++;
				else if (ds == 3)
					countSevSAPFICORel1_1_3++;
				else if (ds == 4)
					countSevSAPFICORel1_1_4++;
			}

			else if (defectApplicationName.equals("TXWeb") && defectReleaseName.equals("Rel 1.1")) {
				// System.out.println("defect severity: "+defectSeverity);

				ds = Double.parseDouble(defectSeverity);
				// System.out.println("check---- "+ds);

				if (ds == 1)
					countSevTXWebRel1_1_1++;
				else if (ds == 2)
					countSevTXWebRel1_1_2++;
				else if (ds == 3)
					countSevTXWebRel1_1_3++;
				else if (ds == 4)
					countSevTXWebRel1_1_4++;
			}

		}

		workbook.close();
		inputStream.close();

		System.out.println("Citrix Rel 1.1 : ");
		System.out.println("ds1: " + countSevCitrixRel1_1_1);
		System.out.println("ds2: " + countSevCitrixRel1_1_2);
		System.out.println("ds3: " + countSevCitrixRel1_1_3);
		System.out.println("ds4: " + countSevCitrixRel1_1_4);

		double calCitrixDSI = ((countSevCitrixRel1_1_1 * 5) + (countSevCitrixRel1_1_2 * 3)
				+ (countSevCitrixRel1_1_3 * 1) + (countSevCitrixRel1_1_4 * 0))
				/ (countSevCitrixRel1_1_1 + countSevCitrixRel1_1_2 + countSevCitrixRel1_1_3 + countSevCitrixRel1_1_4);

		DecimalFormat df2 = new DecimalFormat("###.##");
		dsiCitrixRel1_1 = Double.parseDouble(df2.format(calCitrixDSI));
		System.out.println("DSI Citrix Rel 1.1 : " + dsiCitrixRel1_1);

		double calSAPBillDSI = ((countSevSAPBillRel1_1_1 * 5) + (countSevSAPBillRel1_1_2 * 3)
				+ (countSevSAPBillRel1_1_3 * 1) + (countSevSAPBillRel1_1_4 * 0))
				/ (countSevSAPBillRel1_1_1 + countSevSAPBillRel1_1_2 + countSevSAPBillRel1_1_3
						+ countSevSAPBillRel1_1_4);

		dsiSAPBillRel1_1 = Double.parseDouble(df2.format(calSAPBillDSI));

		System.out.println("DSI SAP Billing Rel 1.1 : " + dsiSAPBillRel1_1);

		double calSAPFICODSI = ((countSevSAPFICORel1_1_1 * 5) + (countSevSAPFICORel1_1_2 * 3)
				+ (countSevSAPFICORel1_1_3 * 1) + (countSevSAPFICORel1_1_4 * 0))
				/ (countSevSAPFICORel1_1_1 + countSevSAPFICORel1_1_2 + countSevSAPFICORel1_1_3
						+ countSevSAPFICORel1_1_4);

		dsiSAPFICORel1_1 = Double.parseDouble(df2.format(calSAPFICODSI));

		System.out.println("DSI SAP FICO Rel 1.1 : " + dsiSAPFICORel1_1);

		double calTXWebDSI = ((countSevTXWebRel1_1_1 * 5) + (countSevTXWebRel1_1_2 * 3) + (countSevTXWebRel1_1_3 * 1)
				+ (countSevTXWebRel1_1_4 * 0))
				/ (countSevTXWebRel1_1_1 + countSevTXWebRel1_1_2 + countSevTXWebRel1_1_3 + countSevTXWebRel1_1_4);

		dsiTXWebRel1_1 = Double.parseDouble(df2.format(calTXWebDSI));

		System.out.println("DSI TXWeb Rel 1.1 : " + dsiTXWebRel1_1);

		HashMap<String, Double> map = new HashMap<String, Double>();
		map.put("Citrix", dsiCitrixRel1_1);
		map.put("SAP-Billing", dsiSAPBillRel1_1);
		map.put("SAP-FICO", dsiSAPFICORel1_1);
		map.put("TXWeb", dsiTXWebRel1_1);

		return map;
	}

	public HashMap<String, Double> getTestCoverageBarGraph(String excelFilePath) throws IOException {
		FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
		TreeSet<String> reqIdCitrix = new TreeSet<String>();
		TreeSet<String> reqIdSAPBill = new TreeSet<String>();
		TreeSet<String> reqIdSAPFICO = new TreeSet<String>();
		TreeSet<String> reqIdTXWeb = new TreeSet<String>();

		Workbook workbook = new HSSFWorkbook(inputStream);
		Sheet thirdSheet = workbook.getSheetAt(3);
		System.out.println("Rows : " + thirdSheet.getPhysicalNumberOfRows());

		String testExecStatus = null;
		String testExecReleaseName = null;
		String testExecApplicationName = null;
		String testExecReqID = null;

		double countDistinctCitrixReqId = 0;
		double countDistinctSAPBillReqId = 0;
		double countDistinctSAPFICOReqId = 0;
		double countDistinctTXWebReqId = 0;

		double countCitrixReqId = 0;
		double countSAPBillReqId = 0;
		double countSAPFICOReqId = 0;
		double countTXWebReqId = 0;

		Iterator<Row> iterator = thirdSheet.iterator();
		iterator.next();
		while (iterator.hasNext()) {
			Row nextRow = iterator.next();
			Iterator<Cell> cellIterator = nextRow.cellIterator();
			ExecutionTestCasesData execObj = new ExecutionTestCasesData();
			cellIterator.hasNext();

			while (cellIterator.hasNext()) {
				Cell nextCell = cellIterator.next();
				int columnIndex = nextCell.getColumnIndex();

				switch (columnIndex) {
				case 3:
					testExecStatus = execObj.setTestExecutionStatus(getCellValue(nextCell).toString());
					break;

				case 7:
					testExecApplicationName = execObj.setTestApplicationName(getCellValue(nextCell).toString());
					break;

				case 9:
					testExecReleaseName = execObj.setTestReleaseName(getCellValue(nextCell).toString());
					break;

				case 10:
					testExecReqID = execObj.setTestRequirementId(getCellValue(nextCell).toString());
					break;

				}

			}

			if (testExecReleaseName.equals("Rel 1.1") && testExecApplicationName.equals("Citrix")
					&& !(testExecStatus.equals("Delayed"))) {
				reqIdCitrix.add(testExecReqID);
			}

			countDistinctCitrixReqId = reqIdCitrix.size();

			if (testExecReleaseName.equals("Rel 1.1") && testExecApplicationName.equals("SAP - Billing")
					&& !(testExecStatus.equals("Delayed"))) {
				reqIdSAPBill.add(testExecReqID);
			}

			countDistinctSAPBillReqId = reqIdSAPBill.size();

			if (testExecReleaseName.equals("Rel 1.1") && testExecApplicationName.equals("SAP - FICO")
					&& !(testExecStatus.equals("Delayed"))) {
				reqIdSAPFICO.add(testExecReqID);
			}

			countDistinctSAPFICOReqId = reqIdSAPFICO.size();

			if (testExecReleaseName.equals("Rel 1.1") && testExecApplicationName.equals("TXWeb")
					&& !(testExecStatus.equals("Delayed"))) {
				reqIdTXWeb.add(testExecReqID);
			}

			countDistinctTXWebReqId = reqIdTXWeb.size();

		}
		// System.out.println(reqId);

		System.out.println(("count citrix req id: " + countDistinctCitrixReqId));
		System.out.println(("count req id: " + countDistinctSAPBillReqId));
		System.out.println(("count req id: " + countDistinctSAPFICOReqId));
		System.out.println(("count req id: " + countDistinctTXWebReqId));

		workbook.close();
		inputStream.close();

		Sheet fourthSheet = workbook.getSheetAt(4);

		String reqReleaseName = null;
		String reqApplicationName = null;
		String reqReqId = null;

		Iterator<Row> itr = fourthSheet.iterator();
		itr.next();
		while (itr.hasNext()) {
			Row nextRow = itr.next();
			Iterator<Cell> cellIterator = nextRow.cellIterator();
			RequirementTestCasesData edbObj = new RequirementTestCasesData();
			cellIterator.hasNext();

			while (cellIterator.hasNext()) {
				Cell nextCell = cellIterator.next();
				int columnIndex = nextCell.getColumnIndex();

				switch (columnIndex) {
				case 3:
					// System.out.println("case 0
					// "+getCellValue(nextCell).toString());
					reqApplicationName = edbObj.setApplicationName(getCellValue(nextCell).toString());
					break;
				case 5:
					// System.out.println("case 1
					// "+getCellValue(nextCell).toString());
					reqReleaseName = edbObj.setReleaseName(getCellValue(nextCell).toString());
					break;

				case 6:
					// System.out.println("case 0
					// "+getCellValue(nextCell).toString());
					reqReqId = edbObj.setRequirementId(getCellValue(nextCell).toString());
					break;

				}

			}

			if (reqApplicationName.equals("Citrix") && reqReleaseName.equals("Rel 1.1")) {
				countCitrixReqId++;
			}

			if (reqApplicationName.equals("SAP - Billing") && reqReleaseName.equals("Rel 1.1")) {
				countSAPBillReqId++;
			}

			if (reqApplicationName.equals("SAP - FICO") && reqReleaseName.equals("Rel 1.1")) {
				countSAPFICOReqId++;
			}

			if (reqApplicationName.equals("TXWeb") && reqReleaseName.equals("Rel 1.1")) {
				countTXWebReqId++;
			}
		}

		System.out.println("req: " + countCitrixReqId);
		System.out.println("req: " + countSAPBillReqId);
		System.out.println("req: " + countSAPFICOReqId);
		System.out.println("req: " + countTXWebReqId);

		workbook.close();
		inputStream.close();

		testCovCitrix = (countDistinctCitrixReqId / countCitrixReqId) * 100;
		testCovSAPBilling = (countDistinctSAPBillReqId / countSAPBillReqId) * 100;
		testCovSAPFICO = (countDistinctSAPFICOReqId / countSAPFICOReqId) * 100;
		testCovTXWeb = (countDistinctTXWebReqId / countTXWebReqId) * 100;

		System.out.println("res: " + testCovCitrix);
		System.out.println("res: " + testCovSAPBilling);
		System.out.println("res: " + testCovSAPFICO);
		System.out.println("res: " + testCovTXWeb);

		HashMap<String, Double> tcMap = new HashMap<String, Double>();
		tcMap.put("Citrix", testCovCitrix);
		tcMap.put("SAP-Billing", testCovSAPBilling);
		tcMap.put("SAP-FICO", testCovSAPFICO);
		tcMap.put("TXWeb", testCovTXWeb);

		return tcMap;

	}

}
	


