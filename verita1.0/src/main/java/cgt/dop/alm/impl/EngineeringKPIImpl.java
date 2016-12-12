package cgt.dop.alm.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeMap;

import javax.xml.bind.JAXBException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cgt.dop.alm.EngineeringKPI;
import cgt.dop.alm.KPIHelper;
import cgt.dop.alm.exception.ConstraintViolatedException;
import cgt.dop.alm.infrastructure.Entities;
import cgt.dop.alm.infrastructure.Entity;
import cgt.dop.alm.infrastructure.Entity.Fields.Field;
import cgt.dop.alm.infrastructure.EntityMarshallingUtils;
import cgt.dop.alm.infrastructure.RestConnector;
import cgt.dop.alm.util.DefectSeverity;
import cgt.dop.alm.util.DefectSeverityIndexTO;
import cgt.dop.alm.util.DefectSeverityTO;
import cgt.dop.alm.util.DefectsTO;
import cgt.dop.alm.util.OpenDefectSeverityTO;
import cgt.dop.alm.util.PrescriptiveAnalysisComparator;
import cgt.dop.alm.util.PrescriptiveAnalysisTO;
import cgt.dop.alm.util.RequirementTO;
import cgt.dop.alm.util.RunTO;
import cgt.dop.alm.util.TCBlockedTO;
import cgt.dop.alm.util.TestCoverageTO;
import cgt.dop.alm.util.TestExecutionProgressTO;
import cgt.dop.service.QcDetailsService;

/**
 * Calculates EngineeringKPIImpl for the project.
 * 
 */
@Service("engineeringKPI")
public class EngineeringKPIImpl extends KPIHelper implements EngineeringKPI {

	private static final Logger logger = Logger.getLogger(EngineeringKPIImpl.class);

	private static final String DEFECT_SEVERITY = "defect-severity";
	
	private static final String QCDETAILS_CONFIG = "qcdetails-config";
	
	private ResourceBundle resourceBundle = ResourceBundle.getBundle(QCDETAILS_CONFIG, Locale.getDefault());

	@Autowired
	private QcDetailsService qcDetailsService;

	
	public DefectSeverityIndexTO getDefectSeverityIndex(String releaseNo, RestConnector conn,String defectListAsXML) throws ConstraintViolatedException {

	    	DefectSeverityIndexTO defectSeverityIndexTO = this.calculateDefectSeverityIndex(defectListAsXML, releaseNo);

		return defectSeverityIndexTO;
	}

	
	public TestExecutionProgressTO getTestExecutionProgress(String releaseNo, RestConnector conn,String runListAsXML) throws ConstraintViolatedException {
		
	    	TestExecutionProgressTO testExecutionProgressTO = this.calculateTestExecutionProgress(runListAsXML, releaseNo);

		return testExecutionProgressTO;
	}

	
	public TestCoverageTO getTestCoverage(String releaseNo, RestConnector conn,String requirementListAsXML)  throws ConstraintViolatedException {

	    TestCoverageTO testCoverageTO = this.calculateTestCoverage(requirementListAsXML, releaseNo);

		return testCoverageTO;
	}

	
	public OpenDefectSeverityTO getOpenDefectSeverity(String releaseNo, RestConnector conn,String defectListAsXML) throws ConstraintViolatedException {

	    	OpenDefectSeverityTO openDefectSeverityTO = this.calculateOpenDefectSeverity(conn,defectListAsXML, releaseNo);

		return openDefectSeverityTO;
	}
	
	public List<PrescriptiveAnalysisTO> getPrescriptiveAnalysis(String releaseNo, RestConnector conn,String defectListAsXML) throws ConstraintViolatedException {

	    List<PrescriptiveAnalysisTO> resultList = this.getPrescriptiveAnalysisList(conn,defectListAsXML, releaseNo);

		return resultList;
	}


