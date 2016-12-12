package cgt.dop.alm.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;

import javax.xml.bind.JAXBException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import cgt.dop.alm.AnalyticalKPI;
import cgt.dop.alm.KPIHelper;
import cgt.dop.alm.exception.ConstraintViolatedException;
import cgt.dop.alm.infrastructure.Entities;
import cgt.dop.alm.infrastructure.Entity;
import cgt.dop.alm.infrastructure.Entity.Fields.Field;
import cgt.dop.alm.infrastructure.EntityMarshallingUtils;
import cgt.dop.alm.infrastructure.RestConnector;
import cgt.dop.alm.util.DefectsTO;
import cgt.dop.alm.util.FeatureReadynessTO;
import cgt.dop.alm.util.MeanTimeTO;
import cgt.dop.alm.util.RequirementStabilityIndexTO;
import cgt.dop.alm.util.RunTO;
import cgt.dop.alm.util.TestCaseYieldTO;
import cgt.dop.service.QcDetailsService;

/**
 * Calculates EngineeringKPIImpl for the project.
 * 
 */
@Service("anlyticalKPIImpl")
public class AnlyticalKPIImpl extends KPIHelper implements AnalyticalKPI {

	private static final Logger logger = Logger.getLogger(AnlyticalKPIImpl.class);
	
	private static final String QCDETAILS_CONFIG = "qcdetails-config";
	
	private ResourceBundle resourceBundle = ResourceBundle.getBundle(QCDETAILS_CONFIG, Locale.getDefault());

	@Autowired
	private QcDetailsService qcDetailsService;
	
	public TestCaseYieldTO getTestCaseYield(String releaseNo, RestConnector conn, String defectListAsXML) throws ConstraintViolatedException {

	    	TestCaseYieldTO testCaseYieldTO = this.calculateTestCaseYield(defectListAsXML, releaseNo);

		return testCaseYieldTO;
	}

	public RequirementStabilityIndexTO getRequirementStabilityIndex(String releaseNo, RestConnector conn, String groupedReqListAsXML) throws ConstraintViolatedException {

	    	RequirementStabilityIndexTO requirementStabilityIndexTO = this.calculateRequirementStabilityIndex(groupedReqListAsXML, releaseNo);

		return requirementStabilityIndexTO;
	}

	public FeatureReadynessTO getFeatureReadyness(String releaseNo, RestConnector conn,String runListAsXML,String defectListAsXML) throws ConstraintViolatedException {

	    	FeatureReadynessTO featureReadynessTO = this.calculateFeatureReadyness(releaseNo, runListAsXML, defectListAsXML);

		return featureReadynessTO;
	}
	
	public MeanTimeTO getMeanTime(String releaseNo, RestConnector conn, String defectListAsXML) throws ConstraintViolatedException {

	    	MeanTimeTO meanTimeTO = this.calculateMeanTime(defectListAsXML, releaseNo);

		return meanTimeTO;
	}

	private TestCaseYieldTO calculateTestCaseYield(String listAsXML, String releaseNo) throws ConstraintViolatedException {
		
		Map<String, Float> resultMap = new TreeMap<String, Float>();
		TestCaseYieldTO testCaseYieldTO = new TestCaseYieldTO();
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
			String detectedStage = resourceBundle.getObject("defect.detectedstage").toString();
			String detectedDate = resourceBundle.getObject("defect.detecteddate").toString();
			String detectedBy = resourceBundle.getObject("defect.detectedby").toString();
			String mttd = resourceBundle.getObject("defect.mttd").toString();			
			String[] defectdetectedstage = resourceBundle.getObject("defect.detectedstage.value").toString().split(",");
			List<String> strList = Arrays.asList(defectdetectedstage);
			String preRelease = strList.get(0);
			
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
				 DefectsTO defectsTO = new cgt.dop.alm.util.DefectsTO(entityValue.get(id), entityValue.get(name),
						entityValue.get(applicationName), entityValue.get(severity),entityValue.get(priority),entityValue.get(status),
						entityValue.get(detectedStage),entityValue.get(detectedDate),entityValue.get(mttd),entityValue.get(detectedBy));
				 defectsList.add(defectsTO);

			}
			Map<String, String> appName = new HashMap<String, String>();

