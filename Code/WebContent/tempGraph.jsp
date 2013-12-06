<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>

<t:desktopPage>

	<body>
		<div class="actualValues">
			<h3>Actual Values</h3>
			<hr />
			<br/>
			<div>
				<p>
					Anemometer: <b>0.315 m/s</b>
				</p>
				<p>
					Sonar: <b>2.19 m</b>
				</p>
				<p>
					Hydrometer: <b>17.28625 m</b>
				</p>
			</div>
			<hr />
			<br/>
			<div>
				<img class=sensorPics src="${requestScope.mantovaPath}" alt="Mantova camera picture of Bridge Borgoforte" />
				<img class=sensorPics src="${requestScope.modenaPath}" alt="Modena camera picture of Bridge Borgoforte" />
			</div>
			<div class="clear_float"></div>

		</div>
		
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
		
		

		<div class="graphs">
			<h3>Last 24 hours trend:</h3>
			<hr>

			<div class="leftDiv">
				<div id="windSpeedGraph" class="graph">
					<p>Wind Speed Graph:</p>
					<canvas id="windSpeed" width="600" height="400"></canvas>

					<script type="text/javascript">
						var buyers = document.getElementById('windSpeed')
								.getContext('2d');

						var list = eval('(' + '${rawDataList}' + ')');

						var array = list.Dates;
						var dataSet = list.ValuesOfWindSpeed;

						var buyerData =
						{
							labels : array,
							datasets : [
							{
								//fillColor : "rgba(172,194,132,0.4)",
								fillColor : "rgba(61, 118, 65, 1)",
								//strokeColor : "#ACC26D",
								strokeColor : "#660066",
								pointColor : "#fff",
								pointStrokeColor : "#9DB86D",
								data : dataSet
							}]
						};
						new Chart(buyers).Line(buyerData);
					</script>
				</div>


				<div id="sonarGraph" class="graph">
					<p>Sonar Graph:</p>
					<canvas id="sonar" width="600" height="400"></canvas>
					<script type="text/javascript">
						var sonar = document.getElementById('sonar')
								.getContext('2d');

						var list = eval('(' + '${rawDataList}' + ')');

						var array = list.Dates;

						var sonarValues = list.ValuesOfSonar;

						var sonarData =
						{
							labels : array,
							datasets : [
							{
								//fillColor : "rgba(172,194,132,0.4)",
								fillColor : "rgba(61, 118, 65, 1)",
								//strokeColor : "#ACC26D",
								strokeColor : "#660066",
								pointColor : "#fff",
								pointStrokeColor : "#9DB86D",
								data : sonarValues
							}]
						};
						new Chart(sonar).Line(sonarData);
					</script>
				</div>
			</div>
			<div class="rightDiv">

				<div id="hydrometerGraph" class="graph">
					<p>Hydrometer Graph:</p>
					<canvas id="hydrometer" width="600" height="400"></canvas>
					<script type="text/javascript">
						var hydrometer = document.getElementById('hydrometer')
								.getContext('2d');

						var list = eval('(' + '${rawDataList}' + ')');

						var array = list.Dates;

						var hydrometerValues = list.ValuesOfHydrometer;

						var hydrometerData =
						{
							labels : array,
							datasets : [
							{
								//fillColor : "rgba(172,194,132,0.4)",
								fillColor : "rgba(61, 118, 65, 1)",
								//strokeColor : "#ACC26D",
								strokeColor : "#660066",
								pointColor : "#fff",
								pointStrokeColor : "#9DB86D",
								data : hydrometerValues
							}]
						};
						new Chart(hydrometer).Line(hydrometerData);
					</script>
				</div>
			</div>
			<div class="clear_float"></div>
		</div>

	</body>

</t:desktopPage>