	private DefectSeverityIndexTO calculateDefectSeverityIndex(String listAsXML, String releaseNo) throws ConstraintViolatedException {

		ResourceBundle resourceBundle = ResourceBundle.getBundle(DEFECT_SEVERITY, Locale.getDefault());
		DefectSeverityIndexTO defectSeverityIndexTO = new DefectSeverityIndexTO();
		Map<String, Float> resultMap = new TreeMap<String, Float>();

		Map<String, Map<String, Integer>> groupByHeaderMap = super.getGroupByHeaderMapOfMap(listAsXML, releaseNo);
		int count = 0;
		float meanTotal = 0;
		for (Map.Entry<String, Map<String, Integer>> applicationNameDefects : groupByHeaderMap.entrySet()) {

			float calculateSeverity = 0;
			float totalDefect = 0;

			for (Map.Entry<String, Integer> defectsEntrySet : applicationNameDefects.getValue().entrySet()) {
				String severity = defectsEntrySet.getKey();
				float ratingForSeverity = Float.valueOf(resourceBundle.getObject(severity).toString());
				float severityCount = defectsEntrySet.getValue();
				logger.debug("severity  " + severity);
				logger.debug("severityCount  " + severityCount);
				logger.debug("ratingForSeverity  " + ratingForSeverity);

				calculateSeverity = calculateSeverity + (ratingForSeverity * severityCount);
				totalDefect = totalDefect + severityCount;
			}

			float result = calculateSeverity / totalDefect;
			meanTotal = meanTotal + result;
			count++;
			if (!resultMap.containsKey(applicationNameDefects.getKey())) {
				resultMap.put(applicationNameDefects.getKey(), super.getLimitedPrecision(result, 2));
			}

		}
		meanTotal = meanTotal / count;
		
		defectSeverityIndexTO.setDefectSeverityIndexMap(resultMap);
		defectSeverityIndexTO.setMeanTotal(super.getLimitedPrecision(meanTotal, 2));
		return defectSeverityIndexTO;
	}
	
	private TestCoverageTO calculateTestCoverage(String requirementListAsXML, String releaseNo) throws ConstraintViolatedException {

		Map<String, Float> resultMap = new TreeMap<String, Float>();
		TestCoverageTO testCoverageTO = new TestCoverageTO();
		Entities entities = new Entities();
		try {
			entities = EntityMarshallingUtils.marshal(Entities.class, requirementListAsXML);
		} catch (JAXBException e1) {
			logger.error(e1);
		}
		List<RequirementTO> reqWithNoTestCases = new LinkedList<RequirementTO>();
		if(null != entities && null != entities.getEntityList()){
		    
		    	String id = resourceBundle.getObject("requirement.id").toString();			
			String name = resourceBundle.getObject("requirement.name").toString();
			String applicationName = resourceBundle.getObject("requirement.application.name").toString();
			String status = resourceBundle.getObject("requirement.status").toString();
			
			String[] reqStatusValue = resourceBundle.getObject("requirement.status.value").toString().split(",");
			List<String> strList = Arrays.asList(reqStatusValue);
			String notCovered = strList.get(0);
			
			List<RequirementTO> reqList = new LinkedList<RequirementTO>();
			Map<String, String> entityValue = null;
			for (Entity e : entities.getEntityList()) {
				List<Field> fields = e.getFields().getField();
				entityValue = new LinkedHashMap<String, String>();
				for (Field field : fields) {
				    if(field.getValue().size() > 0){
					entityValue.put(field.getName(), field.getValue().get(0));
				    }
				}

				RequirementTO requirementTO = new cgt.dop.alm.util.RequirementTO(entityValue.get(id),entityValue.get(name),
						entityValue.get(applicationName),entityValue.get(status));

				reqList.add(requirementTO);

			}

			Map<String, String> appName = new HashMap<String, String>();
			Map<String, Float> allMap = new TreeMap<String, Float>();
			Map<String, Float> coveredMap = new TreeMap<String, Float>();
			float total = 0;
			float covered = 0;
			for (RequirementTO requirementTO : reqList) {

				if (!appName.containsKey(requirementTO.getApplicationName())) {
				    total = 0;
				    covered = 0;
				}
				total++;
				
				if(notCovered.equals(requirementTO.getStatus()))
				{
				    RequirementTO reqWithNoTestCase = new cgt.dop.alm.util.RequirementTO(requirementTO.getId(),requirementTO.getName(),
					    requirementTO.getApplicationName(),requirementTO.getStatus());
				    reqWithNoTestCases.add(reqWithNoTestCase);
				}
				else
				{
				    covered++;
				}
				
				coveredMap.put(requirementTO.getApplicationName(), super.getLimitedPrecision(covered, 2));
				allMap.put(requirementTO.getApplicationName(), super.getLimitedPrecision(total, 2));

				appName.put(requirementTO.getApplicationName(), requirementTO.getApplicationName());

			}			
			Map<String, String> totalMap = new TreeMap<String, String>();
			float val = 0;
			for (Map.Entry<String, Float> entrySet : allMap.entrySet()) {
				
				float totCount = entrySet.getValue();
				float coveredCount = coveredMap.get(entrySet.getKey());
				String tot = (int)coveredCount +" / "+ (int)totCount;
				val = (coveredCount / totCount) * 100;				
				resultMap.put(entrySet.getKey(), super.getLimitedPrecision(val, 2));
				totalMap.put(entrySet.getKey(), tot);
				
			}
			
			float meanTotal = 0;
			int meanCount = 0;
			for (Map.Entry<String, Float> mean : resultMap.entrySet()) {
				meanTotal = meanTotal + mean.getValue();
				meanCount++;
			}
			meanTotal = meanTotal/meanCount;
			testCoverageTO.setTotalMap(totalMap);
			testCoverageTO.setTestCoverageMap(resultMap);
			testCoverageTO.setReqWithNoTestCases(reqWithNoTestCases);
			testCoverageTO.setMeanTotal(super.getLimitedPrecision(meanTotal, 2));
		}
		
		logger.debug("calculateTestCoverage : " + testCoverageTO);

		return testCoverageTO;

	}

