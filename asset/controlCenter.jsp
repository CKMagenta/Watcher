<%@page import="lis3306.Watcher.LoginManager"%>
<%
	LoginManager lm = new LoginManager(request, response);
	boolean isLoggedIn = lm.isLoggedIn();
	if ( isLoggedIn ) {
%>

<jsp:include page="header.jsp" flush="true" ></jsp:include>
<!-- BODY START -->
<link 
	href="style/controlCenter.css" 
	rel="stylesheet" 
	type="text/css" />
<script>
if(!window.watcher) {
	window.watcher = {};
}
watcher.children = {
	pic : '<%=session.getAttribute("childrenPic")+""%>',
	phonenumber : '<%=session.getAttribute("childrenPhonenumber")%>',
	name : '<%=session.getAttribute("childrenName")%>'
};
var p = watcher.children.pic;
if(!p || p.length == 0 || p=='' || p=='null' || p=='NULL') {
	watcher.children.pic = null;
}
</script>
<script 
	type="text/javascript"
	src="http://maps.googleapis.com/maps/api/js?key=AIzaSyDtsuy7sS51ZKOYNPVBgGO7uQkZBVBQELE&sensor=false">
</script>
<script 
	type="text/javascript" 
	src="app/dev.js" >
</script>
<script 
	type="text/javascript" 
	src="app/controlCenter.js" >
</script>

<div class="container">
	<div class="row">
		
		<div id="date-container">
			<span class="label label-default date label" id="date-indicator"></span>
			<input type="text" class="date hidden" />
			<button type="button" class="date btn btn-default btn-sm" id="date-button">
				<span class="glyphicon glyphicon-calendar"></span>
			</button>
			<button type="button" class="btn btn-danger btn-xm logout" id="logout-button"> Log Out </button>
		</div>
		
		
	</div> <!-- row -->

	<div class="row" id="map-container" >
		<div id="map"></div>
	</div> <!-- map container -->
	
	<div class="row" id="info-conatiner">
		<span class="row address"></span>
	</div>

</div> <!-- container -->

<!-- BODY END -->
<jsp:include page="footer.jsp" ></jsp:include>

<%
	} else {
		request.getRequestDispatcher("/error.jsp").forward(request, response);
		request.setAttribute("message", "비정상적인 접근입니다. 다시 로그인 해 주세요.");
	}
%>