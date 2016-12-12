package cgt.dop.service.perf;

import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cgt.dop.dao.impl.NeoLoadDao;
import cgt.dop.model.NeoLoadStaticsGraphModel;
import cgt.dop.model.NeoLoadTransactionDetails;

@Repository
public class NeoLoadService {

	@Autowired
	NeoLoadDao neoLoadDao;

	public String getKPIImprovementForLastBuild() throws Exception {
		JSONObject parentObject = new JSONObject();

		JSONObject childObject1 = new JSONObject();
		JSONObject childObject2 = new JSONObject();
		JSONObject childObject3 = new JSONObject();

		JSONObject childObject4 = new JSONObject();
		JSONObject childObject5 = new JSONObject();
		JSONObject childObject6 = new JSONObject();
		List compare = neoLoadDao.getlastTwoDetails();
		if (compare != null) {
			NeoLoadStaticsGraphModel object1 = (NeoLoadStaticsGraphModel) compare.get(0);
			NeoLoadStaticsGraphModel object2 = (NeoLoadStaticsGraphModel) compare.get(1);
			NeoLoadStaticsGraphModel object3 = (NeoLoadStaticsGraphModel) compare.get(2);


			childObject1.put("Prevvalue", String.valueOf(Double.parseDouble(object2.getAvgReqResponseTime())));
			childObject1.put("currentvalue", String.valueOf(Double.parseDouble(object1.getAvgReqResponseTime())));
			
			childObject2.put("Prevvalue", String.valueOf(Double.parseDouble(object2.getAvgThroughput())));
			childObject2.put("currentvalue", String.valueOf(Double.parseDouble(object1.getAvgThroughput())));
			
			childObject3.put("Prevvalue", String.valueOf(Double.parseDouble(object2.getErrorPercentile())));
			childObject3.put("currentvalue", String.valueOf(Double.parseDouble(object1.getErrorPercentile())));
			
			parentObject.append("ReponseTime", childObject1);
			parentObject.append("TPS", childObject2);
			parentObject.append("Errors", childObject3);
			
			
			

			/*if (Double.parseDouble(object2.getAvgReqResponseTime()) > Double
					.parseDouble(object3.getAvgReqResponseTime())) {

				childObject4.put("Prevstatus", "down");
				childObject4.put("Prevvalue", String.valueOf(Double.parseDouble(object2.getAvgReqResponseTime())
						- Double.parseDouble(object3.getAvgReqResponseTime())));
				parentObject.append("PrevReponseTime", childObject4);

			} else {

				childObject4.put("Prevstatus", "up");
				childObject4.put("Prevvalue", String.valueOf(Double.parseDouble(object3.getAvgReqResponseTime())
						- Double.parseDouble(object2.getAvgReqResponseTime())));
				parentObject.append("PrevReponseTime", childObject4);

			}

			if (Double.parseDouble(object1.getAvgReqResponseTime()) > Double
					.parseDouble(object2.getAvgReqResponseTime())) {

				childObject1.put("status", "down");
				childObject1.put("value", String.valueOf(Double.parseDouble(object1.getAvgReqResponseTime())
						- Double.parseDouble(object2.getAvgReqResponseTime())));
				parentObject.append("ReponseTime", childObject1);

			} else {

				childObject1.put("status", "up");
				childObject1.put("value", String.valueOf(Double.parseDouble(object2.getAvgReqResponseTime())
						- Double.parseDouble(object1.getAvgReqResponseTime())));
				parentObject.append("ReponseTime", childObject1);

			}

			if (Double.parseDouble(object2.getAvgHits()) > Double.parseDouble(object3.getAvgHits())) {

				childObject5.put("Prevstatus", "up");
<<<<<<< .mine
				childObject5.put("Prevvalue", String
						.valueOf(Double.parseDouble(object2.getAvgHits()) - Double.parseDouble(object3.getAvgHits())));
				parentObject.append("PrevTPS", childObject5);
=======
				childObject5.put("Prevvalue", String
						.valueOf(Double.parseDouble(object2.getAvgHits()) - Double.parseDouble(object3.getAvgHits())));
				parentObject.append("TPS", childObject5);
>>>>>>> .r54

			} else {

				childObject5.put("Prevstatus", "down");
				childObject5.put("Prevvalue", String
						.valueOf(Double.parseDouble(object3.getAvgHits()) - Double.parseDouble(object2.getAvgHits())));
				parentObject.append("PrevTPS", childObject5);

			}

			if (Double.parseDouble(object1.getAvgHits()) > Double.parseDouble(object2.getAvgHits())) {

				childObject2.put("status", "up");
				childObject2.put("value", String
						.valueOf(Double.parseDouble(object1.getAvgHits()) - Double.parseDouble(object2.getAvgHits())));
				parentObject.append("TPS", childObject2);

			} else {

				childObject2.put("status", "down");
				childObject2.put("value", String
						.valueOf(Double.parseDouble(object2.getAvgHits()) - Double.parseDouble(object1.getAvgHits())));
				parentObject.append("TPS", childObject2);

			}

			if ((Double.parseDouble(object2.getErrorPercentile())) > Double.parseDouble(object3.getErrorPercentile())) {
				childObject6.put("Prevstatus", "down");
				childObject6.put("Prevvalue", Double.parseDouble(object2.getErrorPercentile())
						- Double.parseDouble(object3.getErrorPercentile()));
				parentObject.append("PrevErrors", childObject6);

			} else {

				childObject6.put("Prevstatus", "up");
				childObject6.put("Prevvalue", Double.parseDouble(object3.getErrorPercentile())
						- Double.parseDouble(object2.getErrorPercentile()));
				parentObject.append("PrevErrors", childObject6);

			}

			if ((Double.parseDouble(object1.getErrorPercentile())) > Double.parseDouble(object2.getErrorPercentile())) {
				childObject3.put("status", "down");
				childObject3.put("value", Double.parseDouble(object1.getErrorPercentile())
						- Double.parseDouble(object2.getErrorPercentile()));
				parentObject.append("Errors", childObject3);

			} else {

				childObject3.put("status", "up");
				childObject3.put("value", Double.parseDouble(object2.getErrorPercentile())
						- Double.parseDouble(object1.getErrorPercentile()));
				parentObject.append("Errors", childObject3);

			}*/
		}
		System.out.println(parentObject.toString());
		return parentObject.toString();

	}

