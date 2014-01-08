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



<script type="text/javascript" language="JavaScript">
function AskAndSubmit(t)
{
  var message = "Alarm is on, are you sure you want to send an email with MN domain values to engineer?";
  
  var answer = confirm( message );
  if (answer)
  {
    t.form.submit();
  }
}
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
				
				
				<form action="#" method="POST" name="subscriberAddForm">
					<input type="button" name="Confirm" value="Send the alarm allert message" onclick="AskAndSubmit(this)"/>
				</form>
				 
		
			
			</div>
		
		
			<h3>MN Domain graph:</h3>
			
			
				<div id="MNGraph" >
				
					<p class="graph_name">MN_Domain:</p>	
						
					<div id="Wind_speed_graph" class="mnGraph" ></div>
							
					<script type="text/javascript">
					var chart;

					var chartData = [];

					AmCharts.ready(function () {
						
						
						generateChartData1();//generate data for graph
						
					    // XY Chart
					    chart = new AmCharts.AmXYChart();
					    chart.pathToImages = "../amcharts/images/";
					    chart.pathToImages = "http://www.amcharts.com/lib/3/images/";
					    chart.dataProvider = chartData;
					    chart.startDuration = 1.5;
					    
					    
					    
					    
					    // AXES
					    // X
					    var xAxis = new AmCharts.ValueAxis();
					    xAxis.title = "N[kN]";
					    xAxis.position = "bottom";
					    xAxis.autoGridCount = true;
					    chart.addValueAxis(xAxis);
					    
					    // Y
					    var yAxis = new AmCharts.ValueAxis();
					    yAxis.title = "M[kNm]";
					    yAxis.position = "left";
					    yAxis.autoGridCount = true;
					    chart.addValueAxis(yAxis);
					    
					    // GRAPH Borders of domain
					    var graph = new AmCharts.AmGraph();
					    graph.valueField = "value"; // valueField responsible for the size of a bullet
					    graph.xField = "x";
					    graph.yField = "y";
					    graph.lineAlpha = 1;
					    graph.connect = true;
					    graph.bullet = "bubble";
					    graph.maxBulletSize = 8;
					    graph.lineColor = "#0411e7";
					    graph.balloonText = "N:<b>[[x]]</b> M:<b>[[y]]</b><br>value:<b>[[value]]</b>";
					    chart.addGraph(graph);
					    
					 // GRAPH pilons of the bridge
					    var graphP = new AmCharts.AmGraph();
					    graphP.valueField = "value"; // valueField responsible for the size of a bullet
					    graphP.xField = "n";
					    graphP.yField = "m";
					    graphP.lineAlpha = 0;
					   // graphP.connect = true;
					    graphP.bullet = "bubble";
					    graphP.maxBulletSize = 8;
					    graphP.lineColor = "#e70411";
					    graphP.balloonText = "N:<b>[[x]]</b> M:<b>[[y]]</b><br>value:<b>[[value]]</b>";
					    chart.addGraph(graphP);
					    
					    
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
					
					function generateChartData1() {
						   
					    var list1 = eval('(' + '${MNDomain}' + ')');
					   // var list2 = eval('(' + '${wcPylonArray}' + ')');

						var Mvalues = list1.MValues;
						var Nvalues = list1.NValues;
						
						
					    for (var i = 0; i < Mvalues.length; i++) {
					        // we create date objects here. In your data, you can have date strings 
					        // and then set format of your dates using chart.dataDateFormat property,
					        // however when possible, use date objects, as this will speed up chart rendering.                    
					        //var newDate = new Date(array[i]);
					        
					  		        
					        chartData.push({
					            x: Nvalues[i],
					            y:  Mvalues[i],
					            value: 1
					        });
					        
					        <c:forEach items="${wcPylonArray}" var="item">
							
						        if ("${item.n}" > Nvalues[i] && "${item.n}" < Nvalues[i+1]){
						        						        	
						    		   chartData.push({
								            n: "${item.n}",
								            m: "${item.m}",
								            value : "pilon: ${item.pylonNumber}"
								        });
						    		  
						    		   chartData.push({
								            date: "${item.n}",
								       });
						    		   
						    	   
							    }														
							</c:forEach>
					    }
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
						<td >${item.my}</td>			
							
				</tr>
			</c:forEach>
		</table>
	</div>
	</c:if>
			 
			<div class="clear_float"></div>
		


</t:desktopPage>
