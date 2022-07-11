<%-- 
    Document   : profile
    Created on : Jul 5, 2022, 11:09:51 AM
    Author     : smileymask
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>${cookie.user.value.substring(1)} Profile </title>
        <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/images/iconPage.ico">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">

        <!-- custom css file link  -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/style.css">

        <script src="${pageContext.request.contextPath}/JS/userScript.js" defer></script>
        <script src="${pageContext.request.contextPath}/JS/functionScript.js" defer></script>
    </head>
    <body>
        <c:if test="${DaoUser != null}">
            <c:set var="user" value="${DaoUser.getUser(cookie.user.value.substring(1))}" scope="page" ></c:set>
            
            <c:if test="${cookie.user.value.codePointAt(0) - 48 == 0 && not empty  param['Id'] }">
                 <c:set var="user" value="${DaoUser.getUser(param['Id'])}" scope="page" ></c:set>
            </c:if>
           
                <div class="container">
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
                            <a href="${pageContext.request.contextPath}/view/User/index.jsp" class="fas fa-user">
                                <a href="${pageContext.request.contextPath}/view/User/index.jsp">YourPlaylist</a>
                                <a href="${pageContext.request.contextPath}/Logout?CurrentUser=${cookie.user.value.substring(1)}">Logout</a>
                            </c:if>
                            <c:if test="${empty cookie.user.value}">
                                <a href="Login">Login</a>
                            </c:if>

                    </div>          
                </div>
                <section class="form-container">
                    <div class="user">
                        <p><i class="fas fa-user"></i> <span>${user.userName}
                                (     <c:if test="${user.userGender}">
                                    <i class="fa fa-mars" style="color: blue;"></i>
                                </c:if>
                                <c:if test="${!user.userGender}">
                                    <i class="fa fa-venus" style="color: pink;"></i>
                                </c:if>
                                )</span></p>

                        <p><i class="fas fa-phone"></i> <span>${user.userPhone}</span></p>
                        <p><i class="fas fa-envelope"></i> <span>${user.userEmail}</span></p>
                        <p class="address"><i class="fas fa-map-marker-alt"></i> <span>${user.userAddress}</span></p>
                        <p class="address"><i class="fas fa-calendar-alt"></i> <span>EnrollDate: ${user.userEnroll}</span></p>
                        <button onclick="showhideOrigin('updateUserForm')" class="btn profile">Update User</button>
                        <button onclick="showhideOrigin('updateUserPassword')"  class="btn profile">Change Password</button>  
                    </div>
                </section>

                <section  class="form-container" >                  
                    <form id="updateUserForm" action="${pageContext.request.contextPath}/profileUser" method='post'>
                        <h3>Update Profile</h3>

                        <input type='text' class="box"  placeholder="Enter user name" name='UserName' value="${user.userName}"><br />

                        <p class="titleCheckBox" style="font-size: 2rem;">Gender:</p>
                        <div class="checkBox">
                            <input type="radio" class="checkBox" name="gender" value="1" 
                                   <c:if test="${user.userGender}"> checked </c:if>       
                                       >Male 
                                   <input type="radio" class="checkBox" name="gender" value="0"
                                   <c:if test="${!user.userGender}"> checked </c:if>   
                                       >Female  <br>
                            </div>
                            <input type="email" class="box"  placeholder="Enter email" name="UserEmail" value="${user.userEmail}"><br>

                        <input type="text" class="box"  placeholder="Enter phone" name="UserPhone" value = ${user.userPhone} ><br>
                        <input type="text"  class="box" placeholder="Enter address" name="UserAddress" value =${user.userAddress}>
                        <br><br>
                        <input type='submit' class="btn" value='Update' name='updateSubmit'>
                        <input type="text" hidden value="${user.userId}" name="userId"> 
                    </form>       
                    
                     <form id="updateUserPassword" action="${pageContext.request.contextPath}/profileUser" method='post'>
                        <h3>Change Password</h3>
                        <c:if test="${changePassError != null}"><p style="color: red;">${changePassError}</p></c:if>
                        <input type="password" class="box"  placeholder="Enter your password"  name="userPasswrod" ><br>
                        <input type="password" class="box"  placeholder="Enter your new password"  name="newPassword"  ><br>
                        <input type="password"  class="box"  placeholder="Enter confirm password" name="confirmPassword" >
                        
                        <br><br>
                        <input type='submit' class="btn" value='Update' name='changePassSubmit'>
                        <input type="text" hidden value="${user.userId}" name="userId"> 
                    </form>       
                        </section>
                                </div>
                            </c:if>

                            <c:if test="${DaoUser == null}">
                                <%

                    //                request.getRequestDispatcher("../../LoadUserPage").forward(request, response);
                                    response.sendRedirect(request.getContextPath() + "/profileUser");
                                %>
                            </c:if>               

                            </body>
                            </html>
