<%@ tag language="java" pageEncoding="ISO-8859-1"%>

<div id="header" class="header">
	<div id="logo" class= "logo">
		<img class="logo" src="SiteImages/logo.png" alt="Real-time bridge monitoring logo" />
	</div>
	
	<div id="title" class=title>
		 <h1><b>Real-time bridge monitoring: Borgoforte Bridge</b></h1>
	</div>

			<div class="login">
			<a href="<%=request.getContextPath()%>/logout.jsp">logout</a>
		</div>
		
	<div id="tabs" class="buttons">
		<div class="button">
			<span ><a href="index.jsp">Home</a></span>
		</div>
		
		<div class="button">
			<span><a href="CurrentStateView">Current State</a></span>
		</div>
		
		<div class="button">
			<span ><a href="TestParametersData">Parameters</a></span>
		</div>
	
		<div class="button">
			<span><a href="HistoryView">History Diagrams</a></span>
		</div>
		
		<div class="button">
			<span><a href="TestTimestampAndCounts">Statistics</a></span>
		</div>
	<!-- 	
		<div class="button">
			<span> <a href="http://www.weatheronline.co.uk/Italy.html">WeatherForcast</span>
		</div>
		  -->
		
		<br/>
	</div>
	 

	
</div>