<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>

<t:desktopPage>
	<jsp:useBean id="currentValidParameters" type="java.util.ArrayList<dsd.model.Parameter>" scope="request"/>
	<%-- <jsp:useBean id="parametersListForTimestamp" type="java.util.ArrayList<dsd.model.Parameter>" scope="request"/>
	<jsp:useBean id="parameterHistory" type="java.util.ArrayList<dsd.model.Parameter>" scope="request"/> --%>
	<h1>Current valid parameters</h1>
	<table border="1">
		<tr>
			<td>Parameter Data ID:</td>
			<td>Parameter ID:</td>
			<td>Name:</td>
			<td>Abbreviation:</td>
			<td>Unit:</td>
			<td>Category:</td>
			<td>Value:</td>
			<td>User ID:</td>
			<td>Time stamp:</td>
			<td>Save</td>
		</tr>
		<c:forEach items="${currentValidParameters}" var="parameter">
			<tr>
				<form name="dateRange" action="TestParametersData" method="post" >
					<td>${parameter.parameterDataID}</td>
					<td>${parameter.parameterID}</td>
					<td>${parameter.name}</td>
					<td>${parameter.abbreviation}</td>
					<td>${parameter.unit}</td>
					<td>${parameter.category}</td>
					<td ><input type="text" name="value" value="${parameter.value}"/></td>
					<td>${parameter.userID}</td>
					<td>${parameter.timestampDate}</td>
					<td> <input type="submit" name="nikica" value="Save"> </td>
					<input type="hidden" name="parameterID" value="${parameter.parameterID}">
				</form>
			</tr>
		</c:forEach>
	</table>
	
	
</t:desktopPage>