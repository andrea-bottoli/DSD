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
<%@page import="dsd.model.WorstPylonCase"%>
<%@page import="dsd.model.calculation.Pylon"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>

<t:desktopPage>
	<jsp:useBean id="wcPylonArray" type="java.util.ArrayList<dsd.model.WorstPylonCase>" scope="request"/>
	
	<br/>
	<h1>Current valid parameters</h1>
	<hr />
	<br/>
	<div id="parameters">
		<table class="hoverTable">
			<tr>
				<td  style="width:5%; font-weight:900; font-size:100%">Pylon ID</td>
				<td  style="width:10%; font-weight:bold">Worst Case</td>
				<td  style="width:20%; font-weight:bold">Combination number/lable</td>
				<td  style="width:10%; font-weight:bold">N</td>
				<td  style="width:5%; font-weight:bold">M</td>
				<td  style="width:10%; font-weight:bold">Tx</td>
				<td style="width:5%; font-weight:bold">Ty</td>
				<td  style="width:10%; font-weight:bold">Mx</td>
				<td  style="width:20%; font-weight:bold">My</td>
			</tr>
			<c:forEach items="${wcPylonArray}" var="item">
				<tr>
						<td >${item.pylonNumber}</td>	
						<td >${item.safetyFactor}</td>
						<td >${item.comboNumber}</td>	
						<td >${item.n}</td>
						<td >${item.m}</td>	
						<td >${item.tx}</td>	
						<td >${item.ty}</td>	
						<td >${item.mx}</td>	
						<td >${item.my}</td>			
							
				</tr>
			</c:forEach>
		</table>
	</div>
	
</t:desktopPage>
