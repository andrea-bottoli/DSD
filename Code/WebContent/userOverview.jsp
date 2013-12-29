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
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>

<t:desktopPage>
	<jsp:useBean id="userList" type="java.util.ArrayList<dsd.model.User>" scope="request"/>
	
	<br/>
	<h1>User-Overview</h1>
	<hr />
	<br/>
	<div id="parameters">
		<table class="hoverTable">
			<tr>
				<td  style="width:20%; font-weight:900; font-size:100%">Username</td>
				<td  style="width:25%; font-weight:bold">Surename</td>
				<td  style="width:25%; font-weight:bold">Lastname</td>
				<td  style="width:25%; font-weight:bold">E-Mail</td>
				<td  style="width:2.5%; font-weight:bold">Edit</td>
				<td  style="width:2.5%; font-weight:bold">Delete</td>
				
			</tr>
			<c:forEach items="${userList}" var="item">
				<tr>
						<td >${item.username}</td>	
						<td >${item.lastname}</td>
						<td >${item.surename}</td>	
						<td >${item.email}</td>
						<td ><a href="UserAdministration?edit=${item.username}"><span class="icon">r</span></a></td>	
						<td ><a href="UserAdministration?del=${item.username}"><span class="icon">I</span></a></td>				
							
				</tr>
			</c:forEach>
		</table>
	</div>
	
</t:desktopPage>
