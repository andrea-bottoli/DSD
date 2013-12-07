<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>

<t:desktopPage>
<body>
Welcome to the history view! :)
	
		<div id="chartdiv" style="width: 640 px; height: 400px;"></div>
		
		<script type="text/javascript">
		var chart;
		var chartData = [];
		var chartCursor;

		AmCharts.ready(function () {
		    // generate some data first
		    generateChartData();
		    
		    // SERIAL CHART    
		    chart = new AmCharts.AmSerialChart();
		    chart.pathToImages = "http://www.amcharts.com/lib/3/images/";
		    chart.dataProvider = chartData;
		    chart.categoryField = "date";
		    
		    // listen for "dataUpdated" event (fired when chart is rendered) and call zoomChart method when it happens
		    chart.addListener("dataUpdated", zoomChart);
		    
		    // AXES
		    // category
		    var categoryAxis = chart.categoryAxis;
		    categoryAxis.parseDates = true; // as our data is date-based, we set parseDates to true
		    categoryAxis.minPeriod = "DD"; // our data is daily, so we set minPeriod to DD
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
		    var firstDate = new Date();
		    firstDate.setDate(firstDate.getDate() - 500);
		    
		    for (var i = 0; i < 500; i++) {
		        // we create date objects here. In your data, you can have date strings 
		        // and then set format of your dates using chart.dataDateFormat property,
		        // however when possible, use date objects, as this will speed up chart rendering.                    
		        var newDate = new Date(firstDate);
		        newDate.setDate(newDate.getDate() + i);
		        
		        var visits = Math.round(Math.random() * 40) - 20;
		        
		        chartData.push({
		            date: newDate,
		            visits: visits
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