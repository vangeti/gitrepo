mainApp.controller('precriptiveReleaseViewController', function($scope, $sessionStorage,
		$rootScope, $http) {

	$('.fancybox').fancybox();
	
	$('.dontNav').click(function() {
		alert('asdasd');
        return false; // cancel the event
    });
	
	$http.get("http://172.16.30.227:90/ALMAP_Dashboard/devops/rest/prescriptiveAnalysis").then(function(response) {
		var data = response.data;
		var tr;
		var table = $('<table>').addClass('table qctable');
		
		
		tr = $('<tr/>');
		tr.append("<th align='center'>" + "Fix Priority" + "</th>");
		tr.append("<th align='center'>" + "Application Name"  + "</th>");
		tr.append("<th align='center'>" + "Defect Id"  + "</th>");
		tr.append("<th align='center'>" + "Severity"  + "</th>");
		tr.append("<th align='center'>" + "TC Blocked"  + "</th>");
		table.append(tr);
		var priority = 0;
		for (var i = 0; i < data.length; i++) {

			var result = getChildTable(data[i].tCBlockedList);
			
			tr = $('<tr/>');
			tr.append("<td align='center'>" + ++priority + "</td>");	      
			tr.append("<td align='center'>" + data[i].applicationName + "</td>");
			tr.append("<td align='center'>" + data[i].defectId + "</td>");
			tr.append("<td align='center'>" + data[i].severity + "</td>");
			tr.append("<td align='center'><a class='fancybox dontNav' style='color: #0000FF; text-decoration: underline;' href='#inline" + i + "' target='_blank'>" + data[i].count + "</a>" +					
				"<div id='inline" + i + "' style='display: none;'> " + result + "</div>" + "</td>");
			table.append(tr);
		}
		$('</table>');
		$('.prescriptive').append(table);
		
	
		

		function getChildTable(data)
		{			
			var tableData = "";
			var rowHead = '';
			$.each(data, function( index1, value1 ) {
				tableData += '<tr>';
				rowHead = '';
				$.each(value1, function( index, value ) {
					rowHead += '<th>' + index.substr(0,1).toUpperCase() + index.substr(1); + '</th>';
					tableData += '<td>' + value + '</td>';
				});
				tableData += '</tr>';
			});			
			var result = "<h4>TC Blocked:</h4><table class='table qctable'>";	
			result += '<tr>' + rowHead + '</tr>';
			result += tableData;
			result += '</table>';
			return result;
		}

	});

});