			float defectsFoundInPreRelease = 0;
			float totalDefects = 0;
			Map<String, Float> defectsPreReleaseMap = new TreeMap<String, Float>();
			Map<String, Float> totalDefectsMap = new TreeMap<String, Float>();
			for (DefectsTO defectsTO : defectsList) {

				if (!appName.containsKey(defectsTO.getApplicationName())) {
					defectsFoundInPreRelease = 0;
					totalDefects = 0;
				}
				if (preRelease.equals(defectsTO.getDefectDetectedStage())) {
					defectsFoundInPreRelease++;
				}			
				totalDefects++;
				defectsPreReleaseMap.put(defectsTO.getApplicationName(), defectsFoundInPreRelease);
				totalDefectsMap.put(defectsTO.getApplicationName(), totalDefects);

				appName.put(defectsTO.getApplicationName(), defectsTO.getApplicationName());

			}
			
			Map<String, String> checkApplicationName = new HashMap<String, String>();
			float meanTotal = 0;		
			float total = 0;	
			int count = 0;
			Map<String, String> allDefectsMap = new TreeMap<String, String>();
			for (Map.Entry<String, Float> mean : defectsPreReleaseMap.entrySet()) {
				
				if (!checkApplicationName.containsKey(mean.getKey())) {
					total = 0;
				}
				float defectsPreRelease = mean.getValue();
				float defectsTot = totalDefectsMap.get(mean.getKey());
				String result = (int)defectsPreRelease +" / "+ (int)defectsTot;
				float tot = (defectsPreRelease / defectsTot) * 100;	
				total = tot;
				
				meanTotal = meanTotal + tot;
				allDefectsMap.put(mean.getKey(), result);
				resultMap.put(mean.getKey(), super.getLimitedPrecision(total, 2));
				count++;
				checkApplicationName.put(mean.getKey(), mean.getKey());
			}
			
