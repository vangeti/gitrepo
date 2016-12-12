package cgt.dop.alm.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import cgt.dop.alm.DashBoardKPI;
import cgt.dop.alm.KPIHelper;
import cgt.dop.alm.exception.ConstraintViolatedException;
import cgt.dop.alm.infrastructure.RestConnector;
import cgt.dop.alm.util.DefectSeverityIndexTO;
import cgt.dop.alm.util.FeatureReadynessTO;
import cgt.dop.alm.util.MeanTimeTO;
import cgt.dop.alm.util.OpenDefectSeverityTO;
import cgt.dop.alm.util.PrescriptiveAnalysisTO;
import cgt.dop.alm.util.ReleaseReadynessTO;
import cgt.dop.alm.util.RequirementStabilityIndexTO;
import cgt.dop.alm.util.TestCaseYieldTO;
import cgt.dop.alm.util.TestCoverageTO;
import cgt.dop.alm.util.TestExecutionProgressTO;

@Service("dashBoardKPI")
@Scope("prototype")
public class DashBoardKPIImpl extends KPIHelper implements DashBoardKPI {

	private static final String QCDETAILS_CONFIG = "qcdetails-config";

	private ResourceBundle bundle = ResourceBundle.getBundle(QCDETAILS_CONFIG, Locale.getDefault());

	@Autowired
	public EngineeringKPIImpl engineeringKPI;

	@Autowired
	public AnlyticalKPIImpl analyticalKPI;

	RestConnector conn;

	public String runListAsXML;
	public String testListAsXML;
	public String requirementListAsXML;
	public String defectListAsXML;
	public String groupedDefectListAsXML;
	public String groupedReqListAsXML;

	//@PostConstruct
	public void init() throws ConstraintViolatedException {

		conn = super.getConnection();
		conn.getQCSession();

		String run = bundle.getObject("run").toString();
		String test = bundle.getObject("test").toString();
		String requirement = bundle.getObject("requirement").toString();
		String defect = bundle.getObject("defect").toString();
		String defectReleaseName = bundle.getObject("defect.release.name").toString();
		String defectApplicationName = bundle.getObject("defect.application.name").toString();
		String defectSeverity = bundle.getObject("defect.severity").toString();

		String requirementReleaseName = bundle.getObject("requirement.release.name").toString();
		String requirementApplicationName = bundle.getObject("requirement.application.name").toString();
		String requirementStatus = bundle.getObject("requirement.requirementstatus").toString();

		runListAsXML = super.getResponseAsXMLForRun(conn, run);
		testListAsXML = super.getResponseAsXMLForTest(conn, test);
		requirementListAsXML = super.getResponseAsXMLForRequirements(conn, requirement);
		defectListAsXML = super.getResponseAsXMLForDefects(conn, defect);
		groupedDefectListAsXML = super.getResponseAsXML(conn, defect, defectReleaseName + ",",
				defectApplicationName + ",", defectSeverity);
		groupedReqListAsXML = super.getResponseAsXML(conn, requirement, requirementReleaseName + ",",
				requirementApplicationName + ",", requirementStatus);

	}

	@PreDestroy
	public void close() throws ConstraintViolatedException {
		super.close();
	}

