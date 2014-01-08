<%------------------------------------------------------------------------------
#Copyright 2013 Andrea Bottoli, Lorenzo Pagliari, Marko Brčić, Dzana Kujan, Nikola Radisavljevic, Jörn Tillmanns, Miraldi Fifo
#
#Licensed under the Apache License, Version 2.0 (the "License");
#you may not use this file except in compliance with the License.
#You may obtain a copy of the License at
#
#  http://www.apache.org/licenses/LICENSE-2.0
#
#Unless required by applicable law or agreed to in writing, software
#distributed under the License is distributed on an "AS IS" BASIS,
#WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
#See the License for the specific language governing permissions and
#limitations under the License.
------------------------------------------------------------------------------%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="dsd.model.WorstPylonCase"%>
<%@page import="dsd.model.calculation.Pylon"%>
<!DOCTYPE html>

<t:desktopPage>
	<jsp:useBean id="wcPylonArray" type="java.util.ArrayList<dsd.model.WorstPylonCase>" scope="request"/>

	
		<div >
			<div id="actual_values">
				<h3>Actual Values</h3>
				<hr />
				<br/>
				<div >
				<table>
					<tr>
					<td class="actual_values_table_entry">Flow rate: </td>
					<td>${water_flow_rate} m3/s</td>
					</tr>
					<tr>
					<td class="actual_values_table_entry">Water level:</td>
					<td>${water_level} m</td>
					</tr>
					<tr>
					<td class="actual_values_table_entry">Water speed:</td>
					<td>${water_speed}  m/s</td>
					</tr>
					<tr>
					<td class="actual_values_table_entry">Wind speed:</td>
					<td>${wind_speed} m/s</td>
					</tr>
					<tr>
					<td class="actual_values_table_entry">Wind direction:</td>
					<td>${wind_direction}°</td>
					</tr>
					<tr>
					<td class="actual_values_table_entry">River bed height:</td>
					<td>${river_bed_height} m</td>
					</tr>
				</table>
				</div>
				<hr />
				<br/>
			</div>

			<div id="pictures">
				<p class=sensorPicsNames><b>Mantova side:	</b></p>
				<img class=sensorPics src="SiteImages/mantova11032212564100.jpg" alt="Mantova camera picture of Bridge Borgoforte" />
				<p class=sensorPicsNames><b>Modena side:	</b></p>
				<img class=sensorPics src="SiteImages/modena11032213541300.jpg" alt="Modena camera picture of Bridge Borgoforte" />
			</div>
			
			<div id="google_rose">
				<img id="pic_google" src="SiteImages/Google_maps_pic.png" alt="Real-time bridge monitoring logo" />
			</div>
			
			<div id="stack_structure_frame">
				<img id="stack_structiure_pic" src="SiteImages/img01_bis.jpg" alt="picture of stack" />
			</div>
			
			<div class="clear_float"></div>

		</div>
		
		<hr />
		<br/>		

		<div>
			<h3>Last 24 hours trend:</h3>
			
			<div id="wrapper">
			
				<div id="first" >
				
					<p class="graph_name">Anemometer graph(Wind speed):</p>	
						
					<div id="Wind_speed_graph" class="all_graphs" ></div>
							
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
					    chart.addListener("dataUpdated", zoomChart1);
					    
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
					    valueAxis.title = "speed (m per second)";
					    valueAxis.axisAlpha = 0.2;
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
					    
					    var legend = new AmCharts.AmLegend();
					    legend.bulletType = "round";
					    legend.equalWidths = false;
					    legend.valueWidth = 120;
					    legend.useGraphSettings = true;
					    legend.color = "#010541";
					    chart.addLegend(legend);
					    
					    // WRITE
					    chart.write("Wind_speed_graph");
					});

					// generate some random data, quite different range
					function generateChartData1() {
					   
					    var list1 = eval('(' + '${rawDataList}' + ')');

						var array = list1.Dates;
						var windSpeed = list1.ValuesOfWindSpeed;
						var max_windSpeed = list1.ValuesOfWindSpeed_MAX;


						
					    for (var i = 0; i < array.length; i++) {
					        // we create date objects here. In your data, you can have date strings 
					        // and then set format of your dates using chart.dataDateFormat property,
					        // however when possible, use date objects, as this will speed up chart rendering.                    
					        var newDate = new Date(array[i]);
					  		        
					        chartData.push({
					            date: newDate,
					            visits:  windSpeed[i].toFixed(2),
					            visits2: max_windSpeed[i].toFixed(2)
					        });
					    }
					}

					// this method is called when chart is first inited as we listen for "dataUpdated" event
					function zoomChart1() {
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
					    valueAxis.title = "Angel of wind direction";
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
					        var newDate = new Date(array[i]);
					  		
					        chartData2.push({
					            date: newDate,
					            visits: dataSet[i].toFixed(2)
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
			
			
				<div id="thirth" >
				
					<p class="graph_name">Hydrometer Graph(Water height):</p>
					<div id="Hydrometer_graph" class="all_graphs" ></div>
				
					<script type="text/javascript">
					var chart3;
					var chartData3 = [];
					var chartCursor3;

					AmCharts.ready(function () {
					    // generate some data first
					    generateChartData3();
					    
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
					    valueAxis.title = "(m asl)";
					    valueAxis.axisAlpha = 0.2;
					    valueAxis.dashLength = 1;
					    chart3.addValueAxis(valueAxis);
					    
					    // GRAPH 
					    var graph = new AmCharts.AmGraph();
					    graph.title = "water hight";
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
					    
					    // SCROLLBAR
					    var chartScrollbar = new AmCharts.ChartScrollbar();
					    chartScrollbar.graph = graph;
					    chartScrollbar.scrollbarHeight = 40;
					    chartScrollbar.color = "#FFFFFF";
					    chartScrollbar.autoGridCount = true;
					    chart3.addChartScrollbar(chartScrollbar);
					    
					    var legend = new AmCharts.AmLegend();
					    legend.bulletType = "round";
					    legend.equalWidths = false;
					    legend.valueWidth = 120;
					    legend.useGraphSettings = true;
					    legend.color = "#010541";
					    chart3.addLegend(legend);
					    
					    // WRITE
					    chart3.write("Hydrometer_graph");
					});

					// generate some random data, quite different range
					function generateChartData3() {
					   
					    var list = eval('(' + '${rawDataList}' + ')');

						var array = list.Dates;
						var hidrometerValues = list.ValuesOfHydrometer;
						var sonarValues = list.ValuesOfSonar;


						
					    for (var i = 0; i < array.length; i++) {
					        // we create date objects here. In your data, you can have date strings 
					        // and then set format of your dates using chart.dataDateFormat property,
					        // however when possible, use date objects, as this will speed up chart rendering.                    
					        var newDate = new Date(array[i]);
					  		        
					        chartData3.push({
					            date: newDate,
					            visits: hidrometerValues[i].toFixed(2),
					            visits2: sonarValues[i].toFixed(2)
					        });
					    }
					}

					// this method is called when chart is first inited as we listen for "dataUpdated" event
					function zoomChart3() {
					    // different zoom methods can be used - zoomToIndexes, zoomToDates, zoomToCategoryValues
					    chart3.zoomToIndexes(chartData3.length - 40, chartData3.length - 1);
					}

					// changes cursor mode from pan to select
					function setPanSelect() {
					    if (document.getElementById("rb1").checked) {
					        chartCursor3.pan = false;
					        chartCursor3.zoomable = true;
					        
					    } else {
					        chartCursor3.pan = true;
					    }
					    chart3.validateNow();
					}  
					</script>

				</div>
			 
			<div class="clear_float"></div>
		


</t:desktopPage>