	private TestExecutionProgressTO calculateTestExecutionProgress(String runListAsXML, String releaseNo) throws ConstraintViolatedException {
		
	    	TestExecutionProgressTO testExecutionProgress = new TestExecutionProgressTO();
		Map<String, Float> resultMap = new TreeMap<String, Float>();
		
		Entities runEntities = new Entities();
		try {
			runEntities = EntityMarshallingUtils.marshal(Entities.class, runListAsXML);
		} catch (JAXBException e1) {
			logger.error(e1);
		}
		if(null != runEntities && null != runEntities.getEntityList()){
		    
		    	String id = resourceBundle.getObject("run.id").toString();			
			String status = resourceBundle.getObject("run.status").toString();
			String name = resourceBundle.getObject("run.name").toString();
			String type = resourceBundle.getObject("run.type").toString();
			String applicationName = resourceBundle.getObject("run.application.name").toString();
			String[] statusValue = resourceBundle.getObject("run.status.value").toString().split(",");
			List<String> strList = Arrays.asList(statusValue);		
			String passed = strList.get(0);
			
			List<RunTO> runList = new LinkedList<RunTO>();
			Map<String, String> runEntityValue = null;
			for (Entity e : runEntities.getEntityList()) {
				List<Field> fields = e.getFields().getField();
				runEntityValue = new LinkedHashMap<String, String>();
				for (Field field : fields) {
				    if(field.getValue().size() > 0){
					runEntityValue.put(field.getName(), field.getValue().get(0));
				    }
				}
				RunTO runTO = new cgt.dop.alm.util.RunTO(runEntityValue.get(id), runEntityValue.get(name), runEntityValue.get(type).substring(10,16)
					, runEntityValue.get(status), runEntityValue.get(applicationName));
				runList.add(runTO);

			}
			
			Map<String, String> appName = new HashMap<String, String>();
			Map<String, Integer> executeMap = new TreeMap<String, Integer>();
			Map<String, Integer> totalMap = new TreeMap<String, Integer>();
			Map<String, String> allTestCasesMap = new TreeMap<String, String>();
			int executeCount = 0;
			int totalCount = 0;
			List<RunTO> notPassedList = new  ArrayList<RunTO>();
			for (RunTO runTO : runList) {
				if (!appName.containsKey(runTO.getApplicationName())) {
					executeCount = 0;
					totalCount = 0;
				}
				if (passed.equals(runTO.getStatus())) {
					executeCount++;
				}
				else{
				    RunTO notPassed = new cgt.dop.alm.util.RunTO(runTO.getId(), runTO.getName(), runTO.getStatus()
						, runTO.getType(), runTO.getApplicationName());
				    notPassedList.add(notPassed);
				}
				
				totalCount++;
				String tot = executeCount +" / "+ totalCount;
				allTestCasesMap.put(runTO.getApplicationName(), tot);
				executeMap.put(runTO.getApplicationName(), executeCount);
				totalMap.put(runTO.getApplicationName(), totalCount);

				appName.put(runTO.getApplicationName(), runTO.getApplicationName());

			}
			
			appName.clear();
			float total = 0;
			float meanTotal = 0;
			int count =0;
			for (Map.Entry<String, Integer> totalSet : totalMap.entrySet()) {
				float totVal = totalSet.getValue();
				float exeVal = executeMap.get(totalSet.getKey());
				
				if (!appName.containsKey(totalSet.getKey())) {
					total = 0;
					totalCount = 0;
				}			
				total = total + exeVal/totVal * 100;
				meanTotal = meanTotal + exeVal/totVal * 100;
				count++;
				resultMap.put(totalSet.getKey(), super.getLimitedPrecision(total, 2));
				appName.put(totalSet.getKey(), totalSet.getKey());
				
			}
			
			meanTotal = meanTotal / count;
			
			testExecutionProgress.setExecutionProgressMap(resultMap);
			testExecutionProgress.setFailedCases(notPassedList);
			testExecutionProgress.setAllTestCasesMap(allTestCasesMap);
			testExecutionProgress.setMeanTotal(super.getLimitedPrecision(meanTotal, 2));			
		}
		
		return testExecutionProgress;

	}
	
