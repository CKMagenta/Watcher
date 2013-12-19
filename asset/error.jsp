<%@page import="java.lang.Exception"%>

<jsp:include page="header.jsp" flush="true" ></jsp:include>

<% 
	String message = "";
	try {
		message = (String)request.getAttribute("message");
	} catch(Exception e) {}
%>
<div class="container">
	<div class="row">
		<h1>Something wrong!</h1>
		<p class="message"><%=message%></p>
		<p class="defaultMessage">After 3 second, It will be redirected to Main Page, automatically.</p>
	</div>
</div>
<script type="text/javascript">
	setTimeout(function() {
		window.location = "index.jsp";
	}, 3000);
</script>
<jsp:include page="footer.jsp" flush="true" ></jsp:include>