	public ReleaseReadynessTO getReleaseReadynessKPI(String releaseNo) throws ConstraintViolatedException {
		System.out.println("inside getReleaseReadynessKPI(..)");

		DefectSeverityIndexTO defectSeverityIndex = engineeringKPI.getDefectSeverityIndex(releaseNo, conn,
				groupedDefectListAsXML);
		TestExecutionProgressTO testExecutionProgress = engineeringKPI.getTestExecutionProgress(releaseNo, conn,
				runListAsXML);
		TestCoverageTO testCoverage = engineeringKPI.getTestCoverage(releaseNo, conn, requirementListAsXML);
		OpenDefectSeverityTO openDefectSeverity = engineeringKPI.getOpenDefectSeverity(releaseNo, conn,
				defectListAsXML);

		TestCaseYieldTO testCaseYield = analyticalKPI.getTestCaseYield(releaseNo, conn, defectListAsXML);
		RequirementStabilityIndexTO requirementStabilityIndex = analyticalKPI.getRequirementStabilityIndex(releaseNo,
				conn, groupedReqListAsXML);
		FeatureReadynessTO featureReadyness = analyticalKPI.getFeatureReadyness(releaseNo, conn, runListAsXML,
				defectListAsXML);
		MeanTimeTO meanTime = analyticalKPI.getMeanTime(releaseNo, conn, defectListAsXML);

		float engKPI = 0;
		float anlyKPI = 0;
		float relReady = 0;

		engKPI = (testExecutionProgress.getMeanTotal() + (100 - defectSeverityIndex.getMeanTotal())
				+ testCoverage.getMeanTotal() + (100 - openDefectSeverity.getMeanTotal())) / 4;

		anlyKPI = (requirementStabilityIndex.getMeanTotal() + (100 - meanTime.getMeanTotal())
				+ featureReadyness.getMeanTotal() + (100 - testCaseYield.getMeanTotal())) / 4;

		String engKPIPercentage = (String) bundle.getObject("engKPIPercentage");
		String analyKPIPercentage = (String) bundle.getObject("analyKPIPercentage");

		relReady = (((engKPI * Integer.valueOf(engKPIPercentage)) / 100)
				+ ((anlyKPI * Integer.valueOf(analyKPIPercentage)) / 100));

		ReleaseReadynessTO releaseReadynessTO = new ReleaseReadynessTO();

		releaseReadynessTO.setAnlyKPI(super.getLimitedPrecision(anlyKPI, 2));
		releaseReadynessTO.setEngKPI(super.getLimitedPrecision(engKPI, 2));
		releaseReadynessTO.setRelReady(super.getLimitedPrecision(relReady, 2));
		releaseReadynessTO.setAnalyKPIPercentage(Integer.valueOf(analyKPIPercentage));
		releaseReadynessTO.setEngKPIPercentage(Integer.valueOf(engKPIPercentage));
		releaseReadynessTO.setDefectSeverityIndex(defectSeverityIndex);
		releaseReadynessTO.setTestExecutionProgress(testExecutionProgress);
		releaseReadynessTO.setOpenDefectSeverity(openDefectSeverity);
		releaseReadynessTO.setTestCoverage(testCoverage);
		releaseReadynessTO.setTestCaseYield(testCaseYield);
		releaseReadynessTO.setRequirementStabilityIndex(requirementStabilityIndex);
		releaseReadynessTO.setFeatureReadyness(featureReadyness);
		releaseReadynessTO.setMeanTime(meanTime);

		return releaseReadynessTO;
	}

	public List<PrescriptiveAnalysisTO> getPrescriptiveAnalysis(String releaseNo) throws ConstraintViolatedException {

		List<PrescriptiveAnalysisTO> prescriptiveAnalysisList = this.engineeringKPI.getPrescriptiveAnalysis(releaseNo,
				conn, defectListAsXML);

		return prescriptiveAnalysisList;

	}

	@Override
	public DefectSeverityIndexTO getScrumMasterDefectSeverityIndex(String releaseNo)
			throws ConstraintViolatedException {

		DefectSeverityIndexTO defectSeverityIndex = engineeringKPI.getDefectSeverityIndex(releaseNo, conn,
				groupedDefectListAsXML);

		return defectSeverityIndex;
	}

	@Override
	public TestExecutionProgressTO getScrumMasterTestExecutionProgress(String releaseNo)
			throws ConstraintViolatedException {

		TestExecutionProgressTO testExecutionProgress = engineeringKPI.getTestExecutionProgress(releaseNo, conn,
				runListAsXML);

		return testExecutionProgress;
	}

	@Override
	public TestCoverageTO getScrumMasterTestCoverage(String releaseNo) throws ConstraintViolatedException {

		TestCoverageTO testCoverage = engineeringKPI.getTestCoverage(releaseNo, conn, requirementListAsXML);

		return testCoverage;
	}

