
<body>
<h1>Current state diagram</h1>
<canvas id="buyers" width="300" height="200"></canvas>
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


<canvas id="sellers" width="450" height="200"></canvas>
<script>
    var buyers = document.getElementById('sellers').getContext('2d');

    
    var buyerData = {
    		labels : ["Miraldi","Ditmar","Dzana","Nikola","Ranieri","June"],
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
