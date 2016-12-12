//gauge1 = loadLiquidFillGauge("fillgauge1", 55);

var config = liquidFillGaugeDefaultSettings();
config.textVertPosition = 0.8;
config.waveAnimateTime = 5000;
config.waveHeight = 0.15;
config.waveAnimate = true;
config.waveOffset = 0.25;
config.valueCountUp = false;
config.displayPercent = false;
var gauge1 = loadLiquidFillGauge("fillgauge1", 50, config);

//var gauge2 = loadLiquidFillGauge("fillgauge2", 50, config);
var gauge3 = loadLiquidFillGauge("fillgauge3", 50, config);
//var gauge4 = loadLiquidFillGauge("fillgauge4", 50, config);

var powerGauge = gauge('#power-gauge', {
	size : 240,
	clipWidth : 240,
	clipHeight : 150,
	ringWidth : 60,
	maxValue : 50000,
	transitionMs : 4000,
});
powerGauge.render();
powerGauge.update(32000);

var powerGauge2 = gauge('#power-gauge2', {
	size : 240,
	clipWidth : 240,
	clipHeight : 150,
	ringWidth : 60,
	maxValue : 5000,
	transitionMs : 4000,
});
powerGauge2.render();
powerGauge2.update(3200);

queue()
//.defer(d3.csv, "Consolidated_Sheet_small.csv")
//.defer(d3.csv, "Consolidated_Sheet.csv")
.defer(d3.csv, "views/lib/Updated_results_sheet.csv")

.await(makeGraphs);

function updateAllGraphs() {

	totalSelectedVal = $('#total-records .number-display').text();
	totalRespTimeVal = $('#totalTime .number-display').text();

	totalThruVal = $('#totalThru .number-display').text();
	totalCPUVal = $('#totalCPU .number-display').text();
	totalMemoryVal = $('#totalMemory .number-display').text();

	totalSelectedVal = Math.round(totalSelectedVal);
	totalRespTimeVal = Math.round(totalRespTimeVal);
	totalThruVal = Math.round(totalThruVal);
	totalCPUVal = Math.round(totalCPUVal);
	totalMemoryVal = Math.round(totalMemoryVal);

	var avgTime = totalRespTimeVal / totalSelectedVal;
	var avgThru = totalThruVal / totalSelectedVal;

	var avgCPU = totalCPUVal / totalSelectedVal;
	var avgMemory = totalMemoryVal / totalSelectedVal;

	gauge1.update(Math.round(avgTime));
	//gauge2.update(Math.round(avgThru));
	powerGauge.update(Math.round(avgThru));

	gauge3.update(Math.round(avgCPU));
	//gauge4.update(Math.round(avgMemory));
	powerGauge2.update(Math.round(avgMemory));

}

