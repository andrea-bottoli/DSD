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
		 	<!--  <form method="POST" action="<%=request.getContextPath()%>/j_security_check">
				Username: <input type="text" name="j_username"><br>
				Password: <input type="password" name="j_password"><br>
				<input type="submit" value="Login">
			</form>
			-->
			<a href="<%=request.getContextPath()%>/Login">Login</a>
		</div>
	

	
	<div id="tabs" class="buttons">
		
		<div class="button">
			<span><a href="index.jsp">Home</a></span>
		</div>
		
		<div class="button">
			<span><a href="CurrentStateView">Current State</a></span>
		</div>
		
		<div class="button">
			<span><a href="HistoryView">History Diagrams</a></span>
		</div>
		
		
		<br/>
	</div>
	 

</div>