	private OpenDefectSeverityTO calculateOpenDefectSeverity(RestConnector conn,String listAsXML, String releaseNo) throws ConstraintViolatedException {

		Map<String, Float> resultMap = new TreeMap<String, Float>();
		OpenDefectSeverityTO openDefectSeverityTO = new OpenDefectSeverityTO();
		Entities entities = new Entities();
		try {
			entities = EntityMarshallingUtils.marshal(Entities.class, listAsXML);
		} catch (JAXBException e1) {
			logger.error(e1);
		}
		
		if(null != entities && null != entities.getEntityList()){
		    
		    	String id = resourceBundle.getObject("defect.id").toString();
			String name = resourceBundle.getObject("defect.name").toString();
			String applicationName = resourceBundle.getObject("defect.application.name").toString();			
			String severity = resourceBundle.getObject("defect.severity").toString();
			String priority = resourceBundle.getObject("defect.severity").toString();			
			String status = resourceBundle.getObject("defect.status").toString();
			String detectedstage = resourceBundle.getObject("defect.detectedstage").toString();
			String detecteddate = resourceBundle.getObject("defect.detecteddate").toString();
			String detectedBy = resourceBundle.getObject("defect.detectedby").toString();
			String mttd = resourceBundle.getObject("defect.mttd").toString();
			String[] statusValue = resourceBundle.getObject("defect.status.value").toString().split(",");
			List<String> strList = Arrays.asList(statusValue);
			String open = strList.get(0);
			String onhold = strList.get(1);
			String closed = strList.get(2);
			
			List<DefectsTO> defectsList = new LinkedList<DefectsTO>();
			Map<String, String> entityValue = null;
			for (Entity e : entities.getEntityList()) {
				List<Field> fields = e.getFields().getField();
				entityValue = new LinkedHashMap<String, String>();
				for (Field field : fields) {
				    if(field.getValue().size() > 0){
					entityValue.put(field.getName(), field.getValue().get(0));
				    }
				}
				if(!closed.equals(entityValue.get(status)))
				{
				    DefectsTO defectsTO = new cgt.dop.alm.util.DefectsTO(entityValue.get(id), entityValue.get(name),
						entityValue.get(applicationName), entityValue.get(severity),entityValue.get(priority),entityValue.get(status),
						entityValue.get(detectedstage),entityValue.get(detecteddate),entityValue.get(mttd),entityValue.get(detectedBy));
				    defectsList.add(defectsTO);
				}				

			}
			Map<String, String> appName = new HashMap<String, String>();
			List<DefectsTO> openDefectsList = new LinkedList<DefectsTO>();
			Map<String, DefectSeverityTO> allOpenDefects = new TreeMap<String, DefectSeverityTO>();
			float openOnHoldCount = 0;
			float sum = 0;
			int totalSeverity = 0;
			int severityOne = 0;
			int severityTwo = 0;
			int severityThree = 0;
			int severityFour = 0;
			DefectSeverityTO defectSeverityTO = null;
			Map<String, DefectSeverityTO> defectSeverityMap = new TreeMap<String, DefectSeverityTO>();
			Map<String, Float> allDefectSeverityCountMap = new TreeMap<String, Float>();
			for (DefectsTO defectsTO : defectsList) {

				if (!appName.containsKey(defectsTO.getApplicationName())) {
					openOnHoldCount = 0;
					severityOne = 0;
					severityTwo = 0;
					severityThree = 0;
					severityFour = 0;
					defectSeverityTO = new DefectSeverityTO();
					sum = 0;
				}
				if ( ( DefectSeverity.ONE.val().equals(defectsTO.getSeverity()) || DefectSeverity.TWO.val().equals(defectsTO.getSeverity()) ) &&
					(open.equals(defectsTO.getStatus()) || defectsTO.getStatus().equals(onhold))) {
					openOnHoldCount++;
				}
				if (open.equals(defectsTO.getStatus()) || defectsTO.getStatus().equals(onhold)) {
				    	totalSeverity++;
				    	sum++;
				    	defectSeverityTO.setTotalSeverity(totalSeverity);
					 DefectsTO openDefectsTO = new cgt.dop.alm.util.DefectsTO(defectsTO.getId(), defectsTO.getName(),
						 defectsTO.getApplicationName(), defectsTO.getSeverity(),defectsTO.getPriority(),defectsTO.getStatus(),
						 defectsTO.getDefectDetectedStage(),defectsTO.getDefectCreatedDate(),defectsTO.getMttd(),defectsTO.getDetectedBy());
					 openDefectsList.add(openDefectsTO);
					
				}
				if (DefectSeverity.ONE.val().equals(defectsTO.getSeverity()) &&
					(open.equals(defectsTO.getStatus()) ||onhold.equals(defectsTO.getStatus()))) {					
				    severityOne++;
				    defectSeverityTO.setSeverityOne(severityOne);
				}
				if (DefectSeverity.TWO.val().equals(defectsTO.getSeverity()) &&
					(open.equals(defectsTO.getStatus()) ||onhold.equals(defectsTO.getStatus()))) {					
				    severityTwo++;
				    defectSeverityTO.setSeverityTwo(severityTwo);
				}
				if (DefectSeverity.THREE.val().equals(defectsTO.getSeverity()) &&
					(open.equals(defectsTO.getStatus()) ||onhold.equals(defectsTO.getStatus()))) {					
				    severityThree++;
				    defectSeverityTO.setSeverityThree(severityThree);
				}
				if (DefectSeverity.FOUR.val().equals(defectsTO.getSeverity()) &&
					(open.equals(defectsTO.getStatus()) ||onhold.equals(defectsTO.getStatus()))) {					
				    severityFour++;
				    defectSeverityTO.setSeverityFour(severityFour);
				}
				
				allOpenDefects.put(defectsTO.getApplicationName(), defectSeverityTO);
				
				resultMap.put(defectsTO.getApplicationName(), super.getLimitedPrecision(openOnHoldCount, 2));
				allDefectSeverityCountMap.put(defectsTO.getApplicationName(), super.getLimitedPrecision(sum, 2));
				defectSeverityMap.put(defectsTO.getApplicationName(), defectSeverityTO);

				appName.put(defectsTO.getApplicationName(), defectsTO.getApplicationName());

			}	
			
			System.out.println("resultMap = "+resultMap);
			System.out.println("defectSeverityMap = "+defectSeverityMap);
			
			float meanTotal = 0;
			for (Map.Entry<String, Float> mean : resultMap.entrySet()) {
				meanTotal = meanTotal + mean.getValue();
			}
			
			float allDefectSeverityCount = 0;
			for (Map.Entry<String, Float> mean : allDefectSeverityCountMap.entrySet()) {
			    allDefectSeverityCount = allDefectSeverityCount + mean.getValue();
			}
			openDefectSeverityTO.setOpenDefects(openDefectsList);
			openDefectSeverityTO.setOpenDefectSeverityMap(defectSeverityMap);
			openDefectSeverityTO.setAllOpenDefects(allOpenDefects);
			openDefectSeverityTO.setAllDefectSeverityCount(allDefectSeverityCount);
			openDefectSeverityTO.setMeanTotal(super.getLimitedPrecision(meanTotal, 2));			
		}
		

		return openDefectSeverityTO;

	}	
	

