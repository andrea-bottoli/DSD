<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>

<t:desktopPage>

	<body>
		<h1>Current state diagram</h1>
		<canvas id="buyers" width="600" height="400"></canvas>
		
		
		
		
		<script type="text/javascript">
		    var buyers = document.getElementById('buyers').getContext('2d');
		    
		    var list = eval('('+'${rawDataList}'+')');
		    
		    var array =  list.Dates;
		    var dataSet = list.ValuesOfWindSpeed;
		    
		    var buyerData = {
		    		labels : array,
		    		datasets : [
		    			{
		    				//fillColor : "rgba(172,194,132,0.4)",
		    				fillColor : "rgba(61, 118, 65, 1)",
		    				//strokeColor : "#ACC26D",
		    				strokeColor : "#660066",
		    				pointColor : "#fff",
		    				pointStrokeColor : "#9DB86D",
		    				data : dataSet
		    			}
		    		]
		    	};
		    new Chart(buyers).Line(buyerData);  
		   
		</script>
		
		<h1>This is so cool! :)</h1>
	</body>

</t:desktopPage>