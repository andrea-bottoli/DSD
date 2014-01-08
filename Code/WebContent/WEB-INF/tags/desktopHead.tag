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
<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ attribute name="headerExtension" fragment="true"%>

<head>
	<!-- <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.10/jquery.min.js"></script>  --> 
	
	<link rel="stylesheet" type="text/css" href="CSS/desktop.css" />
	
		 
	<link rel="stylesheet" href="JS/jQueryUI/themes/base/jquery.ui.all.css" />
	
	<script type="text/javascript" src="JS/bridgemonitoring.js"></script>
	<script type="text/javascript" src="JS/Chart.js"></script>
	<script type="text/javascript" src="JS/amCharts/amcharts.js"></script>
	<script type="text/javascript" src="JS/amCharts/serial.js"></script>
	<script type="text/javascript" src="JS/amCharts/xy.js""></script>

	<script type="text/javascript" src="JS/charts.js"></script>

	<script src="JS/jQueryUI/jquery-1.9.1.js"></script>
	<script src="JS/jQueryUI/ui/jquery.ui.core.js"></script>
	<script src="JS/jQueryUI/ui/jquery.ui.widget.js"></script>
	<script src="JS/jQueryUI/ui/jquery.ui.datepicker.js"></script>
	<script type="text/javascript" src="JS/validations.js"></script>
	
	<jsp:invoke fragment="headerExtension" />	
</head>
