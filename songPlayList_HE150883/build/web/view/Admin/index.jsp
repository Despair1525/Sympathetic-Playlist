<%-- 
    Document   : index
    Created on : Jun 20, 2022, 8:52:39 PM
    Author     : smileymask
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Admin Page</title>
        <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/images/iconPage.ico">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
        <!-- custom css file link  -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/style.css">
        <script src="${pageContext.request.contextPath}/JS/userScript.js" defer></script>
        <script src="${pageContext.request.contextPath}/JS/functionScript.js" defer></script>

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



            </div>

            <c:if test="${DAO != null && DaoUser !=null }">
                <section class="overall-infor">

                    <div class="infor"> <i class="fas fa-user"></i> Users: ${DaoUser.userList.size()} </div>

                    <div class="infor"> <i class="fas fa-music"></i> Songs: ${DAO.songList.size()} </div>

                    <div class="infor" > <i class="fas fa-file-audio"></i> Playlists: ${DAO.playlistList.size()} </div>
                    </<section>

                        <table class="table-infor">
                            <tr>
                                <th>UserID</th>
                                <th>Name</th>
                                <th>Gender</th>
                                <th>Email</th>
                                <th>Phone</th>
                                <th>Address</th>
                              
                                <th>Enroll</th>
                            </tr>
                            <c:forEach items="${DaoUser.userList}" var="user">

                                <tr>
                                    <td><a href="${pageContext.request.contextPath}/profileUser?Id=${user.userId}">${user.userId}</a></td>
                                    <td>${user.userName}</td>
                                    <c:if test="${user.userGender}">
                                        <td><i class="fa fa-mars" style="color: blue;"></i> </td>
                                    </c:if>
                                    <c:if test="${!user.userGender}">
                                        <td> <i class="fa fa-venus" style="color: pink;"></i> </td>
                                </c:if>
                                    <td>${user.userEmail}</td>
                                    <td>${user.userPhone}</td>
                                    <td>${user.userAddress}</td>
                                   
                                    <td>${user.userEnroll}</td>
                                </tr>
                            </c:forEach>
                        </table>

                    </c:if>

                    <c:if test="${DAO == null || DaoUser == null}">
                        <%

                            response.sendRedirect(request.getContextPath() + "/LoadAdminPage");
                        %>
                    </c:if>


                    </div>
                    </body>
                    </html>
