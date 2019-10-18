
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <title>Title</title>
    <link href="../vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">

    <link href="../vendor/datatables/dataTables.bootstrap4.css" rel="stylesheet">

    <link href="../css/sb-admin.css" rel="stylesheet">
    <style>
        .title-header{
            font-family: "Helvetica Neue", Helvetica, Arial, sans-serif;
            font-size: larger;
        }
        .element{
            padding: 5px;
            margin: 2px;
        }

    </style>
</head>
<body>
<jsp:include page="navbar.jsp"></jsp:include>
<div id="wrapper">
    <jsp:include page="sidebar.jsp"></jsp:include>
    <div id="content-wrapper">

        <div class="container-fluid">

<div style="text-align: right"> </div>
<section id ="header" class = "section-padding" style="padding-top: 20px">
    <div class="container">
        <div style="text-align: right">
            <span>Welcome User</span>
        </div>

    </div>
</section>
<section id ="search-box" class = "section-padding">
<div class="container">
    <form class="search" style = " text-align: center;" action="">
        <input type="text" placeholder="Search.." name="search" disabled>
        <button type="submit"><i class="fa fa-search" disabled></i></button>
    </form>
</div>
</section>
<section id ="add-post" class = "section-padding">
    <div class="container">

        <div>
            <form method="post" action ="/savePost" enctype="multipart/form-data">
                <div class="element">
                    <span class="title-header">Post Title</span><br>
                    <input type="text" name="postTitle" class="post-title" required><br>
                </div>
                <div class="element">
                    <span class="title-header">Post Description</span><br>
                    <textarea rows="4" cols="50" name="postDesc" required> </textarea><br>
                </div>
                <div class="element">
                    <span class="title-header">Category</span><br>
                    <select name="category">
                        <option value="1" selected>Discussion</option>
                    </select><br>
                </div>
                <div class="element">
                    <span class="title-header">Group</span><br>
                    <select name="group">
                        <option value="General" selected >General Discussion</option>
                        <option value="Group 2" disabled>Group 1</option>
                        <option value="Group 2" disabled>Group 2</option>
                    </select><br>
                </div>
                <div class="element">
                    <div style="color: red;">
                        ${message}
                    </div>

                    <span class="title-header">Add an image</span><br>
                    <input type="file" name="image" accept="image/*">
                </div>
                <div class="element">
                    <input style="color: white; background-color: darkcyan" type="submit" value="POST">
                </div>

            </form>
        </div>
    </div>
</section>
<div style="text-align: center">

</div>
        </div>
    </div>

    <script src="../vendor/jquery/jquery.min.js"></script>
    <script src="../vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

    <script src="../js/sb-admin.min.js"></script>
</div>

</body>
</html>
