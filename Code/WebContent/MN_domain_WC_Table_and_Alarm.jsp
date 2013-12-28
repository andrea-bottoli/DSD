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

		<script>
//this script is temporarly here

$(function() {
	
    var list = eval('(' + '${MNDomain}' + ')');

	var Tchecked = list.TDChecked[0];
	var Dchecked = list.TDChecked[1];
	
	if (Tchecked == true) 
		$("#Tvalue").attr("checked",true);
	else 
		$("#Tvalue").attr("checked",false);
	
	if (Dchecked == true) 
		$("#Dvalue").attr("checked",true);
	else 
		$("#Dvalue").attr("checked",false);
	
	$("#first").attr("value","notFirst");
});
</script>
		
		
			<div id="chosenHistorySettings">
			
		
			
				<div id="labelRange">
				 <span><h>Pick the T and D parameters : </h></span>
				</div>
				<form id="TDvalue" name="TDvalue" action="#" method="get">
					<div id="TD">
						<input type="checkbox" id="Tvalue" name="Tvalue" value="true" onclick="this.form.submit()" checked><b>T</b>	
						<input type="checkbox" id="Dvalue" name="Dvalue" value="true" onclick="this.form.submit()" checked><b>D</b> 
						<input type="hidden" id="first" name="first" value="First">
					</div>
				</form>
		
			
			</div>
		
		
			<h3>MN Domain graph:</h3>
			
			
				<div id="MNGraph" >
				
					<p class="graph_name">Anemometer graph(Wind speed):</p>	
						
					<div id="Wind_speed_graph" class="mnGraph" ></div>
							
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
					    categoryAxis.parseDates = false; // as our data is date-based, we set parseDates to true
					    categoryAxis.minPeriod = "ss"; // our data is daily, so we set minPeriod to DD
					    categoryAxis.dashLength = 1;
					    categoryAxis.gridAlpha = 0.15;
					    categoryAxis.minorGridEnabled = true;
					    categoryAxis.axisColor = "#DADADA";
					    categoryAxis.title = "N_Values";
					    
					    // value                
					    var valueAxis = new AmCharts.ValueAxis();
					    valueAxis.title = "M_Values";
					    valueAxis.axisAlpha = 0.2;
					    valueAxis.dashLength = 1;
					    chart.addValueAxis(valueAxis);
					    
					    // GRAPH
					    var graph = new AmCharts.AmGraph();
					    graph.title = "wind speed";
					    graph.valueField = "visits";
					    graph.bullet = "round";
					    graph.connect = false;//////////****************************bitno
					    graph.bulletBorderColor = "#FFFFFF";
					    graph.bulletBorderThickness = 2;
					    graph.bulletBorderAlpha = 1;
					    graph.lineThickness = 2;
					    graph.lineColor = "#b5030d";
					    graph.negativeLineColor = "#b5030d";
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
					    graph_max.negativeLineColor = "#b5030d";
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
					   
					    var list1 = eval('(' + '${MNDomain}' + ')');

						var Mvalues = list1.MValues;
						var Nvalues = list1.NValues;
						
						
					    for (var i = 0; i < Mvalues.length/2; i++) {
					        // we create date objects here. In your data, you can have date strings 
					        // and then set format of your dates using chart.dataDateFormat property,
					        // however when possible, use date objects, as this will speed up chart rendering.                    
					        //var newDate = new Date(array[i]);
					  		        
					        chartData.push({
					            date: Nvalues[i],
					            visits:  Mvalues[Mvalues.length-i-1],
					            visits2: Mvalues[i]
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
				
				
				<hr />
				<br/>
				
			
		
		<c:if test = "${pageContext.request.authType !=null}"> 
		<div id="parameters" class=" WorstCaseTable">
		<pclass="graph_name" > Safety Factor</p>
		<table class="hoverTable">
			<tr>
				<td  style="width:5%; font-weight:900; font-size:100%">Pylon ID</td>
				<td  style="width:10%; font-weight:bold">Worst Case</td>
				<td  style="width:20%; font-weight:bold">Combination </td>
				<td  style="width:10%; font-weight:bold">N</td>
				<td  style="width:5%; font-weight:bold">M</td>
				<td  style="width:10%; font-weight:bold">Tx</td>
				<td style="width:5%; font-weight:bold">Ty</td>
				<td  style="width:10%; font-weight:bold">Mx</td>
				<td  style="width:20%; font-weight:bold">My</td>
			</tr>
			<c:forEach items="${wcPylonArray}" var="item">
				<tr>
						<td >${item.pylonNumber}</td>	
						<td >${item.safetyFactor}</td>
						<td >${item.comboNumber}</td>	
						<td >${item.n}</td>
						<td >${item.m}</td>	
						<td >${item.tx}</td>	
						<td >${item.ty}</td>	
						<td >${item.mx}</td>	
						<td >${MNDomain}</td>			
							
				</tr>
			</c:forEach>
		</table>
	</div>
	</c:if>
			 
			<div class="clear_float"></div>
		


</t:desktopPage>
