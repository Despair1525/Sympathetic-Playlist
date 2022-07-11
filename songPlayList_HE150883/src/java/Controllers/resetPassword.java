/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import DAL.*;
import jakarta.mail.MessagingException;
import model.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import Validation.LoginValidation;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author smileymask
 */
public class resetPassword extends HttpServlet {

    DAOUser daoUser;
    String captcha;
    SendEmail se;
    LoginValidation lova = new LoginValidation();

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet resetPassword</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet resetPassword at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getAttribute("DaoUser") != null) {

            daoUser = (DAOUser) request.getAttribute("DaoUser");
            String userId = request.getParameter("user");
            String email = request.getParameter("email");
            User_HE150883 forgetUser = daoUser.checkUserEmail(userId, email);
            if (forgetUser == null) {
                request.setAttribute("resetMess", "User or Email not found in database !");
                request.getRequestDispatcher("view/resetPass.jsp").forward(request, response);
            } else {
                
                captcha = daoUser.genCaptcha();
                String webEmail = getServletContext().getInitParameter("WebEmail");
                se = new SendEmail(webEmail);
                Date date = new Date();
                String subject = "sympathetic OTP  (" + date.toString() + " )";
                try {
                    se.sendMail(email, subject, userId, captcha);
                    System.out.println(se.status);
                request.setAttribute("step", "1");
                request.setAttribute("userId", userId);
                request.getRequestDispatcher("view/resetPass.jsp").forward(request, response);
                } catch (MessagingException ex) {
                    captcha = null;
                    Logger.getLogger(resetPassword.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println(ex.toString());
                }
                
            }

        } else {
            request.setAttribute("resetPass", "reserPassword");
            request.getRequestDispatcher("LoadDB").forward(request, response);

        };

    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        if(daoUser == null){
            doGet(request, response);
        } else{
        if (request.getParameter("Ok-Step3") != null) {
            String userId = request.getParameter("userId");
            String password = request.getParameter("password");
            String confirmPass = request.getParameter("confirmPassword");
            
            String pass = lova.checkPassword(password, confirmPass);
            if(pass == null ) {
            request.setAttribute("step", "2");
            request.setAttribute("Resetmess", lova.status);
            request.getRequestDispatcher("view/resetPass.jsp").forward(request, response);
            }
            else {
                HttpSession ses = request.getSession();
                User_HE150883 user = daoUser.getUser(userId);
                daoUser.updatePass(userId, pass);
                System.out.println(daoUser.status);
                ses.setAttribute("reloadUser", true);
                ses.setAttribute("user", user);
                Cookie c = new Cookie("user", user.getUserType() + user.getUserId());
                c.setMaxAge(1800);
                response.addCookie(c);
                response.sendRedirect("view/mainPage.jsp");
                
            
            };
            
        } else {
            if (request.getParameter("Ok-Step2") != null) {
                String OTP = request.getParameter("OTP");
                String userId = request.getParameter("userId");
                System.out.println("OTP is: " + captcha);
                if (OTP.equals(captcha)) {
                    request.setAttribute("userId", userId);
                    request.setAttribute("step", "2");
                    request.getRequestDispatcher("view/resetPass.jsp").forward(request, response);
                } else {
                    request.setAttribute("step", "1");
                    request.setAttribute("OTPmess", "OTP not correct !");
                    request.getRequestDispatcher("view/resetPass.jsp").forward(request, response);
                }

            }
        };
        }

    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