	public String getTestDetails() throws Exception {
		JSONObject jsonObject = new JSONObject();
		JSONObject child1 = new JSONObject();
		JSONObject child2 = new JSONObject();
		JSONObject child3 = new JSONObject();

		List tableList = neoLoadDao.getGraph2Details();
		NeoLoadStaticsGraphModel graphModel = (NeoLoadStaticsGraphModel) tableList.get(0);
		//
		child1.put("projectName", "My Load Test");
		child1.put("date", graphModel.getTestdate());
		child1.put("duration", graphModel.getTestduration());
		child1.put("users", graphModel.getTotalUserLaunched());
		jsonObject.append("Header", child1);

		child2.put("hits", graphModel.getAvgHits());
		child2.put("pages", graphModel.getAvgPages());
		child2.put("response", graphModel.getAvgReqResponseTime());
		child2.put("throughput", graphModel.getAvgThroughput());
		jsonObject.append("Average", child2);

		child3.put("hits", graphModel.getTotalHits());
		child3.put("pages", graphModel.getTotalPages());
		child3.put("percentileError", graphModel.getErrorPercentile());
		child3.put("throughput", graphModel.getTotalThroughtput());
		jsonObject.append("Total", child3);

		System.out.println(jsonObject.toString());
		return jsonObject.toString();

	}

	public String getTransactionDetails() throws Exception {

		JSONObject jsonObject = new JSONObject();
		List transaction = neoLoadDao.getSingleTransaction();
		for (int i = 0; i < transaction.size(); i++) {
			JSONObject child = new JSONObject();
			NeoLoadTransactionDetails loadTransactionDetails = (NeoLoadTransactionDetails) transaction.get(i);
			child.put("transactionName", loadTransactionDetails.getTransactionName());
			child.put("min", loadTransactionDetails.getMin());
			child.put("avg", loadTransactionDetails.getAvg());
			child.put("max", loadTransactionDetails.getMax());
			child.put("sample", loadTransactionDetails.getHits());
			jsonObject.append(loadTransactionDetails.getTransactionName(), child);
		}
		System.out.println(jsonObject.toString());
		return jsonObject.toString();

	}

	public String getTransactionPerSecondWithBuild() throws Exception {

		List list = neoLoadDao.getgraph1Details();
		String bar_graph = "";
		JSONObject jsonObject = new JSONObject();

		double tps = 0.0, errorsPercentile = 0.0;
		for (int i = 0; i < list.size(); i++) {

			NeoLoadStaticsGraphModel obje = (NeoLoadStaticsGraphModel) list.get(i);

			if (i + 1 < list.size()) {

				jsonObject.put(String.valueOf(obje.getBuildNo()), /*obje.getAvgHits()+*/obje.getAvgThroughput() );

				/*
				 * bar_graph = bar_graph.concat("[" + obje.getBuildNo() + "," +
				 * obje.getAvgHits() + "],");
				 */

			} else {
				jsonObject.put(String.valueOf(obje.getBuildNo()), /*obje.getAvgHits()*/obje.getAvgThroughput() );

				/*
				 * bar_graph = bar_graph.concat("[" + obje.getBuildNo() + "" +
				 * "," + obje.getAvgHits() + "]");
				 */

			}

		}
		System.out.println("bar graph: " + jsonObject);
		return jsonObject.toString();

	}

	public String getResponseTimeForPreviousBuilds() throws Exception {

		List list = neoLoadDao.getgraph1Details();
		String colNames = "";
		String colVals = "";
		JSONObject jsonObject = new JSONObject();
		double tps = 0.0, errorsPercentile = 0.0;
		for (int i = 0; i < list.size(); i++) {
			NeoLoadStaticsGraphModel obje = (NeoLoadStaticsGraphModel) list.get(i);

			if (i + 1 < list.size()) {

				jsonObject.put(String.valueOf(obje.getBuildNo()), obje.getAvgReqResponseTime());

			} else {

				jsonObject.put(String.valueOf(obje.getBuildNo()), obje.getAvgReqResponseTime());

			}
		}
		System.out.println("colNames: " + jsonObject);
		return jsonObject.toString();

	}

	public String getErrorPercentileTrend() throws Exception {

		List list = neoLoadDao.getgraph1Details();

		JSONObject jsonObject = new JSONObject();

		for (int i = 0; i < list.size(); i++) {
			NeoLoadStaticsGraphModel obje = (NeoLoadStaticsGraphModel) list.get(i);

			if (i + 1 < list.size()) {

				jsonObject.put(String.valueOf(obje.getBuildNo()), obje.getErrorPercentile());

			} else {

				jsonObject.put(String.valueOf(obje.getBuildNo()), obje.getErrorPercentile());

			}
		}
		System.out.println("line graph : " + jsonObject);
		return jsonObject.toString();

	}

}