	@Override
	public OpenDefectSeverityTO getScrumMasterOpenDefectSeverity(String releaseNo) throws ConstraintViolatedException {

		OpenDefectSeverityTO openDefectSeverity = engineeringKPI.getOpenDefectSeverity(releaseNo, conn,
				defectListAsXML);

		return openDefectSeverity;
	}

	@Override
	public RequirementStabilityIndexTO getScrumMasterRequirementStabilityIndex(String releaseNo)
			throws ConstraintViolatedException {

		RequirementStabilityIndexTO requirementStabilityIndex = analyticalKPI.getRequirementStabilityIndex(releaseNo,
				conn, groupedReqListAsXML);

		return requirementStabilityIndex;
	}

	@Override
	public FeatureReadynessTO getScrumMasterFeatureReadyness(String releaseNo) throws ConstraintViolatedException {

		FeatureReadynessTO featureReadyness = analyticalKPI.getFeatureReadyness(releaseNo, conn, runListAsXML,
				defectListAsXML);

		return featureReadyness;
	}

	@Override
	public MeanTimeTO getScrumMasterMeanTime(String releaseNo) throws ConstraintViolatedException {

		MeanTimeTO meanTime = analyticalKPI.getMeanTime(releaseNo, conn, defectListAsXML);
		return meanTime;
	}

	@Override
	public TestCaseYieldTO getScrumMasterTestCaseYield(String releaseNo) throws ConstraintViolatedException {

		TestCaseYieldTO testCaseYield = analyticalKPI.getTestCaseYield(releaseNo, conn, defectListAsXML);

		return testCaseYield;
	}

	@SuppressWarnings("unchecked")
	public String getDefectDetails() {
		JSONObject obj = new JSONObject();

		conn.getQCSession();
		obj.put("Defects", getCustomDetails(super.getEntityDetails(conn, "defect"),
				super.getEntityFields(conn, "defect", "fields")));
		return obj.toString();
	}

	@SuppressWarnings("unchecked")
	private JSONArray getCustomDetails(Object data, Object fieldsdata) {

		Map<String, String> fieldnameMap = getCustomFields(fieldsdata);
		JSONArray subjsonarray = new JSONArray();
		try {
			JSONParser parser = new JSONParser();
			JSONObject jsonObject = (JSONObject) parser.parse(data.toString());
			JSONArray entitiesArray = (JSONArray) jsonObject.get("entities");
			Iterator<Object> entities = entitiesArray.iterator();

			while (entities.hasNext()) {
				JSONObject entity = (JSONObject) entities.next();
				JSONArray fieldsarray = (JSONArray) entity.get("Fields");
				Iterator<Object> fields = fieldsarray.iterator();
				JSONObject subjsonobject = new JSONObject();
				while (fields.hasNext()) {

					JSONObject fieldObject = (JSONObject) fields.next();
					JSONArray valuesarray = (JSONArray) fieldObject.get("values");

					if (valuesarray.isEmpty()) {
						subjsonobject.put(fieldnameMap.get(fieldObject.get("Name")), "");
					} else {

						for (Object value : valuesarray) {
							JSONObject valueobj = (JSONObject) value;
							if (valueobj.get("value") != null) {
								subjsonobject.put(fieldnameMap.get(fieldObject.get("Name")),
										valueobj.get("value").toString());
							} else {
								subjsonobject.put(fieldnameMap.get(fieldObject.get("Name")), "");
							}
						}
					}
				}
				subjsonarray.add(subjsonobject);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return subjsonarray;
	}

	private Map<String, String> getCustomFields(Object fieldsdata) {
		Map<String, String> fieldnameMap = new HashMap<String, String>();
		try {
			JSONParser parser = new JSONParser();
			System.out.println(fieldsdata.toString());
			JSONObject jsonObject = (JSONObject) parser.parse(fieldsdata.toString());
			JSONObject fieldsObject = (JSONObject) jsonObject.get("Fields");
			JSONArray fieldsArray = (JSONArray) fieldsObject.get("Field");
			for (Object fiel : fieldsArray) {
				JSONObject field = (JSONObject) fiel;
				fieldnameMap.put(field.get("name").toString().trim(), field.get("label").toString().trim());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fieldnameMap;
	}
}
