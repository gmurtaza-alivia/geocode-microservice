<html>
<head>

    <title>Registration form</title>

    <script>
        function validateform(){
            var emailReg = /^[A-Za-z0-9._]*\@[A-Za-z]*\.[A-Za-z]{2,5}$/;
            var fname=document.forms["myform"]["fname"];
            var lname=document.forms["myform"]["lname"];
            var email=document.forms["myform"]["email"];
            var password=document.forms["myform"]["password"];

            if (fname.value == null || fname.value == "") {
                document.getElementById('firstname').style.borderColor = "red";
                document.getElementById('firstname').innerHTML = "First name is required";
                return false;
            }
            else{
                document.getElementById('fname').style.borderColor = "green";
            }
            if (lname.value == null || lname.value == ""){
                document.getElementById('lname').style.borderColor = "red";
                document.getElementById('lastname').innerHTML = "Last name is required";
                return false;
            }
            else{
                document.getElementById('lname').style.borderColor = "green";
            }

            if (email.value == ""){
                document.getElementById("emailvalue").innerHTML = "Enter the email";
                return false;
            }else if(!emailReg.test(email)){
                document.getElementById("emailvalue").innerHTML = "enter the valid email";
            }

            if(password.value.length<6){
                document.getElementById('passwordvalue').innerHTML = "Password is required";
                return false;
            }
        }

    </script>


</head>

<h2 ALIGN="CENTER">Registration form</h2>

<form action="/register" onsubmit="return validateform()" method="post" name="myform">
    <table border="0" align="center">
        <tbody>

        <tr>

            <td><label for="fname">First Name: </label></td>
            <td><input id="fname" maxlength="50" name="fname" type="text" placeholder="Enter first name" />
                <div id = "firstname"></div></td>
        </tr>

        <tr>
            <td><label for="lname">Last Name: </label></td>
            <td><input id="lname" maxlength="50" name="lname" type="text" placeholder="Enter last name" />
                <div id = "lastname"></div></td>
        </tr>


        <tr>
            <td><label for="email">Email_Address:</label></td>
            <td><input id="email" maxlength="50" name="email" type="email" placeholder="Enter email address" />
                <div id = "emailvalue"></div></td>
        </tr>

        <tr>
            <td><label for="password">Password:</label></td>
            <td><input id="password" maxlength="50" name="password"
                       type="password"  />
                <div id = "passwordvalue"></div></td>
        </tr>

        <tr>
            <td align="right"><input name="Submit" type="Submit" value="Submit" /></td>
        </tr>

        </tbody>
    </table>
</form>
</html>
