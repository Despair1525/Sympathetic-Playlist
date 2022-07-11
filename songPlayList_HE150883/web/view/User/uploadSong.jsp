<%-- 
    Document   : uploadSong
    Created on : Jun 22, 2022, 10:50:45 PM
    Author     : smileymask
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>UploadSong</title>
        <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/images/iconPage.ico">
        <script src="${pageContext.servletContext.contextPath}/JS/functionScript.js" defer></script>
        <!-- font awesome cdn link  -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">

        <!-- custom css file link  -->
        <link rel="stylesheet" href="CSS/style.css">

        <!-- custom js file link  -->
        <script src="JS/script.js" defer></script>

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


            <c:if test="${updateSong ==  null}"> 
                <section class="form-container">
                    <h1>${cookie.user.value.substring(1)} uploadSong</h1>

                    <form id="formFile" method="post" action="${pageContext.servletContext.contextPath}/LoadDB" enctype="multipart/form-data">
                        <input class="box" id="songFile"  name="songFile"  onchange= "getUploadName('songFile');showhide('insertSong')"  type="file"  />
                        <div id="insertSong" style="display: none" >
                            <input class="box" id="songId" type="text" name="songName" value="" placeholder="enter song name" > <br>
                            <input class="box" id="Artist" type="text" name="Artist" value="" placeholder="enter artist"> <br> 

                            <div class="genres"><p class="titleCheckBox" style="font-size: 2rem;">Genre:</p>
                                <ul>
                                    <c:forEach items="${DAO.genreHm}" var="genre" >
                                        <li>
                                            <input class="box" type="checkbox" id="${genre.key}" name="${genre.key}" value="${genre.key}">
                                            <label  for="${genre.key}"> ${genre.value.getGenreName()}</label>
                                        </li>
                                    </c:forEach>
                                </ul>
                            </div> 
                            <br>
                            Image of Song: <input class="box"   id="imageFile" type="file" name="fileImage" /> <br>
                            <input class="box" type="text"name ="user" hidden value="${cookie.user.value.substring(1)}" >
                            <input class="btn" type="submit" name="submitSong" value="add Song">
                        </div>
                    </form>
                </section>
            </c:if>

            <c:if test="${updateSong !=  null}">
                <section class="form-container">
                    <h1>${cookie.user.value.substring(1)} updateSong</h1>
                    <form id="formFile" method="post" action="${pageContext.servletContext.contextPath}/LoadDB" enctype="multipart/form-data">
                        <div id="insertSong"  >
                            <input class="box" id="songId" type="text" name="songName" placeholder="enter song name" value="${updateSong.songName}" > <br>
                            <input class="box" id="Artist" type="text" name="Artist"  placeholder="enter artist" value="${updateSong.artist}" > <br> 
                             <div class="genres"><p class="titleCheckBox" style="font-size: 2rem;">Genre:</p>
                                 <ul>
                                 <c:forEach items="${DAO.genreHm}" var="genre" >
                                     <li>
                                <input class="box" type="checkbox" id="${genre.key}" name="${genre.key}" value="${genre.key}" 
                                       <c:if test="${DAO.listContain(genre.value.getGenreName(), updateSong.genre)}">
                                           checked
                                       </c:if>
                                       >
                                <label for="${genre.key}"> ${genre.value.getGenreName()}</label>
                                     </li>
                            </c:forEach>
                                 </ul>
                             </div>
                            <br>
                            Image of Song: <input   id="imageFile" type="file" name="fileImage" /> <br>
                            <input class="box" type="text"name ="songLink" hidden value="${updateSong.linkSong}" >
                            <input class="box" type="text"name ="songId" hidden value="${updateSong.songId}" >
                            <input class="btn" type="submit" name="UpdateSong" value="Update Song">
                        </div>
                    </form>
                </section>
            </c:if>
        </div>
    </body>
</html>
