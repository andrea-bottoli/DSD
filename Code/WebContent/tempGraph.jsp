<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>

<t:desktopPage>

	<body>
		<h1>Current state diagram</h1>
		<canvas id="buyers" width="300" height="200"></canvas>
		
		<script>
		    var buyers = document.getElementById('buyers').getContext('2d');
		    var time2 = '${rawDataList}';
		    for (var i=0; i<time2.length; i++)
		    { 
		    	document.write(time2[i] + "<br>");
		    }
		    var buyerData = {
		    		labels : [ time2.length ,"February","March","April","May","June"],
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
		
		<h1>This is so cool! :)</h1>
	</body>

</t:desktopPage>