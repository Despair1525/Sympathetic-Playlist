/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAL.DAO;
import DAL.DAOUser;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.*;
import Validation.LoginValidation;

/**
 *
 * @author smileymask
 */
public class profileUser extends HttpServlet {

    DAO dao;
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
            out.println("<title>Servlet profileUser</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet profileUser at " + request.getContextPath() + "</h1>");
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
            request.getRequestDispatcher("view/User/profile.jsp").forward(request, response);

        } else {
            request.setAttribute("profileUser", "profileUser");
            request.getRequestDispatcher("LoadDB").forward(request, response);

        }
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
//        System.out.println(daoUser.userList.size());
        if (daoUser == null) {
            doGet(request, response);
        } else {
            if (request.getParameter("updateSubmit") != null) {
                String userName = request.getParameter("UserName");
                boolean userGender = request.getParameter("gender").equals("1");
                String userEmail = request.getParameter("UserEmail");
                String userPhone = request.getParameter("UserPhone");
                String userAddress = request.getParameter("UserAddress");
                String userId = request.getParameter("userId");
                daoUser.updateUser(userId, userName, userGender, userEmail, userPhone, userAddress);
                System.out.println(daoUser.status);
                HttpSession ses = request.getSession();
                ses.setAttribute("reloadUser", true);
                response.sendRedirect("profileUser");
            }
            if (request.getParameter("changePassSubmit") != null) {
                request.setAttribute("DaoUser", daoUser);
                String userId = request.getParameter("userId");
                String password = request.getParameter("userPasswrod");
                if (daoUser.checkUser(userId, password) == null) {
                    request.setAttribute("changePassError", "Password Not Correct !");
                    request.getRequestDispatcher("view/User/profile.jsp").forward(request, response);
                } else {
                    String newPass = request.getParameter("newPassword");
                    String confirmPass = request.getParameter("confirmPassword");
                    if (newPass.equals(confirmPass)) {
                        daoUser.updatePass(userId, newPass);
                        System.out.println(daoUser.status);
                        HttpSession ses = request.getSession();
                        ses.setAttribute("reloadUser", true);
                        response.sendRedirect("profileUser");
                    } else {
                    request.setAttribute("changePassError", "Password and Confirm Pass must be same !");
                    request.getRequestDispatcher("view/User/profile.jsp").forward(request, response);
                    }

                };

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
