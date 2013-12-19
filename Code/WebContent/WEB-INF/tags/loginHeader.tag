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
		
		
		<br/>
	</div>
	 

</div>