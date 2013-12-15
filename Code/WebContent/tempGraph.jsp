<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>

<t:desktopPage>

	<body>
		<div >
			<div id="actual_values">
				<h3>Actual Values</h3>
				<hr />
				<br/>
				<div >
				<table>
					<tr>
					<td class="actual_values_table_entry">Flow rate: </td>
					<td>520 m3/s</td>
					</tr>
					<tr>
					<td class="actual_values_table_entry">Water level:</td>
					<td>17 m</td>
					</tr>
					<tr>
					<td class="actual_values_table_entry">Water speed:</td>
					<td>1 m/s</td>
					</tr>
					<tr>
					<td class="actual_values_table_entry">Wind speed:</td>
					<td>3 m/s</td>
					</tr>
					<tr>
					<td class="actual_values_table_entry">Wind direction:</td>
					<td>32°</td>
					</tr>
					<tr>
					<td class="actual_values_table_entry">River bed height:</td>
					<td>3 m</td>
					</tr>
				</table>
				</div>
				<hr />
				<br/>
			</div>

			<div id="pictures">
				<img class=sensorPics src="SiteImages/mantova11032212564100.jpg" alt="Mantova camera picture of Bridge Borgoforte" />
				<img class=sensorPics src="SiteImages/modena11032213541300.jpg" alt="Modena camera picture of Bridge Borgoforte" />
			</div>
			
			<div id="google_rose">
				<img id="pic_google" src="SiteImages/Google_maps_pic.png" alt="Real-time bridge monitoring logo" />
			</div>
			
			<div class="clear_float"></div>

		</div>
		
		<hr />
		<br/>		

		<div>
			<h3>Last 24 hours trend:</h3>
			
			<div id="wrapper">
			
				<div id="first" >
				
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
				
				
				<div id="second">
		
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
			
			
				<div id="thirth" >
				
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
			
				<hr />
				<br/>
				<h3>Structure of the stack:</h3>
				<div>
					<img id="bridge_structure" src="SiteImages/Stack.png" alt="Table of pilons" />
				</div>
				
			</div>
			 
			<div class="clear_float"></div>
		

	</body>

</t:desktopPage>