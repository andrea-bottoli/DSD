<%@tag description="Overall Desktop Page template" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<html>

<t:desktopHead>
	<jsp:attribute name="headerExtension">
			<!--  additional header definitions -->
		</jsp:attribute>
</t:desktopHead>

<t:desktopBody>
	<jsp:doBody />
</t:desktopBody>

</html>
