package cgt.dop.service.perf;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.PostConstruct;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Repository;

import cgt.dop.bean.AggregateReportBean;
import cgt.dop.bean.CompareAggregateReportBean;
import cgt.dop.bean.InfluxAggregateReportBean;
import cgt.dop.bean.InfluxSummaryBean;
import cgt.dop.bean.KPIBean;
import cgt.dop.bean.ObservationsBean;
import cgt.dop.bean.ResponseCodeBean;

@Repository
public class InfluxComparisionTestService extends InfluxHelper {

	@PostConstruct
	public void init() {
		try {
			super.test1();
			super.test2();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public String getKPIForComparisionTest() throws Exception {
		this.init(); // to be deleted
		KPIBean kPIBean = new KPIBean();
		ObservationsBean observationsBean = super.getKPIInfo();
		ObservationsBean observationsBeanForComparisionTest = super.getKPIInfoForComparisionTest();
		kPIBean.setErrorForTest1(observationsBean.getError());
		kPIBean.setTpsForTest1(observationsBean.getTps());
		kPIBean.setErrorForTest2(observationsBeanForComparisionTest.getError());
		kPIBean.setTpsForTest2(observationsBeanForComparisionTest.getTps());
		
		kPIBean.setErrorDiff(observationsBeanForComparisionTest.getError() - observationsBean.getError());
		kPIBean.setErrorDiffPercentage((observationsBeanForComparisionTest.getError() - observationsBean.getError())/100);
		
		
		kPIBean.setTpsDiff(observationsBeanForComparisionTest.getTps() - observationsBean.getTps());
		kPIBean.setTpsDiffPercentage((observationsBeanForComparisionTest.getTps() - observationsBean.getTps())/100);
		
		System.out.println("kPIBean== " + kPIBean);
		
		String json = new ObjectMapper().writeValueAsString(kPIBean);
		return json;
	}

	public String getActiveThreadsForComparisionTest() throws Exception {

		Map<Date, Long> resultMap = super.getActiveThreadsInfo();
		String json = new ObjectMapper().writeValueAsString(resultMap);
		return json;

	}

	public String getAggregateReportForComparisionTest() throws Exception {
		this.init(); // to be deleted
		ArrayList<InfluxAggregateReportBean> influxAggregateReportList = getInfluxAggregateReportList();
		ArrayList<InfluxAggregateReportBean> influxAggregateReportListForTest2 = getInfluxAggregateReportListForTest2();

		List<AggregateReportBean> aggregateReportBeanList = super.getAggregateReportInfo(influxAggregateReportList);
		List<AggregateReportBean> aggregateReportBeanListForTest2 = super.getAggregateReportInfo(influxAggregateReportListForTest2);

		System.out.println("aggregateReportBeanList== " + aggregateReportBeanList.size());
		System.out.println("aggregateReportBeanListForTest2== " + aggregateReportBeanListForTest2.size());

		aggregateReportBeanListForTest2.addAll(aggregateReportBeanList);
		
		System.out.println("aggregateReportBeanListForTest2 new == " + aggregateReportBeanListForTest2.size());
		Collections.sort(aggregateReportBeanListForTest2);
		
		
	
		Map<String, List<AggregateReportBean>> map = new HashMap<String, List<AggregateReportBean>>();
		for (AggregateReportBean aggregateReportBean : aggregateReportBeanListForTest2) {

			if (!map.containsKey(aggregateReportBean.getTransactionName())) {

				map.put(aggregateReportBean.getTransactionName(), new ArrayList<AggregateReportBean>());

			}
			map.get(aggregateReportBean.getTransactionName()).add(aggregateReportBean);
			
		}
		List<CompareAggregateReportBean> aggList = new ArrayList<CompareAggregateReportBean>();
		Map<String, String> tempMap = new HashMap<String, String>();
		for (Map.Entry<String, List<AggregateReportBean>> entrySet : map.entrySet()) {
			CompareAggregateReportBean compareAggregateReportBean = null;
			long avgTest1=0;
			long avgTest2=0;
			for(AggregateReportBean arb : entrySet.getValue())
			{
				
				if(!tempMap.containsKey(arb.getTransactionName()))
				{
					compareAggregateReportBean = new CompareAggregateReportBean();
					compareAggregateReportBean.setTransactionNameTest1(arb.getTransactionName());
					
					compareAggregateReportBean.setTransactionNameTest1(arb.getTransactionName()) ;

					compareAggregateReportBean.setCountTest1(arb.getCount()) ;

					compareAggregateReportBean.setAvgTest1(arb.getAvg());
					avgTest1 = arb.getAvg();
					compareAggregateReportBean.setAggregate_report_90_lineTest1(arb.getAggregate_report_90_line());

					compareAggregateReportBean.setAggregate_report_minTest1(arb.getAggregate_report_min());

					compareAggregateReportBean.setAggregate_report_maxTest1(arb.getAggregate_report_max());

					compareAggregateReportBean.setAggregate_report_errorTest1(arb.getAggregate_report_error()) ;
					
				}
				else
				{
					compareAggregateReportBean.setTransactionNameTest2(arb.getTransactionName()) ;

					compareAggregateReportBean.setCountTest2(arb.getCount()) ;

					compareAggregateReportBean.setAvgTest2(arb.getAvg()) ;
					avgTest2 = arb.getAvg();
					compareAggregateReportBean.setAggregate_report_90_lineTest2(arb.getAggregate_report_90_line());

					compareAggregateReportBean.setAggregate_report_minTest2(arb.getAggregate_report_min()) ;

					compareAggregateReportBean.setAggregate_report_maxTest2(arb.getAggregate_report_max());
					compareAggregateReportBean.setAggregate_report_errorTest2(arb.getAggregate_report_error()) ;
					
					compareAggregateReportBean.setDiffAvg(avgTest2 - avgTest1);
					compareAggregateReportBean.setDiffAvgPercentage((avgTest2 - avgTest1)/100);
				}
				tempMap.put(arb.getTransactionName(), arb.getTransactionName());
				
				
			}
			aggList.add(compareAggregateReportBean);
		}
		System.out.println("aggList == " + aggList);
		
		Map<String, List<AggregateReportBean>> aggMap = new HashMap<String, List<AggregateReportBean>>();		
		for(AggregateReportBean aggregateReportBean : aggregateReportBeanList)
		{
			if(aggregateReportBean.getTransactionName().contains("Transaction"))
			{
				if (!aggMap.containsKey("Transaction")) {
					ArrayList<AggregateReportBean> arb = new ArrayList<AggregateReportBean>();
					arb.add(aggregateReportBean);
					aggMap.put("Transaction", arb);
				}
				aggMap.get("Transaction").add(aggregateReportBean);				
			}
			else
			{
				if (!aggMap.containsKey("Request")) {
					ArrayList<AggregateReportBean> arb = new ArrayList<AggregateReportBean>();
					arb.add(aggregateReportBean);
					aggMap.put("Request", arb);
				}
				aggMap.get("Request").add(aggregateReportBean);				
			}
			
		}		
		
		System.out.println("aggMap == "+aggMap);
		
		
		
		String json = new ObjectMapper().writeValueAsString(aggList);
		return json;
	}

	public String getHitsPerSecondsForComparisionTest() throws Exception {

		this.init(); // to be deleted
		ArrayList<InfluxSummaryBean> influxSummaryList = getInfluxSummaryList();
		ArrayList<InfluxSummaryBean> influxSummaryListForTest2 = getInfluxSummaryListForTest2();

		Map<Long, Long> resultMapForTest1 = super.getHitsPerSecondsInfo(influxSummaryList);
		Map<Long, Long> resultMapForTest2 = super.getHitsPerSecondsInfo(influxSummaryListForTest2);

		List result = this.collateResults(resultMapForTest1, resultMapForTest2);

		String json = new ObjectMapper().writeValueAsString(result);
		return json;

	}
	
	public String getResponseCodesForComparisionTest() throws Exception {

		this.init(); // to be deleted
		ArrayList<InfluxSummaryBean> influxSummaryList = getInfluxSummaryList();
		ArrayList<InfluxSummaryBean> influxSummaryListForTest2 = getInfluxSummaryListForTest2();

		ArrayList<ResponseCodeBean> resultTest1 = super.getResponseCodesInfo(influxSummaryList);
		ArrayList<ResponseCodeBean> resultTest2 = super.getResponseCodesInfo(influxSummaryListForTest2);

		List result = this.collateResults2(resultTest1, resultTest2);

		System.out.println("getResponseCodesForComparisionTest result : " + result);
		String json = new ObjectMapper().writeValueAsString(result);
		return json;

	}
	
	
	public List collateResults2(ArrayList<ResponseCodeBean> resultTest1, ArrayList<ResponseCodeBean> resultTest2) {

		List result = new ArrayList();
			Map<Date, Date> tempMap = new TreeMap<Date, Date>();
			for (ResponseCodeBean responseCodeForTest2 : resultTest2) {
				List list = null;
				List list2 = null;
				for (ResponseCodeBean responseCodeForTest1 : resultTest1) {/*

					list = new ArrayList();
					list2 = new ArrayList();
					list.add(responseCodeForTest2.getElapsedTime());
					
					if (responseCodeForTest2.getElapsedTime().equals(responseCodeForTest1.getElapsedTime())) {
						list.add(responseCodeForTest2.getResponseCode());
						list.add(responseCodeForTest1.getResponseCode());
						//break;
					} else if (Long.parseLong(responseCodeForTest2.getElapsedTime()) < Long.parseLong(responseCodeForTest1.getElapsedTime())) {
						list.add(responseCodeForTest2.getResponseCode());
						list.add(null);
						if (!tempMap.containsKey(responseCodeForTest1.getElapsedTime())) {
							list2.add(responseCodeForTest1.getElapsedTime());
							list2.add(null);
							list2.add(responseCodeForTest1.getResponseCode());
							break;
						}
							
					} else if (Long.parseLong(responseCodeForTest2.getElapsedTime()) > Long.parseLong(responseCodeForTest1.getElapsedTime())) {
						list.add(null);
						list.add(responseCodeForTest1.getResponseCode());
						if (!tempMap.containsKey(responseCodeForTest1.getElapsedTime())) {
							list2.add(responseCodeForTest1.getElapsedTime());
							list2.add(responseCodeForTest1.getResponseCode());
							list2.add(null);
							break;
						}

					}

				*/}
				result.add(list);
				if(!list2.isEmpty())
				{
					tempMap.put((Date) list2.get(0), (Date) list2.get(0));
					result.add(list2);
				}
			}

			return result;
		}

	public List collateResults(Map<Long, Long> resultMapForTest1, Map<Long, Long> resultMapForTest2) {

		List result = new ArrayList();
		Map<Date, Date> tempMap = new TreeMap<Date, Date>();
		for (Map.Entry<Long, Long> entrySetForTest2 : resultMapForTest2.entrySet()) {
			List list = null;
			List list2 = null;
			for (Map.Entry<Long, Long> entrySetForTest1 : resultMapForTest1.entrySet()) {

				list = new ArrayList();
				list2 = new ArrayList();
				list.add(entrySetForTest2.getKey());
				if (entrySetForTest2.getKey().equals(entrySetForTest1.getKey())) {

					list.add(entrySetForTest2.getValue());
					list.add(entrySetForTest1.getValue());
					//break;
				} /*else if (entrySetForTest2.getKey().before((entrySetForTest1.getKey()))) {
=======
				} else if (entrySetForTest2.getKey() < ((entrySetForTest1.getKey()))) {
>>>>>>> .r15

					list.add(entrySetForTest2.getValue());
					list.add(null);
					if (!tempMap.containsKey(entrySetForTest1.getKey())) {
						list2.add(entrySetForTest1.getKey());
						list2.add(null);
						list2.add(entrySetForTest1.getValue());
						break;
					}

<<<<<<< .mine
				}*/ /*else if (entrySetForTest2.getKey().after((entrySetForTest1.getKey()))) {
=======
				} else if (entrySetForTest2.getKey() > ((entrySetForTest1.getKey()))) {
>>>>>>> .r15

					list.add(null);
					list.add(entrySetForTest1.getValue());
					if (!tempMap.containsKey(entrySetForTest1.getKey())) {
						list2.add(entrySetForTest1.getKey());
						list2.add(entrySetForTest1.getValue());
						list2.add(null);						
						break;
					}

				}*/

			}
			result.add(list);
			if(!list2.isEmpty())
			{
				tempMap.put((Date) list2.get(0), (Date) list2.get(0));
				result.add(list2);
			}
		}

		return result;
	}
}
