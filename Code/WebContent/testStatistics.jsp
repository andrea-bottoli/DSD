<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>

<t:desktopPage>
	<h1>Statistics</h1>
	
	<p><b>Count of raw data </b></p>
	<p>${requestScope.countRawData}</p>
	<br />
	<p><b> Raw data max timestamp </b></p>
	<p>${requestScope.maxTimestampRawData}</p>
	<br />
	<p><b> Count of Mantove picture files </b></p>
	<p>${requestScope.countMantova}</p>
	<br />
	<p><b> Mantova pictures max timestamp </b></p>
	<p>${requestScope.maxTimestampMantova}</p>
	<br />
	<p><b> Count of Modena picture files </b></p>
	<p>${requestScope.countModena}</p>
	<br />
	<p><b> Modena pictures max timestamp </b></p>
	<p>${requestScope.maxTimestampModena}</p>
	<br />
	<p><b> Count of Analog text files </b></p>
	<p>${requestScope.countAnalog}</p>
	<br />
	<p><b> Analog files max timestamp </b></p>
	<p>${requestScope.maxTimestampAnalog}</p>
	<br />
	<p><b> Count of Sonar text files </b></p>
	<p>${requestScope.countSonar}</p>
	<br />
	<p><b> Sonar files max timestamp </b></p>
	<p>${requestScope.maxTimestampSonar}</p>
	
	<br />
</t:desktopPage>