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
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>

<t:desktopPage>
	<jsp:useBean id="currentValidParameters" type="java.util.ArrayList<dsd.model.Parameter>" scope="request"/>
	<%-- <jsp:useBean id="parametersListForTimestamp" type="java.util.ArrayList<dsd.model.Parameter>" scope="request"/>
	<jsp:useBean id="parameterHistory" type="java.util.ArrayList<dsd.model.Parameter>" scope="request"/> --%>
	<br/>
	<h1>Current valid parameters</h1>
	<hr />
	<br/>
	<div id="parameters">
		<table class="hoverTable">
			<tr>
				<td  style="width:5%; font-weight:900; font-size:100%">Parameter Data ID:</td>
				<td  style="width:10%; font-weight:bold">Parameter ID:</td>
				<td  style="width:20%; font-weight:bold">Name:</td>
				<td  style="width:10%; font-weight:bold">Abb:</td>
				<td  style="width:5%; font-weight:bold">Unit:</td>
				<td  style="width:10%; font-weight:bold">Category:</td>
				<td style="width:5%; font-weight:bold">Val:</td>
				<td  style="width:10%; font-weight:bold">User ID:</td>
				<td  style="width:20%; font-weight:bold">Time stamp:</td>
				<td  style="width:5%; font-weight:bold">Save</td>
			</tr>
			<c:forEach items="${currentValidParameters}" var="parameter">
				<tr>
					<form name="dateRange" action="TestParametersData" method="post" >
						<td >${parameter.parameterDataID}</td>
						<td  >${parameter.parameterID}</td>
						<td >${parameter.name}</td>
						<td >${parameter.abbreviation}</td>
						<td >${parameter.unit}</td>
						<td >${parameter.category}</td>
						<td ><input type="text" name="value" value="${parameter.value}" size="3"/></td>
						<td >${parameter.userID}</td>
						<td >${parameter.timestampDate}</td>
						<td > <input type="submit" name="nikica" value="Save"> </td>
						<input type="hidden" name="parameterID" value="${parameter.parameterID}">
					</form>
				</tr>
			</c:forEach>
		</table>
	</div>
	
</t:desktopPage>
