<%@include file="header.jsp" %>
<div class="container">
<!-- BODY START -->

<div class="row">
<br />
</div>

<div class="row">

	<div class="jumbotron col-xs-8">
		<h1>Watcher</h1>
		<p>
			Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris auctor aliquet sem, sit amet lobortis velit congue eget.
			<br /> Pellentesque justo justo, dapibus vitae tortor et, laoreet dictum quam. Vestibulum eget.
		</p>
	</div>
	
	<div class="col-xs-4">
	
		<h3>Register Account!</h3>
		<br />
		<form class="form-horizontal" role="form">
		  <div class="form-group">
		    <label for="inputEmail3" class="col-xs-3 control-label">ID</label>
		    <div class="col-xs-9">
		      <input type="text" class="form-control" id="userid" placeholder="User ID">
		    </div>
		  </div>
		  <div class="form-group">
		    <label for="inputPassword3" class="col-xs-3 control-label">Password</label>
		    <div class="col-xs-9">
		      <input type="password" class="form-control" id="password" placeholder="Password">
		    </div>
		  </div>
		  <br />
		  
		  <div class="form-group">
		    <div class="col-xs-offset-3 col-xs-9">
		      <button type="submit" class="btn btn-danger btn-block">Register</button>
		    </div>
		  </div>
		
		</form>
		
		<br /><br />
		
		<h3>Having Account?</h3>
		<br />
		<div class="col-xs-offset-3 col-xs-9">
		  <button type="submit" class="btn btn-success btn-block">Sign in</button>
		</div>
	
	</div>

</div>

<!-- BODY END -->
</div>
<%@include file="footer.jsp" %>