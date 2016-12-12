
	var dataSet;

queue()
    .defer(d3.json, "devops/rest/customdefect")	
    .await(buildCharts);
	
function buildCharts (error, data) {

	if(error)
		throw new Error(error);

		dataSet = data["Defects"];
		
		var objData = dataSet[0];
		var options = '';
		for (var p in objData) {
			if( objData.hasOwnProperty(p) ) {
			  options += '<option value="'+ p + '">' + p + '</option>';
			} 
		}		
		$('#xColumn').append(options);
		$('#yColumn').append(options);
		
		//buildGraph('barChart', '#chart', dataSet, 'Severity', 'Int', '', '', 'count', '#total-records', 'Severity Level', 'Bug Count');
  }
  
  function drawChart() {
	  var dimension = $( "#xColumn option:selected" ).val();
	  var group = $( "#yColumn option:selected" ).val();
	  var chartType = $( "#chartType option:selected" ).val();
	  var aggrFunc = $( "#aggrFunc option:selected" ).val();
	  
	  var xLabel = dimension;
	  var yLabel = group;
	  if(aggrFunc == 'count') {
		  yLabel = 'Count';
	  }
	  buildGraph(chartType, '#chart', dataSet, dimension, 'Int', '', '', aggrFunc, '#total-records', xLabel, yLabel);
	  
  }
  function buildGraph(chartType, chartId, dataSet, dimension, dimDataType, group, groupDataType, aggrFunc, total_records_id, xLabel, yLabel) {
	
	var chart = getChartInstance(chartType, chartId);	
	
	var ndx = crossfilter(dataSet);
	
	var objDim = ndx.dimension(function(d) { return d[dimension]; });
	
	if(aggrFunc == 'count') {
		var objGroup   = objDim.group();
	} else {
		var objGroup = objDim.group().reduceSum(function(d) {return d[group];});		
	}
	
	var all = ndx.groupAll();
	
	if(dimDataType == 'Date') {
		var minDate = objDim.bottom(1)[0][dimension];
		var maxDate = objDim.top(1)[0][dimension];
	}

	var totalRecords = dc.numberDisplay(total_records_id);		
	totalRecords
		.formatNumber(d3.format("d"))
		.valueAccessor(function(d){return d; })
		.group(all);

		chart
		.width(500).height(300)
		.margins({top: 10, right: 50, bottom: 30, left: 50})
		.dimension(objDim)
		.group(objGroup);
		
		if(chartType == 'lineChart') {
			chart.renderArea(true);		
		}
		
		if(dimDataType == 'Date') {
			chart.x(d3.time.scale().domain([minDate, maxDate]));
		}
		else {
			chart
			.x(d3.scale.ordinal().domain(objDim))
			.xUnits(dc.units.ordinal);
		}
		
		chart
		.transitionDuration(500)
		.elasticY(true)
		.renderHorizontalGridLines(true)
    	.renderVerticalGridLines(true)
		.yAxisLabel(yLabel)
		.xAxisLabel(xLabel);
		if(chartType == 'barChart') {
			chart.barPadding(0.4).outerPadding(0.2);
		}
		/* .on('renderlet', function(chart) {			

		})
		 .on("filtered", function (chart, filter) {
		});  */
			
		dc.renderAll();
}

function getChartInstance(chartType, chartId) {
	
	var chartInst;
	if(chartType == 'lineChart') {
		chartInst = dc.lineChart(chartId);
	} else if(chartType == 'barChart') {
		chartInst = dc.barChart(chartId);
	} 
	return chartInst;
	
}


