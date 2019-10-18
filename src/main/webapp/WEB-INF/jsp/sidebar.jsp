
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>

</head>
<body>
<ul class="sidebar navbar-nav">
    <li class="nav-item">
    	<c:url var="userprofile" value="/userprofile" />
        <a class="nav-link" href="${userprofile}">
            <i class="far fa-user"></i>
            <span>Profile</span>
        </a>
    </li>
    <li class="nav-item">
    <c:url var="dashboard" value="/dashboard" />
        <a class="nav-link" href="${dashboard }">
            <i class="fas fa-fw fa-tachometer-alt"></i>
            <span>Dashboard</span>
        </a>
    </li>
    <li class="nav-item ">
    	<c:url var="subscription" value="/subscription" />
        <a class="nav-link" href="${subscription}">
            <i class="fas fa-thumbtack"></i>
            <span>Subscription</span>
        </a>
    </li>
    <li class="nav-item">
    <c:url var="logout" value="/logout" />
        <a class="nav-link" href="${logout }">
            <i class="fas fa-sign-out-alt"></i>
            <span>Logout</span>
        </a>
    </li>

</ul>
</body>
</html>