			meanTotal = meanTotal / count;	
			testCaseYieldTO.setTestCaseYieldMap(resultMap);
			testCaseYieldTO.setAllDefects(allDefectsMap);
			testCaseYieldTO.setMeanTotal(super.getLimitedPrecision(meanTotal, 2));			
		}
			
		return testCaseYieldTO;
	}

	private RequirementStabilityIndexTO calculateRequirementStabilityIndex(String listAsXML, String releaseNo) throws ConstraintViolatedException {

		Map<String, Float> resultMap = new TreeMap<String, Float>();
		RequirementStabilityIndexTO requirementStabilityIndexTO = new RequirementStabilityIndexTO();
		Map<String, Map<String, Integer>> groupByHeaderMap = getGroupByHeaderMapOfMap(listAsXML, releaseNo);
	    	
	    	String[] requirementstage = resourceBundle.getObject("requirement.stage.value").toString().split(",");
		List<String> strList = Arrays.asList(requirementstage);
		String planned = strList.get(0);
		int count = 0;
		float meanTotal = 0;
		for (Map.Entry<String, Map<String, Integer>> applicationNameDefects : groupByHeaderMap.entrySet()) {

			float rsiPlanned = 0;
			float rsiTotal = 0;
			float total = 0;
			float result = 0;

			for (Map.Entry<String, Integer> rsiEntrySet : applicationNameDefects.getValue().entrySet()) {

				if (planned.equals(rsiEntrySet.getKey())) {
					rsiPlanned = rsiPlanned + rsiEntrySet.getValue();
				}
				rsiTotal = rsiTotal + rsiEntrySet.getValue();				
			}

			total = rsiPlanned / rsiTotal;
			if (Float.isNaN(total)) {
				total = 0;
			}
			logger.debug(total);
			result = total * 100;
			meanTotal = meanTotal + result;
			count++;
			
			resultMap.put(applicationNameDefects.getKey(), super.getLimitedPrecision(result, 2));

		}

		meanTotal = meanTotal / count;
		
		requirementStabilityIndexTO.setRequirementStabilityIndexMap(resultMap);
		requirementStabilityIndexTO.setMeanTotal(super.getLimitedPrecision(meanTotal, 2));
		return requirementStabilityIndexTO;
	}

	private FeatureReadynessTO calculateFeatureReadyness(String releaseNo,String runListAsXML, String defectListAsXML)
			throws ConstraintViolatedException {
		Map<String, Float> resultMap = new TreeMap<String, Float>();
		FeatureReadynessTO featureReadynessTO = new FeatureReadynessTO();
		
		Entities entities = new Entities();
		try {
			entities = EntityMarshallingUtils.marshal(Entities.class, defectListAsXML);
		} catch (JAXBException e1) {
			logger.error(e1);
		}
		
		Map<String, Float> defClosedPercentageMap = new LinkedHashMap<String, Float>();
		
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
			
			List<DefectsTO> defList = new LinkedList<DefectsTO>();
			Map<String, String> entityValue = null;
			for (Entity e : entities.getEntityList()) {
				List<Field> fields = e.getFields().getField();
				entityValue = new LinkedHashMap<String, String>();
				for (Field field : fields) {
				    if(field.getValue().size() > 0){
					entityValue.put(field.getName(), field.getValue().get(0));
				    }
				}

				 DefectsTO defectsTO = new cgt.dop.alm.util.DefectsTO(entityValue.get(id), entityValue.get(name),
						entityValue.get(applicationName), entityValue.get(severity),entityValue.get(priority),entityValue.get(status),
						entityValue.get(detectedstage),entityValue.get(detecteddate),entityValue.get(mttd),entityValue.get(detectedBy));

				defList.add(defectsTO);

			}

			Map<String, String> defAppName = new HashMap<String, String>();
			Map<String, Float> defectsFixedMap = new LinkedHashMap<String, Float>();
			Map<String, Float> defTotalMap = new LinkedHashMap<String, Float>();
			float defectsFixed = 0;
			float defTotal = 0;
			
			String[] statusValue = resourceBundle.getObject("defect.status.value").toString().split(",");
			List<String> strList = Arrays.asList(statusValue);		
			String closed = strList.get(2);

			for (DefectsTO defectsTO : defList) {

				if (!defAppName.containsKey(defectsTO.getApplicationName())) {
					defTotal = 0;
					defectsFixed = 0;
				}
				if (closed.equals(defectsTO.getStatus())) {
					defectsFixed++;
				}
				defTotal++;
				
				defectsFixedMap.put(defectsTO.getApplicationName(), defectsFixed);
				defTotalMap.put(defectsTO.getApplicationName(), defTotal);

				defAppName.put(defectsTO.getApplicationName(), defectsTO.getApplicationName());

			}
			
			float defClosedPercentage = 0;
			
			for (Map.Entry<String, Float> entrySet : defTotalMap.entrySet()) {
				
				float defTot = entrySet.getValue();
				float defFix = defectsFixedMap.get(entrySet.getKey());
				
				defClosedPercentage = (defFix/defTot) * 100;
				defClosedPercentageMap.put(entrySet.getKey(), defClosedPercentage);
			}
		}
		
		
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

				RunTO runTO = new cgt.dop.alm.util.RunTO(runEntityValue.get(id), runEntityValue.get(name), runEntityValue.get(type)
					, runEntityValue.get(status), runEntityValue.get(applicationName));
				runList.add(runTO);

				runList.add(runTO);
			}
			Map<String, String> runAppName = new HashMap<String, String>();
			Map<String, Float> passedTestCasesMap = new LinkedHashMap<String, Float>();
			Map<String, Float> totalTestCasesMap = new LinkedHashMap<String, Float>();
			float passedTestCases = 0;
			float totalTestCases = 0;
			
			for (RunTO runTO : runList) {

				if (!runAppName.containsKey(runTO.getApplicationName())) {
					totalTestCases = 0;
					passedTestCases = 0;
				}
				if (passed.equals(runTO.getStatus())) {
					passedTestCases++;
				}
				totalTestCases++;
				
				passedTestCasesMap.put(runTO.getApplicationName(), passedTestCases);
				totalTestCasesMap.put(runTO.getApplicationName(), totalTestCases);

				runAppName.put(runTO.getApplicationName(), runTO.getApplicationName());

			}
			
			float testPassedPercentage = 0;
			Map<String, Float> testPassedPercentageMap = new LinkedHashMap<String, Float>();
			for (Map.Entry<String, Float> entrySet :	totalTestCasesMap.entrySet()) {
				
				float totTC = entrySet.getValue();
				float exeTC = passedTestCasesMap.get(entrySet.getKey());
				
				testPassedPercentage = (exeTC/totTC) * 100;
				testPassedPercentageMap.put(entrySet.getKey(), testPassedPercentage);
			}
			
			float meanTotal = 0;
			float val = 0;
			int meanCount = 0;
			Map<String, String> checkApplicationName = new HashMap<String, String>();
			Map<String, String> allDefectsMap = new TreeMap<String, String>();
			
			for (Map.Entry<String, Float> entrySet : defClosedPercentageMap.entrySet()) {
				
				if (!checkApplicationName.containsKey(entrySet.getKey())) {
					val = 0;
				}			
				float defPercentage = entrySet.getValue();
				float testPercentage = 0;
				if(null != testPassedPercentageMap.get(entrySet.getKey()))
				{
					testPercentage = testPassedPercentageMap.get(entrySet.getKey());
				}
				String tot = (int)defPercentage +" / "+ (int)testPercentage;
				val = (defPercentage + testPercentage ) / 2;
				meanTotal = meanTotal + (defPercentage + testPercentage ) / 2;
				meanCount++;
				resultMap.put(entrySet.getKey(), super.getLimitedPrecision(val, 2));
				allDefectsMap.put(entrySet.getKey(), tot);
				checkApplicationName.put(entrySet.getKey(), entrySet.getKey());
				
			}
			
			meanTotal = meanTotal/meanCount;
			featureReadynessTO.setFeatureReadynessMap(resultMap);
			featureReadynessTO.setAllDefects(allDefectsMap);
			featureReadynessTO.setMeanTotal(super.getLimitedPrecision(meanTotal, 2));			
		}

		return featureReadynessTO;

	}


	private MeanTimeTO calculateMeanTime(String listAsXML, String releaseNo) throws ConstraintViolatedException {

		Map<String, Float> resultMap = new TreeMap<String, Float>();
		MeanTimeTO meanTimeTO = new MeanTimeTO();
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
				 DefectsTO defectsTO = new cgt.dop.alm.util.DefectsTO(entityValue.get(id), entityValue.get(name),
						entityValue.get(applicationName), entityValue.get(severity),entityValue.get(priority),entityValue.get(status),
						entityValue.get(detectedstage),entityValue.get(detecteddate),entityValue.get(mttd),entityValue.get(detectedBy));				
				defectsList.add(defectsTO);

			}
			
			Map<String, Float> appName = new HashMap<String, Float>();

			float meanTimeCount = 0;
			float val = 0;
			Map<String, Float> meanMap = new TreeMap<String, Float>();
			for (DefectsTO defectsTO : defectsList) {

				if (!appName.containsKey(defectsTO.getApplicationName())) {
					meanTimeCount = 0;
					val= 0;
				}
				if(!StringUtils.isEmpty(defectsTO.getMttd()))
				{
				    val = (val + Float.valueOf(defectsTO.getMttd()));
				}
				
				meanTimeCount++;
				meanMap.put(defectsTO.getApplicationName(), super.getLimitedPrecision(val, 2));

				appName.put(defectsTO.getApplicationName(), meanTimeCount);
			}
			
			Map<String, String> checkApplicationName = new HashMap<String, String>();
			float total = 0;
			float meanTotal = 0;
			int count =0;
			for (Map.Entry<String, Float> mean : meanMap.entrySet()) {
				if (!checkApplicationName.containsKey(mean.getKey())) {
					total = 0;
				}
				total = ( mean.getValue() /  appName.get(mean.getKey()) );
				resultMap.put(mean.getKey(), super.getLimitedPrecision(total, 2));
				meanTotal = meanTotal + total ;
				checkApplicationName.put(mean.getKey(), mean.getKey());
				count++;
			}
			meanTotal = meanTotal / count;
			
			meanTimeTO.setMeanTimeMap(resultMap);
			meanTimeTO.setMeanTotal(super.getLimitedPrecision(meanTotal, 2));			
		}
		
		return meanTimeTO;
	}

}
