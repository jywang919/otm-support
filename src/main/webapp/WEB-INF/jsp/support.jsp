<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="en">
<head>
	<!-- https://spring.io/blog/2013/12/19/serving-static-web-content-with-spring-boot -->
  <meta http-equiv="Content-Security-Policy"
  content="
  default-src * 'self' 'unsafe-inline'

  'unsafe-eval'
;

  style-src * 'unsafe-inline'
;
  font-src *
 ;
  img-src * 'self'
  data:
  ;
  media-src *
  ;

  ">
  <!--  https://www.mkyong.com/spring-boot/spring-boot-hello-world-example-jsp/ -->
	<!-- Access the bootstrap Css like this, 
		Spring boot will handle the resource mapping automcatically -->
		
		<!-- 
	<link rel="stylesheet" type="text/css" href="webjars/bootstrap/3.3.7/css/bootstrap.min.css" />
	
	<spring:url value="/css/main.css" var="springCss" />
	<link href="${springCss}" rel="stylesheet" />
	
	<c:url value="/css/main.css" var="jstlCss" />
	<link href="${jstlCss}" rel="stylesheet" />
	 -->
	 <link href="<c:url value="/resources/css/main.css" />" rel="stylesheet">
	 
	<script language="javascript" type="text/javascript">
			function expandOptions(off,on, expand) {
				var filtersOff = document.getElementById(off);
				var filtersOn = document.getElementById(on);
				//var resultsObj = document.getElementById("results");
				if (filtersOff && filtersOn) {
					if (expand) {
						filtersOff.style.display = 'none';
						//resultsObj.style.zIndex = '-1';
						filtersOn.style.display = '';
					} else {
						filtersOff.style.display = '';
						filtersOn.style.display = 'none';
						//filtersOn.style.zIndex = '-1';
						//resultsObj.style.zIndex = '9';
					}
				}
				return false;
			}
			
			function allOff() {
				expandOptions('prodListOff','prodListOn',false);
				expandOptions('canDoListOff','canDoListOn',false);
				expandOptions('iOff','iOn',false);
				expandOptions('gOff','gOn',false);
				expandOptions('rAttOff','rAttOn',false);
				expandOptions('sDataOff','sDataOn',false);
				expandOptions('faqOff','faqOn',false); 
				expandOptions('ppOff','ppOn',false);
				return false;
			}
			
			function toggle(e) {
				//alert(e.id+" last style.background: "+e.style.background);
				var btnios = document.getElementById('btnios');
				var btnAndroid = document.getElementById('btnAndroid');
				var divios=document.getElementById('divios');
				var divAndroid=document.getElementById('divAndroid');
				e.style.background='#008CBA';
				
				if(e.id.localeCompare('btnios') == 0){
					btnAndroid.style.background='';
					divAndroid.style.display = 'none'
					divios.style.display = '';
				}
				else{  //android 
					btnios.style.background='';
					divios.style.display = 'none'
					divAndroid.style.display = ''
				}
				return false;
			}
	</script>
</head>
<body onLoad="allOff(); expandOptions('faqOff','faqOn',true); toggle(document.getElementById('btnios')); return false">
	<!-- 
	<nav class="navbar navbar-inverse">
		<div class="container">
			<div class="navbar-header">
				<a class="navbar-brand" href="#">Spring Boot</a>
			</div>
			<div id="navbar" class="collapse navbar-collapse">
				<ul class="nav navbar-nav">
					<li class="active"><a href="#">Home</a></li>
					<li><a href="#about">About</a></li>
				</ul>
			</div>
		</div>
	</nav>
	<div class="container">

		<div class="starter-template">
 
			<h1>Spring Boot Web JSP Example</h1>
			<h2>Message: ${message}</h2>
 
		</div>

	</div>
	 -->

	<script type="text/javascript" src="webjars/bootstrap/3.3.7/js/bootstrap.min.js"></script>


<H1>Welcome to Wang Mobile! v20190528 </H1>
		<a href="mailto:jywang45@hotmail.com" target="_top">Email to Author:  Jiyong Jeff Wang</a>
		<br>
		<br>
