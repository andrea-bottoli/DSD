<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>



<!DOCTYPE html>

<t:desktopPage>
			
<body>

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
			$( "#to" ).datepicker( "option", "minDate", selectedDate);
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
	
    var list = eval('(' + '${rawDataList}' + ')');

	var startDate = list.Dates[0];
	var endDate = list.Dates[list.Dates.length-1];
	var Tchecked = list.TDChecked[0];
	var Dchecked = list.TDChecked[1];
	

	$("#hstartDate").val(startDate);
	$("#hendDate").val(endDate);
	
	if (Tchecked == true) 
		$("#Tvalue").attr("checked",true);
	else 
		$("#Tvalue").attr("checked",false);
	
	if (Dchecked == true) 
		$("#Dvalue").attr("checked",true);
	else 
		$("#Dvalue").attr("checked",false);
	
	
});
</script>



	<div id="historyRange">
	 <h>Choose the range for the history graphs:</h>

		
		<form name="dateRange" action="HistoryView" onsubmit="return validateRange()" method="get" >
			<div class="dateRange">
			
				<div class="formElement">
					<label for="from">Start date:</label><br>
					<input type="text" id="from" name="from"/>
				</div>
				<div class="formElement2">
					<label for="to">End date: </label><br>
					<input type="text" id="to" name="to"/>
				</div>
				
					<div class="formElement3">
					<input type="submit" name="showRange" value=" Show ">
					</div>
				</div>
			</form>
		
		
		<form name="specificDate" action="#" method="get">
			<div class="specificDate">
				<div class="formElement">
					<label>Specific date: </label><br>    
					<input type="text" id="datepicker" name="datepicker"/>
				</div>
				<input type="submit" name="showDate" value=" Show ">
						</div>
		</form>
		
		<div class="specificMonth">
			<form name="specificMonth" action="#" method="get">
				<div class="formElement">
					<label>Specific month: </label><br>
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
				</div>
				<input type="submit" name="showMonth" value=" Show ">
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
				<div id="monthTillNow">
					<input type="submit" value=" Show current month  ">
				</div>
				
			</form>
		</div>
	
	</div>
	
	<div id="chosenHistorySettings">
	

	
	<div id="labelRange">
	 <span><h>Historical data FROM: <h id="labelStartDate">DD.MM.YYYY hh:mm:ss</h> TO: <h id="labelEndDate">DD.MM.YYYY hh:mm:ss</h></h></span>
	</div>
	<form id="TDvalue" name="TDvalue" action="#" method="get">
		<div id="TD">
			<input type="checkbox" id="Tvalue" name="Tvalue" value="true" onclick="this.form.submit()" checked><b>T</b>	
			<input type="checkbox" id="Dvalue" name="Dvalue" value="true" onclick="this.form.submit()" checked><b>D</b> 
			<input type="hidden" id="hstartDate" name="hstartDate" value="Bla">
			<input type="hidden" id="hendDate" name="hendDate" value="Bla">
		</div>
	</form>

	
	</div>
	

	<script >
	
	
    var list = eval('(' + '${rawDataList}' + ')');

	var startDate = new Date(list.Dates[0]);
	var endDate = new Date(list.Dates[list.Dates.length-1]);
	var options = {
		    weekday: "long", year: "numeric", month: "short",
		    day: "numeric", hour: "2-digit", minute: "2-digit"
	};

	$("#labelStartDate").text(startDate.toLocaleTimeString("en-us", options));
	$("#labelEndDate").text(endDate.toLocaleTimeString("en-us", options));


     </script>
	
	
	<div id="wrapperHistory">
				
				<div id="firstHistory" >
				
					<p class="graph_name">Anemometer graph (Wind speed):</p>	
						
					<div id="Wind_speed_graph" class="all_graphs" ></div>
							
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
					    categoryAxis.minPeriod = "ss"; // our data is daily, so we set minPeriod to DD
					    categoryAxis.dashLength = 1;
					    categoryAxis.gridAlpha = 0.15;
					    categoryAxis.minorGridEnabled = true;
					    categoryAxis.axisColor = "#DADADA";
					    
					    // value                
					    var valueAxis = new AmCharts.ValueAxis();
					    valueAxis.axisAlpha = 0.2;
					    valueAxis.title = "Speed (m per second)";
					    valueAxis.dashLength = 1;
					    chart.addValueAxis(valueAxis);
					    
					    // GRAPH
					    var graph = new AmCharts.AmGraph();
					    graph.title = "wind speed";
					    graph.valueField = "visits";
					    graph.bullet = "round";
					    graph.bulletBorderColor = "#FFFFFF";
					    graph.bulletBorderThickness = 2;
					    graph.bulletBorderAlpha = 1;
					    graph.lineThickness = 2;
					    graph.lineColor = "#030db5";
					    graph.negativeLineColor = "#0352b5";
					    graph.balloonText = "[[category]]<br><b><span style='font-size:14px;'>value: [[value]]</span></b>";
					    graph.hideBulletsCount = 50; // this makes the chart to hide bullets when there are more than 50 series in selection
					    chart.addGraph(graph);
					    
					    // GRAPH MAX
					    var graph_max = new AmCharts.AmGraph();
					    graph_max.title = "max wind speed";
					    graph_max.valueField = "visits2";
					    graph_max.bullet = "round";
					    graph_max.bulletBorderColor = "#FFFFFF";
					    graph_max.bulletBorderThickness = 2;
					    graph_max.bulletBorderAlpha = 1;
					    graph_max.lineThickness = 2;
					    graph_max.lineColor = "#b5030d";
					    graph_max.negativeLineColor = "#0352b5";
					    graph_max.balloonText = "[[category]]<br><b><span style='font-size:14px;'>value: [[value]]</span></b>";
					    graph_max.hideBulletsCount = 50; // this makes the chart to hide bullets when there are more than 50 series in selection
					    chart.addGraph(graph_max);
					    
					    //LEGEND
					    var legend = new AmCharts.AmLegend();
					    legend.bulletType = "round";
					    legend.equalWidths = false;
					    legend.valueWidth = 120;
					    legend.useGraphSettings = true;
					    legend.color = "#010541";
					    chart.addLegend(legend);
					    
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
					    chart.write("Wind_speed_graph");
					});

					// generate some random data, quite different range
					function generateChartData() {
					   
					    var list = eval('(' + '${rawDataList}' + ')');

						var array = list.Dates;
						var dataSet = list.ValuesOfWindSpeed;
						var max_windSpeed = list.ValuesOfWindSpeed_MAX;

						
					    for (var i = 0; i < array.length; i++) {
					        // we create date objects here. In your data, you can have date strings 
					        // and then set format of your dates using chart.dataDateFormat property,
					        // however when possible, use date objects, as this will speed up chart rendering.                    
					        var newDate = new Date(array[i]);
					  		        
					        chartData.push({
					            date: newDate,
					            visits: dataSet[i],
					            visits2: max_windSpeed[i]
					        });
					    }
					}

					// this method is called when chart is first inited as we listen for "dataUpdated" event
					function zoomChart() {
					    // different zoom methods can be used - zoomToIndexes, zoomToDates, zoomToCategoryValues
					    chart.zoomToIndexes(chartData.length - 40, chartData.length - 1);
					}

					</script>
				</div>
				
				
				<div id="secondHistory">
		
					<p class="graph_name">Wind direction:</p>
					<div id="Sonar_graph" class="all_graphs" ></div>
					
					<script type="text/javascript">
					var chart2;
					var chartData2 = [];
					var chartCursor2;

					AmCharts.ready(function () {
					    // generate some data first
					    generateChartData2();
					    
					    // SERIAL CHART    
					    chart2 = new AmCharts.AmSerialChart();
					    chart2.dataDateFormat = "YYYY-MM-DD hh:mm:ss";
					    chart2.pathToImages = "http://www.amcharts.com/lib/3/images/";
					    chart2.dataProvider = chartData2;
					    chart2.categoryField = "date";
					    
					    
					    // listen for "dataUpdated" event (fired when chart is rendered) and call zoomChart method when it happens
					    chart2.addListener("dataUpdated", zoomChart2);
					    
					    // AXES
					    // category
					    var categoryAxis = chart2.categoryAxis;
					    categoryAxis.parseDates = true; // as our data is date-based, we set parseDates to true
					    categoryAxis.minPeriod = "ss"; // our data is daily, so we set minPeriod to DD
					    categoryAxis.dashLength = 1;
					    categoryAxis.gridAlpha = 0.15;
					    categoryAxis.minorGridEnabled = true;
					    categoryAxis.axisColor = "#DADADA";
					    
					    // value                
					    var valueAxis = new AmCharts.ValueAxis();
					    valueAxis.title = "Angle of wind direction";
					    valueAxis.axisAlpha = 0.2;
					    valueAxis.dashLength = 1;
					    chart2.addValueAxis(valueAxis);
					    
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
					    chart2.addGraph(graph);
					    
					    // CURSOR
					    chartCursor2 = new AmCharts.ChartCursor();
					    chartCursor2.cursorPosition = "mouse";
					    chart2.addChartCursor(chartCursor2);
					    
					    // SCROLLBAR
					    var chartScrollbar = new AmCharts.ChartScrollbar();
					    chartScrollbar.graph = graph;
					    chartScrollbar.scrollbarHeight = 40;
					    chartScrollbar.color = "#FFFFFF";
					    chartScrollbar.autoGridCount = true;
					    chart2.addChartScrollbar(chartScrollbar);
					    
					    // WRITE
					    chart2.write("Sonar_graph");
					});

					// generate some random data, quite different range
					function generateChartData2() {
					   
					    var list = eval('(' + '${rawDataList}' + ')');

						var array = list.Dates;
						var dataSet = list.ValuesOfWindSpeedDirection;


						
					    for (var i = 0; i < array.length; i++) {
					        // we create date objects here. In your data, you can have date strings 
					        // and then set format of your dates using chart.dataDateFormat property,
					        // however when possible, use date objects, as this will speed up chart rendering.                    
					        var newDate = new Date(array[i]);
					  		        
					        chartData2.push({
					            date: newDate,
					            visits: dataSet[i]
					        });
					    }
					}

					// this method is called when chart is first inited as we listen for "dataUpdated" event
					function zoomChart2() {
					    // different zoom methods can be used - zoomToIndexes, zoomToDates, zoomToCategoryValues
					    chart2.zoomToIndexes(chartData2.length - 40, chartData2.length - 1);
					}

					// changes cursor mode from pan to select
					function setPanSelect() {
					    if (document.getElementById("rb1").checked) {
					        chartCursor2.pan = false;
					        chartCursor2.zoomable = true;
					        
					    } else {
					        chartCursor2.pan = true;
					    }
					    chart2.validateNow();
					}  
					</script>
				</div>
			
			
				<div id="thirthHistory" >
				
					<p class="graph_name">Hydrometer Graph:</p>
					<div id="Hydrometer_graph" class="all_graphs" ></div>
				
					<script type="text/javascript">
					var chart3;
					var chartData3 = [];
					var chartCursor3;

					AmCharts.ready(function () {
					    // generate some data first
					    generateChartData1();
					    
					    // SERIAL CHART    
					    chart3 = new AmCharts.AmSerialChart();
					    chart3.dataDateFormat = "YYYY-MM-DD hh:mm:ss";
					    chart3.pathToImages = "http://www.amcharts.com/lib/3/images/";
					    chart3.dataProvider = chartData3;
					    chart3.categoryField = "date";
					    
					    
					    // listen for "dataUpdated" event (fired when chart is rendered) and call zoomChart method when it happens
					    chart3.addListener("dataUpdated", zoomChart3);
					    
					    // AXES
					    // category
					    var categoryAxis = chart3.categoryAxis;
					    categoryAxis.parseDates = true; // as our data is date-based, we set parseDates to true
					    categoryAxis.minPeriod = "ss"; // our data is daily, so we set minPeriod to DD
					    categoryAxis.dashLength = 1;
					    categoryAxis.gridAlpha = 0.15;
					    categoryAxis.minorGridEnabled = true;
					    categoryAxis.axisColor = "#DADADA";
					    
					    // value                
					    var valueAxis = new AmCharts.ValueAxis();
					    valueAxis.axisAlpha = 0.2;
					    valueAxis.title = "m asl";
					    valueAxis.dashLength = 1;
					    chart3.addValueAxis(valueAxis);
					    
					    // GRAPH
					    var graph = new AmCharts.AmGraph();
					    graph.title = "red line";
					    graph.valueField = "visits";
					    graph.bullet = "round";
					    graph.bulletBorderColor = "#FFFFFF";
					    graph.bulletBorderThickness = 2;
					    graph.bulletBorderAlpha = 1;
					    graph.lineThickness = 2;
					    graph.lineColor = "#030db5";
					    graph.negativeLineColor = "#0352b5";
					    graph.balloonText = "[[category]]<br><b><span style='font-size:14px;'>value: [[value]]</span></b>";
					    graph.hideBulletsCount = 50; // this makes the chart to hide bullets when there are more than 50 series in selection
					    chart3.addGraph(graph);
					    
						 // GRAPH MAX
					    var graph_max = new AmCharts.AmGraph();
					    graph_max.title = "river bed hight";
					    graph_max.valueField = "visits2";
					    graph_max.bullet = "round";
					    graph_max.bulletBorderColor = "#FFFFFF";
					    graph_max.bulletBorderThickness = 2;
					    graph_max.bulletBorderAlpha = 1;
					    graph_max.lineThickness = 2;
					    graph_max.lineColor = "#b5030d";
					    graph_max.negativeLineColor = "#0352b5";
					    graph_max.balloonText = "[[category]]<br><b><span style='font-size:14px;'>value: [[value]]</span></b>";
					    graph_max.hideBulletsCount = 50; // this makes the chart to hide bullets when there are more than 50 series in selection
					    chart3.addGraph(graph_max);
					    
					    // CURSOR
					    chartCursor3 = new AmCharts.ChartCursor();
					    chartCursor3.cursorPosition = "mouse";
					    chart3.addChartCursor(chartCursor3);
					    
					    
					    //LEGEND
					    var legend = new AmCharts.AmLegend();
					    legend.bulletType = "round";
					    legend.equalWidths = false;
					    legend.valueWidth = 120;
					    legend.useGraphSettings = true;
					    legend.color = "#010541";
					    chart3.addLegend(legend);
					    
					    // SCROLLBAR
					    var chartScrollbar = new AmCharts.ChartScrollbar();
					    chartScrollbar.graph = graph;
					    chartScrollbar.scrollbarHeight = 40;
					    chartScrollbar.color = "#FFFFFF";
					    chartScrollbar.autoGridCount = true;
					    chart3.addChartScrollbar(chartScrollbar);
					    
					    // WRITE
					    chart3.write("Hydrometer_graph");
					});

					// generate some random data, quite different range
					function generateChartData1() {
					   
					    var list = eval('(' + '${rawDataList}' + ')');

						var array = list.Dates;
						var hidrometerValues = list.ValuesOfHydrometer;
						var sonarValues = list.ValuesOfSonar;

						
					    for (var i = 0; i < array.length; i++) {                   
					        var newDate = new Date(array[i]);
					  		        
					        chartData3.push({
					            date: newDate,
					            visits: hidrometerValues[i],
					            visits2: sonarValues[i]
					        });
					    }
					}

					// this method is called when chart is first inited as we listen for "dataUpdated" event
					function zoomChart3() {
					    // different zoom methods can be used - zoomToIndexes, zoomToDates, zoomToCategoryValues
					    chart3.zoomToIndexes(chartData3.length - 40, chartData3.length - 1);
					}

					</script>

				</div>
				
			<div id="fourthHistory" >
				
					<p class="graph_name">Safety Factor:</p>
					<div id="SafetyFactor_graph" class="all_graphs" ></div>
				
					<script type="text/javascript">
					var chart4;
					var chartData4 = [];
					var chartCursor4;

					AmCharts.ready(function () {
					    // generate some data first
					    generateChartData4();
					    
					    // SERIAL CHART    
					    chart4 = new AmCharts.AmSerialChart();
					    chart4.dataDateFormat = "YYYY-MM-DD hh:mm:ss";
					    chart4.pathToImages = "http://www.amcharts.com/lib/3/images/";
					    chart4.dataProvider = chartData4;
					    chart4.categoryField = "date";
					    
					    
					    // listen for "dataUpdated" event (fired when chart is rendered) and call zoomChart method when it happens
					    chart4.addListener("dataUpdated", zoomChart4);
					    
					    // AXES
					    // category
					    var categoryAxis = chart4.categoryAxis;
					    categoryAxis.parseDates = true; // as our data is date-based, we set parseDates to true
					    categoryAxis.minPeriod = "ss"; // our data is daily, so we set minPeriod to DD
					    categoryAxis.dashLength = 1;
					    categoryAxis.gridAlpha = 0.15;
					    categoryAxis.minorGridEnabled = true;
					    categoryAxis.axisColor = "#DADADA";
					    
					    // value                
					    var valueAxis = new AmCharts.ValueAxis();
					    valueAxis.axisAlpha = 0.2;
					    valueAxis.dashLength = 1;
					    valueAxis.title = "Safety factor";
					    chart4.addValueAxis(valueAxis);
					    
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
					    chart4.addGraph(graph);
					    
					    // CURSOR
					    chartCursor4 = new AmCharts.ChartCursor();
					    chartCursor4.cursorPosition = "mouse";
					    chart4.addChartCursor(chartCursor4);
					    
					    // SCROLLBAR
					    var chartScrollbar = new AmCharts.ChartScrollbar();
					    chartScrollbar.graph = graph;
					    chartScrollbar.scrollbarHeight = 40;
					    chartScrollbar.color = "#FFFFFF";
					    chartScrollbar.autoGridCount = true;
					    chart4.addChartScrollbar(chartScrollbar);
					    
					    // WRITE
					    chart4.write("SafetyFactor_graph");
					});

					// generate some random data, quite different range
					function generateChartData4() {
					   
					    var list = eval('(' + '${rawDataList}' + ')');
						var array = list.Dates;
						var dataSet = list.Safety11;
						
					    for (var i = 0; i < array.length; i++) {                
					        var newDate = new Date(array[i]);
					  		        
					        chartData4.push({
					            date: newDate,
					            visits: dataSet[i]
					        });
					    }
					}

					// this method is called when chart is first inited as we listen for "dataUpdated" event
					function zoomChart4() {
					    // different zoom methods can be used - zoomToIndexes, zoomToDates, zoomToCategoryValues
					    chart4.zoomToIndexes(chartData4.length - 40, chartData4.length - 1);
					}

					</script>

				</div>
			
			</div>
			
</body>

</t:desktopPage>