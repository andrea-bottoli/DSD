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
			<span ><a href="UserAdministration">User Administration</a></span>
		</div>
	<!-- 	
		<div class="button">
			<span> <a href="http://www.weatheronline.co.uk/Italy.html">WeatherForcast</span>
		</div>
		  -->
		
		<br/>
	</div>
	 

	
</div>
