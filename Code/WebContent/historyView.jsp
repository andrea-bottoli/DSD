<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>

<!DOCTYPE html>

<t:desktopPage>


			
<body>
Welcome to the history view! :)


<script>
//this script is temporarly here

$(function() {
	var min = new Date(2011, 3 - 1, 22);
	var max = new Date(2012,11 - 1, 19);
	$( "#from" ).datepicker({
		defaultDate: "+1w",//TODO: what is default? last month?
		minDate: min,
	//	minDate: -20, //TODO: update min date with our minDate from db
		maxDate: max,  
		numberOfMonths: 2,
		//possible to change both month and year
		changeMonth: true,
		changeYear: true,
		onClose: function( selectedDate ) {
			$( "#to" ).datepicker( "option", "minDate", selectedDate );
		}
	});

	$( "#to" ).datepicker({
		defaultDate: "+1w",
		minDate: min, //min is 
		maxDate: max,
	//	maxDate: "+1M +10D", 
		numberOfMonths: 2,
		//possible to change both month and year
		changeMonth: true, 
		changeYear: true,
		onClose: function( selectedDate ) {
			$( "#from" ).datepicker( "option", "maxDate", selectedDate );
		}
	});
	
	$(function() {
		$( "#datepicker" ).datepicker({ 
			minDate: min,
			maxDate: max,
			changeMonth: true,
			changeYear: true
			 });
	});
});
</script>
	
	<div class="dateRange">
		<form name="dateRange" action="HistoryView" onsubmit="return validateRange()" method="get" >
			<label for="from">Start date: </label>
			<input type="text" id="from" name="from"/>
			<label for="to">End date: </label>
			<input type="text" id="to" name="to"/>
			<input type="submit" name="showRange" value="Show">
			***This part is being implemented. Try it out :)
		</form>
	</div>
	
	<div class="specificDate">
		<form name="specificDate" action="#" method="get">
			<br>Specific date: 
			<input type="text" id="datepicker" name="datepicker"/>
			<input type="submit" name="showDate" value="Show">
			***This part is being implemented. Try it out :)
		</form>
	</div>
	
	<div class="specificMonth">
		<form name="specificMonth" action="#" method="get">
			<br>Specific month: 
			<select name= "month">
	  			<option value="1">January</option>
	  			<option value="2">February</option>
	  			<option value="3">March</option>
	 			<option value="4">April</option>
	 			<option value="5">May</option>
	  			<option value="6">June</option>
	  			<option value="7">July</option>
	 			<option value="8">August</option>
	 			<option value="9">September</option>
	  			<option value="10">October</option>
	  			<option value="11">November</option>
	 			<option value="12">December</option>
			</select>
			<select name="year" >
				 <option value="2011">2011</option>
	  			<option value="2012">2012</option>
			</select>
			<input type="submit" name="showMonth" value="Show">
		</form>
	</div>
	
	<script type="text/javascript">
	/* will read the years...
		function populateList() {
		for(var i=1; i<=31; i++){
		    document.specificMonth.day.options[i-1]=new Option(i, i);
		    }
		}
		
		window.onload=populateList;
		*/
	</script>
	
	<div class="specificDate">
		<form name="specificDate" action="#" method="get">
			Current month till now 
			<input type="submit" value="Show">
		</form>
	</div>
	
	<div id="chartdiv" style="width: 600 px; height: 400px;"></div>
		
		<script type="text/javascript">
		var chart;
		var chartData = [];
		var chartCursor;

		AmCharts.ready(function () {
		    // generate some data first
		    generateChartData();
		    
		    // SERIAL CHART    
		    chart = new AmCharts.AmSerialChart();
		    chart.dataDateFormat = "YYYY-MM-DD hh:mm:ss";
		    chart.pathToImages = "http://www.amcharts.com/lib/3/images/";
		    chart.dataProvider = chartData;
		   	chart.categoryField = "date";
		    
		    // listen for "dataUpdated" event (fired when chart is rendered) and call zoomChart method when it happens
		    chart.addListener("dataUpdated", zoomChart);
		    
		    // AXES
		    // category
		    var categoryAxis = chart.categoryAxis;
		    categoryAxis.parseDates = true; // as our data is date-based, we set parseDates to true
		
		    categoryAxis.minPeriod = "ss"; // our data is in seconds currently, so we set minPeriod to ss
		    categoryAxis.dashLength = 1;
		    categoryAxis.gridAlpha = 0.15;
		    categoryAxis.minorGridEnabled = true;
		    categoryAxis.axisColor = "#DADADA";
		  
		    
		    // value                
		    var valueAxis = new AmCharts.ValueAxis();
		    valueAxis.axisAlpha = 0.2;
		    valueAxis.dashLength = 1;
		    chart.addValueAxis(valueAxis);
		    
		    // GRAPH
		    var graph = new AmCharts.AmGraph();
		    graph.title = "red line";
		    graph.valueField = "visits";
		    graph.bullet = "round";
		    graph.bulletBorderColor = "#FFFFFF";
		    graph.bulletBorderThickness = 2;
		    graph.bulletBorderAlpha = 1;
		    graph.lineThickness = 2;
		    graph.lineColor = "#b5030d";
		    graph.negativeLineColor = "#0352b5";
		    graph.balloonText = "[[category]]<br><b><span style='font-size:14px;'>value: [[value]]</span></b>";
		    graph.hideBulletsCount = 50; // this makes the chart to hide bullets when there are more than 50 series in selection
		    chart.addGraph(graph);
		    
		    // CURSOR
		    chartCursor = new AmCharts.ChartCursor();
		    chartCursor.cursorPosition = "mouse";
		    chart.addChartCursor(chartCursor);
		   
		    
		    // SCROLLBAR
		    var chartScrollbar = new AmCharts.ChartScrollbar();
		    chartScrollbar.graph = graph;
		    chartScrollbar.scrollbarHeight = 40;
		    chartScrollbar.color = "#FFFFFF";
		    chartScrollbar.autoGridCount = true;
		    chart.addChartScrollbar(chartScrollbar);
		    
		    // WRITE
		    chart.write("chartdiv");
		});

		// generate some random data, quite different range
		function generateChartData() {
		 		    
		    var list = eval('(' + '${rawDataList}' + ')');

			var array = list.Dates;
			var dataSet = list.ValuesOfWindSpeed;
			
			for (var i = 0; i < array.length; i++) {
				var newDate = new Date(array[i]);
				
		        chartData.push({
		            date: newDate,
		            visits: dataSet[i]
		        });
			}
		}


		// this method is called when chart is first inited as we listen for "dataUpdated" event
		function zoomChart() {
		    // different zoom methods can be used - zoomToIndexes, zoomToDates, zoomToCategoryValues
		    chart.zoomToIndexes(chartData.length - 40, chartData.length - 1);
		}

		// changes cursor mode from pan to select
		function setPanSelect() {
		    if (document.getElementById("rb1").checked) {
		        chartCursor.pan = false;
		        chartCursor.zoomable = true;
		        
		    } else {
		        chartCursor.pan = true;
		    }
		    chart.validateNow();
		}   
		
		</script>

		

</body>

</t:desktopPage>