	private List<PrescriptiveAnalysisTO> getPrescriptiveAnalysisList(RestConnector conn,String listAsXML, String releaseNo) throws ConstraintViolatedException {

	    Entities entities = new Entities();
	    try {
		entities = EntityMarshallingUtils.marshal(Entities.class, listAsXML);
	    } catch (JAXBException e1) {
		logger.error(e1);
	    }
	    Set<PrescriptiveAnalysisTO> prescriptiveAnalysisSet = new LinkedHashSet<PrescriptiveAnalysisTO>();
	    
	    List<PrescriptiveAnalysisTO> prescriptiveAnalysisList = null;
	    if(null != entities && null != entities.getEntityList()){
		
	    	String id = resourceBundle.getObject("defect.id").toString();
		String name = resourceBundle.getObject("defect.name").toString();
		String applicationName = resourceBundle.getObject("defect.application.name").toString();			
		String severity = resourceBundle.getObject("defect.severity").toString();
		String priority = resourceBundle.getObject("defect.severity").toString();			
		String status = resourceBundle.getObject("defect.status").toString();
		String detectedstage = resourceBundle.getObject("defect.detectedstage").toString();
		String detecteddate = resourceBundle.getObject("defect.detecteddate").toString();
		String detectedBy = resourceBundle.getObject("defect.detectedby").toString();
		String mttd = resourceBundle.getObject("defect.mttd").toString();
		String defectlink = resourceBundle.getObject("defect.link").toString();
		String defectLinkId = resourceBundle.getObject("defect.link.id").toString();
		String defectLinkTestId = resourceBundle.getObject("defect.link.test.id").toString();
		String defectLinkTestName = resourceBundle.getObject("defect.link.test.name").toString();
		String defectLinkTestStatus = resourceBundle.getObject("defect.link.test.status").toString();
		String defectLinkDetectedBy = resourceBundle.getObject("defect.link.test.detectedby").toString();
		String defectLinkDetectedDate = resourceBundle.getObject("defect.link.test.detecteddate").toString();
		
		String[] statusValue = resourceBundle.getObject("defect.status.value").toString().split(",");
		List<String> strList = Arrays.asList(statusValue);		
		String closed = strList.get(2);

		List<DefectsTO> defectsList = new LinkedList<DefectsTO>();
		Map<String, String> entityValue = null;
		for (Entity e : entities.getEntityList()) {
		    List<Field> fields = e.getFields().getField();
		    entityValue = new LinkedHashMap<String, String>();
		    for (Field field : fields) {
			if(field.getValue().size() > 0){
			    entityValue.put(field.getName(), field.getValue().get(0));
			}
		    }
		    if(!closed.equals(entityValue.get(status)))
		    {
			 DefectsTO defectsTO = new cgt.dop.alm.util.DefectsTO(entityValue.get(id), entityValue.get(name),
					entityValue.get(applicationName), entityValue.get(severity),entityValue.get(priority),entityValue.get(status),
					entityValue.get(detectedstage),entityValue.get(detecteddate),entityValue.get(mttd),entityValue.get(detectedBy));
			    defectsList.add(defectsTO);
		    }
		   

		}
		Map<String, List<TCBlockedTO>> prescriptiveAnalysisMap = new LinkedHashMap<String, List<TCBlockedTO>>();
		
		for (DefectsTO defectsTO : defectsList) {
		    prescriptiveAnalysisMap = this.retriveRelatedEntities(defectlink,defectLinkId,defectLinkTestId, defectLinkTestName,defectLinkTestStatus,defectLinkDetectedBy, defectLinkDetectedDate ,conn,defectsTO,prescriptiveAnalysisMap);
		}

		for (DefectsTO defectsTO : defectsList) {

		    if(prescriptiveAnalysisMap.containsKey(defectsTO.getId()))
		    {
			List<TCBlockedTO> tCBlockedTOList = prescriptiveAnalysisMap.get(defectsTO.getId());
			PrescriptiveAnalysisTO prescriptiveAnalysisTO = new PrescriptiveAnalysisTO( defectsTO.getId(),
				defectsTO.getApplicationName(),  defectsTO.getSeverity(), 
				String.valueOf(tCBlockedTOList.size()),tCBlockedTOList);					
			prescriptiveAnalysisSet.add(prescriptiveAnalysisTO);
		    }

		}
		
		prescriptiveAnalysisList = new LinkedList<PrescriptiveAnalysisTO>(prescriptiveAnalysisSet);
		Collections.sort(prescriptiveAnalysisList,new PrescriptiveAnalysisComparator());	


	    }
	    return prescriptiveAnalysisList;

	}
	
