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
<%@tag import="dsd.model.enums.eUserRole"%>
<%@ tag language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>

<body>
	<%
	
  if  (request.isUserInRole(eUserRole.User.toString())) {
      %>
      	<t:headerUser></t:headerUser>
      <%
  }else if(request.isUserInRole(eUserRole.Engineer.toString())){
	  %>
    	<t:headerEngineere></t:headerEngineere>
    <%
  }else if(request.isUserInRole(eUserRole.Administrator.toString())){
	  %>
    	<t:headerAdministrator></t:headerAdministrator>
    <%
  } else {
	  %>
	  <t:loginHeader></t:loginHeader>
	  <%
  }
%>
	
	
	
	<div id="content" class= "content">
		<jsp:doBody />
	</div>
	
	<t:footer>
	
	</t:footer>

</body>
