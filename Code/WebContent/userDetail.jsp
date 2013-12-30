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
<%@page import="dsd.model.User"%>
<%@page import="dsd.model.enums.eUserRole"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>

<script type="text/javascript">
function chkFormular () {
 	if (document.getElementById("username").value == "") {
    	alert("Please add a username!");
    	document.getElementById("username").focus();
    	return false;
  	}
  	if (document.getElementById("username").value == "new") {
	    alert("username cannot be 'new' !");
	    document.getElementById("username").value="";
	    document.getElementById("username").focus();
	    return false;
	}
  	if (document.getElementById("password1").value != document.getElementById("password2").value) {
    	alert("Passwords are not the identical.");
    	document.getElementById("password1").value="";
    	document.getElementById("password2").value="";
    	document.getElementById("password1").focus();
    	return false;
  	}
}
</script>

<t:desktopPage>
	<jsp:useBean id="user" type="dsd.model.User" scope="request"/>
	<jsp:useBean id="roles" type="dsd.model.enums.eUserRole[]" scope="request"/>
	<jsp:useBean id="error"	type="java.lang.String" scope="request"/>
	
	<br/>
	<h1>User-Detail</h1>
	<hr/>
	<c:if test="${error =='username'}">
		<span class="error">Dieser Username existiert schon!</span>
	</c:if>
	<br/>
	<div id="userdetails">
	<form method="post" action="UserAdministration" onsubmit="return chkFormular()">
	<ul class="ul_left">
		<li>Username:</li>
		<li>Surename:</li>
		<li>Lastname:</li>
		<li>Email:</li>
		<li>Password:</li>
		<li>repeat Password:</li>
		<li>User-Role:</li>
	</ul>
		<ul class="ul_right" >
		<li><input type="text" name="username" id="username" value="${user.username}"></li>
		<li><input type="text" name="surename" id="surename" value="${user.surename}"></li>
		<li><input type="text" name="lastname" id="lastanme" value="${user.lastname}"></li>
		<li><input type="text" name="email"	id="email" value="${user.email}"></li>
		<li><input type="password" name="password1" id="password1"></li>
		<li><input type="password" name="password2" id="password2"></li>
		<li><select name="role" id="role">
		<c:forEach items="${roles}" var="item">
			<option>${item}</option>
		</c:forEach>
		
		</select> </li>
		
	</ul>
	<span class="clear_float"></span>
	<input type="hidden" name="save" value="true">
	<input type="hidden" name="userID" value="${user.ID}">
	<span><input type="submit" value="Save"><input type="button" onClick="window.location.href='UserAdministration'" value="Cancle"></span>
	</form>
	</div>
	
</t:desktopPage>
