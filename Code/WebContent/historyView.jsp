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
		
		
		<div class="specificDate">
			<form name="specificDate" action="#" method="get">
				<div class="formElement">
					<p>Specific date: </p>    
					<input type="text" id="datepicker" name="datepicker"/>
				</div>
				<input type="submit" name="showDate" value=" Show ">
			</form>
		</div>
		
		<div class="specificMonth">
			<form name="specificMonth" action="#" method="get">
				<div class="formElement">
					<p>Specific month: </p>
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
	 <h>Historical data from: DD.MM.YYYY hh:mm:ss to: DD.MM.YYYY hh:mm:ss</h>
	</div>
		<form>
			<div id="TD">
			<input type="checkbox" name="vehicle" value="T"><b>T</b>
			<input type="checkbox" name="vehicle" value="D"><b>D</b> 
			</div>
		</form>
	
	</div>
	
	
	<div id="wrapperHistory">
				
				<div id="firstHistory" >
				
					<p class="graph_name">Wind speed graph:</p>	
						
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
					    chart.write("Wind_speed_graph");
					});

					// generate some random data, quite different range
					function generateChartData() {
					   
					    var list = eval('(' + '${rawDataList}' + ')');

						var array = list.Dates;
						var dataSet = list.ValuesOfWindSpeed;


						
					    for (var i = 0; i < array.length; i++) {
					        // we create date objects here. In your data, you can have date strings 
					        // and then set format of your dates using chart.dataDateFormat property,
					        // however when possible, use date objects, as this will speed up chart rendering.                    
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
				</div>
				
				
				<div id="secondHistory">
		
					<p class="graph_name">Sonar Graph:</p>
					<div id="Sonar_graph" class="all_graphs" ></div>
					
					<script type="text/javascript">
					var chart;
					var chartData = [];
					var chartCursor;

					AmCharts.ready(function () {
					    // generate some data first
					    generateChartData2();
					    
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
					    chart.write("Sonar_graph");
					});

					// generate some random data, quite different range
					function generateChartData2() {
					   
					    var list = eval('(' + '${rawDataList}' + ')');

						var array = list.Dates;
						var dataSet = list.ValuesOfSonar;


						
					    for (var i = 0; i < array.length; i++) {
					        // we create date objects here. In your data, you can have date strings 
					        // and then set format of your dates using chart.dataDateFormat property,
					        // however when possible, use date objects, as this will speed up chart rendering.                    
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
				</div>
			
			
				<div id="thirthHistory" >
				
					<p class="graph_name">Hydrometer Graph:</p>
					<div id="Hydrometer_graph" class="all_graphs" ></div>
				
					<script type="text/javascript">
					var chart;
					var chartData = [];
					var chartCursor;

					AmCharts.ready(function () {
					    // generate some data first
					    generateChartData1();
					    
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
					    chart.write("Hydrometer_graph");
					});

					// generate some random data, quite different range
					function generateChartData1() {
					   
					    var list = eval('(' + '${rawDataList}' + ')');

						var array = list.Dates;
						var dataSet = list.ValuesOfHydrometer;


						
					    for (var i = 0; i < array.length; i++) {
					        // we create date objects here. In your data, you can have date strings 
					        // and then set format of your dates using chart.dataDateFormat property,
					        // however when possible, use date objects, as this will speed up chart rendering.                    
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

				</div>
			
			</div>
			
</body>

</t:desktopPage>