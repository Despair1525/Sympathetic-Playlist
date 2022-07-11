<%-- 
    Document   : Login
    Created on : Jun 13, 2022, 3:42:02 PM
    Author     : smileymask
--%>
<%@page import="model.*" %>
<%@page import="DAL.DAO" %>
<%@page import="java.util.*"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title>
        <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/images/iconPage.ico">
        <!-- font awesome cdn link  -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">

        <!-- custom css file link  -->
        <link rel="stylesheet" href="CSS/style.css">

        <!-- custom js file link  -->
        <script src="JS/script.js" defer></script>


    </head>
    <body class="login-body">
         <div class="container">
        <div class="topnav">
            <div class="left">
                <a class="active" href="LoadDB">Home</a>
                <a href="#about">About</a>
            </div>
        </div>

           
            <section class="form-container">
                <form action="Login" method ="post" >
                    <h3> Login now </h3>
                    <p id="loginMess" style="display: <c:if test="${empty LoginMess}" >
                       none
                        </c:if>" >${LoginMess} </p>
                    <input class="box" type="text" name="user" required placeholder="Enter Your Username" ><br/><!-- comment -->
                    <input class="box" type="password" name="pass" required placeholder="Enter Your Password" ><br/><!-- comment -->
                    <input class="btn" type="submit" value ="Login" name="Login"> 
                    <p> don't have an account?<a href="Register"> register now </a> </p>
                    <p><a href="view/resetPass.jsp">  forget password?  </a> </p>
                    <!--            <input class="btn" type="submit" value ="Register" name ="Register"> -->
                </form>
            </section>
        </div>
    </body>
</html>
