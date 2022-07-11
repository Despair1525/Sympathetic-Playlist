<%-- 
    Document   : resetPass
    Created on : Jul 6, 2022, 12:11:04 AM
    Author     : smileymask
--%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Reset password</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
        <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/images/iconPage.ico">
        <!-- custom css file link  -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/style.css">

        <!-- custom js file link  -->
        <script src="${pageContext.request.contextPath}/JS/script.js" defer></script>

    </head>
    <body>
        <div class="container"></div>
        <c:if test="${step == null}">
          <section class="form-container">
                <form action="${pageContext.request.contextPath}/resetPassword" method ="get" >
                    <h3> Forget Password</h3>
                    <c:if test="${not empty resetMess}">
                        <p style="color:red;" >${resetMess}</p>             
                    </c:if>
                    <input class="box" type="text" name="user" required placeholder="Enter Your UserId" ><br/><!-- comment -->
                    <input class="box" type="email" name="email" required placeholder="Enter Your Email" ><br/><!-- comment -->
                    <input class="btn" type="submit" value ="Ok" name="Ok-Step1"> 
                    
                </form>
            </section>
                 </c:if>   
                      <c:if test="${step == '1'}">
                <section class="form-container">
                <form action="${pageContext.request.contextPath}/resetPassword" method ="post" >
                    <h3> Enter OTP </h3>
                    <p>The OTP Code has been sent into your email</p>
                    <c:if test="${not empty OTPmess}">
                        <p style="color:red;" >${OTPmess}</p>             
                    </c:if>
                    <input class="box" type="text" name="OTP" required placeholder="Enter OTP" ><br/><!-- comment -->
                    <input class="btn" type="submit" value ="Ok" name="Ok-Step2"> 
                    <input type="text" hidden value="${userId}" name="userId">
                    
                </form>
            </section>
                      </c:if>
        
         <c:if test="${step == '2'}">
                <section class="form-container">
                <form action="${pageContext.request.contextPath}/resetPassword" method ="post" >
                    <h3> Reset Password </h3>
                    <p>Enter your new password</p>
                    <c:if test="${not empty Resetmess}">
                        <p style="color:red;" >${Resetmess}</p>             
                    </c:if>
                    <input class="box" type="password" name="password" required placeholder="Enter newpassword" ><br/>
                     <input class="box" type="password" name="confirmPassword" required placeholder="Enter confirm password" ><br/><!-- comment -->
                    <input class="btn" type="submit" value ="Ok" name="Ok-Step3"> 
                     <input type="text" hidden value="${userId}" name="userId">
                    
                </form>
            </section>
                      </c:if>

                    
                    </div>
    </body>
</html>