<H3>Since Apple won't allow update of the products listed below, iOS users can get the latest updates with better offline map coverage from within the free version of <a href="https://itunes.apple.com/us/app/offline-tour-map-scaled-subway/id1425138817?ls=1&mt=8" >Offline Tour Map Scaled Subway</a> by downloading the specific product.
<br>
<br>  <a href="https://itunes.apple.com/us/app/offline-tour-map-scaled-subway/id1425138817?ls=1&mt=8" >See latest free versions</a></h3>	
		<br>		
	<H3>Private Policy <a href="#pp" onclick="allOff();expandOptions('ppOff','ppOn',true);" > - Jump to Private Policy </a></h3>		
	<H3>Frequently Asked Questions <a href="#faq"  onclick="allOff();expandOptions('faqOff','faqOn',true);"> - Jump to FAQ </a></h3>
	
	
	<div id="prodListOff" onclick="allOff();expandOptions('prodListOff','prodListOn',true); return false;"><H3>Products covered:<<a  href="#">+</a>></H3></div>
	<div id="prodListOn"><H3 onclick="expandOptions('prodListOff','prodListOn',false); return false;">Products covered: <<a  href="#">-</a>></H3>
			<div><button id="btnios" onclick="toggle(this)">iOS</button>&nbsp&nbsp<button id="btnAndroid" onclick="toggle(this)">Android</button></div>
			<div id="divios">
					<h4>Base module available to all other modules</h4>
					<ul>
						<li><h4><a href="https://itunes.apple.com/us/app/attendance-roll-call-track-map/id1107597609?ls=1&mt=8">Attendance Roll Call On Map</a></h4></li>
					</ul>
					<h4>Parkways</h4>
					<ul>
						<li><h4><a href="https://itunes.apple.com/us/app/brp-offline-tour-map-poas/id1317303326?ls=1&mt=8"> Blue Ridge Parkway (BRP) Tour Map</a></h4>
						<li><h4><a href="https://itunes.apple.com/us/app/subway-tour-map-scaled-offline/id1425138817?ls=1&mt=8">Natchez Trace Tour Maps Offline</a></h4></li>
						<li><h4><a href="https://itunes.apple.com/us/app/subway-tour-map-scaled-offline/id1425138817?ls=1&mt=8">Skyline Drive Pkwy Tour Maps Offline</a></h4></li>
						<li><h4><a href="https://itunes.apple.com/us/app/george-w-mem-pkwy-tour-maps/id1408918868?ls=1&mt=8">George Washington Memorial Parkway Tour Maps</a></h4></li>
					</ul>
						<h4>U.S. Cities</h4>
					<ul>
						<li><h4><a href="https://itunes.apple.com/us/app/subway-tour-map-scaled-offline/id1425138817?ls=1&mt=8">NYC Subway Map</a></h4></li>
						<li><h4><a href="https://itunes.apple.com/us/app/chicago-subway-bus-offline-map/id1353211614?ls=1&mt=8">Chicago Scaled Offline Subway/Bus Maps</a></h4></li>
						<li><h4><a href="https://itunes.apple.com/us/app/subway-tour-map-scaled-offline/id1425138817?ls=1&mt=8"> D.C. Scaled Offline Subway/Bus Maps</a></h4></li>
					</ul>	
					<h4>International Cities</h4>
					<ul>
						<li><h4><a href="https://itunes.apple.com/us/app/subway-tour-map-scaled-offline/id1425138817?ls=1&mt=8">Beijing Offline Subway Maps</a></h4></li>
						<li><h4><a href="https://itunes.apple.com/us/app/shanghai-scaled-subway-map/id1330021752?ls=1&mt=8">Shanghai Offline Subway Maps</a></h4></li>
						<li><h4><a href="https://itunes.apple.com/us/app/subway-tour-map-scaled-offline/id1425138817?ls=1&mt=8">Tokyo City Offline Subway Maps</a></h4></li>
						<li><h4><a href="https://itunes.apple.com/us/app/subway-tour-map-scaled-offline/id1425138817?ls=1&mt=8">Paris Scaled Offline Tour Maps</a></h4></li>						
						<li><h4><a href="https://itunes.apple.com/us/app/subway-tour-map-scaled-offline/id1425138817?ls=1&mt=8">Munich Offline Subway Tour Maps</a></h4></li>		
						<li><h4><a href="https://itunes.apple.com/us/app/subway-tour-map-scaled-offline/id1425138817?ls=1&mt=8">London Offline Subway Tour Maps</a></h4></li>
						<li><h4><a href="https://itunes.apple.com/us/app/subway-tour-map-scaled-offline/id1425138817?ls=1&mt=8">Moscow Scaled Offline Tour Maps</a></h4></li>	
						<li><h4><a href="https://itunes.apple.com/us/app/barcelona-offline-sub-tour-map/id1391980619?ls=1&mt=8">Barcelona Offline Subway Tour Maps</a></h4>
						<li><h4><a href="https://itunes.apple.com/us/app/hong-kong-subway-tour-maps/id1404024404?ls=1&mt=8">Hong Kong Subway Tour Maps</a></h4></li>
					</ul>
						<h4>U.S. National Parks</h4>
					<ul>
						<li><h4><a href="https://itunes.apple.com/us/app/zion-national-park-tour-maps/id1368085196?ls=1&mt=8">Zion National Park Tour Maps Offline</a></h4></li>
						<li><h4><a href="https://itunes.apple.com/us/app/great-smoky-mountain-tour-maps/id1371060935?ls=1&mt=8">Great Smoky Mt Tour Maps Offline</a></h4></li>
						<li><h4><a href="https://itunes.apple.com/us/app/grand-canyon-map-offline-scale/id1373816729?ls=1&mt=8">Grand Canyon Tour Maps Offline</a></h4></li>
						<li><h4><a href="https://itunes.apple.com/us/app/yellow-stone-map-offline-scale/id1378421152?ls=1&mt=8">Yellow Stone Tour Maps Offline</a></h4></li>
					</ul>
			</div> <!-- end divios -->
			
			
			<div id="divAndroid">
					<h4>Base module available to all other modules</h4>
					<ul>
						<li><h4>Attendance Roll Call On Map</h4></li>
					</ul>
					<h4>Parkways</h4>
					<ul>
						<li><h4><a href="https://play.google.com/store/apps/details?id=com.wang.mobile.nat2&hl=en">Natchez Trace Tour Maps Offline</a></h4></li>
						<li><h4><a href="https://play.google.com/store/apps/details?id=com.wangmobile.citiiap.brp&hl=en"> Blue Ridge Parkway (BRP) Tour Map</a></h4>
						<li><h4><a href="https://play.google.com/store/apps/details?id=com.wangmobile.citiiap.ss&hl=en"> Skyline Drive Pkwy Tour Maps Offline</a></h4></li>
					
					</ul>
						<h4>U.S. Cities</h4>
					<ul>
						<li><h4><a href="https://play.google.com/store/apps/details?id=com.wangmobile.citiiap.ny&hl=en">NYC Subway Map</a></h4></li>
					</ul>	
					<h4>International Cities</h4>
					<ul>
						<li><h4><a href="https://play.google.com/store/apps/details?id=com.wangmobile.citiiap.tk&hl=en">Tokyo City Offline Subway Maps</a></h4></li>
					</ul>					
			</div><!-- end dicAndroid -->
	</div>
     <div id="canDoListOff" onclick="expandOptions('canDoListOff','canDoListOn',true); return false;"><H3> You can do following with this application::<<a  href="#">+</a>></H3></div>
	 <div id="canDoListOn" onclick="expandOptions('canDoListOff','canDoListOn',false); return false;"><H3> You can do following with this application::<<a  href="#">-</a>></H3>
       <ul>
	    <li>
	       	Display your locations and trail on scaled subway map, tour map, offline map or online map. <a href="#trackMember"> - see How can I see the latest/current location of (a) member(s) or mine</a><br>
	    </li><li>
       		Find nearby subway routes and station(s) on the scaled offline subway map (for subway maps only)
       	</li>	<li>
        	Manage your friends and track them on the maps  <a href="#trackMember"> - see How can I see the latest/current location of (a) member(s) or mine</a>
        </li><li>
        	Plot current/past trails of your friends or yourself on the maps  <a href="#trackMember"> - see How can I see the latest/current location of (a) member(s) or mine</a>
        </li><li>
        	Keep notes, reminder, and run a timer
        </li><li>
        	Log and report (in PDF) peoples attendance on incidents or events including your own personal activities <a href="#rollcall"> - see Record attendance for an incident and check for reports</a>
        </li><li>
        	Backup your contacts and migrate to another phone  <a href="#shareContacts"> - see How to share my contacts with friends?</a>
        </li><li>
		</ul>
	</div>

    <div id="iOff" onclick="expandOptions('iOff','iOn',true); return false;"><H3> Incidents::<<a  href="#">+</a>></H3></div>
	<div id="iOn" onclick="expandOptions('iOff','iOn',false); return false;"><H3> Incidents::<<a  href="#">-</a>></H3>	
            An incident or event could be a session of a class in a school, a fire drill in a company, a working day for a crew, service crew's presence, uncle Joe's birthday, your shipping list, or the next oil change on your car, etc.
           	<br>
    </div>
 	<div id="gOff" onclick="expandOptions('gOff','gOn',true); return false;"><H3> Groups::<<a  href="#">+</a>></H3></div>
	<div id="gOn" onclick="expandOptions('gOff','gOn',false); return false;"><H3> Groups::<<a  href="#">-</a>></H3>
		 	A group could be the name of the department in a company, a class name in a school, a working crew, a unit of soldiers in the army, or your self for your personal activities<br>
		    If this the the first time you use the application, you should start by creating your own group(s) to organize peoples<br>
		    Once you have the group(s) created, you can then add people to the group(s) or modify people's group association(s) afterwards<br>
            !!!Important, two default groups are created:  Myself and Contacts - please don't delete them !!!<br>
	</div>

    <a id="rollcall"></a>
	<div id="rAttOff" onclick="expandOptions('rAttOff','rAttOn',true); return false;"><H3> Record attendance for an incident and check for reports::<<a  href="#">+</a>></H3></div>
	 <div id="rAttOn" onclick="expandOptions('rAttOff','rAttOn',false); return false;"><H3> Record attendance for an incident and check for reports::<<a  href="#">-</a>></H3>
     
        1.  go to 'Member(s)/Roll Call/Track on Map' page<br>
        2.  Select the incident by clicking the incident button at the lower right corner<br>
        3.  Select the group(s) that is(are) participating the incident by clicking the left side of the second row of the page<br>
        4.  Click the Roll Call button at the lower left corner to start the Roll Call (recording)<br>
        5.  Check people(s) who showed up and optionally add notes for the incident<br>
        6.  Click Save/End<br>
        7.  Click the report symbol on upper right corner of 'Member(s)/Roll Call/Track on Map' page for PDF report of attendances<br>
        8.  Attendance results can be accessed on Incident/Event page
	</div>
 	<div id="sDataOff" onclick="expandOptions('sDataOff','sDataOn',true); return false;"><H3> Sample Data::<<a  href="#">+</a>></H3></div>
	<div id="sDataOn" onclick="expandOptions('sDataOff','sDataOn',false); return false;"><H3> Sample Data::<<a  href="#">-</a>></H3>
        The app is initially loaded with template groups, peoples, and incidents, so you can play with them before deleting and creating your own data.
        If you have pre version 2.2.8 and experience problems changing member's information, try to change its image first.
        !!!Important, Don't delete the two default groups:  Myself and Contacts !!! - deleting them may result in strange behavior
	</div>
	<h3>Help (within the Application)</h3>
        You can return to the Help page by clicking the ... at the upper right corner followed by clicking ? then Help.

    <a id="faq"></a>
    <h3></h3>
    <div id="faqOff" onclick="expandOptions('faqOff','faqOn',true); return false;"><H3>Frequently Asked Questions(FAQ)::<<a  href="#">+</a>></H3></div>
	<div id="faqOn"><H3>Frequently Asked Questions(FAQ)::<<a  href="#" onclick="expandOptions('faqOff','faqOn',false); return false;">-</a>></H3>
		  <h4>Q: What are the buttons on top of the map page for?</h4>
          	<img src="<c:url value='/resources/imgs/map_btns_top.jpg'/>">
	      <h4>A: As shown above, 4 to 6 buttons are provided in the top of "Subway (Tour) Map / My Tracks ..." page: they are (starting from left):</h4>
	      	<img src="<c:url value='/resources/imgs/t10.png'/>" > - back to the main menu - from main menu, go to Timer page, then click the bell symbol at upper left of the page will take you to the root menu which has more tools<br>
	        <img src="<c:url value='/resources/imgs/t20.png'/>" > - search for points of attractions(POAs) by key word or category (appears only when POAs added)<br>
	        <img src="<c:url value='/resources/imgs/t30.png'/>" > - plot named trails on maps (disappears once you started collecting your locations)<br>
	        <img src="<c:url value='/resources/imgs/t40.png'/>" > - start collecting your location (will turn to red when collecting)<br>	          
	        <img src="<c:url value='/resources/imgs/t50.png'/>" > - switch for uploading your collected locations so you or your authorized friends can plot your locations on the maps - will turn to solid-red when uploading is on<br>
	        <img src="<c:url value='/resources/imgs/t60.png'/>" > - show your fitness data: steps walked, stairs climbed, etc.<br>
	          
          <h4>Q: How to turn on/off tour map /subway map / offline map</h4>
          	<img src="<c:url value='/resources/imgs/map_btns.jpg'/>"/>
	          <h4>A: As shown above, 4 to 6 buttons are provided in the bottom of "Subway (Tour) Map / My Tracks ..." page depending on data availability of that product:</h4>
	          	<img src="<c:url value='/resources/imgs/b1.png'/>" > - On/Off for offline map tiles<br>
	          	<img src="<c:url value='/resources/imgs/b2.png'/>" > - Multi-task menu for your current location, plotting a circled pin at a specific latitude/longitude, etc.<br>
	          	<img src="<c:url value='/resources/imgs/b3.png'/>" > - On/Off for scaled Subway/Tour Map<br>
	          	<img src="<c:url value='/resources/imgs/b4.png'/>" > - On/Off for diagram of Subway or Tour Map (may be scaled)<br>
	          	<img src="<c:url value='/resources/imgs/b5.png'/>" > - Set mapping region when using the application inside China, so your location, points of attractions will match the maps<br>
	          	<img src="<c:url value='/resources/imgs/b6.png'/>" > - Add/Remove/search points of attractions, restaurants, lodgings to/from maps, or open tour guide document<br>
	          <br>

         <a id="shareContacts"></a>
          <h4>Q: How to share my contacts with friends?</h4>
	          <h4>A:</h4>
	          <h4>Step A:  on your device</h4>
	          1 Open this application, go to Backup/Restore/Report Email page<br>
	          2 Select Backup (Send Email) and Application Data then click 'Go'<br>
	          3 In the email sender specify your friend's email address and modify the Subject line !!! DO NOT MODIFY ANY PART OF THE EMAIL BODY !!!<br>
	          4 In the email sender, send the email to your friend<br>
	         <h4>Step B:  on your friend's device</h4>
	          1 Open the email from you, select the content of the email body and copy<br>
	          2 Install this application, then open this application, go to Backup/Restore/Report Email page<br>
	          3 Select Restore/Replace and Application Data<br>
	          4 Paste the copied email content from 1 above into the text area between the two buttons then click 'Go'<br>
	          5 !!!Important - Restart this application!!! (you can re-start by powering off then powering on your friend's phone/unit)<br>
	          6 Now all members' information in this app. are transferred to your friend's phone/unit<br>

          <h4>Q: How to export all my members information to phone's contact's ?</h4>
	          <h4>A: </h4>
	          	1. In this application, from main menu go to 'Member(s)/Roll Call/Track on Map' page<br>
	            2. Click the bi-directional arrow symbol next to the map symbol on the page top and follow on-screen instructions<br>

			<a id="addMemberId"></a>
           <h4>Q: How to add member or my location id(s)</h4>
	           <h4>A: </h4>
	           Step A:  On your device, ask the member to send his/her location id which can be initiated if you are in member creation/editing page and click the location button<br>
	           Step B:  On member's device, go to 'xxx / My Tracks' page and click the location button on the lower left of the map page and follow the instructions to send the location id to you<br>
	            The member sends his/her location from within 'xxx / My Tracks' page via SMS<br>
	           Step C:  On your device, after receiving the location id, edit the member's profile, and put the location id in the locid field and make sure the location button next to it is turned on (solid)<br>
	           Note: Member's location id may change if the member re-installs the application<br>

           <h4>Q: How can I see the latest/current location of (a) member(s) or mine ?</h4>
	           <h4>A: </h4>
	           1. Follow <a href="#addMemberId"> How to add member or my location id(s)</a> described above<br>
	           2. On member's device go to 'xxx / My Tracks...' page's top, click the Star button located in the middle of the page's top, location upload button so they both turn into red.  On some Android devices, it may take up to an hour before it starts to post - an alert will show with message: 'Successfully posted your location ...'<br>
	           3. On this device, from main menu goto to 'Member(s)/Roll Call ...' page, click the map symbol in the upper right corner, then click the location button to show member's location<br>
			  <br>
			   Note: To be able to upload location or see location, make sure Internet connection is available on the device and this application has the permission to the Internet. 
			<a id="trackMember"></a>
           <h4>Q: How can I see the tracks of (a) member(s) or mine?</h4>
	           <h4>A: </h4>
	           1. Follow <a href="#addMemberId"> How to add member or my location id(s)</a> described above<br>
	           2. On member's device<br>
	           <ul>
	           	 <li>go to 'xxx / My Tracks...' page's top</li>
	           	 <li>click the White Star in Green button located in page's top middle</li>
	           	 <li>click the location upload/push button (a square arrow-out) so it turns into red</li>
	           	 <li>move the device for about 100 meter (more responsive when driving)</li>
	           	 <li>Android ONLY: click the 3-bar button in page bottom, then "My current Location" to get an immediate location push</li>
	           	 <li>An alert will show with message: 'Successfully posted your location ...'</li>
	           	 <li>The trail of the device will be plotted on the 'xxx / My Tracks' page as device is moving</li>
	           	 </ul>
	           <br>
	           3. On this device, from main menu goto to 'Member(s)/Roll Call ...' page, click the map button in the upper right corner, then click the tracks button at the upper right of the map to show member's tracks<br>
	           <br>	        
			   Notes: <ul><li> To be able to upload location or see tracks, make sure Internet connection is available on the device(s) and this application has the permission to the Internet.</li> 
	                  <li>step 2 must be performed, otherwise, without members pushed location, the app may be freeze on step 3 </li>
	                  </ul>

           <h4>Q: How can I hide the location or tracks of (a) member(s) or mine on map?</h4>
           <h4>A: </h4>
           Edit the member(s) profile and turn the location symbol into outline from solid.<br>
           <br>
           Note: You can first test using this device as member's device to check if you can see the tracks and locations on the member map (not 'My Tracks' map)
	</div>	
			
    <a id="pp"></a>
    <h3></h3>
    <div id="ppOff" onclick="allOff();expandOptions('ppOff','ppOn',true);"><H3>Private Policy:<<a  href="#">+</a>></H3></div>
	<div id="ppOn"  onclick="expandOptions('ppOff','ppOn',false);return false;"><H3>Private Policy:<<a  href="#">-</a>></H3>
		  The app may collect user's locations, so the app. can plot those locations on the device as a trail.
		  <br>
		  The app may send messages via the device's SMS tool when the user opens the multi-task menu on the bottom of the map page, <br>the SMS messages will carry any information the user 
		  choose to send, and the destination will be specified by the user.  
		  <br>
		  The app may access users contact information on the phone when user chooses to copy between the contact and the app's members profiles
		  <br>
		  Under no circumstances the app. disclose to any third parties any information it collects from user's device 		  		 
	</div>
		  			
</p>

</body>

</html>