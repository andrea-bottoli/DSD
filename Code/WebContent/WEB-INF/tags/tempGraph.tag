<%@ tag language="java" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>

<t:desktopHead>
	<jsp:attribute name="headerExtension">
			<!--  additional header definitions -->
		</jsp:attribute>
</t:desktopHead>


<body>
<h1>Current state diagram</h1>
<canvas id="buyers" width="600" height="400"></canvas>
<script>
    var buyers = document.getElementById('buyers').getContext('2d');

    
    var buyerData = {
    		labels : ["January","February","March","April","May","June"],
    		datasets : [
    			{
    				fillColor : "rgba(172,194,132,0.4)",
    				strokeColor : "#ACC26D",
    				pointColor : "#fff",
    				pointStrokeColor : "#9DB86D",
    				data : [203,156,99,251,305,247]
    			}
    		]
    	}
    new Chart(buyers).Line(buyerData);
    
</script>
<h1>This is cool! :)</h1>
</body>
