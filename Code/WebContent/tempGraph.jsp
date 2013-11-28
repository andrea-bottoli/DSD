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