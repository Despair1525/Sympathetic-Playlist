 <%-- 
    Document   : Register
    Created on : Jun 15, 2022, 7:50:11 PM
    Author     : smileymask
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Register Page</title>
        <!-- font awesome cdn link  -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
        <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/images/iconPage.ico">
        <!-- custom css file link  -->
        <link rel="stylesheet" href="../CSS/style.css">
        <link rel="stylesheet" href="CSS/style.css">

        <!-- custom js file link  -->
        <script src="../JS/script.js" defer></script>
    </head>

    <body>
        <div class="container">
            <div class="topnav">
                <div class="left">
                    <a class="active" href="LoadDB">Home</a>
                    <a href="#about">About</a>
                </div>

                <div class="right">
                    <c:if test="${not empty cookie.user.value}">

                        <c:if test="${cookie.user.value.codePointAt(0) - 48 == 0}">
                            <a href="view/Admin/index.jsp">Admin</a>
                        </c:if>
                        <a href="view/User/index.jsp">YourPlaylist</a>
                        <a href="Logout?CurrentUser=${cookie.user.value.substring(1)}">Logout</a>
                    </c:if>
                    <c:if test="${empty cookie.user.value}">
                        <a href="Login">Login</a>
                    </c:if>

                </div>
            </div>
            
            <section class="form-container">
                <form action="${pageContext.request.contextPath}/Register" method='post'>
                    <h3>Register</h3>
                    <c:if test ="${not empty UserStatus }" > <h4 style="color: red">${UserStatus}</h4></c:if>
                        <input type='text' class="box" required placeholder="Enter user ID" name='UserId'><br />
                    <c:if test ="${not empty PassStatus }" > <h4 style="color: red">${PassStatus}</h4></c:if>
                        <input type='password' class="box"  placeholder="Enter password" name='UserPassword'><br />
                        <input type='password' class="box"  placeholder="Enter confirm password" name='UserConfirmPass'><br />

                        <input type='text' class="box"  placeholder="Enter user name" name='UserName'><br />
                        
                        <p class="titleCheckBox" style="font-size: 2rem;">Gender:</p>
                        <div class="checkBox">
                            <input type="radio" class="checkBox" name="genderMale" value="1" checked>Male 
                            <input type="radio" class="checkBox" name="genderFemale" value="0">Female  <br>
                        </div>
                    <c:if test ="${not empty EmailStatus }"> <h4 style="color: red">${EmailStatus}</h4></c:if>
                        <input type="email" class="box"  placeholder="Enter email" name="UserEmail"><br>

                    <c:if test ="${not empty PhoneStatus }"> <h4 style="color: red">${PhoneStatus}</h4></c:if>
                    <input type="text" class="box"  placeholder="Enter phone" name="UserPhone"><br>
                    <input type="text"  class="box" placeholder="Enter address" name="UserAddress">
                    <br><br>
                    <input type='submit' class="btn" value='Register' name='RegisterSubmit'>
                    </section>
                    </div>
                    </body>

                    </html>
