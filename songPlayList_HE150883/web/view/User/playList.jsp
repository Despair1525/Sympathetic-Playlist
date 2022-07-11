<%-- 
    Document   : playList
    Created on : Jun 27, 2022, 8:57:29 AM
    Author     : smileymask
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
    <head>

        <title>${cookie.user.value.substring(1)} playList </title>
        <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/images/iconPage.ico">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">

        <!-- custom css file link  -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/userStyle.css">
        <script src="${pageContext.request.contextPath}/JS/userScript.js" defer></script>
        <script src="${pageContext.request.contextPath}/JS/functionScript.js" defer></script>

    </head>
    <body>
        <div class="container">

            <div class="topnav playlist">
                <div class="left">
                    <a class="active" href="${pageContext.request.contextPath}/LoadDB">Home</a>
                    <a href="#about">About</a>
                </div>

                <div class="right">
                    <c:if test="${not empty cookie.user.value}">

                        <c:if test="${cookie.user.value.codePointAt(0) - 48 == 0}">
                            <a href="${pageContext.request.contextPath}/view/Admin/index.jsp">Admin</a>
                        </c:if>
                         <a href="view/User/profile.jsp" class="fas fa-user">
                        <a href="${pageContext.request.contextPath}/view/User/index.jsp">YourPlaylist</a>
                        <a href="${pageContext.request.contextPath}/Logout?CurrentUser=${cookie.user.value.substring(1)}">Logout</a>
                    </c:if>
                    <c:if test="${empty cookie.user.value}">
                        <a href="${pageContext.request.contextPath}/Login">Login</a>
                    </c:if>

                </div>
            </div>


            <c:if test="${ DAO != null }">

                <c:set var="list" value="${DAO.playListListUser}" scope="page" ></c:set>

                    <div class="playlist-content ">
                        <a class="imageLink" href="${pageContext.request.contextPath}/LoadDB?songId-playList=${songId}">
                        <img src="${pageContext.request.contextPath}/images/add.png">
                    </a>
                    <c:if test="${songId != null }" >

                        <form action="${pageContext.request.contextPath}/LoadUserPage" method="get">
                            <c:forEach items="${list}" var="playlist" >
                                <div class="playlist-box" >
                                    <label for="${playlist.playlistId}"> ${playlist.playlistName}</label>
                                    <input type="checkbox"  name="${playlist.playlistId}" value="${playlist.playlistId}"><br>
                                </div>
                            </c:forEach>
                            <input class="btn" type="submit" name="addPlaylist" value="ok">
                            <input type="text" hidden value="${songId}" name="songId">
                        </form>
                    </c:if>

                    <c:if test="${songId == null}" >
                        <c:forEach items="${list}" var="playlist" >
                            <div class="playlist-box">
                                <br> <a  href="${pageContext.request.contextPath}/LoadUserPage?playlistId=${playlist.playlistId}">${playlist.playlistName}</a> 
                            </div>
                        </c:forEach>
                    </c:if>
                </div>  
            </c:if>
            <c:if test="${ DAO == null }">
                <%
                    response.sendRedirect(request.getContextPath() + "/loadPlaylist");
                %>
            </c:if>

        </div>
    </body>
</html>
