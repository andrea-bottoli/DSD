
<form method="POST" action="<%= request.getContextPath()%>/j_security_check">
			Username: <input type="text" name="j_username"><br>
			Password: <input type="password" name="j_password"><br>
			<input type="submit" value="Login">
	</form>