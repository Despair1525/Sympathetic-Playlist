<%-- 
    Document   : makePlaylist
    Created on : Jun 27, 2022, 10:37:32 PM
    Author     : smileymask
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Make playlist</title>
        <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/images/iconPage.ico">
        <!-- font awesome cdn link  -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">

        <!-- custom css file link  -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/style.css">

        <!-- custom js file link  -->
        <script src="${pageContext.request.contextPath}/JS/script.js" defer></script>
    </head>
    <body>
       
         <body class="login-body">
         <div class="container">
        <div class="topnav">
            <div class="left">
                <a class="active" href="#home">Home</a>
                <a href="#about">About</a>
            </div>
        </div>

            <section class="form-container">
                <form action="${pageContext.request.contextPath}/LoadDB" method ="post" >
                    <h3> Create Playlist </h3>
                    <p id="loginMess" style="display: <c:if test="${empty LoginMess}" >
                       none
                        </c:if>" >${LoginMess} </p>
                    <input class="box" type="text" name="namePlaylist" required placeholder="Enter Your PlayList Name" ><br/><!-- comment -->
                    <select class="box select" type="radio" name="playlistPublic" ><br/><!-- comment -->
                        <option value="1" >Public</option>
                        <option value="0" selected >Private</option>
                    </select>
                    <div class="btnOption">
                    <input class="btn" type="submit" value ="cancel" name="NoPlay"> 
                    <input class="btn" type="submit" value ="Ok" name="OkPlay"> 
                    <input type="text" hidden value="${songId}" name="songId-playlist">
                    <input type="text" hidden value="${cookie.user.value.substring(1)}" name="user">
                </div>
                    </form>
            </section>
        </div>
    </body>
        
    </body>
</html>
