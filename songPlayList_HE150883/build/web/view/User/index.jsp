<%-- 
    Document   : index
    Created on : Jun 15, 2022, 7:35:40 PM
    Author     : smileymask
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>${cookie.user.value.substring(1)} song list </title>
        <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/images/iconPage.ico">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
        <!-- custom css file link  -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/userStyle.css">
        <script src="${pageContext.request.contextPath}/JS/userScript.js" defer></script>
        <script src="${pageContext.request.contextPath}/JS/functionScript.js" defer></script>
    </head>
    <body>


        <c:if test="${DAO != null }">
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
                           <a href="view/User/profile.jsp" class="fas fa-user">
                            <a href="${pageContext.request.contextPath}/view/User/index.jsp">YourPlaylist</a>
                            <a href="${pageContext.request.contextPath}/Logout?CurrentUser=${cookie.user.value.substring(1)}">Logout</a>
                        </c:if>
                        <c:if test="${empty cookie.user.value}">
                            <a href="${pageContext.request.contextPath}/Login">Login</a>
                        </c:if>

                    </div>



                </div>


                       
                <div class="boxContent">
                    
                     <h1 class="headTitle">Hello ${cookie.user.value.substring(1)}</h1>

                    <c:if test="${ DAO.songListUser.size()  > 0}"> 
                        <c:set var="firstSong" value="${DAO.songListUser.get(0)}" scope="session"></c:set>

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
                             <h4>${cookie.user.value.substring(1)} number song: ${DAO.songListUser.size()}</h4>
                        </div>
                        <div class="song-list">
                           
                            <c:forEach begin="${CP.getBegin()}" end="${CP.getEnd()}" var="index">
                                <div class="song-box">
                                    <c:if test="${index == 0}">
                                        <div class="son active">
                                            <audio muted>
                                                <source src= "${pageContext.request.contextPath}/${DAO.songListUser.get(index).linkSong}" type="audio/mpeg">
                                            </audio>
                                            <h3 class="title"> ${DAO.songListUser.get(index).songName}</h3>
                                            <img style="display: none ;" src="${pageContext.request.contextPath}/${DAO.songListUser.get(index).linkImage}">
                                        </div>
                                    </c:if>

                                    <c:if test="${index != 0}">
                                        <div class="son">
                                            <audio muted>
                                                <source src= "${pageContext.request.contextPath}/${DAO.songListUser.get(index).linkSong}" type="audio/mpeg">
                                            </audio>
                                            <h3 class="title"> ${DAO.songListUser.get(index).songName}</h3>
                                            <img style="display: none ;" src="${pageContext.request.contextPath}/${DAO.songListUser.get(index).linkImage}">
                                        </div>
                                    </c:if>
                                    <div class="option">
                                        <input type="button" value="&#8286;" onclick="showhideOrigin('listOption-${DAO.songListUser.get(index).songId}')">

                                        <ul class ="listOption" id="listOption-${DAO.songListUser.get(index).songId}">
                                            <li> <a href="${pageContext.request.contextPath}/UpdateUserSong?type=0&id=${DAO.songListUser.get(index).songId}">Update</a>  </li>
                                            <li> <a href="${pageContext.request.contextPath}/UpdateUserSong?type=1&id=${DAO.songListUser.get(index).songId}">Delete</a>  </li>
                                            <li> <a href="${pageContext.request.contextPath}/UpdateUserSong?type=2&id=${DAO.songListUser.get(index).songId}">Addplaylist</a></li>
                                            
                                        </ul>
                                            
                                    </div>    
                                </div>                             
                            </c:forEach>

                            <c:if test="${DAO.songListUser.size() > 7}">
                            <div class="pageNumber">
                                <form action="${pageContext.request.contextPath}/LoadUserPage" method="post">
                                    <c:if test="${CP.cp!=0}">
                                        <input class="btn" type="submit" name="home" value="Home">
                                        <input class="btn" type="submit" name="pre" value="Pre">
                                        </c:if>
                                        <c:forEach begin="${CP.pageStart}" end="${CP.pageEnd}" var="i">
                                        <input class="btn" type="submit" name="btn${i}" value="${i+1}">
                                        </c:forEach>
                                        <c:if test="${CP.cp!=CP.np-1}">
                                        <input class="btn" type="submit" name="next" value="Next">
                                        <input class="btn" type="submit" name="end" value="End">
                                        </c:if>
                                    <input  type="text" hidden value="${CP.cp}" name="cp">
                                    <input  type="text" hidden value="${CP.np}" name="np">
                                    <input  type="text" hidden value="${DAO.songListUser.size()}" name="size">
                                </form>
                            </div>   
                                </c:if>
                        </div>
                    </c:if>


                </div>
            </div>
        </c:if> 

        <c:if test="${DAO == null}">
            <%
//               
    response.sendRedirect(request.getContextPath()+"/LoadUserPage");               
            %>
        </c:if>


    </body>
</html>

<!--request.getRequestURI()-->