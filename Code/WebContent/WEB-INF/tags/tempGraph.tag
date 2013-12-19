<%------------------------------------------------------------------------------
#Copyright 2013 Andrea Bottoli, Lorenzo Pagliari, Marko Br?i?, Dzana Kujan, Nikola Radisavljevic, Jörn Tillmanns, Miraldi Fifo
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
