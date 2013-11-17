<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>

<t:desktopPage>
	<jsp:useBean id="rawDataList" type="java.util.ArrayList<dsd.model.RawData>" scope="request"/>
	<table border="1">
		<tr>
			<td>ID:</td>
			<td>Wind Speed:</td>
			<td>Wind Direction:</td>
			<td>Hydrometer:</td>
			<td>Sonar:</td>
			<td>Sonar Type:</td>
			<td>Timestamp:</td>
		</tr>
		<c:forEach items="${rawDataList}" var="rawData">
			<tr>
				<td>${rawData.rawDataID}</td>
				<td>${rawData.windSpeed}</td>
				<td>${rawData.windDirection}</td>
				<td>${rawData.hydrometer}</td>
				<td>${rawData.sonar}</td>
				<td>${rawData.sonarType}</td>
				<td>${rawData.timestampDate}</td>
			</tr>
		</c:forEach>
	</table>
</t:desktopPage>