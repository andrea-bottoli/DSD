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