	private Map<String, List<TCBlockedTO>> retriveRelatedEntities(String defectlink,String defectLinkId,String defectLinkTestId,String  defectLinkTestName,
		String defectLinkTestStatus,String defectLinkDetectedBy,String  defectLinkDetectedDate ,RestConnector conn, DefectsTO defectsTO,Map<String, List<TCBlockedTO>> prescriptiveAnalysisMap) throws ConstraintViolatedException{
	
		String xml = super.getRelatedEntities(conn, defectlink,defectsTO.getId());
		Entities entities = new Entities();
		try {
			entities = EntityMarshallingUtils.marshal(Entities.class, xml);
		} catch (JAXBException e1) {
			logger.error(e1);
		}
		
		if(null != entities && null != entities.getEntityList()){
			Map<String, String> entityValue = null;
			List<TCBlockedTO> tCBlockedList = new LinkedList<TCBlockedTO>();
			for (Entity e : entities.getEntityList()) {
				List<Field> fields = e.getFields().getField();
				entityValue = new LinkedHashMap<String, String>();
				for (Field field : fields) {
				    if(field.getValue().size() > 0){
					entityValue.put(field.getName(), field.getValue().get(0));
				    }
				}
				TCBlockedTO tCBlockedTO  = new TCBlockedTO(entityValue.get(defectLinkTestId),
							entityValue.get(defectLinkTestName),
							entityValue.get(defectLinkTestStatus),entityValue.get(defectLinkDetectedBy),entityValue.get(defectLinkDetectedDate));
				tCBlockedList.add(tCBlockedTO);
				prescriptiveAnalysisMap.put(entityValue.get(defectLinkId), tCBlockedList);
			}			
		}
		return prescriptiveAnalysisMap;
	}

}
