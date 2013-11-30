<%@include file="header.jsp" %>
<div class="container">
<!-- BODY START -->

<div class="row">

<div class="jumbotron col-xs-8">
	<h1>Lolem Ipsum</h1>
	<p>
		Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris auctor aliquet sem, sit amet lobortis velit congue eget.
		<br /> Pellentesque justo justo, dapibus vitae tortor et, laoreet dictum quam. Vestibulum eget.
	</p>
</div>

<div class="col-xs-4">

<h3>Register Account!</h3>
<form class="form-horizontal" role="form">
  <div class="form-group">
    <label for="inputEmail3" class="col-xs-1 control-label">ID</label>
    <div class="col-xs-3">
      <input type="text" class="form-control" id="userid" placeholder="User ID">
    </div>
  </div>
  
  <div class="form-group">
    <label for="inputPassword3" class="col-xs-1 control-label">Password</label>
    <div class="col-sm-3">
      <input type="password" class="form-control" id="password" placeholder="Password">
    </div>
  </div>
  
  <div class="form-group">
    <div class="col-sm-4">
      <button type="submit" class="btn btn-danger">Register</button>
    </div>
  </div>

</form>
<div class="col-sm-4">
  <button type="submit" class="btn btn-success">Sign in</button>
</div>

<h3>Having Account?</h3>

</div>

</div>

<!-- BODY END -->
</div>
<%@include file="footer.jsp" %>