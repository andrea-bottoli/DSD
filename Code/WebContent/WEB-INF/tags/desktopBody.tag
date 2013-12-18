<%@ tag language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>

<body>
	<%
	String auth = request.getAuthType();
	%>
	<%= request.getAuthType() %>
	<% 
  if  (auth != null) {
      %>
      	<t:header></t:header>
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