function makeGraphs(error, apiData) {

	var dataSet = apiData;
	//var dateFormat = d3.time.format("%m/%d/%Y");
	//var dateFormat = d3.timestamp.format("%H:%M:%S");
	var dateFormat = d3.time.format("%Y-%m-%d;%H:%M:%S.%L");

	dataSet.forEach(function(d) {
		d.timestamp = dateFormat.parse(d.timestamp);
		d.timestamp.setDate(1);
		d.response_time = +d.response_time;
	});

	var ndx = crossfilter(dataSet);

	var datePosted = ndx.dimension(function(d) {
		return d.timestamp;
	});

	//var ResponseTime = ndx.dimension(function(d) { return d.response_time; });
	//var Throughput = ndx.dimension(function(d) { return d.throughput; });

	//ResponseTime = ResponseTime.group();
	//ResponseTime = ResponseTime.group().reduce(function(d) {return d.response_time;})

	ResponseTime = datePosted.group().reduceSum(function(d) {
		return d.response_time;
	});
	//Throughput = datePosted.group();
	Throughput = datePosted.group().reduceSum(function(d) {
		return d.throughput;
	});

	cpuUsage = datePosted.group().reduceSum(function(d) {
		return d.cpu_usage;
	});
	AvailableMemory = datePosted.group().reduceSum(function(d) {
		return d.available_memory;
	});

	var all = ndx.groupAll();

	var netTotalTime = ndx.groupAll().reduceSum(function(d) {
		return d.response_time;
	});
	var netTotalThru = ndx.groupAll().reduceSum(function(d) {
		return d.throughput;
	});

	var netTotalCPU = ndx.groupAll().reduceSum(function(d) {
		return d.cpu_usage;
	});
	var netTotalMemory = ndx.groupAll().reduceSum(function(d) {
		return d.available_memory;
	});

	var minDate = datePosted.bottom(1)[0].timestamp;
	var maxDate = datePosted.top(1)[0].timestamp;

	//Charts
	var dateChart = dc.lineChart("#date-chart");
	var dateChart1 = dc.lineChart("#tp-chart");
	var cpuChart = dc.lineChart("#cpu-chart");
	var memoryChart = dc.lineChart("#memory-chart");

	var totalRecords = dc.numberDisplay("#total-records");

	var totalTime = dc.numberDisplay("#totalTime");
	var totalThru = dc.numberDisplay("#totalThru");

	var totalCPU = dc.numberDisplay("#totalCPU");
	var totalMemory = dc.numberDisplay("#totalMemory");

	dc.dataCount("#row-selection").dimension(ndx).group(all);

	totalRecords.formatNumber(d3.format("d")).valueAccessor(function(d) {
		return d;
	}).group(all);

	totalTime.formatNumber(d3.format("d")).valueAccessor(function(d) {
		return d;
	}).group(netTotalTime);

	totalThru.formatNumber(d3.format("d")).valueAccessor(function(d) {
		return d;
	}).group(netTotalThru);

	totalCPU.formatNumber(d3.format("r")).valueAccessor(function(d) {
		return d;
	}).group(netTotalCPU)
	//.formatNumber(d3.format(".3s"))
	;

	totalMemory.formatNumber(d3.format("d")).valueAccessor(function(d) {
		return d;
	}).group(netTotalMemory);

	dateChart
	.width(550)
	.height(220).margins({
		top : 10,
		right : 50,
		bottom : 30,
		left : 50
	}).dimension(datePosted).group(ResponseTime).renderArea(true)
			.transitionDuration(500).x(
					d3.time.scale().domain([ minDate, maxDate ]))
			.elasticY(true).renderHorizontalGridLines(true)
			.renderVerticalGridLines(true).yAxisLabel("Response Time")
			.xAxisLabel("Time").on('renderlet', function(chart) {

				//			updateAllGraphs();

				/*chart.selectAll('rect').on("click", function(d) {
					//console.log('on click!');
				});*/
			}).on("filtered", function(chart, filter) {
				var count = $('.filter-count').text();
				//console.log('count: ' + count);			
			})
	//.yAxis().ticks(6)
	;

	dateChart1
	.width(550)
	.height(220).margins({
		top : 10,
		right : 50,
		bottom : 30,
		left : 50
	}).dimension(datePosted).group(Throughput).renderArea(true)
			.transitionDuration(500).x(
					d3.time.scale().domain([ minDate, maxDate ]))
			.elasticY(true).renderHorizontalGridLines(true)
			.renderVerticalGridLines(true).xAxisLabel("Time").yAxisLabel(
					"Throughput").on('renderlet', function(chart1) {
				//			updateAllGraphs();		
			}).on("filtered", function(chart1, filter1) {
				//var count = $('.filter-count').text();
				//console.log('count: ' + count);
			}).yAxis().ticks(6);

	cpuChart
	.width(550)
	.height(220).margins({
		top : 10,
		right : 50,
		bottom : 30,
		left : 50
	}).dimension(datePosted).group(cpuUsage).renderArea(true)
			.transitionDuration(500).x(
					d3.time.scale().domain([ minDate, maxDate ]))
			.elasticY(true).renderHorizontalGridLines(true)
			.renderVerticalGridLines(true).xAxisLabel("Time").yAxisLabel(
					"CPU Usage").on('renderlet', function(chart) {
				//			updateAllGraphs();
			}).on("filtered", function(chart, filter) {

			}).yAxis().ticks(6);

	memoryChart
	.width(550)
	.height(220).margins({
		top : 10,
		right : 50,
		bottom : 30,
		left : 50
	}).dimension(datePosted).group(AvailableMemory).renderArea(true)
			.transitionDuration(500).x(
					d3.time.scale().domain([ minDate, maxDate ]))
			.elasticY(true).renderHorizontalGridLines(true)
			.renderVerticalGridLines(true).xAxisLabel("Time").yAxisLabel(
					"Memory").on('renderlet', function(chart) {
				updateAllGraphs();
			}).on("filtered", function(chart, filter) {

			}).yAxis().ticks(6);

	dc.renderAll();

};