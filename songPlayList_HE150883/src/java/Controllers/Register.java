/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAL.DAOUser;
import Validation.LoginValidation;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.*;

/**
 *
 * @author smileymask
 */
public class Register extends HttpServlet {
    
    DAOUser daoUser;

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
            out.println("<title>Servlet Register</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Register at " + request.getContextPath() + "</h1>");
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
            request.getRequestDispatcher("view/Register.jsp").forward(request, response);
        } else {
            request.setAttribute("Register", "Register");
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
//        processRequest(request, response);
        request.setCharacterEncoding("UTF-8");
        if (daoUser == null) {
            doGet(request, response);
        } else {
            boolean check = true;
            LoginValidation lova = new LoginValidation();
            String Password = request.getParameter("UserPassword").trim();
            String ConfirmPass = request.getParameter("UserConfirmPass").trim();
            String PassStatus = "";
            String EmailStatus = "";
            String PhoneStatus = "";
            String UserStatus = "";
            
            if (lova.checkPassword(Password, ConfirmPass) == null) {
                check = false;
                System.out.println(Password);
                System.out.println(ConfirmPass);
                PassStatus = lova.getStatus();
            };
            
            String UserName = request.getParameter("UserName");
            
            boolean Gender = request.getParameter("genderMale").equals("1");
            
            String Email = request.getParameter("UserEmail").trim();
            if (lova.checkEmail(Email) == null) {
                
                check = false;
                EmailStatus = lova.getStatus();
            };
            String Phone = request.getParameter("UserPhone").trim();
            if (lova.checkPhone(Phone) == null) {
                System.out.println(Phone);
                check = false;
                PhoneStatus = lova.getStatus();
            };
            
            String address = request.getParameter("UserAddress");

//        daoUser.loadUser();
            String UserID = request.getParameter("UserId").trim();
            
            if (lova.checkUser(UserID, daoUser.userList) != null) {
                check = false;
                UserStatus = "userId have been take";
            };
            if (check) {
                
                daoUser.insertUser(UserID, Password, UserName, Gender, Email, Phone, address);
//            HttpSession ses = request.getSession();
//            ses.setAttribute("reloadUser", true);
//         User_HE150883 newUser = new User_HE150883(UserID, Password, UserName, Gender, UserID, check, Phone, address, 0, userEnroll)
                daoUser.loadUser();
                User_HE150883 user = daoUser.checkUser(UserID, Password);
                Cookie c = new Cookie("user", user.getUserType() + user.getUserId());
                int maxAge = Integer.parseInt(getServletContext().getInitParameter("maxAge"));
                c.setMaxAge(maxAge);
                response.addCookie(c);
                response.sendRedirect("LoadDB");
                
            } else {
                request.setAttribute("PassStatus", PassStatus);
                request.setAttribute("EmailStatus", EmailStatus);
                request.setAttribute("PhoneStatus", PhoneStatus);
                request.setAttribute("UserStatus", UserStatus);
                
                request.getRequestDispatcher("view/Register.jsp").forward(request, response);
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
