<%-- 
    Document   : mainPage
    Created on : Jun 17, 2022, 10:18:34 AM
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
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Search</title>
        <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/images/iconPage.ico">
        <!-- font awesome cdn link  -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">

        <!-- custom css file link  -->
        <link rel="stylesheet" href="CSS/style2.css">

        <!-- custom js file link  -->
        <script src="JS/script.js" defer></script>

    </head>
    <body>
        <c:if test="${requestScope.DAO != null}">


            <div class="container">
                <!--                adjust Top Nav-->
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
                            <a href="view/User/profile.jsp" class="fas fa-user">
                            <a href="view/User/index.jsp">YourPlaylist</a>
                            <a href="Logout?CurrentUser=${cookie.user.value.substring(1)}">Logout</a>
                        </c:if>
                        <c:if test="${empty cookie.user.value}">
                            <a href="Login">Login</a>
                        </c:if>
                              <form class="form-search" action="mainPageFilter" method="get">
                            <input type="text" class="box" name="search_box" placeholder="search here..." maxlength="100">
                            <button type="submit" class="fas fa-search" name="search_btn"></button>
                            <div class="genres">genres &#9661;
                                <ul>
                                    <c:forEach items="${DAO.genreHm}" var="genre" >
                                        <li>
                                        <input type="checkbox" id="${genre.key}" name="${genre.key}" value="${genre.key}">
                                        <label for="${genre.key}"> ${genre.value.getGenreName()}</label>
                                        </li>
                                    </c:forEach>
                                </ul>
                            </div> 
                        </form>



                </div>
                <div id="search-box" class="box-container">

                    <h1>${listFilter.size()} result:</h1>
                    <!--                <h1 class="heading"> music playlist </h1>-->
                    <c:forEach items= "${listFilter}" var= "song"  >

                        <div class="box" data-src="${song.linkSong}">
                            <div class="image">
                                <div class="play">
                                    <i class="fas fa-play"></i>
                                </div>
                                <img src="${song.linkImage}" alt="" 
                                     onError="this.src='images/default.png'">
                            </div>
                            <div class="content">
                                <h3>${song.songName}</h3>
                                <h6 style="font-size: 16px;"> Genres:
                                    <c:forEach items="${requestScope.DAO.getHMSongGenre().get(song.getSongId())}" var="genre">
                                        ${genre},            
                                    </c:forEach>
                                </h6>
                                 <c:if test="${not empty cookie.user.value}">  
                                <div class="likeButton" >
                                    <form method="post" action="Rate">
                                        <input type="text" hidden value ="${song.getSongId()}" name="Id"> 
                                        <input type="text" hidden value="${cookie.user.value.substring(1)}" name="userId">
                                        
                                        <c:if test="${DAO.findRate(cookie.user.value.substring(1),song.getSongId())==0}">  
                                        <input class="heart" type="submit" value="&#10084;" name="likeButton">
                                        </c:if>
                                        <c:if test="${DAO.findRate(cookie.user.value.substring(1),song.getSongId())!=0}"> 
                                            <input class="unheart" type="submit" value="&#10084;" name="unlikeButton">
                                        </c:if>
                                             <a class="imageLink" href="${pageContext.request.contextPath}/UpdateUserSong?type=2&id=${DAO.songHm.get(song.getSongId()).songId}"> + </a> 
                                    </form>
                                </div>
                                
                            </c:if> 
                            </div>

                        
                        </div>
                    </c:forEach>

                </div>


  

            </div>



            <div class="music-player">
                <div id="close-player" class="fas fa-angle-up"></div>
                <h3 class="music-title">(play your song)</h3>
                <audio src="" controls></audio>
            </div>
        </c:if>

        <c:if test="${requestScope.DAO == null}">

            <%
                response.sendRedirect(request.getContextPath()+"/LoadDB");
            %>
        </c:if> 
    </body>
</html>
