<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">

<head>

    <title>User Profile</title>
    <link href="./vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">

    <link href="./vendor/datatables/dataTables.bootstrap4.css" rel="stylesheet">
    <link href="./css/sb-admin.css" rel="stylesheet">

</head>

<body id="page-top">

<%--https://startbootstrap.com/templates/sb-admin/--%>
<jsp:include page="navbar.jsp"></jsp:include>

<div id="wrapper">
    <jsp:include page="sidebar.jsp"></jsp:include>
    <div id="content-wrapper">
    <div class="container">
	<div class="row">
		<form method="POST" action="/updateProfile"  enctype="multipart/form-data">
                    
              
                <div class="column" >
                    <figure>
                        <img id ="output_image" src="/images/${user.profileImageurl}" onerror = "this.src='images/userimg.png'" alt=""  class="circle center ">
                    </figure>
                 	<input value ="change image" name = "file" type="file" accept="image/*" onchange="preview_image(event)">     
                </div>
                <div class="column userdetail">
                    <h2> ${user.firstName} ${user.lastName}</h2>
                    
                    <div class="inputcls">
                    	<input type="text" name ="firstName" placeholder="First Name" value ="${user.firstName}"  >
                    </div>
                    
                    <div class="inputcls">
                    	<input type="text" name ="lastName" placeholder="Last Name" value ="${user.lastName} " >
                    </div>
                    
                    <div class="inputcls">
                    	<button  type="submit" class="btn btn-primary">Update Profile</button>
                    </div>
                </div>             
             </form>  
           
	</div>
</div>
    

</div>

<script src="../vendor/jquery/jquery.min.js"></script>
<script src="../vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

<script src="../js/sb-admin.min.js"></script>

</div>

</body>
<style>

.column {
  float: left;
}

.left {
  width: 25%;
}
.inputcls
{
	width: 100%;
	padding-bottom: 10px;
  	padding-left: 10px;
}
.right {
  width: 75%;
  padding-top: 30px;
  padding-left: 30px;
}


/* Clear floats after the columns */
.row:after {
  content: "";
  display: table;
  clear: both;
}

/* Responsive layout - makes the two columns stack on top of each other instead of next to each other */
@media screen and (max-width: 600px) {
  .column {
    width: 100%;
  }
}

.circle {
  border-radius: 50%;
}
.center {
  display: block;
  margin-left: auto;
  margin-right: auto;
  width: 200px;
}
.userdetail{
	padding-top: 50px;
	padding-left:50px;
}
</style>

<script type='text/javascript'>
function preview_image(event) 
{
 var reader = new FileReader();
 reader.onload = function()
 {
  var output = document.getElementById('output_image');
  output.src = reader.result;
 }
 reader.readAsDataURL(event.target.files[0]);
}
</script>
</html>
