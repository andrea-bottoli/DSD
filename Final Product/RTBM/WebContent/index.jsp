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
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>

<!DOCTYPE html>

<t:desktopPage>
<div>

<h1 style="text-align:center"><strong>REAL-TIME BRIDGE MONITORING</strong></h1>

<p style="text-align:center"><img alt="" src="SiteImages/logo.png" style="height:227px; width:300px" /></p>

<h2>DESCRIPTION</h2>

<p style="margin-left:10px">Real-Time Bridge Monitoring allows to verify in real-time (with a delay of 1 hour due to local server delay) the status of a number of piers of a bridge (in this case the Borgoforte bridge on Po river in Italy) based on some physical parameters, including:</p>

<ul>
	<li>wind speed [m/s]</li>
	<li>wind direction [°]</li>
	<li>water level [m asl]</li>
	<li>water rate [m3/s]</li>
	<li>river bed level [m asl]</li>
	<li>presence of debris</li>
	<li>presence and type of traffic</li>
	<li>material of the bridge</li>
	<li>structural parameters</li>
</ul>

<h2>USERS</h2>

<p style="margin-left:10px">Users that could use the system are:</p>

<ul>
	<li><strong>External users</strong>

	<ul>
		<li>​anyone who wants to access the site without having to log​</li>
	</ul>
	</li>
	<li><strong>Operators</strong>
	<ul>
		<li>the persons responsible for the monitoring of the bridge​</li>
	</ul>
	</li>
	<li><strong>Engineers</strong>
	<ul>
		<li>the persons responsible for the monitoring of the bridge and the definition of the structural model that describes the bridge and the forces acting on it​</li>
	</ul>
	</li>
	<li><strong>Administrator</strong>
	<ul>
		<li>the person responsible for adding new users (with the right role) and for edit or delete existing users</li>
	</ul>
	</li>
</ul>

<h2>FEATURES</h2>

<p style="margin-left:10px">Real-Time Bridge Monitoring also provides access to different types of users with different roles; they could perform different tasks such as:</p>

<ul>
	<li>view the current state</li>
	<li>view the historical states</li>
	<li>view the MN domain</li>
	<li>add/edit/delete users</li>
	<li>edit some parameters</li>
	<li>view some statistics</li>
	<li>launch the alarm</li>
</ul>

<p>For more Information please look at the <a href="http://www.fer.unizg.hr/rasip/dsd">offical DSD Homepage</a>.</p>
<h2>TEAM</h2>
<ul class="ul_no_dot team-members-list-left">
<li class="team-members-list-item"><img alt="" src="SiteImages/dev-team/ab.jpg" class="team-img"> Andrea Bottoli</li>
<li class="team-members-list-item"><img alt="" src="SiteImages/dev-team/dk.jpg" class="team-img"> Dzana Kujan</li>
<li class="team-members-list-item"><img alt="" src="SiteImages/dev-team/lp.jpg" class="team-img"> Lorenzo Pagliari</li>
<li class="team-members-list-item"><img alt="" src="SiteImages/dev-team/mf.jpg" class="team-img">  Miraldi Fifo</li>
</ul>
<ul class="ul_no_dot team-members-list-right">
<li class="team-members-list-item"><img alt="" src="SiteImages/dev-team/mb.jpg" class="team-img"> Marko Brčić</li>
<li class="team-members-list-item"><img alt="" src="SiteImages/dev-team/nr.jpg" class="team-img"> Nikola Radisavljevic</li>
<li class="team-members-list-item"><img alt="" src="SiteImages/dev-team/jt.jpg" class="team-img"> J&ouml;rn Tillmanns</li>
</ul>

<div class ="clear_float">
</div>
<h2>CONTACT</h2>
If you want to contact us, send an e-mail to:
<a href="andrea.bottoli@mail.polimi.it"> Andrea Bottoli</a>

<h2>LICENSE</h2>
We provide our Software under the <a href="LICENSE-2.0.jsp">Apache License 2.0</a>.
 You will find the source code in our <a href="https://github.com/andrea-bottoli/DSD">GitHub Repository</a>. All pictograms used on this side are licenced under <a href="license_pictogramms.jsp">SIL Open Font License</a>. 
</div>

</t:desktopPage>
