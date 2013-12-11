<%@page import="lis3306.Watcher.LoginManager"%>
<%
	LoginManager lm = new LoginManager(request, response);
	boolean isLoggedIn = lm.isLoggedIn();
	if ( !isLoggedIn ) {
%>
<%@include file="header.jsp" %>
<div class="container">
<!-- BODY START -->
<script 
	type="text/javascript"
	src="app/index.js" >
</script>
<div class="row">
<!-- Register Modal -->
	<div class="modal fade" id="registerModal" tabindex="-1" role="dialog" aria-labelledby="registerModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="registerModalLabel">Register Your Account!</h4>
				</div>
				<div class="modal-body">
					
					<form class="form-horizontal" role="form" id="register">
						<div class="form-group">
							<label for="register_userid" class="col-xs-3 control-label">ID</label>
							<div class="col-xs-9">
								<input type="text" class="form-control" name="userid" id="register_userid" placeholder="User ID">
							</div>
						</div>
						<div class="form-group">
							<label for="register_password" class="col-xs-3 control-label">Password</label>
							<div class="col-xs-9">
								<input type="password" class="form-control" name="password" id="register_password" placeholder="Password">
							</div>
						</div>
						<br />
						
						<div class="form-group">
							<label for="register_parent_name" class="col-xs-3 control-label">Your Name</label>
							<div class="col-xs-9">
								<input type="text" class="form-control" name="parent_name" id="register_parent_name" placeholder="Parent Name">
							</div>
						</div>
						<br />
						
						<div class="form-group">
							<label for="register_name" class="col-xs-3 control-label">Your Child's Name</label>
							<div class="col-xs-9">
								<input type="text" class="form-control" name="name" id="register_name" placeholder="Child Name">
							</div>
						</div>
						<br />
						
						<div class="form-group">
							<label for="register_phonenumber" class="col-xs-3 control-label">Your Child's Phone Number</label>
							<div class="col-xs-9">
								<input type="text" class="form-control" name="phonenumber" id="register_phonenumber" placeholder="01012345678">
							</div>
						</div>
						<br />
					
					</form>
			
				</div>
				
				<span id="registerStatus" class="alert alert-danger hidden"></span>
				
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
					<button type="button" class="btn btn-primary" id="register">Register!</button>
				</div>
			</div><!-- /.modal-content -->
		</div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
<br />
</div>

<div class="row" id="content-container">
	
	<div class="jumbotron col-xs-8">
		<h1>Watcher</h1>
		<p>
			Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris auctor aliquet sem, sit amet lobortis velit congue eget.
			<br /> Pellentesque justo justo, dapibus vitae tortor et, laoreet dictum quam. Vestibulum eget.
		</p>
	</div>
	
	<div class="col-xs-4">
		<br />
		<h3>Having Account?</h3>
		<br />
		<form class="form-horizontal" role="form" id="login" action="HandlerServlet" method="post">
			<div class="form-group">
				<label for="userid" class="col-xs-3 control-label">ID</label>
				<div class="col-xs-9">
					<input type="text" class="form-control" id="userid" name="userid" placeholder="User ID">
				</div>
			</div>
			<div class="form-group">
				<label for="password" class="col-xs-3 control-label">Password</label>
				<div class="col-xs-9">
					<input type="password" class="form-control" id="password" name="password" placeholder="Password">
				</div>
			</div>
			<br />
		  	
		  	<input type="hidden" name="action" value="login" >
			<div class="col-xs-offset-3 col-xs-9">
		  		<button type="submit" class="btn btn-success btn-block">Log in</button>
			</div>
		
		</form>
		
		<br /><br /><br /><br />
		
		<h3>Register Account!</h3> <br />
		<div class="form-group">
			<div class="col-xs-offset-3 col-xs-9">
				<!-- Button trigger register modal -->
				<button type="submit" class="btn btn-danger btn-block" data-toggle="modal" data-target="#registerModal" >Register</button>
			</div>
		</div>
		
		<br />
		
			
	</div>

</div>


<!-- BODY END -->
</div>
<%@include file="footer.jsp" %>
<%
	} else {
		request.getRequestDispatcher("/controlCenter.jsp").forward(request, response);
	}
%>