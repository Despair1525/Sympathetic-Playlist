<%-- 
    Document   : index
    Created on : Jun 15, 2022, 7:35:40 PM
    Author     : smileymask
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="model.*" %>
<%@page import="DAL.DAO" %>
<%@page import="java.util.*"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>${cookie.user.value.substring(1)} Playlist </title>
        <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/images/iconPage.ico">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">

        <!-- custom css file link  -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/userStyle.css">
        <script src="${pageContext.request.contextPath}/JS/userScript.js" defer></script>
        <script src="${pageContext.request.contextPath}/JS/functionScript.js" defer></script>




    </head>
    <body>


        <c:if test="${requestScope.DAO != null }">
            <div class="container">

                <div class="leftBar">
                    <ul>
                        <li>
                            <a class="imageLink" href="${pageContext.request.contextPath}/moveFile">
                                <img src="${pageContext.request.contextPath}/images/add.png">
                            </a>
                        </li>
                        <li><a class="titleLink" href="${pageContext.request.contextPath}/view/User/playList.jsp">PlayList</a></li>

                    </ul>
                </div>
                <div class="topnav">
                    <div class="left">
                        <a class="active" href="${pageContext.request.contextPath}/LoadDB">Home</a>
                        <a href="#about">About</a>
                    </div>

                    <div class="right">
                        <c:if test="${not empty cookie.user.value}">

                            <c:if test="${cookie.user.value.codePointAt(0) - 48 == 0}">
                                <a href="${pageContext.request.contextPath}/view/Admin/index.jsp">Admin</a>
                            </c:if>
                            <a href="${pageContext.request.contextPath}/view/User/index.jsp">YourPlaylist</a>
                            <a href="${pageContext.request.contextPath}/Logout?CurrentUser=${cookie.user.value.substring(1)}">Logout</a>
                        </c:if>
                        <c:if test="${empty cookie.user.value}">
                            <a href="${pageContext.request.contextPath}/Login">Login</a>
                        </c:if>
                    </div>
                </div>
                <div class="boxContent">
                    <h1 class="headTitle"><c:if test="${playlistPlay.playlistPublic}" >Public</c:if> 
                        <c:if test="${!playlistPlay.playlistPublic}" >Private</c:if>
                            Playlist ${playlistPlay.playlistName}
                    </h1>
                     <c:if test="${ playlistPlay.userId == cookie.user.value.substring(1) }"> 
                    <div class="option">
                                <input type="button" value="&#8286;" onclick="showhideOrigin('listOption-')">

                                <ul class ="listOption" id="listOption-">
                                    <c:if test="${playlistPlay.playlistPublic}" >
                                        <li> <a href="${pageContext.request.contextPath}/UpdateUserSong?type=4&playlistId=${param['playlistId']}">Set Private</a></li>
                                        </c:if>
                                        <c:if test="${!playlistPlay.playlistPublic}" >

                                        <li> <a href="${pageContext.request.contextPath}/UpdateUserSong?type=4&playlistId=${param['playlistId']}">Set Public</a></li>
                                        </c:if>

                                    <li> <a href="${pageContext.request.contextPath}/UpdateUserSong?type=5&&playlistId=${param['playlistId']}">DeletePlaylist</a></li>
                                </ul>
                            </div>    
                     </c:if>
                    <c:if test="${ playlistPlay.songs.size()  > 0 }"> 

                        <c:set var="firstSong" value="${playlistPlay.songs.get(0)}" scope="session"></c:set>

                            <div class="main-song">
                                <img id="mainImage" src="${pageContext.request.contextPath}/${firstSong.linkImage}" 
                                 onError="this.src='${pageContext.request.contextPath}/images/default.png'">
                            <div class="song">
                                <audio controls id="mainSong" >
                                    <source src="${pageContext.request.contextPath}/${firstSong.linkSong}" type="audio/mpeg">
                                </audio>
                                <h3 class="title"> ${firstSong.songName}</h3>
                                <div class="musicButton">
                                    <input class="previous" onclick="changeSong(false)" type="button" value="&#8249;&#8249;">
                                    <input class="next" onclick="changeSong(true)" type="button" value="&#8250;&#8250;">
                                </div>
                            </div>
                            <h4>${playlistPlay.playlistName} number song: ${playlistPlay.songs.size()}</h4>
                            
                        </div>
                        <div class="song-list">

                            <c:forEach begin="0" end="${playlistPlay.songs.size()-1}" var="index">
                                <div class="song-box">
                                    <c:if test="${index == 0}">
                                        <div class="son active">
                                            <audio muted>
                                                <source src= "${pageContext.request.contextPath}/${playlistPlay.songs.get(index).linkSong}" type="audio/mpeg">
                                            </audio>
                                            <h3 class="title"> ${playlistPlay.songs.get(index).songName}</h3>
                                            <img style="display: none ;" src="${pageContext.request.contextPath}/${playlistPlay.songs.get(index).linkImage}">
                                        </div>
                                    </c:if>

                                    <c:if test="${index != 0}">
                                        <div class="son">
                                            <audio muted>
                                                <source src= "${pageContext.request.contextPath}/${playlistPlay.songs.get(index).linkSong}" type="audio/mpeg">
                                            </audio>
                                            <h3 class="title"> ${playlistPlay.songs.get(index).songName}</h3>
                                            <img style="display: none ;" src="${pageContext.request.contextPath}/${playlistPlay.songs.get(index).linkImage}">
                                        </div>
                                    </c:if>
                                    <div class="option">
                                        <input type="button" value="&#8286;" onclick="showhideOrigin('listOption-${playlistPlay.songs.get(index).songId}')">

                                        <ul class ="listOption" id="listOption-${playlistPlay.songs.get(index).songId}">
                                            <li> <a href="${pageContext.request.contextPath}/UpdateUserSong?type=3&id=${playlistPlay.songs.get(index).songId}&playlistId=${param['playlistId']}">Remove</a>  </li>
                                            <li> <a href="${pageContext.request.contextPath}/UpdateUserSong?type=2&id=${playlistPlay.songs.get(index).songId}">Addplaylist</a></li>

                                        </ul>

                                    </div>    
                                </div>                             
                            </c:forEach>


                        </div>

                    </c:if>



                </div>
            </div>
        </c:if> 

        <c:if test="${DAO == null}">
            <%
//             
                response.sendRedirect(request.getContextPath() + "/view/User/playList.jsp");
            %>
        </c:if>


    </body>
</html>

<!--request.getRequestURI()-->