<%@ tag language="java" pageEncoding="ISO-8859-1"%>

<div id="header" class="header">
	<div id="logo" class= "logo">
		<img class=center src="SiteImages/logo.png" alt="Real-time bridge monitoring logo" />
	</div>
	
	<div id="title" class=title>
		 <h1>Real-time bridge monitoring</h1>
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
			<span><a href="#">History Diagrams</a></span>
		</div>
	<!-- 	
		<div class="button">
			<span> <a href="http://www.weatheronline.co.uk/Italy.html">WeatherForcast</span>
		</div>
		  -->
		
		<br/>
	</div>
	 
		<div class="login">
		 	<form method="POST" action="j_security_check">
				Username: <input type="text" name="j_username"><br>
				Password: <input type="password" name="j_password"><br>
				<input type="submit" value="Login">
			</form>
		</div>
	
</div>