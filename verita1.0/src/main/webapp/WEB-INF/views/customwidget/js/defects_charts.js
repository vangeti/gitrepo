
	var dataSet;

/* queue()
    .defer(d3.json, "../data/defects_data.json")	
    .await(buildCharts); */
	
function loadData() {
	var api_url = $('#rest_api').val();
	queue()
		//.defer(d3.json, "devops/rest/customdefect")
		//.defer(d3.json, "http://localhost/defects_report/data/defects_data.json")		
		.defer(d3.json, api_url)		
		.await(buildCharts)
	;
}
			
function buildCharts (error, data) {

	if(error)
		throw new Error(error);

		if(data["Defects"]) {
			dataSet = data["Defects"];
		} else {
			dataSet = data;
		}
		var objData = dataSet[0];
		
		 var sortObjData = [];
		Object.keys(objData)
			.sort()
			.forEach(function(v, i) {
				sortObjData[v] = objData[v];
			});
			
	    var objData = sortObjData; 		
		var options = '';
		for (var p in objData) {
			if( objData.hasOwnProperty(p) ) {
			  options += '<option value="'+ p + '">' + p + '</option>';
			} 
		} 
		$('#xColumn').append(options);
		$('#yColumn').append(options);
		$('#groupBy').append(options);
				
		//buildGraph('barChart', '#chart', dataSet, 'Application Name', 'Int', '', '', 'count', 'Severity', '#total-records', 'Application Name', 'Bug Count');
  }
  
  function drawChart() {
	  var dimension = $( "#xColumn option:selected" ).val();
	  var group = $( "#yColumn option:selected" ).val();
	  var chartType = $( "input:radio[name='chartType']:checked" ).val();
	  var aggrFunc = $( "input:radio[name='aggrFunc']:checked" ).val();	  
	  var groupBy = $( "#groupBy option:selected" ).val();
	  
	  var xLabel = dimension;
	  var yLabel = group;
	  if(aggrFunc == 'count') {
		  yLabel = 'Count';
	  }
	  
	  buildGraph(chartType, '#chart', dataSet, dimension, group, aggrFunc, groupBy, '#total-records', xLabel, yLabel);
	  
  }
  

  function buildGraph(chartType, chartId, dataSet, dimension, group, aggrFunc, groupBy, total_records_id, xLabel, yLabel) {
	
	/* var dimDataType = checkDateType(dataSet, dimension);
	if(dimDataType == 'Date') {
		var dateFormat = d3.time.format("%Y-%m-%d");
		dataSet.forEach(function(d) {
			d[dimension] = dateFormat.parse(d[dimension]);
			d[dimension].setDate(1);
		});		
	} */
	dimDataType = '';

	var chart = getChartInstance(chartType, chartId);	
	
	var ndx = crossfilter(dataSet);
	
    if(dimension == ""){		
		$("#errorMsg").text("Please select x-axis");
		return false;		
	}
	
	var objDim = ndx.dimension(function(d) { return d[dimension]; });
	
	if(aggrFunc == 'count') {
		var objGroup   = objDim.group();
	} else if(group != ""){
		var objGroup = objDim.group().reduceSum(function(d) {return d[group];});		
	} else {
		$("#errorMsg").text("Please select aggregate function or y-axis to plot the graph");
		return false;		
	}
		  
	if(groupBy != '') {
		var groupByDim = ndx.dimension(function(d) { return d[groupBy]; });				
		var groupByGroup   = groupByDim.group();
		var groups = groupByGroup.top(Infinity);

		groups.sort(function(a, b) {
			return a.key - b.key;
		});
				
		var groupGroups = [];
		var sVals = [];
		groups.forEach(
			function (item, index) {
				var sVal = groups[index].key;
				sVals.push(sVal);
				groupGroups[index] = objDim.group().reduceSum(function(d) {
																if (d[groupBy]==sVal) { return true; }
																else { return false; }
															});	
			}
		);
	}
	
	if(aggrFunc == "count" || group != "") {
		var objGroups = objGroup.top(Infinity);
		if(objGroups.length > 0) {
			objGroups.forEach(
				function (item, index) {
					var sVal = objGroups[index].value;
					if(!sVal) {
						$("#errorMsg").text("Please select appropriate values to plot the graph");
						return false;
					} else {
						$("#errorMsg").text("");
						return true;
					}
				}
			);
		}
	} else if(groupBy != '') {
		groups.forEach(
			function (item, index) {
				var sVal = groups[index].key;
				if(!sVal) {
					$("#errorMsg").text("Please select appropriate values to plot the graph");
					return false;
				} else {
					$("#errorMsg").text("");
					return true;					
				}
			}
		);		
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
			.width(330).height(280)
		;
		if(chartType != 'pieChart') {
			//.margins({top: 10, right: 50, bottom: 30, left: 50})
			chart.margins({top: 20, right: 10, bottom: 80, left: 50});
		}
		
		chart.dimension(objDim);
		
		if(groupBy != '') {			
			 for(var i = 0; i < groupGroups.length; i++) {				
				if(i==0) {
					chart.group(groupGroups[i], sVals[i]);
				} else {
					chart.stack(groupGroups[i], sVals[i]);
				}
			}
			
			chart
				.colors(d3.scale.ordinal()			
				.domain(sVals)
				.range(["#ff1a1a", "#ff4d4d", "#ff8080", "#ffb3b3"])
				)
			; 
			
			chart.title(function(d) {
				var g = this.layer;
				return d.key + ' - ' + groupBy + '-' + this.layer + ': ' + d.value;
			});
			
		} else if(aggrFunc == "count" || group != "") {
			chart.group(objGroup);
		}
		
		
			
		if(chartType == 'lineChart') {
			chart.renderArea(true);		
		}
		
		if(chartType != "pieChart") {
			if(dimDataType == 'Date') {
				chart.x(d3.time.scale().domain([minDate, maxDate]));
				//chart.x(d3.time.scale().domain(dc.filters.RangedFilter(minDate, maxDate)));
				chart.brushOn(false);
				
				chart.on('renderlet', function (chart) {
						chart.selectAll("g.x text")
						  .attr('dx', '-30')
						  .attr('dy', '0')
						  .attr('transform', "rotate(-55)");
				});				
			}
			else {
				chart
				.x(d3.scale.ordinal().domain(objDim))
				.xUnits(dc.units.ordinal);
			}
			
			chart
				.transitionDuration(500)
				.elasticY(true)
				.elasticX(true)
				.renderHorizontalGridLines(true)
				.renderVerticalGridLines(true)
				.yAxisLabel(yLabel)
				.xAxisLabel(xLabel);
		}
		
		if(chartType == "pieChart") {
			chart
				//.slicesCap(4)
				.innerRadius(50)
				.legend(dc.legend())
				.on('renderlet', function(chart) {
					chart.selectAll('text.pie-slice').text(function(d) {
						var percentage = dc.utils.printSingleValue((d.endAngle - d.startAngle) / (2*Math.PI) * 100) + '%';
						var value = d.data.key + ' ' + percentage;
						return value;
					})
				})
			;
	
		}
		if(chartType == 'barChart' || chartType == 'stackedChart') {
			chart.barPadding(0.4).outerPadding(0.2);
			//chart.yAxis().tickFormat(d3.format("d"));
		}
		
		//dc.renderAll();
		chart.render();
}

function getChartInstance(chartType, chartId) {	
	var chartInst;
	if(chartType == 'lineChart') {
		chartInst = dc.lineChart(chartId);
	} else if(chartType == 'barChart') {
		chartInst = dc.barChart(chartId);
	} else if(chartType == 'stackedChart') {
		chartInst = dc.barChart(chartId);
	} else if(chartType == 'pieChart') {
		chartInst = dc.pieChart(chartId);
	}		
	return chartInst;	
}

function checkDateType(dataSet, dimension) {
	var objData = dataSet[0];	
	var dimDataType = '';
	for (var p in objData) {
		if( objData.hasOwnProperty(p) ) {
			if(p == dimension) {
				var temp = Date.parse(objData[p]);
				if(temp) {
					dimDataType = 'Date';				
				}
				break;
			}
		} 
	}
	return dimDataType;
}
  
$('input:radio[name="chartType"]').on('click', function() {
  if($(this).val() == "stackedChart") {
	$('#group_by').show();
  } else {
	$("#group_by select").val("");
	$('#group_by').hide();
  }  
});

/* $('input:radio[name="chartType"]').change(
function(){
	if ($(this).is(':checked') && $(this).val() == 'Yes') {
	}
}); */

$('input:radio[name="aggrFunc"]').on('click', function() {
  if($(this).val() != "") {
	  $('#yColumn').prop('disabled', true);
  }
  else {
	  $('#yColumn').prop('disabled', false);	  
  }
});


$('input[id="csv_file"]').on('change', function(e) {
	upload(e);
});

//$('#csv_file').addEventListener('change', upload, false);

	
    function browserSupportFileUpload() {
        var isCompatible = false;
        if (window.File && window.FileReader && window.FileList && window.Blob) {
        isCompatible = true;
        }
        return isCompatible;
    }

    // Method that reads and processes the selected file
    function upload(evt) {
        if (!browserSupportFileUpload()) {
            alert('The File APIs are not fully supported in this browser!');
            } else {
                var data = null;
                var file = evt.target.files[0];
                var reader = new FileReader();
                reader.readAsText(file);
                reader.onload = function(event) {
                    var csvData = event.target.result;
					data = $.csv.toObjects(csvData);
                    if (data && data.length > 0) {
						buildCharts ('', data);
                    } else {
                        console('No data to import!');
                    }
                };
                reader.onerror = function() {
                    console('Unable to read ' + file.fileName);
                };
            }
        }
		

